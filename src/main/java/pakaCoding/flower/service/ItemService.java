package pakaCoding.flower.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pakaCoding.flower.domain.entity.Brand;
import pakaCoding.flower.domain.entity.Item;
import pakaCoding.flower.domain.entity.ItemImage;
import pakaCoding.flower.domain.entity.Type;
import pakaCoding.flower.dto.*;
import pakaCoding.flower.repository.ItemImageRepository;
import pakaCoding.flower.repository.ItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ImageService imageService;
    private final ItemImageRepository itemImageRepository;

    @Transactional
    public Long saveItem(ItemFormDto itemFormDto) throws Exception {
        Item item = null;
        log.info("ItemService에서 saveItem 실행");

        //insert
        if(itemFormDto.getId() == null){
            item = itemFormDto.toEntity();
            itemRepository.save(item);
        }
        //상품 update
        else{
            item = itemRepository.findById(itemFormDto.getId()).get();
        }

        List<MultipartFile> multipartFile = itemFormDto.getMultipartFile();
        MultipartFile thumbnails = itemFormDto.getThumbnails();

        multipartFile.add(0, thumbnails);

        //파일저장
        List<ImageDto> files = imageService.saveImageFile(multipartFile);
        List<ItemImage> fileImages = files.stream().map(ImageDto::toEntityItemImage).collect(toList());
        item.addFiles(fileImages);

        log.info("item.getId() = {}", item.getId());

        Type type = item.getType();
        type.addTypeCount();

        Brand brand = item.getBrand();
        brand.addBrandCount();

        return item.getId();
    }


    @Transactional(readOnly = true)
    public ItemFormDto getFetchItemDetail(Long itemId) {

        Item findItem = itemRepository.findAllByItemImagesAndTypeAndBrand(itemId);


        List<ImageDto> imgDtoList = new ArrayList<>();


        List<ItemImage> fileImages = findItem.getItemImages();

        fileImages.forEach(i -> imgDtoList.add(new ImageDto(i)));

        return new ItemFormDto(findItem, imgDtoList);
    }




    @Transactional(readOnly = true)
    public Page<AdminItemListDto> adminPageFindAllItems(int page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Item> adminItems = itemRepository.findAdminItems(pageable);

        return adminItems.map(f -> AdminItemListDto.builder()
                .itemName(f.getName())
                .stockQuantity(f.getStockQuantity())
                .price(f.getPrice())
                .id(f.getId())
                .build());
    }

    @Transactional(readOnly = true)
    //list에 기본 페이지 Select
    public Page<MainItemDto> findAllItems(int page){
        Pageable pageable = PageRequest.of(page, 8);
        log.info("findAllItems 시작");

        Page<MainItemDto> itemList = itemRepository.findItemListDto(pageable);
        List<MainItemDto> itemDtoList = itemList.getContent();

        return new PageImpl<>(itemDtoList, pageable, itemList.getTotalElements());
    }

    @Transactional(readOnly = true)
    //type별로 출력
    public Page<MainItemDto> findItemsType(int typeId, int page){
        Pageable pageable = PageRequest.of(page, 16);
        log.info("Item Service findItemsType 시작");
        Page<MainItemDto> itemList = itemRepository.findAllByTypeIdListDtos(typeId, pageable);
        List<MainItemDto> itemDtoList = itemList.getContent();
        return new PageImpl<>(itemDtoList, pageable, itemList.getTotalElements());
    }

    @Transactional(readOnly = true)
    public Page<MainItemDto> findItemsTypeOrgin(int typeId, int page){
        Pageable pageable = PageRequest.of(page, 16);
        log.info("Item Service findItemsTypeOrgin 시작");

        Page<Item> items = itemRepository.findAllByTypeId(typeId, pageable);


        List<MainItemDto> mainItemDtos = items.stream()
                .map(item -> {
                    MainItemDto mainItemDto = MainItemDto.builder()
                            .id(item.getId())
                            .price(item.getPrice())
                            .itemName(item.getName())
                            .build();

                    ItemImage image = itemImageRepository.findByItemIdAndRepImgYn(item.getId(), "Y");
                    mainItemDto.addImgUrl(image.getSavedFileImgName());
                    return mainItemDto;
                }).collect(toList());

        return new PageImpl<>(mainItemDtos, pageable, items.getTotalElements());
    }



    @Transactional
    public void updateItem(ItemFormDto itemFormDto) {

        log.info("updateItem 사용");
        Item findItem = itemRepository.findById(itemFormDto.getId()).orElseThrow(EntityNotFoundException::new);

        findItem.updateItem(itemFormDto);

        try {
            List<ImageDto> imageDtoList = imageService.saveUpdateImageFile(itemFormDto);
            List<ItemImage> fileImages = imageDtoList.stream().map(ImageDto::toEntityItemImage).collect(toList());
            findItem.addFiles(fileImages);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Transactional
    public void deleteItem(Long itemId) {
        log.info("delete Item Service");
        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        log.info("item.getItemName() = {}",item.getName());
        item.delete();
    }
}

package pakaCoding.flower.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pakaCoding.flower.domain.entity.BrandImage;
import pakaCoding.flower.domain.entity.Item;
import pakaCoding.flower.domain.entity.ItemImage;
import pakaCoding.flower.dto.ItemFormDto;
import pakaCoding.flower.dto.ImageDto;
import pakaCoding.flower.repository.BrandImageRepository;
import pakaCoding.flower.repository.ItemImageRepository;

import java.io.File;
import java.io.InputStream;
import java.util.*;

import static org.apache.commons.io.FileUtils.copyInputStreamToFile;
import static org.apache.commons.io.FileUtils.deleteQuietly;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ImageService {

    @Value("${upload.path}")
    private String uploadDir;

    private final ItemImageRepository itemImageRepository;

    private final BrandImageRepository brandImageRepository;
    private ImageDto file;

    //이미지 삭제
    public Item deleteImage(Long itemImageId){

        ItemImage itemImage = itemImageRepository.findById(itemImageId).orElseThrow(EntityNotFoundException::new);
        Item item = itemImage.getItem();

        itemImageRepository.delete(itemImage);

        return item;
    }

    @Transactional(readOnly = true)
    public List<ImageDto> loadBrandImage(Long brandId){
        List<BrandImage> brandImages = brandImageRepository.findByBrandId(brandId);

        List<ImageDto> imageDtoList = new ArrayList<>();
        brandImages.forEach(brandImage -> {
            ImageDto imageDto = new ImageDto(brandImage);
            imageDtoList.add(imageDto);
        });
        return imageDtoList;
    }

    //브랜드, 리뷰, 아이템 이미지 저장
    public List<ImageDto> saveImageFile(List<MultipartFile> images){

        List<ImageDto> files = new ArrayList<>();

        int count = 0;
        try{
            if(!images.get(0).getOriginalFilename().equals(""))
                for (MultipartFile file1 : images) {
                    log.info("file1 = {}", file1.getOriginalFilename());

                    String originalFilename = file1.getOriginalFilename();
                    String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                    String saveFileName = UUID.randomUUID() + extension;

                    File targetFile = new File(uploadDir + saveFileName);


                    if (count != 0) {
                        file = buildFileDto(file1, originalFilename, extension, saveFileName,  "N");
                    } else {
                        file = buildFileDto(file1, originalFilename, extension, saveFileName, "Y");
                    }

                    //List 파일 추가
                    files.add(file);

                    if (makeImageFile(file1, targetFile)) break;
                    count += 1;
                }

        }catch (Exception e){
            e.printStackTrace();
        }
        return files;

    }


    //아이템 추가 이미지 저장
    public List<ImageDto> saveUpdateImageFile(ItemFormDto itemFormDto) throws Exception{
        log.info("saveUpdateImageFile 실행");

        List<MultipartFile> multipartFileList  = itemFormDto.getMultipartFile();
        List<ImageDto> files = new ArrayList<>();
        log.info("multipartFileList ={}", multipartFileList);

        try{
            if(!multipartFileList.get(0).getOriginalFilename().equals(""))
                for (MultipartFile file1 : multipartFileList) {
                    log.info("file1 = {}", file1.getOriginalFilename());

                    String originalFilename = file1.getOriginalFilename();
                    String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                    String saveFileName = UUID.randomUUID() + extension;

                    File targetFile = new File(uploadDir + saveFileName);

                    file = buildFileDto(file1, originalFilename, extension, saveFileName,  "N");

                    //List 파일 추가
                    files.add(file);

                    if (makeImageFile(file1, targetFile)) break;
                }

        }catch (Exception e){
            e.printStackTrace();
        }
        return files;
    }

    private static boolean makeImageFile(MultipartFile file1, File targetFile) {
        try {
            InputStream fileStream = file1.getInputStream();
            copyInputStreamToFile(fileStream, targetFile); //파일저장
        } catch (Exception e) {
            deleteQuietly(targetFile); //저장된 현재 파일 삭제
            e.printStackTrace();
            return true;
        }
        return false;
    }


    private ImageDto buildFileDto(MultipartFile file1, String originFileName, String extension, String savedFileName, String repImgYn) {
        return ImageDto.builder()
                .originFileName(originFileName)
                .imgUrl(savedFileName)
                .uploadDir(uploadDir)
                .extension(extension)
                .size(file1.getSize())
                .contentType(file1.getContentType())
                .repImgYn(repImgYn)
                .build();
    }





}

package pakaCoding.flower.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pakaCoding.flower.domain.entity.Item;
import pakaCoding.flower.domain.entity.Type;
import pakaCoding.flower.dto.*;
import pakaCoding.flower.service.CartService;
import pakaCoding.flower.service.ImageService;
import pakaCoding.flower.service.ItemService;
import pakaCoding.flower.service.TypeService;

import java.security.Principal;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final TypeService typeService;
    private final CartService cartService;
    private final ImageService fileImageService;

    //상품 등록 페이지
    @GetMapping("/admin/items/create")
    public String newItem(ItemFormDto itemFormDto , Principal principal, Model model){
        isPrincipal(principal, model);
        List<Type> types = typeService.allType();

        model.addAttribute("itemFormDto", itemFormDto);
        model.addAttribute("types", types);
        return "forms/itemForm";
    }


    @GetMapping("/admin/items/{itemId}")
    public String updatePageItem(@PathVariable(name = "itemId") Long itemId ,Model model){

        ItemFormDto item = itemService.getFetchItemDetail(itemId);

        model.addAttribute("itemFormDto", item);

        return "forms/itemForm";
    }

    @PostMapping("/admin/items/{itemId}")
    public String itemUpdate(@Valid @ModelAttribute ItemFormDto itemFormDto,
                             BindingResult bindingResult,
                             Model model){
        if (bindingResult.hasErrors()) {
            log.info("사이즈 : {}", itemFormDto.getImageDtolist().size());
            return "forms/itemForm";
        }

        try{
            itemService.updateItem(itemFormDto);
        }catch (Exception e){
            model.addAttribute("errorMessage", "상품 수정 에러가 발생하였습니다");
            model.addAttribute("itemFormDto", itemFormDto);
            return "forms/itemForm";
        }
        model.addAttribute("itemFormDto", itemFormDto);

        return "redirect:/admin/items";
    }




    @DeleteMapping("/itemImage/{itemImageId}")
    @ResponseBody
    public ResponseEntity deleteImage(@PathVariable Long itemImageId){
        log.info("deleteImage 실행");
        Item item = fileImageService.deleteImage(itemImageId);
        return new ResponseEntity<Long>(item.getId(), HttpStatus.OK);
    }

    @GetMapping("/admin/items")
    public String itemManage(Model model,
                             @RequestParam(value = "page", defaultValue = "0") int page){

        Page<AdminItemListDto> items = itemService.adminPageFindAllItems(page);

        log.info("items.getTotalPages = {}", items.getTotalPages());

        model.addAttribute("maxPage", 5);
        model.addAttribute("items", items);

        pageModelPut(items, model);

        return "items/itemMng";
    }

    @PostMapping("/admin/items/create")
    public String save(@Valid ItemFormDto itemFormDto,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes, Model model) throws Exception {

        List<Type> types = typeService.allType();

        if(bindingResult.hasErrors()){
            model.addAttribute("types", types);
            return "forms/itemForm";
        }

        Long itemId;
        log.info("ItemController save 호출");
        try {
            itemId = itemService.saveItem(itemFormDto);
        }catch (Exception e){
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생했습니다.");
            return "forms/itemForm";

        }

        redirectAttributes.addAttribute("itemId", itemId);


        return "redirect:/items/{itemId}";
    }


    @GetMapping(value = {"/items", "/"})
    public String list(Principal principal,
                       Model model,
                       @RequestParam(value = "page", defaultValue = "0") int page){

        isPrincipal(principal, model);

        Page<MainItemDto> items = itemService.findAllItems(page);
        log.info("Item 전체 수 = {}", items.getTotalElements());
        List<Type> types = typeService.allType();

        log.info("items.getTotalPages = {}", items.getTotalPages());



        model.addAttribute("maxPage", 5);
        model.addAttribute("items", items);
        model.addAttribute("types", types);

        pageModelPut(items, model);

        return "items/itemList";
    }

    private <T> void pageModelPut(Page<T> results, Model model){
        model.addAttribute("totalCount", results.getTotalElements());
        model.addAttribute("size", results.getPageable().getPageSize());
        model.addAttribute("number", results.getPageable().getPageNumber());
    }

    @GetMapping("/types/{typeId}")
    public String typeIdContain(Principal principal,
                                @PathVariable int typeId,
                                Model model,
                                @RequestParam(value="page", defaultValue = "0") int page){
        isPrincipal(principal, model);

        log.info("ItemController 실행");

        Page<MainItemDto> itemsType = itemService.findItemsType(typeId, page);
        List<Type> types = typeService.allType();

        model.addAttribute("types", types);
        model.addAttribute("maxPage", 5);
        model.addAttribute("items", itemsType);
        return "items/itemList";
    }

    //CartCount 추가
    private void addCartCount(Integer count, Model model) {
        log.info("카트의 수 = {} ", count);
        model.addAttribute("cartCount", count);
    }

    //아이템 자세히 보기
    @GetMapping("/items/{itemId}")
    public String oneFlower(Principal principal,
                            @PathVariable long itemId, Model model){
        isPrincipal(principal, model);

        ItemFormDto item = itemService.getFetchItemDetail(itemId);
        List<Type> types = typeService.allType();

        model.addAttribute("types", types);
        model.addAttribute("item", item);

        return "items/itemDetail";
    }

    private void isPrincipal(Principal principal, Model model) {
        if (principal != null) {
            model.addAttribute("member", principal.getName());
            addCartCount(cartService.getCartListCount(principal.getName()), model);
        }
        else{
            model.addAttribute("cartCount", 0);
        }
    }
}

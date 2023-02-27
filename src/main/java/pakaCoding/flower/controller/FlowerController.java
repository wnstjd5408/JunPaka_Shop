package pakaCoding.flower.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestParam;
import pakaCoding.flower.domain.entity.Flower;
import pakaCoding.flower.domain.entity.Type;
import pakaCoding.flower.dto.FlowerFormDto;
import pakaCoding.flower.service.FlowerService;
import pakaCoding.flower.service.TypeService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class FlowerController {

    private final FlowerService flowerService;
    private final TypeService typeService;

    @GetMapping("/flowers/create")
    public String newFlower(Model model){
        List<Type> types = typeService.allType();

        model.addAttribute("flowerFormDto", new FlowerFormDto());
        model.addAttribute("types", types);
        return "forms/FlowerForm";
    }

    @PostMapping("/flowers/create")
    public String save(@Valid FlowerFormDto flowerFormDto, Model model, BindingResult bindingResult,
                       RedirectAttributes redirectAttributes,
                       @RequestParam(name="flowerImgFile") List<MultipartFile> flowerImgFileList) throws Exception {
        log.info("FlowerController save 호출");
        if(bindingResult.hasErrors()){
            log.info("bindingResult.error");
            return "forms/FlowerForm";
        }

        if(flowerImgFileList.get(0).isEmpty() && flowerFormDto.getId() == null){
            log.info("빈리스트 에러");
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력값입니다.");
            return "forms/FlowerForm";
        }


        try {
            Long flowerId = flowerService.saveFlower(flowerFormDto, flowerImgFileList);
            redirectAttributes.addAttribute("flowerId", flowerId);
        }catch (Exception e){
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "forms/FlowerForm";
        }

        return "redirect:/flowers/{flowerId}";
    }

    @GetMapping("/flowers")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page){

        Page<FlowerFormDto> flowers = flowerService.findAllFlowers(page);
        List<Type> types = typeService.allType();

        log.info("flower.getNumbers = {}", flowers.getTotalPages());

        model.addAttribute("maxPage", 5);
        model.addAttribute("flowers", flowers);
        model.addAttribute("types", types);

        pageModelPut(flowers, model);
        return "flowers/flowerList";
    }

    private void pageModelPut(Page<FlowerFormDto> results, Model model){
        model.addAttribute("totalCount", results.getTotalElements());
        model.addAttribute("size", results.getPageable().getPageSize());
        model.addAttribute("number", results.getPageable().getPageNumber());
    }

    @GetMapping("/types/{typeId}")
    public String typeIdContain(@PathVariable int typeId, Model model,
                                @RequestParam(value="page", defaultValue = "0") int page){
        log.info("FlowerController 실행");
        Page<FlowerFormDto> flowersType = flowerService.findFlowersType(typeId, page);
        List<Type> types = typeService.allType();

        model.addAttribute("types", types);
        model.addAttribute("maxPage", 5);
        model.addAttribute("flowers", flowersType);
        return "flowers/flowerList";
    }

    @GetMapping("/flowers/{flowerId}")
    public String oneFlower(@PathVariable long flowerId, Model model){
        Flower flower = flowerService.findOne(flowerId).get();
        List<Type> types = typeService.allType();
//        String typeName = flowerService.findTypeName(flower.getType().getId());
        String typeName = typeService.findTypeName(flower.getType().getId());

        model.addAttribute("types", types);
        model.addAttribute("flower", flower);
        model.addAttribute("typeName", typeName);

        return "flowers/flower";
    }


}

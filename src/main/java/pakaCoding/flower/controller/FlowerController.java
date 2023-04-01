package pakaCoding.flower.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pakaCoding.flower.domain.entity.FileImage;
import pakaCoding.flower.domain.entity.Flower;
import pakaCoding.flower.domain.entity.Type;
import pakaCoding.flower.dto.FlowerDetailDto;
import pakaCoding.flower.dto.FlowerFormDto;
import pakaCoding.flower.dto.MainFlowerDto;
import pakaCoding.flower.dto.MemberSessionDto;
import pakaCoding.flower.service.FileImageService;
import pakaCoding.flower.service.FlowerService;
import pakaCoding.flower.service.TypeService;

import java.security.Principal;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class FlowerController {

    private final FlowerService flowerService;
    private final FileImageService fileImageService;
    private final TypeService typeService;
    private final HttpSession session;

    @GetMapping("/flowers/create")
    public String newFlower(Principal principal, Model model){
        if (principal != null) {
            model.addAttribute("member", principal.getName());
        }
        List<Type> types = typeService.allType();

        model.addAttribute("flowerFormDto", new FlowerFormDto());
        model.addAttribute("types", types);
        return "forms/flowerForm";
    }

    @PostMapping("/flowers/create")
    public String save(@Valid FlowerFormDto flowerFormDto,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes, Model model) throws Exception {

        List<Type> types = typeService.allType();

        if(bindingResult.hasErrors()){
            model.addAttribute("types", types);
            return "forms/flowerForm";
        }

        log.info("FlowerController save 호출");
        Long flowerId = flowerService.saveFlower(flowerFormDto);

        redirectAttributes.addAttribute("flowerId", flowerId);


        return "redirect:/flowers/{flowerId}";
    }

    @GetMapping(value = {"/flowers", "/"})
    public String list(Principal principal,
                       Model model,
                       @RequestParam(value = "page", defaultValue = "0") int page){

        if (principal != null) {
            model.addAttribute("member", principal.getName());
        }

        Page<MainFlowerDto> flowers = flowerService.findAllFlowers(page);
        log.info("flower 한페이지의 수 = {}", flowers.getTotalElements());
        List<Type> types = typeService.allType();

        log.info("flower.getNumbers = {}", flowers.getTotalPages());

        model.addAttribute("maxPage", 5);
        model.addAttribute("flowers", flowers);
        model.addAttribute("types", types);

        pageModelPut(flowers, model);
        return "flowers/flowerList";
    }

    private void pageModelPut(Page<MainFlowerDto> results, Model model){
        model.addAttribute("totalCount", results.getTotalElements());
        model.addAttribute("size", results.getPageable().getPageSize());
        model.addAttribute("number", results.getPageable().getPageNumber());
    }

    @GetMapping("/types/{typeId}")
    public String typeIdContain(@SessionAttribute(name="member", required = false)MemberSessionDto member
                                ,@PathVariable int typeId,
                                Model model,
                                @RequestParam(value="page", defaultValue = "0") int page){
        if(member != null){
            model.addAttribute("member", member.getUsername());
        }
        log.info("FlowerController 실행");
        Page<MainFlowerDto> flowersType = flowerService.findFlowersType(typeId, page);
        List<Type> types = typeService.allType();

        model.addAttribute("types", types);
        model.addAttribute("maxPage", 5);
        model.addAttribute("flowers", flowersType);
        return "flowers/flowerList";
    }

    @GetMapping("/flowers/{flowerId}")
    public String oneFlower(@SessionAttribute(name="member", required = false)MemberSessionDto member,
                            @PathVariable long flowerId, Model model){
        if(member != null){
            model.addAttribute("member", member.getUsername());
        }

        FlowerDetailDto flower = flowerService.findOne(flowerId);
        List<Type> types = typeService.allType();
//        String typeName = typeService.findTypeName(flower.getType().getId());

        model.addAttribute("types", types);
        model.addAttribute("flower", flower);
//        model.addAttribute("typeName", typeName);

        return "flowers/flowerDetail";
    }


}

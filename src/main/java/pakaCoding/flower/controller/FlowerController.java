package pakaCoding.flower.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pakaCoding.flower.domain.entity.Flower;
import pakaCoding.flower.domain.entity.Type;
import pakaCoding.flower.dto.FlowerDto;
import pakaCoding.flower.service.FlowerService;
import pakaCoding.flower.service.TypeService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class FlowerController {

    private final FlowerService flowerService;
    private final TypeService typeService;

    @GetMapping("/flowers/create")
    public String newFlower(Model model){
        List<Type> types = typeService.allType();

        model.addAttribute("flowerDto", new FlowerDto());
        model.addAttribute("types", types);
        return "forms/FlowerForm";
    }

    @PostMapping("/flowers/create")
    public String save(@Valid FlowerDto flowerDto) throws Exception {

        log.info("FlowerController save 호출");
        flowerService.saveFlower(flowerDto);
        return "redirect:/";
    }

    @GetMapping("/flowers")
    public String list(Model model){

        List<Flower> flowers = flowerService.findFlowers();
        List<Type> types = typeService.allType();

        model.addAttribute("flowers", flowers);
        model.addAttribute("types", types);

        return "flowers/flowerList";
    }
    @GetMapping("/types/{typeId}")
    public String typeIdContain(@PathVariable int typeId, Model model){
        log.info("FlowerController 실행");
        List<Flower> flowersType = flowerService.findFlowersType(typeId);
        List<Type> types = typeService.allType();

        model.addAttribute("types", types);
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

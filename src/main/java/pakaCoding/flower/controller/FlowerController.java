package pakaCoding.flower.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pakaCoding.flower.domain.entity.Flower;
import pakaCoding.flower.domain.entity.Type;
import pakaCoding.flower.service.FlowerService;
import pakaCoding.flower.service.TypeService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class FlowerController {

    private final FlowerService flowerService;
    private final TypeService typeService;

    @GetMapping("/flowers")
    public String list(Model model){

        List<Flower> flowers = flowerService.findFlowers();
        List<Type> types = typeService.allType();

        model.addAttribute("flowers", flowers);
        model.addAttribute("types", types);

        return "flowers/flowerList";
    }
    @GetMapping("/types/{typeId}")
    public String typeIdContain(@PathVariable long typeId, Model model){
        List<Flower> flowersType = flowerService.findFlowersType(typeId);
        List<Type> types = typeService.allType();

        model.addAttribute("types", types);
        model.addAttribute("flower", flowersType);
        return "flowers/flowerList";
    }

    @GetMapping("/flowers/{flowerId}")
    public String oneFlower(@PathVariable long flowerId, Model model){
        Flower flower = flowerService.findOne(flowerId).get();
        model.addAttribute("flower", flower);
        return "flowers/flower";
    }


}

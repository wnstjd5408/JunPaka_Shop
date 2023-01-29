package pakaCoding.flower.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pakaCoding.flower.domain.entity.Flower;
import pakaCoding.flower.service.FlowerService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class FlowerController {

    private final FlowerService flowerService;


    @GetMapping("/flowers")
    public String list(Model model){


        List<Flower> flowers = flowerService.findFlowers();

        model.addAttribute("flowers", flowers);;
        return "flowers/flowerList";
    }

    @GetMapping("/flowers/{flowerId}")
    public String oneFlower(@PathVariable long flowerId, Model model){
        Flower flower = flowerService.findOne(flowerId).get();
        model.addAttribute("flower", flower);
        return "flowers/flower";
    }


}

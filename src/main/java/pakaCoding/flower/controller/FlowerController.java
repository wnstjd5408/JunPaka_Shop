package pakaCoding.flower.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pakaCoding.flower.domain.entity.Flower;
import pakaCoding.flower.domain.entity.Type;
import pakaCoding.flower.dto.FlowerDto;
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
    public String newFlower(){
        return "forms/FlowerForm";
    }

    @GetMapping("/flowers")
    public String list(Model model, @PageableDefault(page = 0 , size = 12) Pageable pageable){

        Page<FlowerDto> flowers = flowerService.findAllFlowers(pageable);
        List<Type> types = typeService.allType();

        log.info("flower.getNumbers = {}", flowers.getNumber());
        model.addAttribute("flowers", flowers);
        model.addAttribute("maxPage", 10);
        model.addAttribute("types", types);
        pageModelPut(flowers, model);
        return "flowers/flowerList";
    }

    private void pageModelPut(Page<FlowerDto> results, Model model){
        model.addAttribute("totalCount", results.getTotalElements());
        model.addAttribute("size", results.getPageable().getPageSize());
        model.addAttribute("number", results.getPageable().getPageNumber());
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

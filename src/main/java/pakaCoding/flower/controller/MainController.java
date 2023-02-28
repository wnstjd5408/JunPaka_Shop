package pakaCoding.flower.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import pakaCoding.flower.domain.entity.FileImage;
import pakaCoding.flower.repository.FileImageRepository;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {

    private final FileImageRepository fileImageRepository;

    @ResponseBody
    @GetMapping("/display/fileImage={saveFileName}")
    public Resource downloadImage(@PathVariable String saveFileName) throws MalformedURLException {

        FileImage fileImage = fileImageRepository.findBySavedFileImgName(saveFileName).get();

        log.info("fileImage = {}", fileImage.getSavedFileImgName());
        Path saveFilePath = Paths.get(fileImage.getUploadDir() + File.separator + fileImage.getSavedFileImgName());

        if(!saveFilePath.toFile().exists()){
            throw new RuntimeException("File not Fount");
        }
        String fullPath = fileImage.getUploadDir() + fileImage.getSavedFileImgName();
        return new UrlResource("file:" + fullPath);
    }

}

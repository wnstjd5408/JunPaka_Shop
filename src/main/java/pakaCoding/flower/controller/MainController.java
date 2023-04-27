package pakaCoding.flower.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pakaCoding.flower.domain.entity.FileImage;
import pakaCoding.flower.repository.FileImageRepository;
import pakaCoding.flower.service.FileImageService;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {

    private final FileImageService fileService;
    private String fullPath;

    //이미지 보여주기
//    @ResponseBody
//    @GetMapping("/display/fileImage={saveFileName}")
    public Resource downloadImage(@PathVariable String saveFileName) throws MalformedURLException {

        if(saveFileName.equals("noItem")){
            log.info("장바구니가 비어있습니다.");
            fullPath = "D:/file/"+ saveFileName + ".jpg";
            return new UrlResource("file:" + fullPath);
        }

        FileImage fileImage = fileService.findBySavedFileImgName(saveFileName);

        log.info("fileImage = {}", fileImage.getSavedFileImgName());
        Path saveFilePath = Paths.get(fileImage.getUploadDir() + File.separator + fileImage.getSavedFileImgName());

        if(!saveFilePath.toFile().exists()){
            throw new RuntimeException("File not Found");
        }
        fullPath = fileImage.getUploadDir() + fileImage.getSavedFileImgName();
        return new UrlResource("file:" + fullPath);
    }

    @ResponseBody
    @GetMapping("/display/fileImage={saveFileName}")
    public ResponseEntity<Resource> displayFileImage(@PathVariable String saveFileName) throws IOException {
        String path = "D:/file/";
        HttpHeaders header = new HttpHeaders();
        Resource resource = new FileSystemResource(path + saveFileName);
        if(!resource.exists())
            return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
        Path filePath = Paths.get(path+saveFileName);
        header.add("Content-type", Files.probeContentType(filePath));
        return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);

    }

}

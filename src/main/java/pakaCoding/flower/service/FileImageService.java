package pakaCoding.flower.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pakaCoding.flower.domain.entity.FileImage;
import pakaCoding.flower.repository.FileImgRepository;

import java.io.FileOutputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileImageService {


    @Value("${upload.path}")
    private String uploadDir;

    private final FileImgRepository fileImgRepository;


    //꽃 이미지 저장
    @Transactional
    public void saveFile(FileImage fileImage, MultipartFile flowerImgFile) throws Exception {
        log.info("saveFile 실행");

        String originalFilename = flowerImgFile.getOriginalFilename();

        String saveFileName = "";
        String imgUrl = "";
        String extension = "";


        try{
            if(StringUtils.hasText(originalFilename)) {
                log.info("flowerImgFile = {}", flowerImgFile.getOriginalFilename());
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                saveFileName = UUID.randomUUID() + extension;

                String filedUploadUrl = uploadDir + "/" + saveFileName;

                FileOutputStream fos = new FileOutputStream(filedUploadUrl);
                fos.write(flowerImgFile.getBytes());
                fos.close();

                imgUrl = "images/flower/" + saveFileName;
            }

            // 꽃 이미지 정보 저장
            fileImage.updateFlowerImg(originalFilename, saveFileName, imgUrl, extension, flowerImgFile.getSize(), flowerImgFile.getContentType() );
            fileImgRepository.save(fileImage);

        }catch (Exception e){
            e.printStackTrace();
        }
    }





}

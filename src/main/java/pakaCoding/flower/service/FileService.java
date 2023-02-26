package pakaCoding.flower.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pakaCoding.flower.domain.entity.File;
import pakaCoding.flower.domain.entity.Flower;
import pakaCoding.flower.dto.FlowerDto;

import java.io.InputStream;
import java.util.*;

import static org.apache.commons.io.FileUtils.copyInputStreamToFile;
import static org.apache.commons.io.FileUtils.deleteQuietly;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {


    @Value("${upload.path}")
    private String uploadDir;



    @Transactional
    public List<File> saveFile(FlowerDto flowerDto) throws Exception {
        log.info("saveFile 실행");
        List<MultipartFile> multipartFile  = flowerDto.getMultipartFile();
        List<File> files = new ArrayList<>();
        log.info("multipartFileList ={}", multipartFile);

        Flower flower = flowerDto.toEntity();

        //결과 map
        Map<String, Object> result = new HashMap<>();

        Long fileId = null;
        //파일 시퀀스 리스트
        List<Long> fileIds = new ArrayList<>();

        try{
            if(!multipartFile.isEmpty() && multipartFile != null){
                if(multipartFile.size() > 0 && !multipartFile.get(0).getOriginalFilename().equals("")){
                    for (MultipartFile file1 : multipartFile) {
                        log.info("file1 = {}", file1.getOriginalFilename());
                        String originalFilename = file1.getOriginalFilename();
                        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                        String saveFileName = UUID.randomUUID() + extension;

                        java.io.File targetFile = new java.io.File(uploadDir + saveFileName);


                        //초기값으로 fail 설정
                        result.put("result", "FAIL");


                        File file = buildFileDto(file1, originalFilename, extension, saveFileName, flower);
                        files.add(file);


                        try{
                            InputStream fileStream = file1.getInputStream();
                            copyInputStreamToFile(fileStream, targetFile); //파일저장

                            //배열에 담기
                            fileIds.add(fileId);
                            result.put("fileIdxs", fileIds.toString());
                            result.put("result", "OK");
                        }
                        catch (Exception e)
                        {
                            deleteQuietly(targetFile); //저장된 현재 파일 삭제
                            e.printStackTrace();
                            result.put("result", "FAIL");
                            break;
                        }


                    }
                }

            }
            else{
                files = Collections.emptyList();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return files;
    }

    private File buildFileDto(MultipartFile file1, String originalFilename, String extension, String saveFileName, Flower flower) {
        return File.builder()
                .originFileName(originalFilename)
                .savedFileName(saveFileName)
                .uploadDir(uploadDir)
                .extension(extension)
                .size(file1.getSize())
                .contentType(file1.getContentType())
                .flower(flower)
                .build();
    }



}

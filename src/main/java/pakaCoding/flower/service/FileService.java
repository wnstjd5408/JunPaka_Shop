package pakaCoding.flower.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pakaCoding.flower.domain.entity.FlowerFile;
import pakaCoding.flower.domain.entity.File;
import pakaCoding.flower.dto.FlowerFileDto;
import pakaCoding.flower.dto.FileDto;
import pakaCoding.flower.dto.FlowerDto;
import pakaCoding.flower.repository.FileRepository;
import pakaCoding.flower.repository.FlowerFileRepository;

import java.io.InputStream;
import java.util.*;

import static org.apache.commons.io.FileUtils.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {


    @Value("${upload.path}")
    private String uploadDir;


    private final FileRepository fileRepository;
    private final FlowerFileRepository flowerFileRepository;


    @Transactional
    public Map<String, Object> saveFile(FlowerDto flowerDto, Long flowerId) throws Exception {
        log.info("FileService에서 saveFile 실행");
        List<MultipartFile> multipartFile  = flowerDto.getMultipartFile();
        log.info("multipartFileList ={}", multipartFile);
        log.info("multipartFileList ={}", multipartFile.get(0).getOriginalFilename());


        //결과 map
        Map<String, Object> result = new HashMap<String, Object>();

        //파일 시퀀스 리스트
        List<Long> fileIds = new ArrayList<Long>();

        try{
            if(multipartFile  != null){
                if(multipartFile.size() > 0 && !multipartFile.get(0).getOriginalFilename().equals("")){
                    for (MultipartFile file1 : multipartFile) {
                        log.info("file1 = {}", file1.getOriginalFilename());
                        String originalFilename = file1.getOriginalFilename();
                        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                        String saveFileName = UUID.randomUUID() + extension;


                        java.io.File targetFile = new java.io.File(uploadDir + saveFileName);


                        //초기값으로 fail 설정
                        result.put("result", "FAIL");

                        FileDto fileDto = FileDto.builder()
                                .originFileName(originalFilename)
                                .savedFileName(saveFileName)
                                .uploadDir(uploadDir)
                                .extension(extension)
                                .size(file1.getSize())
                                .contentType(file1.getContentType())
                                .build();

                        //파일 insert
                        File file = fileDto.toEntity();
                        Long fileId = insertFile(file);
                        log.info("fileId ={}" ,fileId);


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
                        log.info("fileservice 에서 flowerId {}", flowerId);
                        FlowerFileDto flowerFileDto = FlowerFileDto.builder()
                                .flowerId(flowerId)
                                .build();

                        FlowerFile flowerFile = flowerFileDto.toEntity(file);
                        insertFlowerFile(flowerFile);

                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


    //** 파일 저장 db **//
    @Transactional
    public Long insertFile(File uploadFile){
        return fileRepository.save(uploadFile).getId();
    }

    @Transactional
    public Long insertFlowerFile(FlowerFile flowerFile){
        return flowerFileRepository.save(flowerFile).getId();
    }
}

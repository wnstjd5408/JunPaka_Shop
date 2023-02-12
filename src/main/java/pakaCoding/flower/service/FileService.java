package pakaCoding.flower.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pakaCoding.flower.domain.entity.UploadFile;
import pakaCoding.flower.dto.FileDto;
import pakaCoding.flower.dto.FlowerDto;
import pakaCoding.flower.repository.FileRepository;

import java.io.File;
import java.io.IOException;
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


    @Transactional
    public Map<String, Object> saveFile(FlowerDto flowerDto) throws IOException {

        List<MultipartFile> multipartFileList = flowerDto.getMultipartFile();


        //결과 map
        Map<String, Object> result = new HashMap<String, Object>();

        //파일 시퀀스 리스트
        List<Long> fileIds = new ArrayList<Long>();

        try{
            if(multipartFileList != null){
                if(multipartFileList.size() > 0 && !multipartFileList.get(0).getOriginalFilename().equals("")){
                    for (MultipartFile file1 : multipartFileList) {
                        String originalFilename = file1.getOriginalFilename();
                        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                        String saveFileName = UUID.randomUUID() + extension;


                        File targetFile = new File(uploadDir + saveFileName);


                        //초기값으로 fail 서렂ㅇ
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
                        Long fileId = insertFile(fileDto.toEntity());
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
    public Long insertFile(UploadFile uploadFile){
        return fileRepository.save(uploadFile).getId();
    }
}

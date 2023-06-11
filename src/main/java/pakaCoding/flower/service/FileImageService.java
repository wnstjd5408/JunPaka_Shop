package pakaCoding.flower.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pakaCoding.flower.domain.entity.Flower;
import pakaCoding.flower.domain.entity.ItemImage;
import pakaCoding.flower.domain.entity.Review;
import pakaCoding.flower.dto.FlowerFormDto;
import pakaCoding.flower.dto.ImageDto;
import pakaCoding.flower.dto.ReviewFormDto;
import pakaCoding.flower.repository.ItemImageRepository;

import java.io.File;
import java.io.InputStream;
import java.util.*;

import static org.apache.commons.io.FileUtils.copyInputStreamToFile;
import static org.apache.commons.io.FileUtils.deleteQuietly;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class FileImageService {

    @Value("${upload.path}")
    private String uploadDir;

    private final ItemImageRepository fileImageRepository;


    //이미지 삭제
    public Flower deleteImage(Long itemImageId){

        ItemImage itemImage = fileImageRepository.findById(itemImageId).orElseThrow(EntityNotFoundException::new);

        Flower flower = itemImage.getFlower();


        fileImageRepository.delete(itemImage);

        return flower;
    }


    //리뷰 이미지 저장
    public List<ImageDto> saveReviewFile(ReviewFormDto reviewFormDto){
        log.info("saveReviewFile 실행");
        Review review = reviewFormDto.toEntity();


        List<MultipartFile> multipartFileList =  reviewFormDto.getMultipartFile();
        List<ImageDto> files = new ArrayList<>();


        Map<String, Object> result = new HashMap<>();
        ImageDto file = null;
        Long fileId = null;

        List<Long> fileIds = new ArrayList<>();

        int count = 0;
        try{
            if(!multipartFileList.get(0).getOriginalFilename().equals(""))
                for (MultipartFile file1 : multipartFileList) {
                    log.info("file1 = {}", file1.getOriginalFilename());

                    String originalFilename = file1.getOriginalFilename();
                    String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                    String saveFileName = UUID.randomUUID() + extension;

                    File targetFile = new File(uploadDir + saveFileName);

                    result.put("result", "FAIL");

                    if (count != 0) {
                        file = buildFileDto(file1, originalFilename, extension, saveFileName,  "N");
                    } else {
                        file = buildFileDto(file1, originalFilename, extension, saveFileName, "Y");
                    }

                    //List 파일 추가
                    files.add(file);

                    try {
                        InputStream fileStream = file1.getInputStream();
                        copyInputStreamToFile(fileStream, targetFile); //파일저장

                        //배열에 담기
                        fileIds.add(fileId);
                        result.put("fileIdxs", fileIds.toString());
                        result.put("result", "OK");
                    } catch (Exception e) {
                        deleteQuietly(targetFile); //저장된 현재 파일 삭제
                        e.printStackTrace();
                        result.put("result", "FAIL");
                        break;
                    }

                    count += 1;
                }

        }catch (Exception e){
            e.printStackTrace();
        }
        return files;
    }



    //아이템 파일 저장
    public List<ImageDto> saveFile(FlowerFormDto flowerDto) throws Exception {
        log.info("saveFile 실행");
        Flower flower = flowerDto.toEntity();


        List<MultipartFile> multipartFileList  = flowerDto.getMultipartFile();
        List<ImageDto> files = new ArrayList<>();
        log.info("multipartFileList ={}", multipartFileList);

        MultipartFile thumbnails = flowerDto.getThumbnails();
        multipartFileList.add(0, thumbnails);

        //결과 map
        Map<String, Object> result = new HashMap<>();
        ImageDto file = null;
        Long fileId = null;
        //파일 시퀀스 리스트
        List<Long> fileIds = new ArrayList<>();
        int count = 0;
        try{
            if (!multipartFileList.get(0).getOriginalFilename().equals("")) {
                for (MultipartFile file1 : multipartFileList) {
                    log.info("file1 = {}", file1.getOriginalFilename());
                    String originalFilename = file1.getOriginalFilename();
                    String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                    String saveFileName = UUID.randomUUID() + extension;


                    java.io.File targetFile = new File(uploadDir + saveFileName);


                    //초기값으로 fail 설정
                    result.put("result", "FAIL");

                    if (count != 0) {
                        file = buildFileDto(file1, originalFilename, extension, saveFileName,  "N");
                    } else {
                        file = buildFileDto(file1, originalFilename, extension, saveFileName, "Y");
                    }

                    //List 파일 추가
                    files.add(file);

                    try {
                        InputStream fileStream = file1.getInputStream();
                        copyInputStreamToFile(fileStream, targetFile); //파일저장

                        //배열에 담기
                        fileIds.add(fileId);
                        result.put("fileIdxs", fileIds.toString());
                        result.put("result", "OK");
                    } catch (Exception e) {
                        deleteQuietly(targetFile); //저장된 현재 파일 삭제
                        e.printStackTrace();
                        result.put("result", "FAIL");
                        break;
                    }

                    count += 1;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return files;
    }



    private ImageDto buildFileDto(MultipartFile file1, String originFileName, String extension, String savedFileName, String repImgYn) {
        return ImageDto.builder()
                .originFileName(originFileName)
                .imgUrl(savedFileName)
                .uploadDir(uploadDir)
                .extension(extension)
                .size(file1.getSize())
                .contentType(file1.getContentType())
                .repImgYn(repImgYn)
                .build();
    }


}

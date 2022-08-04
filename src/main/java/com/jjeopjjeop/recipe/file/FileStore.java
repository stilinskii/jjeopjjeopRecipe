package com.jjeopjjeop.recipe.file;

import com.jjeopjjeop.recipe.dto.ImageDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {
    private String imageDir = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\media\\";



    public List<ImageDTO> storeImages(List<MultipartFile> multipartFiles,String dirName) {
        List<ImageDTO> storeImageResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()){
                storeImageResult.add(storeImage(multipartFile, dirName));
            }
        }
        return storeImageResult;
    }


    public ImageDTO storeImage(MultipartFile image, String dirName) {

        if(image==null){
            return null;
        }

        String fullPath=imageDir+dirName+"\\";
        String storeImageName = UUID.randomUUID()+"_"+image.getOriginalFilename();

        try {
            image.transferTo(new File(fullPath + storeImageName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new ImageDTO(storeImageName);
    }
}

package com.jjeopjjeop.recipe.file;

import com.jjeopjjeop.recipe.dto.ImageDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${fileStore.dir}")
    private String imageDir; //??????????????????


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

        String storePath= getStorePath(dirName);
        String storeImageName = UUID.randomUUID()+"_"+image.getOriginalFilename();

        try {
            image.transferTo(new File(getFullPath(storePath,storeImageName)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new ImageDTO(storeImageName);
    }

    public String getFullPath(String storePath,String fileName){
        return storePath + fileName;
    }

    public String getStorePath(String dirName){
        return imageDir+dirName+"\\";
    }

    public void deleteImages(List<ImageDTO> images, String dirName){
        for (ImageDTO image : images) {
            if(image!=null) {
                deleteImage(image, dirName);
            }
        }
    }

    public void deleteImage(ImageDTO imageDTO, String dirName){
        String storePath= getStorePath(dirName);
        String fullPath= getFullPath(storePath, imageDTO.getFilename());
        new File(fullPath).delete();
    }
}

package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.DirNameConst;
import com.jjeopjjeop.recipe.dao.CommunityDAO;
import com.jjeopjjeop.recipe.dto.CommunityDTO;
import com.jjeopjjeop.recipe.dto.ImageDTO;
import com.jjeopjjeop.recipe.dto.PagenationDTO;
import com.jjeopjjeop.recipe.file.FileStore;
import com.jjeopjjeop.recipe.mapper.CommunityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

import static com.jjeopjjeop.DirNameConst.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityDAO communityDAO;
    private final FileStore fileStore;

    public List<CommunityDTO> getBoard(PagenationDTO pagenationDTO){
        return communityDAO.list(pagenationDTO);
    }
    public List<CommunityDTO> getRecipeReviews(PagenationDTO pagenationDTO){
        return communityDAO.recipeReviewList(pagenationDTO);
    }

    public List<CommunityDTO> getFreeForums(PagenationDTO pagenationDTO){
        return communityDAO.freeForumList(pagenationDTO);
    }

    public void save(CommunityDTO dto){
        communityDAO.insert(dto);
    }

    public void saveImagesToDB(List<MultipartFile> multipartFiles,Integer boardId){
        List<ImageDTO> community = fileStore.storeImages(multipartFiles, COMMUNITY);
        for (ImageDTO imageDTO : community) {
            saveImageToDB(imageDTO,boardId);
        }
    }

    public void saveImageToDB(ImageDTO imageDTO,Integer boardId){
        imageDTO.setBoard_id(boardId);
        communityDAO.storeImage(imageDTO);
    }

    public CommunityDTO findPostById(Integer id){
        communityDAO.readCnt(id);
        CommunityDTO post = communityDAO.findPostById(id);
        List<ImageDTO> image = communityDAO.findImageByPostId(id);
        if(hasImage(image)){
        log.info("image={}",image.get(0).getFilename());
            post.setImages(image);
        }
        return post;
    }

    private boolean hasImage(List<ImageDTO> image) {
        return !image.isEmpty();
    }

    public int count(){
        return communityDAO.count();
    }
    public int recipeReviewCount(){ return communityDAO.recipeReviewCount();}
    public int freeForumCount(){ return communityDAO.freeForumCount();}
    public void deletePostById(int id){
        //이미지 삭제 로직
        List<ImageDTO> images = communityDAO.findImageByPostId(id);
        fileStore.deleteImages(images,COMMUNITY);

        communityDAO.deletePostById(id);
        communityDAO.deleteImageByPostId(id);
    }


}

package com.jjeopjjeop.recipe.dao;

import com.jjeopjjeop.recipe.dto.CommunityDTO;
import com.jjeopjjeop.recipe.dto.ImageDTO;
import com.jjeopjjeop.recipe.dto.PagenationDTO;
import com.jjeopjjeop.recipe.form.CommunitySearchForm;
import com.jjeopjjeop.recipe.pagenation.Pagenation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface CommunityDAO {
    int count(); // 목록 숫자 세기
    int recipeReviewCount();
    int freeForumCount();
    List<CommunityDTO> list(Pagenation pagenation);
    List<CommunityDTO> recipeReviewList(Pagenation pagenation);
    List<CommunityDTO> freeForumList(Pagenation pagenation);
    void insert(CommunityDTO communityDTO);
    CommunityDTO findPostById(int id);
    void storeImage(ImageDTO imageDTO);
    List<ImageDTO> findImageByPostId(int id);
    void addReadCnt(int id);

    void deletePostById(int id);
    void deleteImageByPostId(int id);

    void reportPostById(int id);

    void addLikeCntByPostId(int id);
    void subtractLikeCntByPostId(int id);

    void insertLikeInfo(Map<String,Object> likeInfo);

    void deleteLikeInfo(Map<String,Object> likeInfo);

    Integer checkIfUserLikedPost(Map<String,Object> likeInfo);

    void updatePost(CommunityDTO community);

    List<CommunityDTO> findCommunityBySearch(Map<String, Object> form);

    Integer countCommunityBySearch(CommunitySearchForm communitySearchForm);
}

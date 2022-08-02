package com.jjeopjjeop.recipe.dao;

import com.jjeopjjeop.recipe.dto.CommunityDTO;
import com.jjeopjjeop.recipe.dto.ImageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CommunityDAO {
    int count(); // 목록 숫자 세기
    List<CommunityDTO> list();
    void insert(CommunityDTO communityDTO);
    CommunityDTO findPostById(int id);
    void storeImage(ImageDTO imageDTO);
}

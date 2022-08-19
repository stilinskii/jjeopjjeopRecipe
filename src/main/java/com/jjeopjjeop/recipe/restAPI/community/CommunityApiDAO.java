package com.jjeopjjeop.recipe.restAPI.community;

import com.jjeopjjeop.recipe.dto.CommunityDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommunityApiDAO {

    List<CommunityDTO> findAll();
}

package com.jjeopjjeop.recipe.mapper;

import com.jjeopjjeop.recipe.dto.CommunityBoardDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommunityBoardMapper {

    @Select("select * from CommunityBoard")
    List<CommunityBoardDTO> listAll();



}

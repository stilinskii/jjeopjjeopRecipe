package com.jjeopjjeop.recipe.mapper;

import com.jjeopjjeop.recipe.dto.CommunityBoardDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommunityBoardMapper {

    @Select("select * from CommunityBoard")
    List<CommunityBoardDTO> listAll();

    @Insert("Insert into CommunityBoard values(CommunityBoard_SEQ.nextval,'jenn',0,#{dto.category},#{dto.title},#{dto.content},sysdate,sysdate,0,0,0)")
    void insert(@Param("dto") CommunityBoardDTO communityBoardDTO);




}

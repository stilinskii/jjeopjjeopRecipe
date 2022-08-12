package com.jjeopjjeop.recipe.mapper;

import com.jjeopjjeop.recipe.dto.CommunityDTO;
import org.apache.ibatis.annotations.*;


import java.util.List;

@Mapper
public interface CommunityMapper {

//    @Select("select * from CommunityBoard")
//    List<CommunityDTO> listAll();

//    @Insert("Insert into CommunityBoard values(CommunityBoard_SEQ.nextval,'jenn',0,#{dto.category},#{dto.title},#{dto.content},sysdate,sysdate,0,0,0)")
//   // @SelectKey(statement = "SELECT identity('communityboard')", keyProperty = "id", before=false, resultType = Integer.class)
//    void insert(@Param("dto") CommunityDTO community);
//   // @Options(useGeneratedKeys=true, keyColumn = "ID", keyProperty = "id")

//    @Select("SELECT CommunityBoard_SEQ.CURRVAL FROM DUAL")
//    Integer getCurrentSeq();

//    @Select("select * from CommunityBoard where id=#{id}")
//    CommunityDTO findPostById(@Param("id") Integer id);

    //이미지저장

    //이미지 불러오기

}

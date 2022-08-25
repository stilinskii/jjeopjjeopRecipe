package com.jjeopjjeop.recipe.mapper;

import com.jjeopjjeop.recipe.dto.CommunityDTO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;


import java.util.List;

@Mapper
@Repository
public interface CommunityApiMapper {

    @Select("select * from CommunityBoard ORDER BY id DESC")
    List<CommunityDTO> findAll();


    @Insert("INSERT INTO CommunityBoard VALUES(CommunityBoard_SEQ.nextval,#{dto.user_id},#{dto.rcp_seq},#{dto.category},#{dto.title},#{dto.content},sysdate,sysdate,0,0,0,0)")
    @SelectKey(statement = "SELECT CommunityBoard_SEQ.CURRVAL FROM DUAL", keyProperty = "id", before=false, resultType = Integer.class)
    void insert(@Param("dto") CommunityDTO communityDTO);
   // @Options(useGeneratedKeys=true, keyColumn = "ID", keyProperty = "id")


    @Select("select * from CommunityBoard where id=#{id}")
    CommunityDTO findCommunityPostById(@Param("id") Integer id);

    @Insert("UPDATE communityboard" +
            "        SET" +
            "        CATEGORY = #{dto.category}," +
            "        TITLE = #{dto.title,jdbcType=VARCHAR}," +
            "        CONTENT = #{dto.content,jdbcType=VARCHAR}," +
            "        rcp_seq = #{dto.rcp_seq,jdbcType=VARCHAR}," +
            "        UPDATED_AT = sysdate" +
            "        WHERE" +
            "        id = #{dto.id}")
    Integer updatePost(@Param("dto") CommunityDTO communityDTO);

    @Delete("DELETE FROM CommunityBoard WHERE id=#{id}")
    Integer deletePost(@Param("id") Integer id);


}

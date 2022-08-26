package com.jjeopjjeop.recipe.mapper;

import com.jjeopjjeop.recipe.dto.*;


import org.apache.ibatis.annotations.*;


import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;



@Mapper
public interface AdminMapper {

    //회원상세
    @Select("select user_id as user_id, password as password, usertype as usertype, username as username, nickname as nickname, email as email, phone as phone, birthday as birthday, gender as gender, postno as postno, address as address from User_j where user_id = #{user_id}")
    UserDTO detailUser(@Param("user_id") String user_id);


    //회원삭제 (ing)
    @Delete("delete from User_j where user_id = #{user_id}")
    void delUser(String user_id);


    //판매자 신청자 카운트
    @Select("select count(*) from Seller where approval = 0")
    int countNseller();


    //판매자 승인완료 카운트
    @Select("select count(*) from Seller where approval = 1")
    int countYseller();

    //판매자 승인
    @Update("update Seller set approval = 1 where user_id=#{user_id}")
    void updateSeller(String user_id);

    @Update("update User_j set usertype = 2 where user_id=#{user_id}")
    void updateUsertype(String user_id);

    //판매자 승인취소
    @Update("update Seller set approval = 0 where user_id=#{user_id}")
    void cancelSeller(String user_id);

    @Update("update User_j set usertype = 1 where user_id = #{user_id}")
    void cancelUsertype(String user_id);


    //게시판 삭제구현
    @Delete("delete from CommunityBoard where id=#{id}")
    void delComm(Integer id);


    //레시피 카운드
    @Select("select count(*) from Recipe")
    int countrcp(int cate_seq);


    //레시피글 누르면 상세내용 페이지
    @Select("select * from Recipe where rcp_name=#{rcp_name}")
    RecipeDTO rcpDetail(@Param("rcp_name") String rcp_name);

    //레시피 삭제
    @Delete("delete from Recipe where rcp_seq=#{num}")
    void delRcp(int rcp_seq);

}
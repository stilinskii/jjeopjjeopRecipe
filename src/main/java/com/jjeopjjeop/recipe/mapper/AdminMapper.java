package com.jjeopjjeop.recipe.mapper;

import com.jjeopjjeop.recipe.dto.*;


import org.apache.ibatis.annotations.*;

import com.jjeopjjeop.recipe.dto.CommunityDTO;
import com.jjeopjjeop.recipe.dto.SellerDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


import java.util.List;

@Mapper
public interface AdminMapper {

    //회원카운트
//    @Select("select count(*) from User_j")
//    int countUser();

    //회원목록 불러오기 (end)
//    @Select("select * from User_j")
//    List<A_userDTO> listUser_j(A_criteria cri);

    //회원상세
    @Select("select user_id as user_id, password as password, usertype as usertype, username as username, nickname as nickname, email as email, phone as phone, birthday as birthday, gender as gender, postno as postno, address as address from User_j where user_id = #{user_id}")
    UserDTO detailUser(@Param("user_id") String user_id);

    //회원수정(수정은 불가하게)
//    @Update("update User_j set password = #{password}, usertype = #{usertype}, username = #{username}, nickname = #{nickname}, email = #{email}, phone = #{phone}, birthday = #{birthday}, gender = #{gender}, postno = #{postno}, address = #{address} where user_id=#{user_id}")
//    void updateU(String user_id);

    //회원삭제 (ing)
    @Delete("delete form User_j where user_id = #{user_id}")
    void delUser(String user_id);


    //판매자 신청자 카운트
    @Select("select count(*) from Seller where approval = 0")
    int countNseller();

    //판매자 신청자 목록
//    @Select("select * from Seller where approval = 0")
//    List<SellerDTO> listNSeller();

    //판매자 승인완료 카운트
    @Select("select count(*) from Seller where approval = 1")
    int countYseller();

    //판매자 승인완료 목록
//    @Select("select * from Seller where approval = 1")
//    List<SellerDTO> listSeller();

    //판매자 삭제 판매자 등록 취소?
//    @Update("update Seller set approval = 0 where user_id=#{user_id}")


    //판매자 승인
    //https://github.com/search?l=Java&q=%ED%8C%90%EB%A7%A4%EC%9E%90+%EC%8A%B9%EC%9D%B8&type=Code
    @Update("update Seller set approval = 1 where user_id=#{user_id}")
    void updateSeller(String user_id);

    @Update("update User_j set usertype = 2 where user_id=#{user_id}")
    void updateUsertype(String user_id);

    //판매자 승인취소
    @Update("update Seller set approval = 0 where user_id=#{user_id}")
    void cancelSeller(String user_id);

    @Update("update User_j set usertype = 1 where user_id = #{user_id}")
    void cancelUsertype(String user_id);

    //게시판 신고순으로 게시판 목록 조회
//    @Select("select * from CommunityBoard order by report desc")
//    List<CommunityDTO> approCommunity();

    //게시판 삭제구현
    @Delete("delete from CommunityBoard where id=#{id}")
    void delComm(Integer id);


    //레시피 신고순으로 목록 조회
//    @Select("select * from Recipe order by report desc")
//    List<RecipeDTO> approRecipe();

    //레시피 카운드
    @Select("select count(*) from Recipe")
    int countrcp(int cate_seq);

    //신고 카운트 ?


    //레시피글 누르면 상세내용 페이지
    @Select("select * from Recipe where rcp_name=#{rcp_name}")
    RecipeDTO rcpDetail(@Param("rcp_name") String rcp_name);

    //레시피 삭제
    @Delete("delete from Recipe where user_id=#{user_id}")
    void delRcp(String user_id);

}
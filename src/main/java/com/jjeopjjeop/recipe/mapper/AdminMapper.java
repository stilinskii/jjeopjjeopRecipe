package com.jjeopjjeop.recipe.mapper;

import com.jjeopjjeop.recipe.dto.A_userDTO;

import com.jjeopjjeop.recipe.dto.Community;
import com.jjeopjjeop.recipe.dto.SellerDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AdminMapper {

    //회원목록 불러오기 (end)
    @Select("select * from User_j")
    List<A_userDTO> listUser_j();

    //회원삭제 (ing)
//    @Delete("delete form User_j where user_id = #{user_id}")

    //판매자 목록 불러오기(sellerMapper)

    //판매자 삭제 판매자 등록 취소?
//    @Update("update Seller set approval = 0 where user_id=#{user_id}")


    //판매자 승인
    //https://github.com/search?l=Java&q=%ED%8C%90%EB%A7%A4%EC%9E%90+%EC%8A%B9%EC%9D%B8&type=Code
//    @Update("update Seller set approval = 1 where user_id=#{user_id}")
//    void updateSeller(String user_id);

    //게시판 신고순으로 게시판 목록 조회
//    @Select("select * from CommunityBoard order by report desc")
//    List<Community> approCommunity();

    //게시판 삭제구현
//    @Delete("delete from CommunityBoard where id=#{id}")
//    void delComm(Integer id);


    //레시피 신고순으로 목록 조회
    //@Select("select * from Recipe order by report desc")

    //레시피 삭제
    //@Delete("delete from Recipe where user_id=#{user_id}")

}
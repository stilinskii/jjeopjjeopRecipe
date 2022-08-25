package com.jjeopjjeop.recipe.dao;


import com.jjeopjjeop.recipe.dto.*;
import com.jjeopjjeop.recipe.pagenation.Pagenation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AdminDAO {

    //회원 카운트
    //public int countUser(Pagenation pagenation);
    public int countUser();

    //회원 리스트
    public List<UserDTO> userList(Pagenation pagenation);
    //public List<UserDTO> UserList(A_criteria cri);

    //레시피 신고순 리스트
    public List<RecipeDTO> rcpList(Pagenation pagenation);

    //미승인 판매자 리스트
    public List<SellerDTO> nSellerList(Pagenation pagenation);

    //승인 판매자 리스트
    public List<SellerDTO> ySellerList(Pagenation pagenation);

    //게시판 신고순 리스트
    public List<CommunityDTO> reportCom(Pagenation pagenation);



}

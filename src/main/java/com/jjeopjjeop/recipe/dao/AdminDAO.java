package com.jjeopjjeop.recipe.dao;



import com.jjeopjjeop.recipe.dto.*;

import com.jjeopjjeop.recipe.dto.A_criteria;
import com.jjeopjjeop.recipe.dto.AdminDTO;
import com.jjeopjjeop.recipe.dto.PageDTO;
import com.jjeopjjeop.recipe.dto.UserDTO;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AdminDAO {


    //회원 카운트
    public int countUser(A_criteria cri);

    //회원 리스트
    public List<UserDTO> UserList(A_criteria cri);
//    public List<UserDTO> UserList(A_criteria cri);

    public int countUser();

    public List<UserDTO> UserList(PageDTO pageDTO);


//    public List<AdminDTO> userList() throws DataAccessException;
//
//    public int insertUser (AdminDTO adminDTO) throws DataAccessException;


    //레시피 신고순 리스트
    public List<RecipeDTO> rcpList(RecipePageDTO recipePageDTO);


    //미승인 판매자 리스트
    public List<SellerDTO> nSellerList(A_criteria cri);

    //승인 판매자 리스트
    public List<SellerDTO> ySellerList(A_criteria cri);




}

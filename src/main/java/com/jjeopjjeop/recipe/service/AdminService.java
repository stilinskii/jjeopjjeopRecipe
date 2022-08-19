package com.jjeopjjeop.recipe.service;



import com.jjeopjjeop.recipe.dao.AdminDAO;
import com.jjeopjjeop.recipe.dto.*;

import com.jjeopjjeop.recipe.dto.A_userDTO;
import com.jjeopjjeop.recipe.dto.AdminDTO;
import com.jjeopjjeop.recipe.dto.CommunityDTO;
import com.jjeopjjeop.recipe.dto.SellerDTO;

import com.jjeopjjeop.recipe.mapper.AdminMapper;
import com.jjeopjjeop.recipe.mapper.CommunityMapper;
import com.jjeopjjeop.recipe.mapper.SellerMapper;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminMapper adminMapper;
    private final SellerMapper sellerMapper;

    private final AdminDAO adminDAO;






    //public int countProcess();


    //회원 카운트
    public int countUser(){
        return adminDAO.countUser();
    }

    //회원리스트 가져오기
//    public List<A_userDTO> listUser(A_criteria cri){
//        return adminMapper.listUser_j(cri);
//    }// adminmapper
    public List<UserDTO> UserList(PageDTO pageDTO){
        return adminDAO.UserList(pageDTO);
    }

    //회원상세
    public UserDTO detailUser(String user_id){
       return adminMapper.detailUser(user_id);
    }

    //회원삭제
    public void delUser(String user_id){
        adminMapper.delUser(user_id);
    }

    //미승인 판매자리스트
    public List<SellerDTO> listNSeller(){
        return adminMapper.listNSeller();
    }

    //승인 판매자리스트
    public  List<SellerDTO> listSeller(){
        return adminMapper.listSeller();
    }


    //판매자 승인 update
    public void approSeller(String user_id){
        adminMapper.updateSeller(user_id);
        adminMapper.updateUsertype(user_id);
    }

    //신고순 레시피 리스트
    public List<RecipeDTO> apprcpList(){
        return adminMapper.approRecipe();
    }

    //레시피 삭제
    public void delRcp(String user_id){
        adminMapper.delRcp(user_id);
    }


    //게시판 신고순으로 게시판 목록 조회
    public List<CommunityDTO> appcomList(){
        return adminMapper.approCommunity();
    }

    //게시판 삭제
    public void delcomm(Integer id){
        adminMapper.delComm(id);
    }




}

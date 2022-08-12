package com.jjeopjjeop.recipe.service;


import com.jjeopjjeop.recipe.dto.A_userDTO;
import com.jjeopjjeop.recipe.dto.AdminDTO;
import com.jjeopjjeop.recipe.dto.CommunityDTO;
import com.jjeopjjeop.recipe.dto.SellerDTO;
import com.jjeopjjeop.recipe.mapper.AdminMapper;
import com.jjeopjjeop.recipe.mapper.CommunityMapper;
import com.jjeopjjeop.recipe.mapper.SellerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminMapper adminMapper;
    private final SellerMapper sellerMapper;


    //public int countProcess();

    //회원리스트 가져오기
    public List<A_userDTO> listUser(){
        return adminMapper.listUser_j();
    }

    //판매자리스트

    //판매자 승인
//    public void approSeller(SellerDTO sellerDTO, String user_id){
//        adminMapper.updateSeller(user_id);
//    }

    //게시판 신고순으로 게시판 목록 조회
//    public List<Community> appcomList(){
//        return adminMapper.approCommunity();
//    }

    //게시판 삭제
//    public void delcomm(Integer id){
//        adminMapper.delComm(id);
//    }




}

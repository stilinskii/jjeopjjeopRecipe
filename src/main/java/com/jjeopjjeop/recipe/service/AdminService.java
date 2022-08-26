package com.jjeopjjeop.recipe.service;



import com.jjeopjjeop.recipe.dao.AdminDAO;
import com.jjeopjjeop.recipe.dto.*;

import com.jjeopjjeop.recipe.dto.CommunityDTO;
import com.jjeopjjeop.recipe.dto.SellerDTO;

import com.jjeopjjeop.recipe.mapper.AdminMapper;
import com.jjeopjjeop.recipe.pagenation.Pagenation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminMapper adminMapper;

    private final AdminDAO adminDAO;



    //회원 카운트
    public int countUser(Pagenation pagenation){
        return adminDAO.countUser(pagenation);
    }

    public List<UserDTO> userList(Pagenation pagenation){
        return adminDAO.userList(pagenation);
    }


    //회원상세
    public UserDTO detailUser(String user_id){
        return adminMapper.detailUser(user_id);
    }

    //회원삭제
    public void delUser(String user_id){
        adminMapper.delUser(user_id);
    }

    //회원 업데이트
    public void updateUser(String user_id){
        adminMapper.updateUser(user_id);
    }

    //미승인 판매자 카운트
    public int countNseller(){
        return adminMapper.countNseller();
    }


    //미승인 판매자 리스트
    public List<SellerDTO> nSellerList(Pagenation pagenation){
        return adminDAO.nSellerList(pagenation);
    }

    //승인 판매자 카운트
    public int countYseller(){
        return adminMapper.countYseller();
    }

    //승인 판매자리스트
    public List<SellerDTO> ySellerList(Pagenation pagenation){
        return adminDAO.ySellerList(pagenation);
    }


    //판매자 승인 update
    public void approSeller(String user_id){
        adminMapper.updateSeller(user_id);
        adminMapper.updateUsertype(user_id);
    }

    //판매자 승인취소
    public void cancelSeller(String user_id){
        adminMapper.cancelSeller(user_id);
        adminMapper.cancelUsertype(user_id);
    }


    //레시피 리스트(신고순)
    public List<RecipeDTO> rcpList(Pagenation pagenation, int rcp_sort, int cate_seq){
        return adminDAO.rcpList(Map.of("page",pagenation,"rcp_sort",rcp_sort,"cate_seq",cate_seq));
    }

    //레시피 카운트
    public int countrcp(int cate_seq){
        return adminMapper.countrcp(cate_seq);
    }


    //레시피 삭제
    public void delRcp(int rcp_seq){
        adminMapper.delRcp(rcp_seq);
    }

    //게시판 목록 조회(신고순)
    public List<CommunityDTO> reportCom(Pagenation pagenation){
        return adminDAO.reportCom(pagenation);
    }

    //게시판 삭제
    public void delcomm(Integer id){
        adminMapper.delComm(id);
    }




}

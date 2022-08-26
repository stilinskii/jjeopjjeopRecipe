package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dto.PagenationDTO;
import com.jjeopjjeop.recipe.dto.PayDTO;
import com.jjeopjjeop.recipe.dto.ProduceDTO;
import com.jjeopjjeop.recipe.dto.RecipePageDTO;
import com.jjeopjjeop.recipe.pagenation.Pagenation;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PayService {
    public void cartWriteProcess(PayDTO payDTO);

    public List<ProduceDTO> cartView(Pagenation pagenation);//마이페이지 들어가서 장바구니 보기

    public List<ProduceDTO> payView(Pagenation pagenation);//마이페이지 들어가서 구매내역 보기

    public void cartDelete(int pay_num); //장바구니 삭제

    public ProduceDTO payInfo(int pay_num); //결제에 필요한 정보 담기.

    public void cartUpdate(int pay_num); //결제후 pay를 1로 바꿔주기.

    public int cartCount(HttpServletRequest request); //페이지 처리를 위해 장바구니 항목 개수 세기. 

    public int payCount(HttpServletRequest request); //페이지 처리를 위해 구매내역 항목 개수 세기.

    public int cartSelect(String user_id); //판매글에서 바로 결제할 때 쓰는것. 가장 최근에 장바구니에 들어간 항목의 pay_num 부르기
}

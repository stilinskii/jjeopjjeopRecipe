package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dao.PayDAO;
import com.jjeopjjeop.recipe.dto.PayDTO;
import com.jjeopjjeop.recipe.dto.ProduceDTO;
import com.jjeopjjeop.recipe.dto.RecipePageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayServiceImp implements PayService{

    @Autowired
    private PayDAO payDAO;

    public PayServiceImp() {

    }

    //장바구니에 넣기
    @Override
    public void cartWriteProcess(PayDTO payDTO) {
        payDAO.cartWrite(payDTO);
    }

    //마이페이지 들어가서 장바구니 보기
    @Override
    public List<ProduceDTO> cartView(RecipePageDTO recipePageDTO) {
        return payDAO.cartView(recipePageDTO);
    }

    //장바구니 항목 삭제
    @Override
    public void cartDelete(int pay_num) {
        payDAO.cartDelete(pay_num);
    }

    //결제에 필요한 정보 담기.
    @Override
    public ProduceDTO payInfo(int pay_num) {

        return payDAO.payInfo(pay_num);
    }

    //결제후 pay를 1로 바꿔주기.
    @Override
    public void cartUpdate(int pay_num) {
        payDAO.cartUpdate(pay_num);
    }

    //페이지 처리를 위해 장바구니 항목 개수 세기. 나중에 id부분변경
    @Override
    public int cartCount() {
        return payDAO.cartCount();
    }


}

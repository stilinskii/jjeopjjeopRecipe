package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dao.PayDAO;
import com.jjeopjjeop.recipe.dto.PagenationDTO;
import com.jjeopjjeop.recipe.dto.PayDTO;
import com.jjeopjjeop.recipe.dto.ProduceDTO;
import com.jjeopjjeop.recipe.dto.RecipePageDTO;
import com.jjeopjjeop.recipe.pagenation.Pagenation;
import org.apache.catalina.session.StandardSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

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
    public List<ProduceDTO> cartView(Map<String, Object> map) {
        return payDAO.cartView(map);
    }

    //마이페이지 들어가서 구매내역 보기
    @Override
    public List<ProduceDTO> payView(Map<String, Object> map) {
        return payDAO.payView(map);
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

    //페이지 처리를 위해 각 회원의 장바구니 항목 개수 세기. 나중에 id부분변경 ->0816 변경함.
    @Override
    public int cartCount(String user_id) {
        int count = payDAO.cartCount("user_id");  //왜 0이나오는걸까??????????
        System.out.println("~~~~~~~~~~~~");
        System.out.println(count);
        System.out.println("~~~~~~~~~~~~");

        return count;
    }

    //페이지 처리를 위해 각 회원의 구매내역 항목 개수 세기.
    @Override
    public int payCount(String user_id) {
        return payDAO.payCount("user_id");
    }

    //판매글에서 바로 결제할 때 쓰는것. 가장 최근에 장바구니에 들어간 항목의 pay_num 부르기
    @Override
    public int cartSelect(String user_id) {
        return payDAO.cartSelect(user_id);
    }


}

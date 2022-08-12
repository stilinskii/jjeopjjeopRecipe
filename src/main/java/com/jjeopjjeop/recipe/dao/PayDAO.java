package com.jjeopjjeop.recipe.dao;

import com.jjeopjjeop.recipe.dto.PayDTO;
import com.jjeopjjeop.recipe.dto.ProduceDTO;
import com.jjeopjjeop.recipe.dto.RecipePageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PayDAO {
   public void cartWrite(PayDTO payDTO); //장바구니에 넣기

   public List<ProduceDTO> cartView(RecipePageDTO recipePageDTO);  //마이페이지 들어가서 장바구니 보기

   public void cartDelete(int pay_num); //장바구니 삭제

   public ProduceDTO payInfo(int pay_num); //결제에 필요한 정보 담기.

   public void cartUpdate(int pay_num); //결제후 pay를 1로 바꿔주기.

   public int cartCount(); //페이지 처리를 위해 장바구니 항목 개수 세기. 나중에 id부분변경
}

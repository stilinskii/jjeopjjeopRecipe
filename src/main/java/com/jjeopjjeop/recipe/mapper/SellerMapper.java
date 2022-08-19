package com.jjeopjjeop.recipe.mapper;

import com.jjeopjjeop.recipe.dto.SellerDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SellerMapper {

    @Select("select * from Seller")
    List<SellerDTO> listSeller();

    //(user_id, business_name, registration_number, seller_phone, seller_address)
    //pk문제로 값이 안 넘어가는 것 같음....(user_id)-> 값을 넣으니 들어감

    @Insert("insert into Seller values(#{seller.user_id}, #{seller.business_name}, #{seller.registration_number}, #{seller.seller_phone}, #{seller.seller_address},'0')")

    @Insert("insert into Seller values('hy', #{seller.business_name}, #{seller.registration_number}, #{seller.seller_phone}, #{seller.seller_address},'0')")

     void insert(@Param("seller") SellerDTO SellerDTO);

}

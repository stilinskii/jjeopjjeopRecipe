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

    @Insert("insert into Seller values(#{seller.user_id}, #{seller.business_name}, #{seller.registration_number}, #{seller.seller_phone}, #{seller.seller_address},'0')")
    void insert(@Param("seller") SellerDTO SellerDTO);


    @Select("select count(*) from Seller where user_id=#{user_id}")
    Integer findSellerById(@Param("user_id") String user_id);

}

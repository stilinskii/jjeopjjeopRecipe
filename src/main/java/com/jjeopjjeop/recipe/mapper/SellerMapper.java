package com.jjeopjjeop.recipe.mapper;

import com.jjeopjjeop.recipe.dto.SellerDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SellerMapper {

    @Select("select * from Seller")
    List<SellerDTO> listSeller();
}

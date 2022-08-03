package com.jjeopjjeop.recipe.dao;

import com.jjeopjjeop.recipe.dto.PayDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface PayDAO {
   public void cartWrite(PayDTO payDTO);
}

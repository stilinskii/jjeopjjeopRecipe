package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dto.SellerDTO;
import com.jjeopjjeop.recipe.mapper.SellerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerService {
    private final SellerMapper sellerMapper;

    public List<SellerDTO> getSeller(){
        return sellerMapper.listSeller();
    }
    //insert
    public void save(SellerDTO seller) {
        sellerMapper.insert(seller);
    }

    public Integer findSellerById(String user_id) {
        return sellerMapper.findSellerById(user_id);
    }
}

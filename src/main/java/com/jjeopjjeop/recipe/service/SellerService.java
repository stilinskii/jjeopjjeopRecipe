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
    //    public List<SellerDTO> listSeller() throws Exception;
//    public int insertSeller(SellerDTO sellerDTO) throws Exception;
    public List<SellerDTO> getSeller(){
        return sellerMapper.listSeller();
    }
    //insert
    public void save(SellerDTO seller) {
        sellerMapper.insert(seller);
    }

}

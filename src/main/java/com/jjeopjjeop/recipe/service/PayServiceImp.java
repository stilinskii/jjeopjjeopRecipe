package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dao.PayDAO;
import com.jjeopjjeop.recipe.dto.PayDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImp implements PayService{

    @Autowired
    private PayDAO payDAO;

    public PayServiceImp() {

    }

    @Override
    public void cartWriteProcess(PayDTO payDTO) {
        payDAO.cartWrite(payDTO);
    }
}

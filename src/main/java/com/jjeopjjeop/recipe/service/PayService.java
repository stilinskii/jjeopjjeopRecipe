package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dto.PayDTO;
import org.springframework.stereotype.Service;

public interface PayService {
    public void cartWriteProcess(PayDTO payDto);
}

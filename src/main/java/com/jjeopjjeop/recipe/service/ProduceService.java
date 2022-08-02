package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dto.ProduceDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProduceService {
    void writeProcess(ProduceDTO produceDto, MultipartFile file) throws Exception;

    List<ProduceDTO> produceListProcess();

    List<ProduceDTO> produceListTypeProcess(int type);

    void produceDeleteProcess(int produce_num);

    ProduceDTO produceViewProcess(int produce_num);

    void produceUpdateProcess(ProduceDTO produceDto);
}
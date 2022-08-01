package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dto.ProduceDTO;
import java.util.List;

public interface ProduceService {
    void writeProcess(ProduceDTO dto);

    List<ProduceDTO> produceListProcess();

    List<ProduceDTO> produceListTypeProcess(int type);

    void produceDeleteProcess(int produce_num);

    ProduceDTO produceViewProcess(int produce_num);

    void produceUpdateProcess(ProduceDTO dto);
}
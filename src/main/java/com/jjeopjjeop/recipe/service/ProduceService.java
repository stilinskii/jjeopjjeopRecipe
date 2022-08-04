package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dto.PageDTO;
import com.jjeopjjeop.recipe.dto.ProduceDTO;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.util.List;

public interface ProduceService {
    void writeProcess(ProduceDTO produceDTO, MultipartFile file) throws Exception;

    List<ProduceDTO> produceListProcess(PageDTO pageDTO);

    List<ProduceDTO> produceListTypeProcess(int type);

    void produceDeleteProcess(int produce_num);

    ProduceDTO produceViewProcess(int produce_num);

    void produceUpdateProcess(ProduceDTO produceDTO);

    int produceCountProcess(); //페이지 처리를 위해 판매글 개수 세기
}
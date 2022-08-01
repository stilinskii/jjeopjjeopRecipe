
package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dao.ProduceDAO;
import com.jjeopjjeop.recipe.dto.ProduceDTO;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class ProduceServiceImp implements ProduceService {
    @Autowired
    private ProduceDAO dao;

    public ProduceServiceImp() {
    }

    public void writeProcess(ProduceDTO dto) {
        dao.write(dto);
    }

    public List<ProduceDTO> produceListProcess() {
        return dao.produceList();
    }

    public List<ProduceDTO> produceListTypeProcess(int type) {
        return dao.produceListType(type);
    }

    public void produceDeleteProcess(int produce_num) {
        dao.produceDelete(produce_num);
    }

    public ProduceDTO produceViewProcess(int produce_num) {
        return dao.produceView(produce_num);
    }

    public void produceUpdateProcess(ProduceDTO dto) {
        dao.produceUpdate(dto);
    }
}
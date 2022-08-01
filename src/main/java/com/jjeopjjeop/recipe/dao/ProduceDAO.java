package com.jjeopjjeop.recipe.dao;

import com.jjeopjjeop.recipe.dto.ProduceDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ProduceDAO {
    void write(ProduceDTO dto);

    List<ProduceDTO> produceList();

    List<ProduceDTO> produceListType(int type);

    void produceDelete(int produce_num);

    ProduceDTO produceView(int produce_num);

    void produceUpdate(ProduceDTO dto);
}

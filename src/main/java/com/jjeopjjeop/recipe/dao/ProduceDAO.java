package com.jjeopjjeop.recipe.dao;

import com.jjeopjjeop.recipe.dto.PayDTO;
import com.jjeopjjeop.recipe.dto.ProduceDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ProduceDAO {
    public void write(ProduceDTO produceDTO);

    public List<ProduceDTO> produceList();

    public List<ProduceDTO> produceListType(int type);

    public void produceDelete(int produce_num);

    public ProduceDTO produceView(int produce_num);

    public void produceUpdate(ProduceDTO produceDto);

    public int produceCount();


}

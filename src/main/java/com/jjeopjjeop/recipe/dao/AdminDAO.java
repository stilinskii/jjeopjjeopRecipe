package com.jjeopjjeop.recipe.dao;


import com.jjeopjjeop.recipe.dto.AdminDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AdminDAO {

    public int count();

    public List<AdminDTO> userList() throws DataAccessException;

    public int insertUser (AdminDTO adminDTO) throws DataAccessException;



}

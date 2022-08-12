package com.jjeopjjeop.recipe.dao;


import com.jjeopjjeop.recipe.dto.A_criteria;
import com.jjeopjjeop.recipe.dto.AdminDTO;
import com.jjeopjjeop.recipe.dto.PageDTO;
import com.jjeopjjeop.recipe.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AdminDAO {

    public int countUser();

    public List<UserDTO> UserList(PageDTO pageDTO);

//    public List<AdminDTO> userList() throws DataAccessException;
//
//    public int insertUser (AdminDTO adminDTO) throws DataAccessException;



}

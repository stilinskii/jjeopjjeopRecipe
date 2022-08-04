package com.jjeopjjeop.recipe.dao;

import com.jjeopjjeop.recipe.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserDAO {
   public List<UserDTO> selectAllUserList() throws DataAccessException;
   public int insertUser(UserDTO userDTO) throws DataAccessException;
   public int deleteUser(String user_id);
   public UserDTO loginById(UserDTO userDTO) throws DataAccessException;

   public String findId(String username, String email) throws DataAccessException;

   public UserDTO findPassword(UserDTO userDTO) throws DataAccessException;
   public int updatePassword(UserDTO userDTO) throws DataAccessException;
}

package com.jjeopjjeop.recipe.service;


import com.jjeopjjeop.recipe.dto.AdminDTO;

import java.util.List;

public interface AdminService {

    public int countProcess();

    public List<AdminDTO> listUser() throws Exception;

    public int addUser(AdminDTO adminDTO) throws Exception;

}

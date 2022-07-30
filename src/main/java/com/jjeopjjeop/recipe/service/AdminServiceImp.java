package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dao.AdminDAO;
import com.jjeopjjeop.recipe.dto.AdminDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImp implements AdminService{


    @Autowired
    private AdminDAO adminDao;

    public AdminServiceImp(AdminDAO adminDao) {

    }

    @Override
    public int countProcess() {
        return adminDao.count();
    }

    @Override
    public List<AdminDTO> listUser() throws Exception {
        return adminDao.userList();
    }

    @Override
    public int addUser(AdminDTO adminDTO) throws Exception {
        return adminDao.insertUser(adminDTO);
    }


}

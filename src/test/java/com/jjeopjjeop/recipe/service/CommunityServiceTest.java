package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dao.CommunityDAO;
import com.jjeopjjeop.recipe.dto.CommunityDTO;
import com.jjeopjjeop.recipe.file.FileStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@Transactional
class CommunityServiceTest {

    @Autowired
    CommunityDAO communityDAO;
    @Mock
    FileStore fileStore;

   // CommunityService communityService = new CommunityService(communityDAO,fileStore);




//    @Test
//    void name() {
//        //when
//        communityService.getBoard();
//        //then
//        verify(communityDAO).list();
//    }
}
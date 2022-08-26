package com.jjeopjjeop.recipe.restAPI.community;

import com.jjeopjjeop.recipe.mapper.CommunityApiMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CommunityApiController.class)
class CommunityApiControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    CommunityApiMapper apiMapper;

    @Test
    void findAllCommunityBoard() throws Exception {
//        List<CommunityDTO> communityList = new ArrayList<>();
//        communityList.add(new CommunityDTO("lhy98410","0",0,"title","content"));
//
//        given(apiMapper.findAll()).willReturn(communityList);
//
//        mvc.perform(get("/api/community.all"))
//                .andExpect(status().isOk());

    }

    @Test
    void findCommunityPostById() {
    }

    @Test
    void saveCommunityPost() {
    }

    @Test
    void updateCommunityPost() {
    }

    @Test
    void deleteCommunityPost() {
    }
}
package com.jjeopjjeop.recipe.controller;

import com.jjeopjjeop.recipe.service.CommunityService;
import com.jjeopjjeop.recipe.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

//@ExtendWith(MockitoExtension.class)
@WebMvcTest(CommunityController.class)
class CommunityControllerTest {

    @Mock
    CommunityService communityService;
    @Mock
    RecipeService recipeService;

    @InjectMocks
    CommunityController underTest;
    @Autowired
    MockMvc mvc;
    @Test
    void all() throws Exception {
        //given
//        Pagenation pagenation = new Pagenation(0,10,communityService.count());
//        List<CommunityDTO> mockedList = mock(List.class);
//        given(communityService.getBoard(pagenation)).willReturn(mockedList);
//
//        mvc.perform(get("/community"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("community/index"));

    }

    @Test
    void recipeReview() {
    }

    @Test
    void freeForum() {
    }

    @Test
    void form() {
    }

    @Test
    void forFormSubmit() {
    }

    @Test
    void searchKeyword() {
    }

    @Test
    void post() {
    }

    @Test
    void editPostById() {
    }

    @Test
    void editPostByIdSubmit() {
    }

    @Test
    void deletePostById() {
    }

    @Test
    void reportPostById() {
    }

    @Test
    void updateLikeCnt() {
    }

    @Test
    void submitComment() {
    }

    @Test
    void editComment() {
    }

    @Test
    void deleteComment() {
    }

    @Test
    void reportComment() {
    }

    @Test
    void detailSearch() {
    }

    @Test
    void detailSearchSubmit() {
    }
}
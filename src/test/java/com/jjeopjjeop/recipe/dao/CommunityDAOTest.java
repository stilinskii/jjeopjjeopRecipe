package com.jjeopjjeop.recipe.dao;

import com.jjeopjjeop.recipe.dto.CommunityDTO;
import com.jjeopjjeop.recipe.pagenation.Pagenation;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommunityDAOTest {

    @Autowired
    private CommunityDAO dao;

    @Test
    void count() {
        //given
        int count = dao.count();
        dao.insert(new CommunityDTO("lhy98410","0",0,"hi","dfdf"));
        //when
        int countUpdated = dao.count();
        //then
        assertThat(countUpdated-count).isEqualTo(1);
    }

    @Test
    void recipeReviewCount() {
        //given
        int count = dao.count();
        dao.insert(new CommunityDTO("lhy98410","1",0,"hi","dfdf"));
        //when
        int countUpdated = dao.count();
        //then
        assertThat(countUpdated-count).isEqualTo(1);
    }

    @Test
    void freeForumCount() {
        //given
        int count = dao.count();
        dao.insert(new CommunityDTO("lhy98410","0",0,"hi","dfdf"));
        //when
        int countUpdated = dao.count();
        //then
        assertThat(countUpdated-count).isEqualTo(1);

    }

    @Test
    void list() {
        //given
        Pagenation pagenation = new Pagenation(1,4,dao.count());
        //when
        List<CommunityDTO> list = dao.list(pagenation);
        //then
        assertThat(list.size()).isLessThanOrEqualTo(4);

    }

}
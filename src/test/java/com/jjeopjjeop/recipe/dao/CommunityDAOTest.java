package com.jjeopjjeop.recipe.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class CommunityDAOTest {

    @Autowired
    private CommunityDAO communityDAO;

    @Test
    void count() {
        int count = communityDAO.count();
        assertThat(count).isGreaterThan(0);
    }
}
package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dto.Community;
import com.jjeopjjeop.recipe.mapper.CommunityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityMapper boardMapper;

    public List<Community> getBoard(){
        return boardMapper.listAll();
    }

    public void insert(Community dto){
        boardMapper.insert(dto);
    }
}

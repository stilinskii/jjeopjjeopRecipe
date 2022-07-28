package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dto.CommunityBoardDTO;
import com.jjeopjjeop.recipe.mapper.CommunityBoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityBoardService {
    private final CommunityBoardMapper boardMapper;

    public List<CommunityBoardDTO> getBoard(){
        return boardMapper.listAll();
    }

    public void insert(CommunityBoardDTO dto){
        boardMapper.insert(dto);
    }
}

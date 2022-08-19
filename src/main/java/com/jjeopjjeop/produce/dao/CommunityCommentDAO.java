package com.jjeopjjeop.recipe.dao;

import com.jjeopjjeop.recipe.dto.CommunityCommentDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface CommunityCommentDAO {

    Integer count();
    void insert(CommunityCommentDTO communityCommentDTO);

    List<CommunityCommentDTO> findCommentsByBoardId(Integer id);

    void deleteCommentById(Integer commentId);

    void reportCommentById(Integer commentId);

    void editComment(Map<String,Object> commentEditInfo);
}

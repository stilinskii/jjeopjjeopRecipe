package com.jjeopjjeop.recipe.restAPI.community;

import com.jjeopjjeop.recipe.dto.CommunityDTO;
import com.jjeopjjeop.recipe.restAPI.community.CommunityApiDAO;
import com.jjeopjjeop.recipe.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/community")
@CrossOrigin("*")
@RequiredArgsConstructor
public class CommunityApiController {

    private final CommunityService communityService;
    private final CommunityApiDAO apiDAO;

    @GetMapping
    public List<CommunityDTO> testrest(){

        return apiDAO.findAll();
    }
}

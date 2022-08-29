package com.jjeopjjeop.recipe.restAPI.community;

import com.jjeopjjeop.recipe.dto.CommunityDTO;
import com.jjeopjjeop.recipe.mapper.CommunityApiMapper;
import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//http://localhost:8081/swagger-ui.html#/community-api-controller
@Slf4j
@RestController
@RequestMapping("/api/community")
@CrossOrigin("*")
@RequiredArgsConstructor
public class CommunityApiController {

    private final CommunityApiMapper apiMapper;

    @GetMapping("/all")
    public ResponseEntity<List<CommunityDTO>> findAllCommunityBoard(){
        return ResponseEntity.ok().body(apiMapper.findAll());
    }

    @GetMapping("/{id}")
    @ApiImplicitParam(name = "id", value = "community post id")
    public ResponseEntity<CommunityDTO> findCommunityPostById(@PathVariable Integer id){
        CommunityDTO foundPost = apiMapper.findCommunityPostById(id);
        if(foundPost==null){
            return new ResponseEntity("post id: "+id+" not found", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok().body(foundPost);
    }

    @PostMapping
    public ResponseEntity saveCommunityPost(@RequestBody CommunityForm communityForm){
        CommunityDTO communityDTO = getCommunityDTO(communityForm);
        apiMapper.insert(communityDTO);

        return ResponseEntity.ok().body("post save success");
    }

    @PutMapping("/{id}")
    @ApiImplicitParam(name = "id", value = "community post id")
    public ResponseEntity<CommunityDTO> updateCommunityPost(@PathVariable Integer id, @RequestBody CommunityForm communityForm){
        CommunityDTO communityDTO = getCommunityDTO(communityForm);
        communityDTO.setId(id);

        Integer updated = apiMapper.updatePost(communityDTO);
        if(updated<=0){
            return new ResponseEntity("update fail", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().body(apiMapper.findCommunityPostById(id));
    }

    private CommunityDTO getCommunityDTO(CommunityForm communityForm) {
        CommunityDTO communityDTO = new CommunityDTO();
        communityDTO.setUser_id(communityForm.getUser_id());
        communityDTO.setCategory(communityForm.getCategory());
        communityDTO.setRcp_seq(communityForm.getRcp_seq());
        communityDTO.setTitle(communityForm.getTitle());
        communityDTO.setContent(communityForm.getTitle());
        return communityDTO;
    }

    @DeleteMapping("/{id}")
    @ApiImplicitParam(name = "id", value = "community post id")
    public ResponseEntity deleteCommunityPost(@PathVariable Integer id){
        Integer deleted = apiMapper.deletePost(id);
        if(deleted<=0){
          //  return ResponseEntity.notFound().build();
            return new ResponseEntity("post id: "+id+" not found", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok().body("post id: "+id+" delete success");
    }




}

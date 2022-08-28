package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dao.CommunityCommentDAO;
import com.jjeopjjeop.recipe.dao.CommunityDAO;
import com.jjeopjjeop.recipe.dto.CommunityCommentDTO;
import com.jjeopjjeop.recipe.dto.CommunityDTO;
import com.jjeopjjeop.recipe.dto.ImageDTO;
import com.jjeopjjeop.recipe.file.FileStore;
import com.jjeopjjeop.recipe.form.CommunitySearchForm;
import com.jjeopjjeop.recipe.pagenation.Pagenation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.jjeopjjeop.DirNameConst.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityDAO communityDAO;
    private final CommunityCommentDAO communityCommentDAO;
    private final FileStore fileStore;

    public List<CommunityDTO> getBoard(Pagenation pagenation){
        return communityDAO.list(pagenation);
    }
    public List<CommunityDTO> getRecipeReviews(Pagenation pagenation){
        return communityDAO.recipeReviewList(pagenation);
    }

    public List<CommunityDTO> getFreeForums(Pagenation pagenation){
        return communityDAO.freeForumList(pagenation);
    }

    public void save(CommunityDTO dto, List<MultipartFile> image){

        //자유글이면 레시피가 선택됐더라도 선택X
        if(dto.getCategory().equals("0")){
            dto.setRcp_seq(null);
        }

        //해당 포스트의 이미지 여부 표시
        if(hasUploadedImage(image)){
            dto.setImage_exists(1);
        }else{
            dto.setImage_exists(0);
        }
        communityDAO.insert(dto);

        //해당 포스트가 저장된 후 이미지테이블에 포스트가 저장된 후 생성된 포스트 아이디와 함께 이미지정보 저장.
        if(dto.getImage_exists()==1){
            saveImagesToDBAndLocal(image,dto.getId());
        }
    }

    private boolean hasUploadedImage(List<MultipartFile> image) {
        return !image.get(0).isEmpty();
    }


    public void saveImagesToDBAndLocal(List<MultipartFile> multipartFiles, Integer boardId){
        List<ImageDTO> community = fileStore.storeImages(multipartFiles, COMMUNITY);
        for (ImageDTO imageDTO : community) {
            saveImageToDB(imageDTO,boardId);
        }
    }

    public void saveImageToDB(ImageDTO imageDTO,Integer boardId){
        log.info("boardId={}",boardId);
        imageDTO.setBoard_id(boardId);
        communityDAO.storeImage(imageDTO);
    }

    public CommunityDTO findPostById(Integer id){
        CommunityDTO post = communityDAO.findPostById(id);
        List<ImageDTO> images = communityDAO.findImageByPostId(id);
        post.setImages(images);
        return post;
    }

    public CommunityDTO findPostWithLikeInfo(Integer id, String userId){
        //조회수 올리기
        communityDAO.addReadCnt(id);
        //포스트 찾기
        CommunityDTO post = communityDAO.findPostById(id);
        //포스트에 해당하는 이미지들 db에서 불러와서 set하기
        List<ImageDTO> image = communityDAO.findImageByPostId(id);
        if(hasImage(image)){
            post.setImages(image);
        }
        //유저가 해당 포스트 like했는지 확인
        Integer liked = communityDAO.checkIfUserLikedPost(Map.of("postId", id, "userId", userId));
        if(liked!=null){
            post.setLiked(true);
        }

        return post;
    }

    private boolean hasImage(List<ImageDTO> image) {
        return !image.isEmpty();
    }

    public int count(){
        return communityDAO.count();
    }
    public int recipeReviewCount(){ return communityDAO.recipeReviewCount();}
    public int freeForumCount(){ return communityDAO.freeForumCount();}
    public void deletePost(int id){
        //이미지 삭제 로직
        List<ImageDTO> images = communityDAO.findImageByPostId(id);
        fileStore.deleteImages(images,COMMUNITY);

        communityDAO.deletePostById(id);
        communityDAO.deleteImageByPostId(id);
    }

    public void reportPost(Integer id) {
        communityDAO.reportPostById(id);
    }

        //라이크할때 라이크테이블에 유저아이디, 포스트아이디 추가 / 해당포스트 like ++
    public void addLikeCnt(Integer postId, String userId) {
        communityDAO.addLikeCntByPostId(postId);
        communityDAO.insertLikeInfo(Map.of("postId",postId,"userId",userId));

    }

        //라이크 취소 라이크테이블에 유저아이디, 포스프아이디로된 데이터 삭제/ 해당 포스트 like --
    public void subtractLikeCnt(Integer postId, String userId) {
        communityDAO.deleteLikeInfo(Map.of("postId",postId,"userId",userId));
        communityDAO.subtractLikeCntByPostId(postId);
    }

    public void postComment(CommunityCommentDTO communityCommentDTO){
        communityCommentDAO.insert(communityCommentDTO);
    }

    public List<CommunityCommentDTO> findComments(Integer id) {
        return communityCommentDAO.findCommentsByBoardId(id);
    }

    public void deleteComment(Integer commentId) {
        communityCommentDAO.deleteCommentById(commentId);
    }

    public CommunityCommentDTO findCommentById(Integer commentId){
       return communityCommentDAO.findCommentById(commentId);
    }

    public void reportComment(Integer commentId){
        communityCommentDAO.reportCommentById(commentId);
    }

    public void editComment(Map<String,Object> commentEditInfo){
        communityCommentDAO.editComment(commentEditInfo);
    }

    @Transactional
    public void editPost(CommunityDTO community, List<MultipartFile> image) {

        if (hasUploadedImage(image)) {
            deleteCurrentImages(community.getId());
            community.setImage_exists(1);
            saveImagesToDBAndLocal(image,community.getId());
        }
        communityDAO.updatePost(community);

    }



    public void deleteCurrentImages(Integer postId) {
        //이미지 삭제 로직
        List<ImageDTO> images = communityDAO.findImageByPostId(postId);
        fileStore.deleteImages(images,COMMUNITY);
        communityDAO.deleteImageByPostId(postId);
    }

    public List<CommunityDTO> findCommunityBySearch(CommunitySearchForm searchForm, Pagenation pagenation) {
        return communityDAO.findCommunityBySearch(Map.of("form",searchForm,"page",pagenation));
    }

    public Integer countCommunityBySearch(CommunitySearchForm searchForm){
        return communityDAO.countCommunityBySearch(searchForm);
    }



}

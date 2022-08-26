package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dao.CommunityCommentDAO;
import com.jjeopjjeop.recipe.dao.CommunityDAO;
import com.jjeopjjeop.recipe.dto.CommunityCommentDTO;
import com.jjeopjjeop.recipe.dto.CommunityDTO;
import com.jjeopjjeop.recipe.dto.ImageDTO;
import com.jjeopjjeop.recipe.file.FileStore;
import com.jjeopjjeop.recipe.form.CommunitySearchForm;
import com.jjeopjjeop.recipe.pagenation.Pagenation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static com.jjeopjjeop.DirNameConst.COMMUNITY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommunityServiceTest {

    @Mock
    CommunityDAO communityDAO;
    @Mock
    CommunityCommentDAO communityCommentDAO;
    @Mock
    FileStore fileStore;
    @InjectMocks
    CommunityService underTest;

    CommunityDTO communityDTO;
    @BeforeEach
    void setUp() {
        communityDTO = new CommunityDTO(0,"lhy98410", "0", 0, "hi", "dfdf");
    }

    @Test
    void getBoard() {
        //given
        Pagenation pagenation = new Pagenation(1,4,communityDAO.count());
        //when
        underTest.getBoard(pagenation);
        //then
        verify(communityDAO).list(any());
    }

    @Test
    void getRecipeReviews() {
        //given
        Pagenation pagenation = new Pagenation(1,4,communityDAO.count());
        //when
        underTest.getRecipeReviews(pagenation);
        //then
        verify(communityDAO).recipeReviewList(any());

    }

    @Test
    void getFreeForums() {
        //given
        Pagenation pagenation = new Pagenation(1,4,communityDAO.count());
        //when
        underTest.getFreeForums(pagenation);
        //then
        verify(communityDAO).freeForumList(any());
    }

    @Test
    void save() {
        //given
        List<MultipartFile> images = new ArrayList<>();
        MockMultipartFile image1
                = new MockMultipartFile(
                "image",
                "hello.png",
                String.valueOf(MediaType.IMAGE_PNG),
                "Hello, World!".getBytes()
        );
        MockMultipartFile image2
                = new MockMultipartFile(
                "image",
                "hello.jpg",
                String.valueOf(MediaType.IMAGE_JPEG),
                "Hello, World!".getBytes()
        );
        images.add(image1);
        images.add(image2);

        //when
        underTest.save(communityDTO,images);

        //then
        ArgumentCaptor<CommunityDTO> communityDTOArgumentCaptor = ArgumentCaptor.forClass(CommunityDTO.class);
        verify(communityDAO).insert(communityDTOArgumentCaptor.capture());
        assertThat(communityDTOArgumentCaptor.getValue()).isEqualTo(communityDTO);
        verify(fileStore).storeImages(any(),anyString());
    }


    @Test
    void saveImageToDB() {
    }

    @Test
    void findPostById() {
        //given
        communityDAO.insert(communityDTO);
        Integer id = communityDTO.getId();
        given(communityDAO.findPostById(id)).willReturn(communityDTO);
        //when
        CommunityDTO postById = underTest.findPostById(id);
        //then
        verify(communityDAO).findPostById(id);
        verify(communityDAO).findImageByPostId(id);
        assertThat(postById).isEqualTo(communityDTO);

    }

    @Test
    void findPostWithLikeInfo() {
        //given
        communityDAO.insert(communityDTO);
        Integer id = communityDTO.getId();
        given(communityDAO.findImageByPostId(id)).willReturn(Collections.emptyList());
        given(communityDAO.findPostById(id)).willReturn(communityDTO);

        //when
        String userId = anyString();
        CommunityDTO postWithLikeInfo = underTest.findPostWithLikeInfo(id, userId);

        //then
        verify(communityDAO).addReadCnt(id);
        verify(communityDAO).findPostById(id);
        verify(communityDAO).checkIfUserLikedPost(Map.of("postId", id, "userId", userId));
        assertThat(postWithLikeInfo).isEqualTo(communityDTO);
    }

    @Test
    void count() {
        //given
        communityDAO.insert(communityDTO);

        //when
        int count = underTest.count();

        //then
        verify(communityDAO).count();
    }

    @Test
    void recipeReviewCount() {

    }

    @Test
    void freeForumCount() {

    }

    @Test
    void deletePost() {
        //given
       // CommunityDTO communityDTO = new CommunityDTO(0,"lhy98410", "0", 0, "hi", "dfdf");
        communityDAO.insert(communityDTO);
        Integer id = communityDTO.getId();

        //when
        underTest.deletePost(id);

        //then
        ArgumentCaptor<Integer> idArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(communityDAO).deletePostById(idArgumentCaptor.capture());
        assertThat(idArgumentCaptor.getValue()).isEqualTo(id);

    }

    @Test
    void reportPost() {
        //given
        communityDAO.insert(communityDTO);
        Integer id = communityDTO.getId();

        //when
        underTest.reportPost(id);

        //then
        verify(communityDAO).reportPostById(id);

    }

    @Test
    void addLikeCnt() {
        //given

        //when
        String userId = anyString();
        underTest.addLikeCnt(communityDTO.getId(),userId);

        //then
        verify(communityDAO).addLikeCntByPostId(communityDTO.getId());
        verify(communityDAO).insertLikeInfo(Map.of("postId",communityDTO.getId(),"userId",userId));
    }

    @Test
    void subtractLikeCnt() {
        //given

        //when
        String userId = anyString();
        underTest.subtractLikeCnt(communityDTO.getId(),userId);

        //then
        verify(communityDAO).subtractLikeCntByPostId(communityDTO.getId());
        verify(communityDAO).deleteLikeInfo(Map.of("postId",communityDTO.getId(),"userId",userId));
    }

    @Test
    void postComment() {
        //given
        CommunityCommentDTO comment = CommunityCommentDTO.builder()
                .id(0)
                .board_id(0)
                .user_id("lhy98410")
                .content("내용")
                .created_at(new Date())
                .updated_at(new Date())
                .report(0)
                .build();
        //when
        underTest.postComment(comment);

        //then
        ArgumentCaptor<CommunityCommentDTO> commentArgumentCaptor =  ArgumentCaptor.forClass(CommunityCommentDTO.class);
        verify(communityCommentDAO).insert(commentArgumentCaptor.capture());
        assertThat(commentArgumentCaptor.getValue()).isEqualTo(comment);
    }

    @Test
    void findComments() {
        //given
        Integer boardId = 0;

        //when
        List<CommunityCommentDTO> comments = underTest.findComments(boardId);

        //then
        verify(communityCommentDAO).findCommentsByBoardId(boardId);

    }

    @Test
    void deleteComment() {
        //given
        Integer commentId = 0;
        //when
        underTest.deleteComment(commentId);
        //then
        verify(communityCommentDAO).deleteCommentById(commentId);

    }

    @Test
    void reportComment() {
        //given
        Integer commentId = 0;

        //when
        underTest.reportComment(commentId);

        //then
        verify(communityCommentDAO).reportCommentById(commentId);
    }

    @Test
    void editComment() {
        //given

        //when
        underTest.editComment(anyMap());
        //then
        verify(communityCommentDAO).editComment(anyMap());

    }

    @Test
    void editPostAndSaveNewImage() {
        //given
        List<MultipartFile> images = new ArrayList<>();
        MockMultipartFile image1
                = new MockMultipartFile(
                "image",
                "hello.png",
                String.valueOf(MediaType.IMAGE_PNG),
                "Hello, World!".getBytes()
        );
        MockMultipartFile image2
                = new MockMultipartFile(
                "image",
                "hello.jpg",
                String.valueOf(MediaType.IMAGE_JPEG),
                "Hello, World!".getBytes()
        );
        images.add(image1);
        images.add(image2);

        //when
        underTest.editPost(communityDTO,images);

        //then
        verify(communityDAO).updatePost(communityDTO);
        verify(fileStore).storeImages(images,COMMUNITY);
    }

    @Test
    void editPostAndNotSaveNewImage() {
        //given
        List<MultipartFile> images = new ArrayList<>();

        //when
        underTest.editPost(communityDTO,images);

        //then
        verify(communityDAO).updatePost(communityDTO);
        verify(fileStore,never()).storeImages(images,COMMUNITY);
    }

    @Test
    void deleteCurrentImages() {
        //given
        Integer postId = 0;
        List<ImageDTO> images = new ArrayList<>();
        ImageDTO image1 = ImageDTO.builder()
                    .board_id(postId)
                    .filename("image1")
                    .id(0).build();
        images.add(image1);
        given(communityDAO.findImageByPostId(0)).willReturn(images);

        //when
        underTest.deleteCurrentImages(postId);

        //then
        verify(fileStore).deleteImages(images,COMMUNITY);
        verify(communityDAO).deleteImageByPostId(postId);


    }

    @Test
    void findCommunityBySearch() {
        //given
        CommunitySearchForm searchForm = new CommunitySearchForm();
        Pagenation pagenation = new Pagenation();
        //when
        underTest.findCommunityBySearch(searchForm,pagenation);
        //then
        verify(communityDAO).findCommunityBySearch(anyMap());

    }

    @Test
    void countCommunityBySearch() {
        //when
        underTest.countCommunityBySearch(any());
        //then
        verify(communityDAO).countCommunityBySearch(any());
    }
}
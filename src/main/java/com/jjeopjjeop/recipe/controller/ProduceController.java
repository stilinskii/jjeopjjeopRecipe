
package com.jjeopjjeop.recipe.controller;

import com.jjeopjjeop.recipe.dto.*;
import com.jjeopjjeop.recipe.service.ProduceService;

import java.io.File;
import java.util.List;

import com.jjeopjjeop.recipe.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class ProduceController {

    @Autowired
    private ProduceService produceService;

    //일단 임시로 넣음.
    @Autowired
    private ReviewService reviewService;


    public ProduceController() {
    }

    //판매글 작성폼 불러오기
    @GetMapping({"/produce/write"})
    public String produceWriteForm() {
        return "/produce/produceWrite";
    }

    //판매글 작성 반영
    @PostMapping({"/produce/write"})
    public String produceWrite(ProduceDTO produceDTO, MultipartFile file) throws Exception{
        log.info("dto={}", produceDTO.getUser_id());
        log.info("dto={}", produceDTO.getProduce_image());
        produceService.writeProcess(produceDTO, file);
        return "redirect:/produce/list";
    }
    //////////////////////////////////////////////////////////////////////////////////////////
    private int currentPage;

    //모든 판매글 조회
    @GetMapping("/produce/list")
    public ModelAndView produceList(ModelAndView mav, RecipePageDTO recipePageDTO) {
        // 전체 레코드 수
        int totalRecord = produceService.countProcess();

        if(totalRecord>0){//전체 레코드 수가 0개보다 많으면
            //현재페이지와 1중에 큰 것을 currentPage에 넣음.게시판에 들어오고 아무것도 안누르면 currentPage 0이니까
            currentPage = Math.max(recipePageDTO.getCurrentPage(), 1);

            recipePageDTO = new RecipePageDTO(currentPage, totalRecord);  //이제 startrow, endrow 계산됨.
        }

        mav.addObject("totalRecord", totalRecord); //전체 레코드 정보 넘기기
        List<ProduceDTO> list = produceService.produceListProcess(recipePageDTO);
        mav.addObject("list", list);  //판매글 리스트 넘겨주기
        mav.addObject("pDto", recipePageDTO); //페이지 정보 넘겨주기
        mav.setViewName("/produce/produceList");
        return mav;
    }


    //필터링한 판매글 조회
    @GetMapping("/produce/list/{type}")
    public ModelAndView produceListType(@PathVariable("type") int type, ModelAndView mav, RecipePageDTO recipePageDTO) {
        // 전체 레코드 수
        int totalRecord = produceService.countProcess();

        if(totalRecord>0){//전체 레코드 수가 0개보다 많으면
            //현재페이지와 1중에 큰 것을 currentPage에 넣음.게시판에 들어오고 아무것도 안누르면 currentPage 0이니까
            currentPage = Math.max(recipePageDTO.getCurrentPage(), 1);

            recipePageDTO = new RecipePageDTO(currentPage, totalRecord);  //이제 startrow, endrow 계산됨.
        }


        mav.addObject("totalRecord", totalRecord); //전체 레코드 정보 넘기기
        mav.addObject("pDto", recipePageDTO); //페이지 정보 넘겨주기

        List<ProduceDTO> list = produceService.produceListTypeProcess(type);
        mav.addObject("list", list);
        mav.setViewName("/produce/produceList");
        return mav;
    }
    //////////////////////////////////////////////////////////////////////////////////////

    //판매글 삭제
    @GetMapping({"/produce/delete/{produceNum}"})
    public String produceDelete(@PathVariable("produceNum") int produce_num) {
        produceService.produceDeleteProcess(produce_num);

        return "redirect:/produce/list";
    }

    //판매글 상세보기
    @GetMapping("/produce/view/{produceNum}")
    public ModelAndView produceView(@PathVariable("produceNum") int produce_num, ModelAndView mav) {
        //판매글 상세내용
        ProduceDTO produceDTO = produceService.produceViewProcess(produce_num); //produce_num에 해당하는 정보 가져오기
        mav.addObject("produceDTO", produceDTO);//가져온 정보 보내기
        mav.setViewName("/produce/produceView");

        //리뷰
        List<ReviewDTO> list = reviewService.reviewListProcess(produce_num);
        mav.addObject("list", list);

        return mav;
    }

    //판매글 수정폼
    @GetMapping({"/produce/update/{produceNum}"})
    public ModelAndView produceUpdateForm(@PathVariable("produceNum") int produce_num, ModelAndView mav) {
        ProduceDTO produceDTO = produceService.produceViewProcess(produce_num);
        mav.addObject("produceDTO", produceDTO);
        mav.setViewName("produce/produceUpdateForm");
        return mav;
    }

    //판매글 수정 반영
    @PostMapping({"/produce/update/{produceNum}"})
    public String produceUpdate(@PathVariable("produceNum") int produce_num, ProduceDTO produceDTO) {
        produceService.produceUpdateProcess(produceDTO);
        return "redirect:/produce/view/" + produce_num;
    }
}
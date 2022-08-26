
package com.jjeopjjeop.recipe.controller;

import com.jjeopjjeop.recipe.config.MySecured;
import com.jjeopjjeop.recipe.dto.*;
import com.jjeopjjeop.recipe.pagenation.Pagenation;
import com.jjeopjjeop.recipe.service.ProduceService;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jjeopjjeop.recipe.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.standard.expression.Each;

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
    @MySecured
    @GetMapping({"/produce/write"})
    public String produceWriteForm(Model model) {

        model.addAttribute("produceDTO", new ProduceDTO()); //빈 오브젝트를 뷰에 넘겨준다.
        return "/produce/produceWrite";
    }


    //판매글 작성 반영
    @MySecured
    @PostMapping({"/produce/write"})
    public String produceWrite(ProduceDTO produceDTO, MultipartFile file) throws Exception{
        produceService.writeProcess(produceDTO, file);
        return "redirect:/produce/list/0";
    }
    //////////////////////////////////////////////////////////////////////////////////////////

    //판매글 조회(필터링)
    @GetMapping("/produce/list/{type}")
    public ModelAndView produceList(@PathVariable("type") int type, @RequestParam(value = "page", required = false, defaultValue = "0") Integer page, ModelAndView mav) {

        int totalRecord = produceService.produceFilterCount(type);// 전체 레코드 수

        Pagenation pagenation = new Pagenation(page,9, totalRecord); //페이지 처리를 위한 계산

        //mapper에 보낼 값들.
        Map<String, Object> map = new HashMap<>();
        map.put("startRow", pagenation.getStartRow());
        map.put("endRow", pagenation.getEndRow());
        map.put("produce_type", type);

        //produceList.html에 보낼 값들.
        mav.addObject("page", pagenation); //페이지 정보 넘겨주기
        mav.addObject("list", produceService.produceList(map));  //판매글 리스트 넘겨주기
        mav.setViewName("/produce/produceList");
        return mav;
    }

    //판매글 조회(정렬)
    @GetMapping("/produce/list/sort/{sort}")
    public ModelAndView produceListSort(@PathVariable("sort") int sort, @RequestParam(value = "page", required = false, defaultValue = "0") Integer page, ModelAndView mav) {

        int totalRecord = produceService.produceSortCount(sort);
        Pagenation pagenation = new Pagenation(page,9, totalRecord); //페이지 처리를 위한 계산

        //mapper에 보낼 값들.
        Map<String, Object> map = new HashMap<>();
        map.put("startRow", pagenation.getStartRow());
        map.put("endRow", pagenation.getEndRow());
        map.put("sort", sort);

        //produceList.html에 보낼 값들.
        mav.addObject("page", pagenation); //페이지 정보 넘겨주기
        mav.addObject("list", produceService.produceListSort(map));  //판매글 리스트 넘겨주기
        mav.setViewName("/produce/produceList");
        return mav;
    }
    //////////////////////////////////////////////////////////////////////////////////////

    //판매글 삭제
    @MySecured
    @GetMapping({"/produce/delete/{produceNum}"})
    public String produceDelete(@PathVariable("produceNum") int produce_num) {
        produceService.produceDeleteProcess(produce_num);

        return "redirect:/produce/list/0";
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
    @MySecured
    @GetMapping({"/produce/update/{produceNum}"})
    public ModelAndView produceUpdateForm(@PathVariable("produceNum") int produce_num, ModelAndView mav) {
        ProduceDTO produceDTO = produceService.produceViewProcess(produce_num);
        mav.addObject("produceDTO", produceDTO);
        mav.setViewName("produce/produceUpdateForm");
        return mav;
    }

    //판매글 수정 반영
    @MySecured
    @PostMapping({"/produce/update/{produceNum}"})
    public String produceUpdate(@PathVariable("produceNum") int produce_num, ProduceDTO produceDTO) {
        produceService.produceUpdateProcess(produceDTO);
        return "redirect:/produce/view/" + produce_num;
    }
}
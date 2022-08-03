
package com.jjeopjjeop.recipe.controller;

import com.jjeopjjeop.recipe.dto.PageDTO;
import com.jjeopjjeop.recipe.dto.ProduceDTO;
import com.jjeopjjeop.recipe.dto.ReviewDTO;
import com.jjeopjjeop.recipe.service.ProduceService;
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

@Slf4j
@Controller
public class ProduceController {
    @Autowired
    private PageDTO pdto;

    @Autowired
    private ProduceService produceService;

    //일단 임시로 넣음.
    @Autowired
    private ReviewService reviewService;


    public ProduceController() {
    }

    @GetMapping({"/produce/write"})
    public String produceWriteForm() {
        return "/produce/produceWrite";
    }

    @PostMapping({"/produce/write"})
    public String produceWrite(ProduceDTO produceDTO, MultipartFile file) throws Exception{
        log.info("dto={}", produceDTO.getUser_id());
        log.info("dto={}", produceDTO.getProduce_image());
        produceService.writeProcess(produceDTO, file);
        return "redirect:/produce/list";
    }

    private int currentPage;

    @GetMapping({"/produce/list"})
    public ModelAndView produceList(ModelAndView mav, PageDTO pageDTO) {

        int totalRecord = produceService.produceCountProcess();

        //현재페이지 가지고 오기.
        if(totalRecord >= 1) {//게시글이 하나이상있어야 페이지 네비게이션의 의미가 있지.
            if(pageDTO.getCurrentPage() == 0) {
                currentPage = 1; //게시판에 들어오자마자 아무것도 안누르고 바로 누르면 파라미터(currentPage)는 0이다. (<a href="list.do">게시판</a>) 그래서 여기서 1로 바꿔줌.
            }else {
                currentPage = pageDTO.getCurrentPage(); //게시판의 페이지네비게이션 누르면 currentPage가 정해짐. <a href="list.do"?currentPage=이값을받아옴>게시판</a>
            }
            pdto = new PageDTO(currentPage, totalRecord);  //이제 startrow, endrow 계산됨.
            List<ProduceDTO> list = produceService.produceListProcess(pageDTO);
            mav.addObject("list", list);

            mav.addObject("pageDTO", pdto); //startPage, endPage구하기..?
        }


        mav.setViewName("/produce/produceList");
        return mav;
    }

    @GetMapping({"/produce/list/{type}"})
    public ModelAndView produceListType(@PathVariable("type") int type, ModelAndView mav) {
        List<ProduceDTO> list = produceService.produceListTypeProcess(type);
        mav.addObject("list", list);
        mav.setViewName("/produce/produceList");
        return mav;
    }

    @GetMapping({"/produce/delete/{produceNum}"})
    public String produceDelete(@PathVariable("produceNum") int produce_num) {
        produceService.produceDeleteProcess(produce_num);
        return "redirect:/produce/list";
    }

    @GetMapping({"/produce/view/{produceNum}"})
    public ModelAndView produceView(@PathVariable("produceNum") int produce_num, ModelAndView mav) {
        ProduceDTO produceDTO = produceService.produceViewProcess(produce_num);

        mav.addObject("produceDTO", produceDTO);
        mav.setViewName("/produce/produceView");

       //리뷰
        List<ReviewDTO> list = reviewService.reviewListProcess(produce_num);
        mav.addObject("list", list);

        return mav;
    }

    @GetMapping({"/produce/update/{produceNum}"})
    public ModelAndView produceUpdateForm(@PathVariable("produceNum") int produce_num, ModelAndView mav) {
        ProduceDTO produceDTO = produceService.produceViewProcess(produce_num);
        mav.addObject("produceDTO", produceDTO);
        mav.setViewName("produce/produceUpdateForm");
        return mav;
    }

    @PostMapping({"/produce/update/{produceNum}"})
    public String produceUpdate(@PathVariable("produceNum") int produce_num, ProduceDTO produceDTO) {
        produceService.produceUpdateProcess(produceDTO);
        return "redirect:/produce/view/" + produce_num;
    }
}
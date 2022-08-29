
package com.jjeopjjeop.recipe.controller;

import com.jjeopjjeop.recipe.config.MySecured;
import com.jjeopjjeop.recipe.dto.*;
import com.jjeopjjeop.recipe.pagenation.Pagenation;
import com.jjeopjjeop.recipe.service.ProduceService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jjeopjjeop.recipe.service.ReviewService;
import com.jjeopjjeop.recipe.service.SellerService;
import com.jjeopjjeop.recipe.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class ProduceController {

    @Autowired
    private ProduceService produceService;

    //일단 임시로 넣음.
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    @Autowired
    private SellerService sellerService;

    public ProduceController() {
    }

    //판매글 작성폼 불러오기
    @MySecured
    @GetMapping("/produce/write")
    public String produceWriteForm(Model model,HttpServletResponse response, HttpSession session) throws IOException {
        String user_id = (String) session.getAttribute("user_id");
        UserDTO user = userService.findUserById(user_id);
        //유저 타입이 판매자가 아닌경우
        if(isSeller(user)){
            //이미 판매자 등록했는데 아직 승인이 안난경우
            if(isApprovedAsSeller(user_id)){
                alertAndMovePage(response,"판매자 등록 처리중입니다. 잠시만 기다려주세요!","/produce/list/0");
            }else{
                //판매자 등록 안한경우
                alertAndMovePage(response,"판매자가 아닙니다. 우선 판매자로 등록해주세요!","/seller/write");
            }
        }
            //성공로직
        model.addAttribute("produceDTO", new ProduceDTO()); //빈 오브젝트를 뷰에 넘겨준다.

        return "/produce/produceWrite";
    }

    private boolean isSeller(UserDTO user) {
        return user.getUsertype() < 2;
    }

    private boolean isApprovedAsSeller(String user_id) {
        return sellerService.findSellerById(user_id) > 0;
    }

    public static void alertAndMovePage(HttpServletResponse response, String alertText, String nextPage)  throws IOException {
        response.setContentType("text/html;charset=euc-kr");
        response.setCharacterEncoding("euc-kr");
        PrintWriter out = response.getWriter();
        out.println("<script>alert('"+alertText+"');location.href='"+nextPage+"';</script>");
        out.flush();
    }

    //판매글 작성 반영
    @MySecured
    @PostMapping("/produce/write")
    public String produceWrite(@Validated ProduceDTO produceDTO, BindingResult bindingResult, MultipartFile file) throws Exception{
        if(bindingResult.hasErrors()){
            System.out.println("==============비정상=======================");
            System.out.println(produceDTO);
            System.out.println("======================================");
            return "/produce/produceWrite";
        }

        System.out.println("===============정상======================");
        System.out.println(produceDTO);
        System.out.println("======================================");
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

        List<ProduceDTO> list = produceService.produceList(map);
        //produceList.html에 보낼 값들.
        mav.addObject("page", pagenation); //페이지 정보 넘겨주기
        mav.addObject("list", list);  //판매글 리스트 넘겨주기

        //판매자 id로 판매자 상호명 구하기.
        Map<String, String> idToBusinessName = new HashMap<>();
        for (ProduceDTO item:list) {
            String businessName = produceService.searchSellerBusinessName(item.getUser_id());
            idToBusinessName.put(item.getUser_id(), businessName);
        }
        mav.addObject("idToBusinessName", idToBusinessName);

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

        List<ProduceDTO> list = produceService.produceListSort(map);

        //produceList.html에 보낼 값들.
        mav.addObject("page", pagenation); //페이지 정보 넘겨주기
        mav.addObject("list", list );  //판매글 리스트 넘겨주기

        //판매자 id로 판매자 상호명 구하기.
        Map<String, String> idToBusinessName = new HashMap<>();
        for (ProduceDTO item:list) {
            String businessName = produceService.searchSellerBusinessName(item.getUser_id());
            idToBusinessName.put(item.getUser_id(), businessName);
        }
        mav.addObject("idToBusinessName", idToBusinessName);

        mav.setViewName("/produce/produceList");
        return mav;
    }
    //////////////////////////////////////////////////////////////////////////////////////
    //판매종료
    @MySecured
    @GetMapping("/produce/update/endOfSale/{produceNum}")
    public String produceUpdateSale(@PathVariable("produceNum") int produce_num){
        produceService.produceUpdateSale(produce_num);
        return "redirect:/produce/list/0";
    }

    //판매글 상세보기
    @GetMapping("/produce/view/{produceNum}")
    public ModelAndView produceView(@PathVariable("produceNum") int produce_num, ModelAndView mav, HttpServletRequest request) {
        //판매글 상세내용
        ProduceDTO produceDTO = produceService.produceViewProcess(produce_num); //produce_num에 해당하는 정보 가져오기
        mav.addObject("produceDTO", produceDTO);//가져온 정보 보내기
        mav.addObject("businessName", produceService.searchSellerBusinessName(produceDTO.getUser_id()));
        mav.setViewName("/produce/produceView");

        //리뷰
        List<ReviewDTO> list = reviewService.reviewListProcess(produce_num);
        mav.addObject("list", list);

        //직전페이지 정보
        String beforeAddress = request.getHeader("referer");
        if(beforeAddress.indexOf("/produce/list") > 0){ //직전페이지가 판매글 리스트라면
            mav.addObject("beforeAddress", beforeAddress);  //직전페이지 url를 뷰에 보내기기
        }


        return mav;
    }

    //판매글 수정폼
    @MySecured
    @GetMapping("/produce/update/{produceNum}")
    public ModelAndView produceUpdateForm(@PathVariable("produceNum") int produce_num, ModelAndView mav) {
        ProduceDTO produceDTO = produceService.produceViewProcess(produce_num);
        produceDTO.setProduce_image(produceDTO.getProduce_image().substring(produceDTO.getProduce_image().lastIndexOf('_')+1)); //기존의 파일이미지 가지고 오기.
        mav.addObject("produceDTO", produceDTO);
        mav.setViewName("produce/produceUpdateForm");
        return mav;
    }

    //판매글 수정 반영
    @MySecured
    @PostMapping("/produce/update/{produceNum}")
    public String produceUpdate(@PathVariable("produceNum") int produce_num,@Validated ProduceDTO produceDTO, MultipartFile file, BindingResult bindingResult, Model model) throws Exception {
        if (bindingResult.hasErrors()) { //에러있으면
            return "/produce/reviewUpdate";
        }


        produceService.produceUpdate(produceDTO, file);
        return "redirect:/produce/view/" + produce_num;
    }
    /*
    @MySecured
    @PostMapping("/produce/write")
    public String produceWrite(ProduceDTO produceDTO, MultipartFile file) throws Exception{
        produceService.writeProcess(produceDTO, file);
        return "redirect:/produce/list/0";
    }
     */
}
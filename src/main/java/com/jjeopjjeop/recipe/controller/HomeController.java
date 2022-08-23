package com.jjeopjjeop.recipe.controller;

import com.jjeopjjeop.recipe.dto.*;
import com.jjeopjjeop.recipe.pagenation.Pagenation;
import com.jjeopjjeop.recipe.service.ProduceService;
import com.jjeopjjeop.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final RecipeService recipeService;
    private final ProduceService produceService;

    @GetMapping("/")
    public String index(Model model){
        RecipePageDTO recipePageDTO = new RecipePageDTO();
        recipePageDTO.setStartRow(1);
        recipePageDTO.setEndRow(4);
        recipePageDTO.setRcp_sort(2);//스크랩많은순
        recipePageDTO.setCate_seq(0);//카테고리 선택안함.
        List<RecipeDTO> rcpList = recipeService.listProcess(recipePageDTO);

//        int count = produceService.countProcess();
//        Pagenation pagenation = new Pagenation(1,4,count);
        List<ProduceDTO> popularProduceList = produceService.getPopularProduceList();
        log.info("reclist={}",rcpList.size());
        model.addAttribute("rcpList",rcpList);
        model.addAttribute("list",popularProduceList);

        return "index";
    }


    @GetMapping("/search")
    public String searchPage(){
        return "searchPage";
    }

    @PostMapping("/search")
    public String searchResult(String keyword, RedirectAttributes redirectAttributes, HttpSession session){
        //맨처음 들어왔을때 검색결과 X / 키워드 없음 알림이 안떠야함.
        if(StringUtils.isEmpty(keyword)){
            redirectAttributes.addFlashAttribute("NoKeyword",true);
            return "redirect:/search";
        }

        //recipe
        List<RecipeDTO> rcpListAll = recipeService.searchListByKeyword(keyword);
        List<RecipeDTO> rcpList = rcpListAll.size()>8 ?getSmallListOfRecipe(rcpListAll):rcpListAll;
        //shopping
        log.info("keyword={}",keyword);
        List<ProduceDTO> productListAll = produceService.findProductsByKeyword(keyword);
        log.info("searchproducesize={}",productListAll.size());
        List<ProduceDTO> productList = productListAll.size()>4 ? getSmallProductList(productListAll):productListAll;
        log.info("resultlist={}",productList.size());

        //뭔가 더 좋은 방법이 있을 거 같은디...
        if(rcpList.size()==0){
            redirectAttributes.addFlashAttribute("NoRcpList",true);
        }else{
            redirectAttributes.addFlashAttribute("rcpList",rcpList);//레시피는 8개만... 키워드 관련 전체 레시피 개수도 필요함
            redirectAttributes.addFlashAttribute("rcpListSize",rcpListAll.size());
            session.setAttribute("rcpListAll",rcpListAll);
        }

        if(productList.size()==0){
            redirectAttributes.addFlashAttribute("NoProductList",true);
        }else{
            redirectAttributes.addFlashAttribute("productList",productList);//상품은 4개
            redirectAttributes.addFlashAttribute("productListSize",productListAll.size());
            session.setAttribute("productListAll",productListAll);
        }

        redirectAttributes.addFlashAttribute("keyword",keyword);

        return "redirect:/search";
    }

    private List<RecipeDTO> getSmallListOfRecipe(List<RecipeDTO> rcpListAll) {
        List<RecipeDTO> rcpList=new ArrayList<>();
        for (int i=0;i<8;i++) {
            rcpList.add(rcpListAll.get(i));
        }
        return rcpList;
    }

    private List<ProduceDTO> getSmallProductList(List<ProduceDTO> productListAll) {
        List<ProduceDTO> list=new ArrayList<>();
        for (int i=0;i<4;i++) {
            list.add(productListAll.get(i));
        }
        return list;
    }


    @GetMapping("/moreProduct")
    public ModelAndView produceList(String keyword, HttpSession session, ModelAndView mav) {
        // 전체 레코드 수
        List<ProduceDTO> productListAll = (List<ProduceDTO>) session.getAttribute("productListAll");
        int totalRecord = productListAll.size();

        RecipePageDTO recipePageDTO = new RecipePageDTO();
        if(totalRecord>0){//전체 레코드 수가 0개보다 많으면
            //현재페이지와 1중에 큰 것을 currentPage에 넣음.게시판에 들어오고 아무것도 안누르면 currentPage 0이니까
           int currentPage = Math.max(recipePageDTO.getCurrentPage(), 1);
            recipePageDTO = new RecipePageDTO(currentPage, totalRecord);  //이제 startrow, endrow 계산됨.
        }

        mav.addObject("totalRecord", totalRecord); //전체 레코드 정보 넘기기
        List<ProduceDTO> list = produceService.findProductsByKeywordWithPaging(keyword,recipePageDTO);
        mav.addObject("list", list);  //판매글 리스트 넘겨주기
        mav.addObject("pDto", recipePageDTO); //페이지 정보 넘겨주기
        mav.setViewName("/produce/produceList");
        return mav;
    }


    @GetMapping("/moreRecipe")
    public ModelAndView rcpSearchMethod(@RequestParam(value="rcp_sort", required=false, defaultValue = "0") Integer rcp_sort,
                                        @RequestParam(value="cate_seq", required=false, defaultValue = "0") int cate_seq,
                                        String keyword,
                                        ModelAndView mav, RecipePageDTO recipePageDTO, HttpSession session){
        List<RecipeDTO> productListAll = (List<RecipeDTO>) session.getAttribute("rcpListAll");
        int totalRecord = productListAll.size();

        if(totalRecord>0){
            int currentPage = Math.max(recipePageDTO.getCurrentPage(), 1);
            recipePageDTO = new RecipePageDTO(currentPage, totalRecord, keyword);
            recipePageDTO.setRcp_sort(rcp_sort);
            recipePageDTO.setCate_seq(cate_seq);
        }

        // 레시피 분류 목록
        List<CategoryDTO> cateList = recipeService.cateListProcess();

        // 검색 레시피 목록
        List<RecipeDTO> rcpList = recipeService.searchListProcess(recipePageDTO);
        //System.out.println(rcpList);

        mav.addObject("totalRecord", totalRecord);
        mav.addObject("cateList", cateList);
        mav.addObject("rcpList", rcpList);
        mav.addObject("recipePageDTO", recipePageDTO);
        mav.setViewName("/recipe/rcpSearch");
        return mav;
    }
}

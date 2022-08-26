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
        List<ProduceDTO> list = produceService.getPopularProduceList();//4개만 가져옴

        Pagenation pagenation = new Pagenation(1, recipeService.countProcess(2), true);
        pagenation.setStartRow(1);
        pagenation.setEndRow(4);//4개만 가져옴
        List<RecipeDTO> rcpList = recipeService.listProcess(pagenation, 2, 0);

        model.addAttribute("rcpList",rcpList);
        model.addAttribute("list",list);

        return "index";
    }

//상세검색 시작
    @GetMapping("/search")
    public String searchPage(){
        return "searchPage";
    }

    @PostMapping("/search")
    public String searchResult(String keyword, RedirectAttributes redirectAttributes, HttpSession session){
        //맨처음 들어왔을때 키워드 없음 알림이 안떠야함.
        if(StringUtils.isEmpty(keyword)){
            redirectAttributes.addFlashAttribute("NoKeyword",true);
            return "redirect:/search";
        }

        //recipe
        List<RecipeDTO> rcpList;
        int recipeByKeywordCnt = recipeService.searchCountProcess(keyword, 0);
        if(recipeByKeywordCnt!=0){
            Pagenation pagenation = new Pagenation(1, recipeByKeywordCnt, true);
            //검색결과 8개보다 많을때 8개만 출력
            if(recipeByKeywordCnt>8){
                pagenation.setStartRow(1);
                pagenation.setEndRow(8);
            }
            rcpList = recipeService.searchListProcess(pagenation, 0, 0, keyword);
            redirectAttributes.addFlashAttribute("rcpList",rcpList);
            redirectAttributes.addFlashAttribute("rcpListSize",recipeByKeywordCnt);//검색결과 전체개수
        }else{
            //검색결과가 0개이면
            redirectAttributes.addFlashAttribute("NoRcpList",true);
        }


        //shopping - 쇼핑에는 키워드 검색 기능이 따로 없어서 만듦
        List<ProduceDTO> productListAll = produceService.findProductsByKeyword(keyword);
        int totalCnt = productListAll.size();

        if(totalCnt==0){
            redirectAttributes.addFlashAttribute("NoProductList",true);
        }else{
            List<ProduceDTO> productList = productListAll.size()>4 ? getSmallProductList(productListAll):productListAll;
            redirectAttributes.addFlashAttribute("productList",productList);//상품 4개
            redirectAttributes.addFlashAttribute("productListSize",productListAll.size());

            //더보기 결과 페이지네이션을 위해
            session.setAttribute("productListAllTotalCnt",totalCnt);
            session.setAttribute("keyword",keyword);
        }

        redirectAttributes.addFlashAttribute("keyword",keyword);

        return "redirect:/search";
    }


    private List<ProduceDTO> getSmallProductList(List<ProduceDTO> productListAll) {
        List<ProduceDTO> list=new ArrayList<>();
        for (int i=0;i<4;i++) {
            list.add(productListAll.get(i));
        }
        return list;
    }


    @GetMapping("/moreProduct")
    public ModelAndView produceList(@RequestParam(value="page", required=false, defaultValue = "0") int page, String keyword, HttpSession session, ModelAndView mav) {
       //1페이지 이상으로갈때 keyword파라미터 없어지는걸 대비.
        if(StringUtils.isEmpty(keyword)){
            keyword= (String) session.getAttribute("keyword");
        }

        // 검색결과 전체 레코드 수
        int totalRecord = (int) session.getAttribute("productListAllTotalCnt");

        Pagenation pagenation = new Pagenation(page,9, totalRecord);
        List<ProduceDTO> list = produceService.findProductsByKeywordWithPaging(keyword,pagenation);

        mav.addObject("totalRecord", totalRecord); //전체 레코드 정보 넘기기
        mav.addObject("list", list);  //판매글 리스트 넘겨주기
        mav.addObject("page", pagenation); //페이지 정보 넘겨주기
        mav.setViewName("/produce/produceList");
        return mav;
    }

    @GetMapping("/moreRecipe")
    public ModelAndView rcpSearchMethod(@RequestParam(value="rcp_sort", required=false, defaultValue = "0") Integer rcp_sort,
                                        @RequestParam(value="cate_seq", required=false, defaultValue = "0") int cate_seq,
                                        @RequestParam(value="keyword", required=false) String keyword,
                                        @RequestParam(value="page", required=false, defaultValue = "1") int page,
                                        ModelAndView mav){

        // 전체 페이지 수
        Pagenation pagenation = new Pagenation(page, recipeService.searchCountProcess(keyword, 0), true);

        // 레시피 분류 목록
        List<CategoryDTO> cateList = recipeService.cateListProcess();

        // 검색 레시피 목록
        List<RecipeDTO> rcpList = recipeService.searchListProcess(pagenation, rcp_sort, cate_seq, keyword);
        //System.out.println(rcpList);

        mav.addObject("rcp_sort", rcp_sort);
        mav.addObject("cate_seq", cate_seq);
        mav.addObject("searchKey", keyword);
        mav.addObject("cateList", cateList);
        mav.addObject("rcpList", rcpList);
        mav.addObject("pagenation", pagenation);
        mav.setViewName("/recipe/rcpSearch");
        return mav;
    }
}

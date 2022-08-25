package com.jjeopjjeop.recipe.controller;

import ch.qos.logback.core.joran.spi.ElementSelector;
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
        List<ProduceDTO> list = produceService.produceListProcess(recipePageDTO);


        Pagenation pagenation = new Pagenation(1, recipeService.countProcess(2), true);
        pagenation.setStartRow(1);
        pagenation.setEndRow(4);
        List<RecipeDTO> rcpList = recipeService.listProcess(pagenation, 2, 0);


        model.addAttribute("rcpList",rcpList);
        model.addAttribute("list",list);

        return "index";
    }


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


        //뭔가 더 좋은 방법이 있을 거 같은디...
        //shopping
        List<ProduceDTO> productListAll = produceService.findProductsByKeyword(keyword);
        List<ProduceDTO> productList = productListAll.size()>4 ? getSmallProductList(productListAll):productListAll;
        log.info("list={}",productListAll.size());


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
                                        @RequestParam(value="keyword", required=false) String keyword,
                                        @RequestParam(value="page", required=false, defaultValue = "1") int page,
                                        ModelAndView mav){

        // 전체 페이지 수
        Pagenation pagenation = new Pagenation(page, recipeService.searchCountProcess(keyword, 0), true);
        log.info("keyword={}",keyword);

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

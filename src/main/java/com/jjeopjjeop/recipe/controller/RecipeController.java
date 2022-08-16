package com.jjeopjjeop.recipe.controller;

import com.jjeopjjeop.recipe.dto.*;
import com.jjeopjjeop.recipe.service.RecipeCommentService;
import com.jjeopjjeop.recipe.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
public class RecipeController {
    @Autowired
    private RecipeService service;
    @Autowired
    private RecipeCommentService commentService;
    private int currentPage;
    private int commentCurrentPage;
    private String searchKey;


    // 레시피 목록 조회 메소드
    @GetMapping("/recipe/list")
    public ModelAndView rcpListMethod(@RequestParam(value="rcp_sort", required=false, defaultValue = "0") Integer rcp_sort,
                                      @RequestParam(value="cate_seq", required=false, defaultValue = "0") int cate_seq,
                                      RecipePageDTO recipePageDTO, ModelAndView mav){

        // 전체 레코드 수
        int totalRecord = service.countProcess(cate_seq);

        if(totalRecord>0){
            currentPage = Math.max(recipePageDTO.getCurrentPage(), 1);
            recipePageDTO = new RecipePageDTO(currentPage, totalRecord);
            recipePageDTO.setRcp_sort(rcp_sort);
            recipePageDTO.setCate_seq(cate_seq);
        }

        // 오늘의 인기 레시피 목록
        List<RecipeDTO> favoriteRcpList = service.favoriteListProcess();

        // 레시피 분류 목록
        List<CategoryDTO> cateList = service.cateListProcess();

        // 전체 레시피 목록
        List<RecipeDTO> rcpList = service.listProcess(recipePageDTO);

        mav.addObject("totalRecord", totalRecord);
        mav.addObject("cateList", cateList);
        mav.addObject("favoriteRcpList", favoriteRcpList);
        mav.addObject("rcpList", rcpList);
        mav.addObject("pDto", recipePageDTO);
        mav.setViewName("/recipe/rcpList");
        return mav;
    }

    // 레시피 목록 검색 메소드
    @GetMapping("/recipe/search")
    public ModelAndView rcpSearchMethod(@RequestParam(value="rcp_sort", required=false, defaultValue = "0") Integer rcp_sort,
                                        @RequestParam(value="cate_seq", required=false, defaultValue = "0") int cate_seq,
                                        ModelAndView mav, RecipePageDTO recipePageDTO){
        searchKey = recipePageDTO.getSearchKey();
        int totalRecord = service.searchCountProcess(recipePageDTO);

        if(totalRecord>0){
            currentPage = Math.max(recipePageDTO.getCurrentPage(), 1);
            recipePageDTO = new RecipePageDTO(currentPage, totalRecord, searchKey);
            recipePageDTO.setRcp_sort(rcp_sort);
            recipePageDTO.setCate_seq(cate_seq);
        }

        // 레시피 분류 목록
        List<CategoryDTO> cateList = service.cateListProcess();

        // 검색 레시피 목록
        List<RecipeDTO> rcpList = service.searchListProcess(recipePageDTO);
        //System.out.println(rcpList);

        mav.addObject("totalRecord", totalRecord);
        mav.addObject("cateList", cateList);
        mav.addObject("rcpList", rcpList);
        mav.addObject("recipePageDTO", recipePageDTO);
        mav.setViewName("/recipe/rcpSearch");
        return mav;
    }

    // 레시피 본문 조회 메소드
    @GetMapping("/recipe/view/{rcp_seq}")
    public ModelAndView rcpViewMethod(@PathVariable("rcp_seq") Integer rcp_seq,
                                      @RequestParam(value="currentPage", required=false, defaultValue = "1") Integer currentPage,
                                      @RequestParam(value="rcp_sort", required=false, defaultValue = "0") Integer rcp_sort,
                                      RecipePageDTO recipeCommentPageDTO, HttpSession session, ModelAndView mav){
        // 레시피 본문 내용
        mav.addObject("rcp", service.contentProcess(rcp_seq));
        mav.addObject("currentPage", currentPage);
        mav.addObject("rcp_sort", rcp_sort);
        mav.addObject("cate_seq", recipeCommentPageDTO.getCate_seq());
        mav.addObject("user_id", String.valueOf(session.getAttribute("user_id")));

        // 스크랩 체크
        UserScrapDTO userScrapDTO = new UserScrapDTO();
        userScrapDTO.setUser_id(String.valueOf(session.getAttribute("user_id")));
        userScrapDTO.setRcp_seq(rcp_seq);
        mav.addObject("scrapOrNot", service.chkScrapProcess(userScrapDTO));

        // 신고 체크
        ReportRecipeDTO reportRecipeDTO = new ReportRecipeDTO();
        reportRecipeDTO.setUser_id(String.valueOf(session.getAttribute("user_id")));
        reportRecipeDTO.setRcp_seq(rcp_seq);
        mav.addObject("reportOrNot", service.chkReportProcess(reportRecipeDTO));

        // 레시피별 요리 단계 내용
        mav.addObject("manualList", service.contentMnlProcess(rcp_seq));

        // 덧글 처리
        int totalComment = commentService.countProcess(rcp_seq);
        if(totalComment>0){
            commentCurrentPage = Math.max(recipeCommentPageDTO.getCommentCurrentPage(), 1);
            recipeCommentPageDTO = new RecipePageDTO(commentCurrentPage, totalComment, rcp_seq);
            System.out.println(commentCurrentPage);
        }
        mav.addObject("totalComment", totalComment);
        mav.addObject("commentList", commentService.listProcess(recipeCommentPageDTO));
        mav.addObject("recipeCommentPageDTO", recipeCommentPageDTO);

        mav.setViewName("/recipe/rcpView");
        return mav;
    }

    // 레시피 스크랩 메소드
    @ResponseBody
    @PostMapping("/recipe/scrap")
    public void rcpScrapMethod(@RequestParam String rcp_seq,
                               @RequestParam String user_id,
                               HttpSession session){
        UserScrapDTO userScrapDTO = new UserScrapDTO();
        userScrapDTO.setUser_id(String.valueOf(session.getAttribute("user_id")));
        userScrapDTO.setRcp_seq(Integer.parseInt(rcp_seq));
        service.scrapProcess(userScrapDTO);
        //mav.addObject("rcp_seq", rcp_seq);
    }

    // 레시피 신고 메소드
    @ResponseBody
    @PostMapping("/recipe/report")
    public void rcpReportMethod(@RequestParam String rcp_seq,
                                @RequestParam String user_id,
                                HttpSession session){
        ReportRecipeDTO reportRecipeDTO = new ReportRecipeDTO();
        reportRecipeDTO.setUser_id(String.valueOf(session.getAttribute("user_id")));
        reportRecipeDTO.setRcp_seq(Integer.parseInt(rcp_seq));
        service.reportProcess(reportRecipeDTO);
    }

    // 레시피 작성 페이지 요청 메소드
    @GetMapping("/recipe/write")
    public ModelAndView rcpWriteMethod(ModelAndView mav){
        RecipeDTO recipeDTO = new RecipeDTO();

        // 레시피 분류 목록
        List<CategoryDTO> cateList = service.cateListProcess();

        mav.addObject("recipeDTO", recipeDTO);
        mav.addObject("cateList", cateList);
        mav.setViewName("/recipe/rcpWrite");
        return mav;
    }

    // 레시피 작성 메소드
    @PostMapping("/recipe/write")
    public String rcpWriteProMethod(@Validated @ModelAttribute("recipeDTO") RecipeDTO recipeDTO, BindingResult bindingResult, String[] manual_txt,
                                    @RequestParam(value="cateArr", required=false) List<String> cateArr, Model model,
                                    MultipartFile[] upload_manual, HttpServletRequest request, HttpSession session){
        // user_id 설정
        recipeDTO.setUser_id(String.valueOf(session.getAttribute("user_id")));

        // 유효성 검사
        if(bindingResult.hasErrors()){
            // 레시피 분류 목록
            List<CategoryDTO> cateList = service.cateListProcess();
            model.addAttribute("cateList", cateList);

            //log.info("errors={}", bindingResult);
            return "recipe/rcpWrite";
        }

        // 본문 작성
        MultipartFile mainFile = recipeDTO.getUpload();
        if(!mainFile.isEmpty()){
            UUID random = saveCopyFile(mainFile, request, 0);
            recipeDTO.setFilename(random+"_"+mainFile.getOriginalFilename());
            recipeDTO.setFilepath("/media/recipe/");
        }

        service.writeProcess(recipeDTO);

        // 요리과정 작성
        for(int i=0; i<manual_txt.length; i++){
            ManualDTO manualDTO = new ManualDTO();
            manualDTO.setManual_no(i+1);
            manualDTO.setManual_txt(manual_txt[i]);
            if(!upload_manual[i].isEmpty()){
                UUID random = saveCopyFile(upload_manual[i], request, 1);
                manualDTO.setFilename(random+"_"+upload_manual[i].getOriginalFilename());
                manualDTO.setFilepath("/media/recipe/manual/");
            }
            service.writeMProcess(manualDTO);
        }

        // 카테고리 작성
        for(String data : cateArr){
            int num = Integer.parseInt(data);
            service.writeCProcess(num);
        }

        return "redirect:/recipe/list";
    }

    // 레시피-카테고리 적용 메소드
    @ResponseBody
    @PostMapping("/recipe/category")
    public void rcpCateWriteMethod(@RequestBody List<String> cateArr){
        System.out.println(cateArr);
    }

    // 레시피 수정 페이지 요청 메소드
    @GetMapping("/recipe/update")
    public String rcpUpdateMethod(){

        return "/recipe/rcpUpdate";
    }

    // 레시피 수정 메소드
//    public String rcpUpdateMethod(){
//
//        return null;
//    }

    // 레시피 삭제 메소드
    @GetMapping("/recipe/delete/{rcp_seq}")
    public String rcpDeleteMethod(@PathVariable("rcp_seq") int rcp_seq, HttpServletRequest request){
        service.deleteProcess(rcp_seq, urlPath(request, 0), urlPath(request, 1));

        return "redirect:/recipe/list";
    }

    // 첨부파일 처리를 위한 메소드
    private String urlPath(HttpServletRequest request, int num){
        //String serverPath = request.getServletContext().getRealPath("/");
        String root = "";
        if(num==0){
            root = "src/main/resources/static/media/recipe/";
        }else{
            root = "src/main/resources/static/media/recipe/manual/";
        }
        //String saveDirectory = root + "temp" + File.separator;
        return root;
    }

    private UUID saveCopyFile(MultipartFile file, HttpServletRequest request, int num){
        String fileName = file.getOriginalFilename();
        UUID random = UUID.randomUUID();

        File fe = new File(urlPath(request, num));
        if(!fe.exists()){
            fe.mkdir();
        }

        File ff = new File(urlPath(request, num), random+"_"+fileName);

        try{
            FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(ff));
        }catch (IOException e){
            e.printStackTrace();
        }

        return random;
    }
}

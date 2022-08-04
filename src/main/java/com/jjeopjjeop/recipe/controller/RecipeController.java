package com.jjeopjjeop.recipe.controller;

import com.jjeopjjeop.recipe.dto.*;
import com.jjeopjjeop.recipe.service.RecipeCommentService;
import com.jjeopjjeop.recipe.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class RecipeController {
    @Autowired
    private RecipeService service;

    @Autowired
    private RecipeCommentService commentService;
    private int currentPage;
    private String searchKey;

    // 레시피 목록 조회 메소드
    @GetMapping("/recipe/list")
    public ModelAndView rcpListMethod(ModelAndView mav, RecipePageDTO recipePageDTO){
        // 전체 레코드 수
        int totalRecord = service.countProcess();

        if(totalRecord>0){
//            if(recipePageDTO.getCurrentPage()==0){
//                currentPage = 1;
//            }else{
//                currentPage = recipePageDTO.getCurrentPage();
//            }
            currentPage = Math.max(recipePageDTO.getCurrentPage(), 1);
            recipePageDTO = new RecipePageDTO(currentPage, totalRecord);
        }

        // 오늘의 인기 레시피 목록
        List<RecipeDTO> favoriteRcpList = service.favoriteListProcess();

        // 레시피 분류 목록
        List<CategoryDTO> cateList = service.cateListProcess();

        // 전체 레시피 목록
        List<RecipeDTO> rcpList = service.listProcess(recipePageDTO);
        //System.out.println(rcpList);

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
    public ModelAndView rcpSearchMethod(ModelAndView mav, RecipePageDTO recipePageDTO){
        searchKey = recipePageDTO.getSearchKey();
        int totalRecord = service.searchCountProcess(searchKey);

        if(totalRecord>0){
            currentPage = Math.max(recipePageDTO.getCurrentPage(), 1);
            recipePageDTO = new RecipePageDTO(currentPage, totalRecord, searchKey);
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
    public ModelAndView rcpViewMethod(@PathVariable("rcp_seq") Integer rcp_seq, Integer currentPage, ModelAndView mav){
        // 레시피 본문 내용
        mav.addObject("rcp", service.contentProcess(rcp_seq));
        mav.addObject("currentPage", currentPage);

        // 레시피별 요리 단계 내용
        mav.addObject("manualList", service.contentMnlProcess(rcp_seq));

        // 덧글 처리
        RecipePageDTO recipePageDTO = new RecipePageDTO();
        int totalComment = commentService.countProcess(rcp_seq);
//        if(totalComment>0){
//            currentPage = 1;
//            recipePageDTO = new RecipePageDTO(currentPage, totalComment);
//            recipePageDTO.setBlockPage(3);
//            recipePageDTO.setBlockCount(5);
//        }
        mav.addObject("totalComment", totalComment);
        mav.addObject("commentList", commentService.listProcess(rcp_seq));

        mav.setViewName("/recipe/rcpView");
        return mav;
    }

    // 레시피 스크랩 메소드
    @GetMapping("/recipe/scrap/{rcp_seq}")
    public String rcpScrapMethod(@PathVariable("rcp_seq") Integer rcp_seq, Integer currentPage){
        UserScrapDTO userScrapDTO = new UserScrapDTO();
        userScrapDTO.setUser_id("테스트");
        userScrapDTO.setRcp_seq(rcp_seq);
        service.scrapProcess(userScrapDTO);

        return "redirect:/recipe/view/"+rcp_seq+"?currentPage="+currentPage;
    }

    // 레시피 신고 메소드
    @GetMapping("/recipe/report/{rcp_seq}")
    public String rcpReportMethod(@PathVariable("rcp_seq") Integer rcp_seq, Integer currentPage){
        ReportRecipeDTO reportRecipeDTO = new ReportRecipeDTO();
        reportRecipeDTO.setUser_id("테스트");
        reportRecipeDTO.setRcp_seq(rcp_seq);
        service.reportProcess(reportRecipeDTO);

        return "redirect:/recipe/view/"+rcp_seq+"?currentPage="+currentPage;
    }

    // 레시피 작성 페이지 요청 메소드
    @GetMapping("/recipe/write")
    public ModelAndView rcpWriteMethod(ModelAndView mav){
        // 레시피 분류 목록
        List<CategoryDTO> cateList = service.cateListProcess();

        mav.addObject("cateList", cateList);
        mav.setViewName("/recipe/rcpWrite");
        return mav;
    }

    // 레시피 작성 메소드
    @PostMapping("/recipe/write")
    public String rcpWriteProMethod(RecipeDTO recipeDTO, String[] manual_txt, MultipartFile[] upload_manual, HttpServletRequest request){
        MultipartFile mainFile = recipeDTO.getUpload();
        if(!mainFile.isEmpty()){
            UUID random = saveCopyFile(mainFile, request, 0);
            recipeDTO.setFilename(random+"_"+mainFile.getOriginalFilename());
            recipeDTO.setFilepath("/media/recipe/");
        }

        service.writeProcess(recipeDTO);
        // validation!!

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

        return "redirect:/recipe/list";
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
    public String rcpDeleteMethod(@PathVariable("rcp_seq") int rcp_seq){
        service.deleteProcess(rcp_seq);

        return "redirect:/recipe/list";
    }

    //////////// 레시피 댓글 관리 ////////////
    // 레시피 댓글 작성 메소드
    @PostMapping("/recipe/comment")
    public String rcpCoWriteMethod(){

        return "redirect:/recipe/view";
    }

    // 레시피 댓글 수정 메소드
    @PutMapping("/recipe/comment/update")
    public String rcpCoUpdateMethod(){

        return "redirect:/recipe/view";
    }

    // 레시피 댓글 삭제 메소드
    @DeleteMapping("/recipe/comment/delete")
    public String rcpCoDeleteMethod(){

        return "redirect:/recipe/view";
    }

    // 레시피 댓글 신고 메소드
    @PutMapping("/recipe/comment/report")
    public String rcpCoReportMethod(){

        return "redirect:/recipe/view";
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

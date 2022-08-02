package com.jjeopjjeop.recipe.controller;

import com.jjeopjjeop.recipe.dto.CategoryDTO;
import com.jjeopjjeop.recipe.dto.ManualDTO;
import com.jjeopjjeop.recipe.dto.RecipeDTO;
import com.jjeopjjeop.recipe.dto.RecipePageDTO;
import com.jjeopjjeop.recipe.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class RecipeController {
    @Autowired
    private RecipeService service;
    private int currentPage;

    // 레시피 목록 조회 메소드
    @GetMapping("/recipe/list")
    public ModelAndView rcpListMethod(ModelAndView mav, RecipePageDTO pDto, HttpServletRequest request){
        // 전체 레코드 수
        int totalRecord = service.countProcess();
        //System.out.println(totalRecord);

        if(totalRecord>0){
            if(pDto.getCurrentPage()==0){
                currentPage = 1;
            }else{
                currentPage = pDto.getCurrentPage();
            }
            //System.out.println(currentPage);
            pDto = new RecipePageDTO(currentPage, totalRecord);
        }

        // 오늘의 인기 레시피 목록
        List<RecipeDTO> favoriteRcpList = service.favoriteListProcess();

        // 레시피 분류 목록
        List<CategoryDTO> cateList = service.cateListProcess();

        // 전체 레시피 목록
        List<RecipeDTO> rcpList = service.listProcess(pDto);
        //System.out.println(rcpList);

        urlPath(request);

        mav.addObject("totalRecord", totalRecord);
        mav.addObject("cateList", cateList);
        mav.addObject("favoriteRcpList", favoriteRcpList);
        mav.addObject("rcpList", rcpList);
        mav.addObject("pDto", pDto);
        mav.setViewName("/recipe/rcpList");
        return mav;
    }

    // 레시피 목록 검색 메소드
    @GetMapping("/recipe/search")
    public String rcpSearchMethod(){

        return "/recipe/rcpSearch";
    }

    // 레시피 본문 조회 메소드
    @GetMapping("/recipe/view/{rcp_seq}")
    public ModelAndView rcpViewMethod(@PathVariable("rcp_seq") Integer rcp_seq, ModelAndView mav){

        mav.addObject("rcp", service.contentProcess(rcp_seq));
        mav.addObject("manualList", service.contentMnlProcess(rcp_seq));

        mav.setViewName("/recipe/rcpView");
        return mav;
    }

    // 레시피 스크랩 메소드
    @PutMapping("/recipe/scrap")
    public String rcpScrapMethod(){

        return "redirect:/recipe/view";
    }

    // 레시피 신고 메소드
    @PutMapping("/recipe/report")
    public String rcpReportMethod(){

        return "redirect:/recipe/view";
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
    public String rcpWriteProMethod(RecipeDTO dto, String[] manual_txt, HttpServletRequest request){
        MultipartFile file = dto.getUpload();
        if(!file.isEmpty()){
            UUID random = saveCopyFile(file, request);
            dto.setFilename(random+"_"+file.getOriginalFilename());
            dto.setFilepath("/media/recipe/");
        }

        service.writeProcess(dto);
        // validation!!

        ManualDTO mDto = new ManualDTO();
        for(int i=0; i<manual_txt.length; i++){
            mDto.setManual_no(i+1);
            mDto.setManual_txt(manual_txt[i]);
            service.writeMProcess(mDto);
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
    private String urlPath(HttpServletRequest request){
        //String serverPath = request.getServletContext().getRealPath("/");
        String root = "src/main/resources/static/media/recipe/";
        //String saveDirectory = root + "temp" + File.separator;
        return root;
    }

    private UUID saveCopyFile(MultipartFile file, HttpServletRequest request){
        String fileName = file.getOriginalFilename();
        UUID random = UUID.randomUUID();

        File fe = new File(urlPath(request));
        if(!fe.exists()){
            fe.mkdir();
        }

        File ff = new File(urlPath(request), random+"_"+fileName);

        try{
            FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(ff));
        }catch (IOException e){
            e.printStackTrace();
        }

        return random;
    }
}

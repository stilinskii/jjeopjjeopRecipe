package com.jjeopjjeop.recipe.controller;

import com.jjeopjjeop.recipe.config.MySecured;
import com.jjeopjjeop.recipe.dto.*;
import com.jjeopjjeop.recipe.pagenation.Pagenation;
import com.jjeopjjeop.recipe.service.RecipeCommentService;
import com.jjeopjjeop.recipe.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
public class RecipeController {
    @Autowired
    private RecipeService service;
    @Autowired
    private RecipeCommentService commentService;

    // 레시피 목록 조회 메소드
    @GetMapping("/recipe/list")
    public ModelAndView rcpListMethod(@RequestParam(value="rcp_sort", required=false, defaultValue = "0") Integer rcp_sort,
                                      @RequestParam(value="cate_seq", required=false, defaultValue = "0") int cate_seq,
                                      @RequestParam(value="page", required=false, defaultValue = "1") int page, ModelAndView mav){

        // 전체 레코드 수
        Pagenation pagenation = new Pagenation(page, service.countProcess(cate_seq), true);

        // 오늘의 인기 레시피 목록
        List<RecipeDTO> favoriteRcpList = service.favoriteListProcess();

        // 레시피 분류 목록
        List<CategoryDTO> cateList = service.cateListProcess();

        // 전체 레시피 목록
        List<RecipeDTO> rcpList = service.listProcess(pagenation, rcp_sort, cate_seq);

        mav.addObject("rcp_sort", rcp_sort);
        mav.addObject("cate_seq", cate_seq);
        mav.addObject("cateList", cateList);
        mav.addObject("favoriteRcpList", favoriteRcpList);
        mav.addObject("rcpList", rcpList);
        mav.addObject("pagenation", pagenation);
        mav.setViewName("/recipe/rcpList");
        return mav;
    }

    // 레시피 목록 검색 메소드
    @GetMapping("/recipe/search")
    public ModelAndView rcpSearchMethod(@RequestParam(value="rcp_sort", required=false, defaultValue = "0") Integer rcp_sort,
                                        @RequestParam(value="cate_seq", required=false, defaultValue = "0") int cate_seq,
                                        @RequestParam(value="searchKey", required=false) String searchKey,
                                        @RequestParam(value="page", required=false, defaultValue = "1") int page,
                                        ModelAndView mav){
        // 전체 페이지 수
        Pagenation pagenation = new Pagenation(page, service.searchCountProcess(searchKey, cate_seq), true);

        // 레시피 분류 목록
        List<CategoryDTO> cateList = service.cateListProcess();

        // 검색 레시피 목록
        List<RecipeDTO> rcpList = service.searchListProcess(pagenation, rcp_sort, cate_seq, searchKey);

        mav.addObject("rcp_sort", rcp_sort);
        mav.addObject("cate_seq", cate_seq);
        mav.addObject("searchKey", searchKey);
        mav.addObject("cateList", cateList);
        mav.addObject("rcpList", rcpList);
        mav.addObject("pagenation", pagenation);
        mav.setViewName("/recipe/rcpSearch");
        return mav;
    }

    // 레시피 본문 조회 메소드
    @GetMapping("/recipe/view/{rcp_seq}")
    public ModelAndView rcpViewMethod(@PathVariable("rcp_seq") Integer rcp_seq,
                                      @RequestParam(value="page", required=false, defaultValue = "1") int page,
                                      @RequestParam(value="cate_seq", required=false, defaultValue = "0") int cate_seq,
                                      @RequestParam(value="rcp_sort", required=false, defaultValue = "0") Integer rcp_sort,
                                      @RequestParam(value="searchKey", required=false) String searchKey,
                                      HttpSession session, ModelAndView mav){
        // 검색글일시 검색어 추가
        if(searchKey != null){
            mav.addObject("searchKey", searchKey);
        }

        // 레시피 본문 내용
        mav.addObject("rcp", service.contentProcess(rcp_seq));
        mav.addObject("page", page);
        mav.addObject("rcp_sort", rcp_sort);
        mav.addObject("user_id", String.valueOf(session.getAttribute("user_id")));


        // 레시피 카테고리 정보
        List<CategoryDTO> list = service.getRcpCateProcess(rcp_seq);
        List<String> cate_list = new ArrayList<>();

        for(int i=0; i<list.size(); i++){
            cate_list.add(list.get(i).getCate_name());
            System.out.println(list.get(i).getCate_name());
        }

        mav.addObject("cate_list", service.getRcpCateProcess(rcp_seq));

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
        Pagenation pagenation = new Pagenation(1, commentService.countProcess(rcp_seq), false);

        mav.addObject("commentList", commentService.listProcess(pagenation, rcp_seq));
        mav.addObject("rcp_sort", rcp_sort);
        mav.addObject("cate_seq", cate_seq);
        mav.addObject("pagenation", pagenation);

        mav.setViewName("/recipe/rcpView");
        return mav;
    }

    // 레시피 스크랩 메소드
    @ResponseBody
    @PostMapping("/recipe/scrap")
    public void rcpScrapMethod(@RequestParam String rcp_seq,
                               HttpSession session){
        UserScrapDTO userScrapDTO = new UserScrapDTO();
        userScrapDTO.setUser_id(String.valueOf(session.getAttribute("user_id")));
        userScrapDTO.setRcp_seq(Integer.parseInt(rcp_seq));
        service.scrapProcess(userScrapDTO);
    }

    // 레시피 신고 메소드
    @ResponseBody
    @PostMapping("/recipe/report")
    public void rcpReportMethod(@RequestParam String rcp_seq,
                                HttpSession session){
        ReportRecipeDTO reportRecipeDTO = new ReportRecipeDTO();
        reportRecipeDTO.setUser_id(String.valueOf(session.getAttribute("user_id")));
        reportRecipeDTO.setRcp_seq(Integer.parseInt(rcp_seq));
        service.reportProcess(reportRecipeDTO);
    }

    // 레시피 작성 페이지 요청 메소드
    @MySecured(role = MySecured.Role.USER)
    @GetMapping("/recipe/write")
    public String rcpWriteMethod(Model model, HttpSession session){
        RecipeDTO recipeDTO = new RecipeDTO();

        // 레시피 분류 목록
        List<CategoryDTO> cateList = service.cateListProcess();

        model.addAttribute("recipeDTO", recipeDTO);
        model.addAttribute("cateList", cateList);
        return "/recipe/rcpWrite";
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

    // 레시피 수정 페이지 요청 메소드
    @GetMapping("/recipe/update")
    public String rcpUpdateMethod(@RequestParam int rcp_seq, Model model, HttpSession session){
        RecipeDTO recipeDTO = service.contentProcess(rcp_seq);
        if(session.getAttribute("user_id") == null || !session.getAttribute("user_id").equals(recipeDTO.getUser_id())){
            return "error/500";
        }

        model.addAttribute("recipeDTO", recipeDTO);

        // 레시피 분류 목록
        List<CategoryDTO> cateList = service.cateListProcess(); //전체 분류 목록
        List<CategoryDTO> cate = service.getRcpCateProcess(rcp_seq); //레시피에 등록된 분류 목록

        for(int i=0; i<cate.size(); i++){
            for(int j=0; j<cateList.size(); j++){
                if(cate.get(i).getCate_seq() == cateList.get(j).getCate_seq())
                    cateList.get(j).setRcp_chk(true);
            }
        }

        model.addAttribute("cateList", cateList); // 전체 분류 목록
        model.addAttribute("manualList", service.contentMnlProcess(rcp_seq));
        return "/recipe/rcpUpdate";
    }

    // 레시피 수정 메소드
    @PostMapping("/recipe/update")
    public String rcpUpdateMethod(@ModelAttribute("recipeDTO") RecipeDTO recipeDTO, String[] manual_txt,
                                  @RequestParam(value="cateArr", required=false) List<String> cateArr, Model model,
                                  MultipartFile[] upload_manual, HttpServletRequest request, HttpSession session){

        // 레시피 분류 목록
        List<CategoryDTO> cateList = service.cateListProcess();
        model.addAttribute("cateList", cateList);

        // 본문 수정
        boolean isChange = false;
        MultipartFile mainFile = recipeDTO.getUpload();
        if(!mainFile.isEmpty()){
            isChange = true;
            UUID random = saveCopyFile(mainFile, request, 0);
            recipeDTO.setFilename(random+"_"+mainFile.getOriginalFilename());
            recipeDTO.setFilepath("/media/recipe/");
        }

        service.updateProcess(recipeDTO, urlPath(request, 0), isChange);

        // 요리과정 수정 (전체 삭제 후 재등록)
        for(int i=0; i<manual_txt.length; i++){
            ManualDTO manualDTO = new ManualDTO();
            manualDTO.setManual_no(i+1);
            manualDTO.setManual_txt(manual_txt[i]);
            manualDTO.setRcp_seq(recipeDTO.getRcp_seq());

            boolean isChangeM = false;
            if(!upload_manual[i].isEmpty()){
                isChangeM = true;
                UUID random = saveCopyFile(upload_manual[i], request, 1);
                manualDTO.setFilename(random+"_"+upload_manual[i].getOriginalFilename());
                manualDTO.setFilepath("/media/recipe/manual/");
            }
            service.updateMProcess(manualDTO, urlPath(request, 1));
        }

        // 카테고리 수정 (전체 삭제 후 재등록)
        service.deleteCProcess(recipeDTO.getRcp_seq());
        for(String data : cateArr){
            int num = Integer.parseInt(data);
            service.updateCateProcess(num, recipeDTO.getRcp_seq());
        }

        return "redirect:/recipe/view/"+recipeDTO.getRcp_seq();
    }

    // 레시피 삭제 메소드
    @GetMapping("/recipe/delete")
    public String rcpDeleteMethod(@RequestParam int rcp_seq, HttpServletRequest request, HttpSession session){
        RecipeDTO recipeDTO = service.contentProcess(rcp_seq);

        int userType = ((UserDTO) session.getAttribute("user")).getUsertype();
        boolean isAdmin = userType == 3 ? true : false;
        if(session.getAttribute("user_id") == null || (!isAdmin && !session.getAttribute("user_id").equals(recipeDTO.getUser_id()))){
            return "error/500";
        }

        service.deleteProcess(rcp_seq, urlPath(request, 0), urlPath(request, 1));
        return session.getAttribute("user_id").equals("admin") ? "redirect:/admin/c_index" : "redirect:/recipe/list";
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

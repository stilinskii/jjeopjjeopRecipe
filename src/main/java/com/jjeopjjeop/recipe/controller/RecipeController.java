package com.jjeopjjeop.recipe.controller;

import com.jjeopjjeop.recipe.dto.*;
import com.jjeopjjeop.recipe.service.RecipeAPIService;
import com.jjeopjjeop.recipe.service.RecipeCommentService;
import com.jjeopjjeop.recipe.service.RecipeService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
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
    private int commentCurrentPage;
    private String searchKey;

    // 오픈 API 테스트
    //@GetMapping("/")
    public ModelAndView openApiTest(ModelAndView mav){
        List<RecipeDTO> getApiList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL("https://openapi.foodsafetykorea.go.kr/api/f25a7b4b1923449dbe5d/COOKRCP01/json/1355/1358");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String result = br.readLine();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
            JSONObject cookrcp01 = (JSONObject) jsonObject.get("COOKRCP01");
            JSONArray row = (JSONArray) cookrcp01.get("row");

            for(int i=0; i<row.size(); i++){
                JSONObject info = (JSONObject) row.get(i);
                RecipeDTO recipeDTO = new RecipeDTO();
                recipeDTO.setRcp_seq(i * -1);
                recipeDTO.setUser_id("식품의약품안전처");
                recipeDTO.setRcp_name(info.get("RCP_NM").toString());
                recipeDTO.setRcp_parts_dtls(info.get("RCP_PARTS_DTLS").toString());
                recipeDTO.setFilepath(info.get("ATT_FILE_NO_MAIN").toString());

                List<ManualDTO> manualDTOList = new ArrayList<>();
                int num = 1;
                while (info.get("MANUAL0"+num) != null && !info.get("MANUAL0"+num).toString().isBlank()){
                    ManualDTO manualDTO = new ManualDTO();
                    //manualDTO.setRcp_seq(i * -1);
                    manualDTO.setManual_txt(info.get("MANUAL0"+num).toString().replaceAll("[a-z]$",""));
                    manualDTOList.add(manualDTO);
                    num++;
                }

                while (info.get("MANUAL"+num) != null && !info.get("MANUAL"+num).toString().isBlank()){
                    ManualDTO manualDTO = new ManualDTO();
                    //manualDTO.setRcp_seq(i * -1);
                    manualDTO.setManual_txt(info.get("MANUAL"+num).toString().replaceAll("[a-z]$",""));
                    manualDTOList.add(manualDTO);
                    num++;
                }

                recipeDTO.setManualDTOList(manualDTOList);
                getApiList.add(recipeDTO);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        mav.addObject("recipeApiList", getApiList);
        //mav.addObject("getApiManualList", getApiManualList);
        mav.setViewName("/recipe/apiTest");
        return mav;
    }

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
                                      RecipePageDTO recipeCommentPageDTO, ModelAndView mav){
        // 레시피 본문 내용
        mav.addObject("rcp", service.contentProcess(rcp_seq));
        mav.addObject("currentPage", currentPage);
        mav.addObject("rcp_sort", rcp_sort);
        mav.addObject("cate_seq", recipeCommentPageDTO.getCate_seq());

        // 스크랩 체크
        UserScrapDTO userScrapDTO = new UserScrapDTO();
        userScrapDTO.setUser_id("테스트");
        userScrapDTO.setRcp_seq(rcp_seq);
        mav.addObject("scrapOrNot", service.chkScrapProcess(userScrapDTO));

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
                               @RequestParam String user_id){
        UserScrapDTO userScrapDTO = new UserScrapDTO();
        userScrapDTO.setUser_id("테스트");
        userScrapDTO.setRcp_seq(Integer.parseInt(rcp_seq));
        service.scrapProcess(userScrapDTO);
        //mav.addObject("rcp_seq", rcp_seq);
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
    public String rcpWriteProMethod(RecipeDTO recipeDTO, String[] manual_txt,
                                    @RequestParam(value="cateArr", required=false) List<String> cateArr,
                                    MultipartFile[] upload_manual, HttpServletRequest request){
        // 본문 작성
        MultipartFile mainFile = recipeDTO.getUpload();
        if(!mainFile.isEmpty()){
            UUID random = saveCopyFile(mainFile, request, 0);
            recipeDTO.setFilename(random+"_"+mainFile.getOriginalFilename());
            recipeDTO.setFilepath("/media/recipe/");
        }

        service.writeProcess(recipeDTO);
        // validation!!

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
        System.out.println(cateArr);
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


package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dao.ProduceDAO;
import com.jjeopjjeop.recipe.dto.ProduceDTO;

import java.io.File;
import java.util.*;

import com.jjeopjjeop.recipe.pagenation.Pagenation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
class ProduceServiceImp implements ProduceService {

    @Autowired
    private ProduceDAO produceDAO;

    public ProduceServiceImp() {
    }

    @Override
    public void writeProcess(ProduceDTO produceDTO, MultipartFile file) throws Exception{
        String fileName = imageStore(file);

        produceDTO.setProduce_image(fileName);
        produceDTO.setProduce_image_path("/media/produce/" + fileName);

        produceDAO.write(produceDTO);
    }

    @Override
    public List<ProduceDTO> produceList(Map<String, Object> map) {
        return produceDAO.produceList(map);
    }

    @Override
    public List<ProduceDTO> produceListSort(Map<String, Object> map) {

        return produceDAO.produceListSort(map);
    }

    //판매글 작성과 판매글 수정을 위한 이미지 저장 메소드
    public String imageStore(MultipartFile file) throws Exception{
        String fileName;
        if(!file.isEmpty()) {//업로드한 파일이 있으면
            // 저장하기
            String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\media\\produce";
            UUID uuid = UUID.randomUUID();
            fileName = uuid + "_" + file.getOriginalFilename();
            File saveFile = new File(projectPath, fileName);
            file.transferTo(saveFile);
        }else {
            fileName = "";
        }

        return fileName;
    }

    ////판매글 수정
    //사진파일을 변경했을때: 기존의 사진파일경로에 가서 사진파일 자체 삭제하고, 새로운 거 넣기.
    //사진파일을 변경하지 않았을때: 기존의 파일이 들어가도록
    //사진을 변경하든 말든 여기서는 produce_image=null, produce_image_path=null이다. 파일을 넣어
    @Override
    public void produceUpdate(ProduceDTO produceDTO, MultipartFile file) throws Exception{
        String fileName;

        if(file.isEmpty()){
            //파일이 안바뀌어서 비어있다면
            //원래이미지를 바꾸지말고 그대로 둔다.
            String originalProduceImage = produceViewProcess(produceDTO.getProduce_num()).getProduce_image();
            String originalProduceImagePath = produceViewProcess(produceDTO.getProduce_num()).getProduce_image_path();
            produceDTO.setProduce_image(originalProduceImage);
            produceDTO.setProduce_image_path(originalProduceImagePath);
        }else {
            //파일이 바뀌어서 비어있지 않다면
            ////media폴더에 있는 이미지파일도 같이 삭제
            String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\media\\produce\\"
                    + produceDAO.produceView(produceDTO.getProduce_num()).getProduce_image(); //이미지 파일 경로
            File deleteFile = new File(filePath);
            if(deleteFile.exists()) {
                deleteFile.delete(); //삭제
            }

            //새로운 사진 저장.
            fileName = imageStore(file);
            produceDTO.setProduce_image(fileName);
            produceDTO.setProduce_image_path("/media/produce/" + fileName);
        }
        produceDAO.produceUpdate(produceDTO);
    }

    //판매 중지
    @Override
    public void produceUpdateSale(int produce_num) {
        produceDAO.produceUpdateSale(produce_num);
    }

    @Override
    public ProduceDTO produceViewProcess(int produce_num) {
        return produceDAO.produceView(produce_num);
    }

    //페이지 처리를 위해 판매글(필터링) 개수 세기
    @Override
    public int produceFilterCount(int produce_type) {
        return produceDAO.produceFilterCount(produce_type);
    }

    //페이지처리를 위한 판매글(정렬) 개수세기
    @Override
    public int produceSortCount(int sort) {
        return produceDAO.produceSortCount(sort);
    }

    @Override
    public String searchSellerBusinessName(String user_id) {
        return produceDAO.searchSellerBusinessName(user_id);
    }

    //하영 통합검색에 필요.
    @Override
    public List<ProduceDTO> findProduceByKeyword(String keyword) {
        return produceDAO.findProduceByKeyword(keyword);
    }

    @Override
    public List<ProduceDTO> findProduceByKeywordWithPaging(String keyword, Pagenation pagenation) {
        Map<String, Object> map = new HashMap<>();
        map.put("keyword",keyword);
        map.put("startRow",pagenation.getStartRow());
        map.put("endRow",pagenation.getEndRow());


        return produceDAO.findProduceByKeywordWithPaging(map);
    }

    @Override
    public List<ProduceDTO> getPopularProduceList() {
        List<Integer> produceNum = produceDAO.getFourProduceNumOrderBySoldCount();
        List<ProduceDTO> products = new ArrayList<>();

        for (Integer produceId : produceNum) {
            products.add(produceDAO.produceView(produceId));
        }
        return products;
    }



}

package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dao.ProduceDAO;
import com.jjeopjjeop.recipe.dto.ProduceDTO;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.jjeopjjeop.recipe.dto.RecipePageDTO;
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
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\media\\produce";
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);
        file.transferTo(saveFile);

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


    @Override
    public void produceDeleteProcess(int produce_num) {

        ////media폴더에 있는 이미지파일도 같이 삭제
        //이미지 파일 경로
        String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\media\\produce\\"
                + produceDAO.produceView(produce_num).getProduce_image();
        File deleteFile = new File(filePath);

        // 파일이 존재하는지 체크 존재할경우 true, 존재하지않을경우 false <-이렇게 꼭해야하나?
        if(deleteFile.exists()) {
            deleteFile.delete(); //삭제
            System.out.println("파일을 삭제하였습니다.");
        } else {
            System.out.println("파일이 존재하지 않습니다.");
        }
        produceDAO.produceDelete(produce_num);
    }

    @Override
    public ProduceDTO produceViewProcess(int produce_num) {
        return produceDAO.produceView(produce_num);
    }

    @Override
    public void produceUpdateProcess(ProduceDTO produceDTO) {
        produceDAO.produceUpdate(produceDTO);
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

    //하영 통합검색에 필요.
    @Override
    public List<ProduceDTO> findProductsByKeyword(String keyword) {
        return produceDAO.findProductsByKeyword(keyword);
    }

    @Override
    public List<ProduceDTO> findProductsByKeywordWithPaging(String keyword, RecipePageDTO pageDTO) {
        Map<String, Object> map = new HashMap<>();
        map.put("keyword",keyword);
        map.put("startRow",pageDTO.getStartRow());
        map.put("endRow",pageDTO.getEndRow());
        log.info("map info={},{},{}",keyword,pageDTO.getStartRow(),pageDTO.getEndRow());

        return produceDAO.findProductsByKeywordWithPaging(map);
    }


}
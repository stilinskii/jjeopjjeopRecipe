
package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dao.ProduceDAO;
import com.jjeopjjeop.recipe.dto.ProduceDTO;

import java.io.File;
import java.util.List;
import java.util.UUID;

import com.jjeopjjeop.recipe.dto.RecipePageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public List<ProduceDTO> produceListProcess(RecipePageDTO recipePageDTO) {
        return produceDAO.produceList(recipePageDTO);
    }

    @Override
    public List<ProduceDTO> produceListTypeProcess(int type) {
        return produceDAO.produceListType(type);
    }

    @Override
    public void produceDeleteProcess(int produce_num) {
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

    //페이지 처리를 위해 판매글 개수 세기
    @Override
    public int countProcess() {
        return produceDAO.produceCount();
    }


}
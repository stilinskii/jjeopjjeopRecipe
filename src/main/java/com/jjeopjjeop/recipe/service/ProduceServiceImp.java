
package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dao.ProduceDAO;
import com.jjeopjjeop.recipe.dto.ProduceDTO;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
class ProduceServiceImp implements ProduceService {
    @Autowired
    private ProduceDAO dao;

    public ProduceServiceImp() {
    }

    public void writeProcess(ProduceDTO produceDto, MultipartFile file) throws Exception{
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);
        file.transferTo(saveFile);

        produceDto.setProduce_image(fileName);
        produceDto.setProduce_image_path("/files/" + fileName);

        dao.write(produceDto);
    }

    public List<ProduceDTO> produceListProcess() {
        return dao.produceList();
    }

    public List<ProduceDTO> produceListTypeProcess(int type) {
        return dao.produceListType(type);
    }

    public void produceDeleteProcess(int produce_num) {
        dao.produceDelete(produce_num);
    }

    public ProduceDTO produceViewProcess(int produce_num) {
        return dao.produceView(produce_num);
    }

    public void produceUpdateProcess(ProduceDTO produceDto) {
        dao.produceUpdate(produceDto);
    }
}
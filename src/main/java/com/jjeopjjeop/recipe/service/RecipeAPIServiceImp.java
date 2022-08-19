package com.jjeopjjeop.recipe.service;

import com.jjeopjjeop.recipe.dto.ManualDTO;
import com.jjeopjjeop.recipe.dto.RecipeDTO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeAPIServiceImp implements RecipeAPIService{
    @Override
    public List<RecipeDTO> list(int num) {
        List<RecipeDTO> getApiList = new ArrayList<>();
        try {
            URL url = new URL("https://openapi.foodsafetykorea.go.kr/api/f25a7b4b1923449dbe5d/COOKRCP01/json/1/5");
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

                recipeDTO.setRcp_seq((i + 1) * -1);
                recipeDTO.setUser_id("식품의약품안전처");
                recipeDTO.setRcp_name(info.get("RCP_NM").toString());
                recipeDTO.setFilepath(info.get("ATT_FILE_NO_MAIN").toString());

                getApiList.add(recipeDTO);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return getApiList;
    }

    @Override
    public RecipeDTO view(int num) {
        RecipeDTO recipeDTO = new RecipeDTO();
        num = num * -1;
        try {
            URL url = new URL("https://openapi.foodsafetykorea.go.kr/api/f25a7b4b1923449dbe5d/COOKRCP01/json/"+num+"/"+num);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String result = br.readLine();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
            JSONObject cookrcp01 = (JSONObject) jsonObject.get("COOKRCP01");
            JSONArray row = (JSONArray) cookrcp01.get("row");

            JSONObject info = (JSONObject) row.get(0);

            recipeDTO.setRcp_seq(num * -1);
            recipeDTO.setUser_id("식품의약품안전처");
            recipeDTO.setRcp_name(info.get("RCP_NM").toString());
            recipeDTO.setFilepath(info.get("ATT_FILE_NO_MAIN").toString());

            List<ManualDTO> manualDTOList = new ArrayList<>();
            int manual_no = 1;
            while (info.get("MANUAL0"+manual_no) != null && !info.get("MANUAL0"+manual_no).toString().isBlank()){
                ManualDTO manualDTO = new ManualDTO();
                //manualDTO.setRcp_seq(i * -1);
                manualDTO.setManual_txt(info.get("MANUAL0"+manual_no).toString().replaceAll("[a-z]$",""));
                manualDTOList.add(manualDTO);
                manual_no++;
            }

            while (info.get("MANUAL"+manual_no) != null && !info.get("MANUAL"+manual_no).toString().isBlank()){
                ManualDTO manualDTO = new ManualDTO();
                //manualDTO.setRcp_seq(i * -1);
                manualDTO.setManual_txt(info.get("MANUAL"+manual_no).toString().replaceAll("[a-z]$",""));
                manualDTOList.add(manualDTO);
                manual_no++;
            }

            recipeDTO.setManualDTOList(manualDTOList);

        }catch (Exception e){
            e.printStackTrace();
        }

        return recipeDTO;
    }
}

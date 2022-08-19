package com.jjeopjjeop.recipe.service;

import java.net.URI;
import java.net.URISyntaxException;

import com.jjeopjjeop.recipe.dto.KakaoPayApprovalVO;
import com.jjeopjjeop.recipe.dto.KakaoPayReadyVO;
import com.jjeopjjeop.recipe.dto.PayDTO;
import com.jjeopjjeop.recipe.dto.ProduceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import lombok.extern.java.Log;

@Service
@Log
public class KakaoPay {

    private static final String HOST = "https://kapi.kakao.com";

    private KakaoPayReadyVO kakaoPayReadyVO;
    private KakaoPayApprovalVO kakaoPayApprovalVO;

    @Autowired
    private ProduceDTO produceDTO;

    //결제 준비
    public String kakaoPayReady(ProduceDTO produceDTO) {

        RestTemplate restTemplate = new RestTemplate();
        //Spring에서 지원하는 객체로 간편하게 Rest 방식 API를 호출할 수 있는 Spring 내장 클래스
        // json, xml 응답을 모두 받을 수 있습니다.
        //Rest API 서비스를 요청 후 응답 받을 수 있으며 HTTP 프로토콜의 메소드(ex. GET, POST, DELETE)들에 적합한 메소드들 제공
        //https://blog.naver.com/hj_kim97/222295259904

        // 서버로 요청할 Header//////////////////////////
        HttpHeaders headers = new HttpHeaders();  //서버와 클라이언트간 전송할 수 있는 메시지인 HTTP요청/응답은 Header와 Body로 구성된다.
        //헤더에는 바디와 요청/응답에 대한 정보를 포함한다. 헤더는 다시 General Header, Request/Response Header, Entity Header로 구성된다.
        //헤더의 프로퍼티는 Name-Value의 쌍으로 설정됨.
        // (https://blueyikim.tistory.com/1999)
        headers.add("Authorization", "KakaoAK " + "12cb9446ee47cea604831d2271b6f9e6");
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        // 서버로 요청할 Body////////////////////////////
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        //Map은 key-value로 저장되는 형태. 그중에 MultiValueMap은 key의 중복을 허용한다. (https://taehoung0102.tistory.com/182)

        for(PayDTO payDTO : produceDTO.getPayDTOList()){
            params.add("partner_order_id", payDTO.getPay_num().toString());  //String만 들어가서 String으로 바꿔줘야함.
            params.add("quantity", payDTO.getQuantity().toString());
            params.add("total_amount", payDTO.getTotal_price().toString());
        };
        params.add("cid", "TC0ONETIME");

        params.add("partner_user_id", produceDTO.getUser_id());
        params.add("item_name", produceDTO.getProduce_name());

        params.add("tax_free_amount", "0");
        params.add("approval_url", "http://localhost:8081/kakaoPaySuccess");
        params.add("cancel_url", "http://localhost:8081/kakaoPayCancel");
        params.add("fail_url", "http://localhost:8081/kakaoPaySuccessFail");

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers); //헤더와 바디를 HttpEntity에 넣어줌.
        //HttpEntity클래스는 HTTP요청또는 응답에 해당하는 HttpHeader와 HttpBody를 포함하는 클래스(https://esoongan.tistory.com/118)

        try {
            kakaoPayReadyVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body, KakaoPayReadyVO.class);
            //postForObject: POST 요청을 보내고 객체로 결과를 반환받는다
            //postForObject(보낼주소, 보낼것, 결과로 받을 객체)

            log.info("" + kakaoPayReadyVO);
/*
INFO 10804 --- [nio-8081-exec-5] com.jjeopjjeop.recipe.service.KakaoPay   : KakaoPayReadyVO(tid=T2f36a1e3fba4dcf8ce3,
    next_redirect_pc_url=https://online-pay.kakao.com/mockup/v1/4461842b91907170a8693dfbb7c0b73ea6acab5679ed2a3d16a8bb80239c5e18/info,
    created_at=Thu Aug 11 02:19:42 KST 2022)
 */

            return kakaoPayReadyVO.getNext_redirect_pc_url(); //마지막 return 값으로 redirect url을 불러와 결제가 완료되면 해당 주소로 가게끔 설정해 놓는다.
//getNext_redirect_pc_url: 요청한 클라이언트가 PC 웹일 경우 카카오톡으로 결제 요청 메시지(TMS)를 보내기 위한 사용자 정보 입력 화면 Redirect URL
 //QR코드나 폰번호 입력화면 나옴.
        } catch (RestClientException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return "/pay"; //이게 뭘의미하는지 모르겠음. ->얘는 그냥 무시하는 듯. 맨밑에 코드 참조하기.

    }
///////////////////////////////////////////////////////////////////////////////////////////////
/////////결제 승인: 사용자가 결제 수단을 선택하고 비밀번호를 입력해 결제 인증을 완료한 뒤, 최종적으로 결제 완료 처리를 하는 단계. 결제 승인 API를 호출하면 결제 준비 단계에서 시작된 결제건이 승인으로 완료 처리
    //https://developers.kakao.com/docs/latest/ko/kakaopay/single-payment#approve

    public KakaoPayApprovalVO kakaoPayInfo(String pg_token, ProduceDTO produceDTO) {  //^&     public KakaoPayApprovalVO kakaoPayInfo(String pg_token)
        //pg_token: 결제승인 요청을 인증하는 토큰. 사용자 결제 수단 선택 완료 시, approval_url로 redirection해줄 때 pg_token을 query string으로 전달
        log.info("KakaoPayInfoVO............................................");

        //이거넣으면 정상 작동함. 이거대신에 이 메소드로 데이터가 담긴 produceDTO에 와야하는데 그걸 모르겠음.
/*
        produceDTO.setUser_id("hee");
        produceDTO.setProduce_name("호박 3개");

        for(PayDTO payDTO : produceDTO.getPayDTOList()){
            payDTO.setPay_num(25);
            payDTO.setQuantity(1);
            payDTO.setTotal_price(5000);
        }
*/
        //////

        RestTemplate restTemplate = new RestTemplate();

        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "12cb9446ee47cea604831d2271b6f9e6");
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("tid", kakaoPayReadyVO.getTid());
        params.add("partner_user_id", produceDTO.getUser_id());
        params.add("pg_token", pg_token);

        for(PayDTO payDTO : produceDTO.getPayDTOList()){
            params.add("partner_order_id", payDTO.getPay_num().toString());  //String만 들어가서 String으로 바꿔줘야함.
            params.add("total_amount", payDTO.getTotal_price().toString());
        };


        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        try {
            kakaoPayApprovalVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/approve"), body, KakaoPayApprovalVO.class);
            log.info("" + kakaoPayApprovalVO);

            return kakaoPayApprovalVO;
            //https://codevang.tistory.com/211
            //return이 두개 있다. try에서 return까지 정상적으로 도달하면 try의 return이 적용되고 이 이후의 return은 무시된다.
        } catch (RestClientException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;//try에서 return까지 정상적으로 도달하면 얘는 무시된다.
    }

}

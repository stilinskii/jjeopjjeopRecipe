# 쩝쩝박사

> 레시피를 공유하고 검색할 수 있는 웹사이트입니다. <br> 레시피와 더불어 식재료들을 판매할 수 있고 <br> 커뮤니티 게시판에서 레시피 후기를 남기는 등 소통을 할 수 있습니다.

<br>
<br>

## 요구사항

---

![팀프로젝트 역할분담](https://user-images.githubusercontent.com/96387509/187103328-3288ff52-5982-4b4a-8ca1-e06a535321c7.png)

<br>
<br>

## ERD

---

<br>
<br>

## 팀원 및 맡은 역할 소개

<hr>
임하영
(팀장)

- 인덱스 + 통합검색 구현
- 커뮤니티 게시판

김민지

- 회원

이희연

- 관리자

백다현

- 레시피

서정아

- 쇼핑

<br>
<br>

## 사용기술

```
Backend: Java 11, Spring boot 2.7.2, Gradle, MyBatis, thymeleaf, Ajax
DB : Oracle 11.2
Front : HTML5, CSS, JavaScript, JQuery, Bootstrap
Tool : IntelliJ, VS Code, GitHub
```

<br>
<br>

## 쩝쩝박사 기능 소개

---

### 인덱스, 통합검색

```
처음 방문하면 나오는 페이지.
재료나 레시피 이름을 통해 레시피와 상품 통합검색으로 넘어갑니다.
페이지에는 스크랩 순, 판매량 순으로 정렬된 레시피와 상품을 볼 수 있고
더보기를 통해 각각 페이지에 들어갈 수 있습니다.
```

![image](https://user-images.githubusercontent.com/96387509/187108033-b7a94b25-0476-4ac4-a145-4c8223e9fe3f.png)

![image](https://user-images.githubusercontent.com/96387509/187108076-ddc30e07-4470-4e8e-a42f-4f8b62f43a47.png)

![totalsearch](https://user-images.githubusercontent.com/96387509/187107594-cc21a5a4-f2d1-46e9-9ca4-774d64b6236a.gif)

<br>
<br>

### 레시피

<br>
<br>

### 쇼핑

<br>
<br>

### 커뮤니티 게시판

```
자유글과 레시피 후기를 남길 수 있는 커뮤니티 게시판.
카테고리마다 글쓰기 양식이 다른 것이 이 게시판의 핵심 기능입니다.
그외에 상세검색, 좋아요, 댓글 기능이 있습니다.
```

- 게시판 index

  ![image](https://user-images.githubusercontent.com/96387509/187107964-d46ef565-df77-4bd7-96a7-0cff70a388fb.png)
  <br>
  <br>

- 게시판 글쓰기
  ![communityform](https://user-images.githubusercontent.com/96387509/187107512-dddabc56-4b8b-49b9-afda-9154eae8e982.gif)
  <br>
  <br>
- 상세검색
  ![communityformDetialSearch](https://user-images.githubusercontent.com/96387509/187107660-24b067df-7ccf-4bdc-a585-a469e451e97e.gif)
  <br>
  <br>
- 댓글
  ![communityComment](https://user-images.githubusercontent.com/96387509/187107765-746cbcf5-39e5-4a91-b9e6-064e630ba517.gif)

<br>
<br>

### 회원

<br>
<br>

### 관리자

```
- 회원 관리
  전체 회원목록, 회원 상세보기, 회원 탈퇴
- 레시피 관리
  전체 레시피 목록(신고순), 레시피 삭제, 레시피 상세보기
- 판매자 관리
  판매자 신청 목록, 판매자 승인, 판매자 승인취소
- 게시판 관리
  게시물 전체 목록(신고순), 게시물 상세보기, 게시물 삭제
- 판매자 등록
  판매자 등록 페이지
```

- 관리자 메인
  ![메인](https://user-images.githubusercontent.com/104446775/187861254-35e6ce54-bdd1-4808-ba35-7165601de7d7.PNG)

- 회원 관리
  ![회원관리페이지](https://user-images.githubusercontent.com/104446775/187858549-9923b987-19ca-483c-9e4e-a23d115ce018.PNG)
  ![회원상세페이지](https://user-images.githubusercontent.com/104446775/187858943-ac621238-15e3-4908-aca1-f69749f8559f.PNG)
  ![아이디 검색결과](https://user-images.githubusercontent.com/104446775/187859202-3766608e-c0cb-4fcd-a2d0-4d46e2799f3a.PNG)

- 레시피 관리
  ![레시피관리1](https://user-images.githubusercontent.com/104446775/187859457-59469cf1-50ef-45b4-9f6b-06496a1b4a1d.PNG)

- 판매자 관리
  ![판매자관리 승인목록](https://user-images.githubusercontent.com/104446775/187859907-4c4bc289-f7c0-4e40-a163-71e509c5b417.PNG)
  ![판매자관리 승인취소](https://user-images.githubusercontent.com/104446775/187860013-35a2c2ed-a07a-4b64-a3fb-63c003925a1e.PNG)

- 게시판 관리
  ![게시판 목록](https://user-images.githubusercontent.com/104446775/187860596-84e1b102-8ebf-4eb7-a3db-866a56be9fb4.PNG)

- 판매자 등록
  ![판매자 등록](https://user-images.githubusercontent.com/104446775/187862654-8468e730-5f0e-4320-a0c2-0f07a869da66.PNG)

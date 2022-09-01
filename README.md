# wanted_clone API 서버

---



## 소개 	

원티드(https://www.wanted.co.kr/) 백엔드 서버를 클론코딩한 프로젝트 입니다. 



### 개발 기간

2022.08.20 ~ 2022.09.02



### 개발에 참여한 사람

- [17wook2] (https://github.com/17wook2)

- [Hamkua] (https://github.com/Hamkua)

  



## 서버 구조

![제목을 입력해주세요_-001](https://user-images.githubusercontent.com/59720037/187969676-ac9241ba-3878-4004-9519-6f2bf2cfc525.png)


## API 도메인

-  커뮤니티 API

-  회사 API

-  채용 API

-  이벤트 API

-  인사이트 API

-  이력서 API

- 유저 API

  

---

 

## 프로젝트 구조

``` src
    ├─main
    │  ├─java
    │  │  └─com
    │  │      └─mockrc8
    │  │          └─app
    │  │              ├─domain
    │  │              │  ├─community 
    │  │              │  │  ├─controller - 컨트롤러 로직
    │  │              │  │  ├─dto - 컨트롤러와 서비스로직 데이터 사이의 데이터 이동
    │  │              │  │  ├─mapper - db와의 연결을 위한 mybatis 매퍼
    │  │              │  │  ├─service - 컨스롤러와 mapper 사이의 비즈니스 로직
    │  │              │  │  └─vo - db에서 가져온 테이블과 매핑된 객체
    │  │              │  ├─company
    │  │              │  ├─employment
    │  │              │  ├─event
    │  │              │  ├─insight
    │  │              │  ├─resume
    │  │              │  ├─upload
    │  │              │  └─user
    │  │              └─global 
    │  │                  ├─config - 설정사항 관련 폴더
    │  │                  │  └─security - 스프링 시큐리티 설정파일
    │  │                  ├─error - 에러처리에 관련된 폴더
    │  │                  │  └─exception - 예외처리와 관련된 로직이 담긴 폴더
    │  │                  │      ├─company - 회사 예외처리 
    │  │                  │      ├─employment - 채용 예외처리
    │  │                  │      ├─event - 이벤트 예외처리
    │  │                  │      ├─upload - 업로드 예외처리
    │  │                  │      └─User - 유저 예외처리
    │  │                  ├─infra - aws 관련 로직
    │  │                  ├─oAuth - 카카오 oauth 로그인 인증 로직
    │  │                  │  ├─controller
    │  │                  │  ├─dto
    │  │                  │  └─service
    │  │                  └─util - 유틸리티 폴더
    │  └─resources - 자원 파일이 담긴 폴더
    │      ├─config
    │      ├─mybatis
    │      │  └─mapper - mybatis sql xml 파일
    │      ├─static
    │      └─templates
    
```

----



## 개발 일지

https://www.notion.so/softsquared/d171e2c23d9a43b7bfe488461b382395



## 배포중인 서버

https://prod.wook2.xyz

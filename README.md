## Reserve-Web-App

***
## 🚀 프로젝트 개요

2021/8/2 프로젝트 시작

코로나19 예방접종 예약 시스템 백엔드 Clone 프로젝트

(+ Github를 이용한 협업 연습)
***

## 🚀 사용 기술

프레임워크: `Spring boot` 2.5.x

IDE: `Intellij`

ORM: `JPA` (Hibernate, Spring Data JPA)

DBMS: `MySQL`, 테스트(`h2`)

템플릿엔진: `Thymeleaf`

- Spring Dependencies
  - `Spring web`
  - `Spring Data JPA`
  - `Spring Security`
  - `Spring Oauth2 client`
  - `Spring Validation`
  - `Lombok`
  - `ModelMapper`
***
  
## 🔎 구현 예정 기능
***
### Admin 측 기능 (수정중)

- `병원등록`
  - 병원에 대한 기본 정보 입력 (이름, 주소 ...)
  - 예약가능날짜, 예약가능시간, 일일 최대예약가능 인원, 시간당 최대예약가능 인원, 접종가능백신(종류별 수량) 입력
- `등록된 병원 목록` (조회)
  - 병원 삭제
- `등록된 병원 상세 페이지` (조회, 수정)
  - 등록된 병원 정보 수정
- `현황 조회`
  - 날짜별, 시간별 예약현황 확인
  - 잔여백신 현황 확인
***

### Client 측 기능 (수정중)

- `본인인증` ( `Oauth2` - `Naver`, `Kakao`, `Google`)
- `예약`
  - 병원선택 -> 예약날짜선택 -> 예약시간선택 -> 백신선택
- `예약확인`
- `예악한 병원 길찾기`


***

# 🚀 ERD
![img.png](img.png)
- 한 명의 `USER`는 한 개의 `예약서(RESERVE_ITEM)`를 가진다.
- 한 개의 `예약서(RESERVE_ITEM)`는 한 개의 `병원(HOSPITAL)`을 가진다.
- 한 명의 `관리자(ADMIN)`은 여러 `병원(HOSPITAL)`을 가진다.
- 한 개 병원은 여러 `예약가능날짜(AVAILABLE_DATE)`를 가진다.
- 한 개 `예약가능날짜(AVAILABLE_DATE)`는 여러 `예약가능시간(AVAILABLE_TIME)`을 가진다.
- 한 개 병원은 여러 `백신(VACCINE)`을 가진다. (여러 종류의 백신)

***

# 🌡 기능시연

#### < Admin 병원등록 >
![병원등록](https://user-images.githubusercontent.com/77182648/129870381-3a4e2745-8251-469f-b295-3c96601b6204.gif)

#### < Admin 병원정보 수정 >
![병원수정](https://user-images.githubusercontent.com/77182648/129870530-0e36293c-10fb-4916-93f7-034eefc21bd5.gif)

#### < 예약하고자 하는 병원 검색 >
![병원검색](https://user-images.githubusercontent.com/77182648/129870729-941ff973-9cbf-41d0-81b2-aa335ac7700b.gif)

#### < 백신 예약 >
![예약](https://user-images.githubusercontent.com/77182648/129870596-955dc0f7-1e1f-463f-a641-12f78f41c27b.gif)

#### < Admin 예약현황 조회 >
![예약현황](https://user-images.githubusercontent.com/77182648/129870824-095d42ff-ab35-4340-bd54-872c0f8273b3.gif)

***



## 👨‍👨‍👦 팀원
***
### BACKEND
### 🧑‍💻 김대현
<a href="https://velog.io/@dhk22" target="_blank"><img src="https://img.shields.io/badge/Velog-20c997?style=flat-square&logo=Vimeo&logoColor=white" width="42"/></a>
<a href="mailto:zbeld123@gmail.com" ><img src="https://img.shields.io/badge/Gmail-d14836?style=flat-square&logo=Gmail&logoColor=white" width="42"/></a>
<a href="https://github.com/kimdh-hi" targe="_blank" ><img src="https://img.shields.io/badge/Github-181717?style=flat-square&logo=Github&logoColor=white" width="42"/></a>

### 🧑‍💻 홍성진
<a href="https://velog.io/@sungjin0757" target="_blank"><img src="https://img.shields.io/badge/Velog-20c997?style=flat-square&logo=Vimeo&logoColor=white" width="42"/></a>
<a href="mailto:sungjin0757@naver.com" ><img src="https://img.shields.io/badge/Naver-03C75A?style=flat-square&logo=Naver&logoColor=white" width="42"/></a>
<a href="https://github.com/sungjin0757" targe="_blank" ><img src="https://img.shields.io/badge/Github-181717?style=flat-square&logo=Github&logoColor=white" width="42"/></a>

***
### Frontend
### 🧑‍💻 김윤성
<a href="mailto:rladbstjd96@naver.com" ><img src="https://img.shields.io/badge/Naver-03C75A?style=flat-square&logo=Naver&logoColor=white" width="42"/></a>
<a href="https://github.com/f-dev-kys" targe="_blank" ><img src="https://img.shields.io/badge/Github-181717?style=flat-square&logo=Github&logoColor=white" width="42"/></a>

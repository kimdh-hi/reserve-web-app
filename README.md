## Reserve-Web-App

***
## π νλ‘μ νΈ κ°μ

2021/8/2 νλ‘μ νΈ μμ

μ½λ‘λ19 μλ°©μ μ’ μμ½ μμ€ν λ°±μλ Clone νλ‘μ νΈ

(+ Githubλ₯Ό μ΄μ©ν νμ μ°μ΅)
***

## π μ¬μ© κΈ°μ 

νλ μμν¬: `Spring boot` 2.5.x

IDE: `Intellij`

ORM: `JPA` (Hibernate, Spring Data JPA)

DBMS: `MySQL`, νμ€νΈ(`h2`)

ννλ¦Ώμμ§: `Thymeleaf`

- Spring Dependencies
  - `Spring web`
  - `Spring Data JPA`
  - `Spring Security`
  - `Spring Oauth2 client`
  - `Spring Validation`
  - `Lombok`
  - `ModelMapper`
***
  
## π κ΅¬ν μμ  κΈ°λ₯
***
### Admin μΈ‘ κΈ°λ₯ (μμ μ€)

- `λ³μλ±λ‘`
  - λ³μμ λν κΈ°λ³Έ μ λ³΄ μλ ₯ (μ΄λ¦, μ£Όμ ...)
  - μμ½κ°λ₯λ μ§, μμ½κ°λ₯μκ°, μΌμΌ μ΅λμμ½κ°λ₯ μΈμ, μκ°λΉ μ΅λμμ½κ°λ₯ μΈμ, μ μ’κ°λ₯λ°±μ (μ’λ₯λ³ μλ) μλ ₯
- `λ±λ‘λ λ³μ λͺ©λ‘` (μ‘°ν)
  - λ³μ μ­μ 
- `λ±λ‘λ λ³μ μμΈ νμ΄μ§` (μ‘°ν, μμ )
  - λ±λ‘λ λ³μ μ λ³΄ μμ 
- `νν© μ‘°ν`
  - λ μ§λ³, μκ°λ³ μμ½νν© νμΈ
  - μμ¬λ°±μ  νν© νμΈ
***

### Client μΈ‘ κΈ°λ₯ (μμ μ€)

- `λ³ΈμΈμΈμ¦` ( `Oauth2` - `Naver`, `Kakao`, `Google`)
- `μμ½`
  - λ³μμ ν -> μμ½λ μ§μ ν -> μμ½μκ°μ ν -> λ°±μ μ ν
- `μμ½νμΈ`
- `μμν λ³μ κΈΈμ°ΎκΈ°`


***

# π ERD
![img.png](img.png)
- ν λͺμ `USER`λ ν κ°μ `μμ½μ(RESERVE_ITEM)`λ₯Ό κ°μ§λ€.
- ν κ°μ `μμ½μ(RESERVE_ITEM)`λ ν κ°μ `λ³μ(HOSPITAL)`μ κ°μ§λ€.
- ν λͺμ `κ΄λ¦¬μ(ADMIN)`μ μ¬λ¬ `λ³μ(HOSPITAL)`μ κ°μ§λ€.
- ν κ° λ³μμ μ¬λ¬ `μμ½κ°λ₯λ μ§(AVAILABLE_DATE)`λ₯Ό κ°μ§λ€.
- ν κ° `μμ½κ°λ₯λ μ§(AVAILABLE_DATE)`λ μ¬λ¬ `μμ½κ°λ₯μκ°(AVAILABLE_TIME)`μ κ°μ§λ€.
- ν κ° λ³μμ μ¬λ¬ `λ°±μ (VACCINE)`μ κ°μ§λ€. (μ¬λ¬ μ’λ₯μ λ°±μ )

***

# π‘ κΈ°λ₯μμ°

#### < Admin λ³μλ±λ‘ >
![αα§αΌαα―α«αα³αΌαα©α¨](https://user-images.githubusercontent.com/77182648/129870381-3a4e2745-8251-469f-b295-3c96601b6204.gif)

#### < Admin λ³μμ λ³΄ μμ  >
![αα§αΌαα―α«αα?αα₯αΌ](https://user-images.githubusercontent.com/77182648/129870530-0e36293c-10fb-4916-93f7-034eefc21bd5.gif)

#### < μμ½νκ³ μ νλ λ³μ κ²μ >
![αα§αΌαα―α«αα₯α·αα’α¨](https://user-images.githubusercontent.com/77182648/129870729-941ff973-9cbf-41d0-81b2-aa335ac7700b.gif)

#### < λ°±μ  μμ½ >
![αα¨αα£α¨](https://user-images.githubusercontent.com/77182648/129870596-955dc0f7-1e1f-463f-a641-12f78f41c27b.gif)

#### < Admin μμ½νν© μ‘°ν >
![αα¨αα£α¨αα§α«ααͺαΌ](https://user-images.githubusercontent.com/77182648/129870824-095d42ff-ab35-4340-bd54-872c0f8273b3.gif)

***



## π¨βπ¨βπ¦ νμ
***
### BACKEND
### π§βπ» κΉλν
<a href="https://velog.io/@dhk22" target="_blank"><img src="https://img.shields.io/badge/Velog-20c997?style=flat-square&logo=Vimeo&logoColor=white" width="42"/></a>
<a href="mailto:zbeld123@gmail.com" ><img src="https://img.shields.io/badge/Gmail-d14836?style=flat-square&logo=Gmail&logoColor=white" width="42"/></a>
<a href="https://github.com/kimdh-hi" targe="_blank" ><img src="https://img.shields.io/badge/Github-181717?style=flat-square&logo=Github&logoColor=white" width="42"/></a>

### π§βπ» νμ±μ§
<a href="https://velog.io/@sungjin0757" target="_blank"><img src="https://img.shields.io/badge/Velog-20c997?style=flat-square&logo=Vimeo&logoColor=white" width="42"/></a>
<a href="mailto:sungjin0757@naver.com" ><img src="https://img.shields.io/badge/Naver-03C75A?style=flat-square&logo=Naver&logoColor=white" width="42"/></a>
<a href="https://github.com/sungjin0757" targe="_blank" ><img src="https://img.shields.io/badge/Github-181717?style=flat-square&logo=Github&logoColor=white" width="42"/></a>

***
### Frontend
### π§βπ» κΉμ€μ±
<a href="mailto:rladbstjd96@naver.com" ><img src="https://img.shields.io/badge/Naver-03C75A?style=flat-square&logo=Naver&logoColor=white" width="42"/></a>
<a href="https://github.com/f-dev-kys" targe="_blank" ><img src="https://img.shields.io/badge/Github-181717?style=flat-square&logo=Github&logoColor=white" width="42"/></a>

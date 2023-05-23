### 개요 ###

JPA 실습을 위해서 상품 주문 및 회원 관리기능을 가진 서비스를 객체지향적으로 개발하는 과정을  
학습

<br>
<br>

### 요구사항 ###


+ 회원은 상품을 주문할 수 있다.
+ 주문 시 여러 종류의 상품을 선택할 수 있다.
+ 모든 데이터는 등록일과 수정일이 있어야 한다.
+ 회원기능
    + 회원 등록
    + 회원 조회
+ 상품 기능
    + 상품 등록
      + 상품을 카테고리로 구분할 수 있다.
      + 상품의 종류는 음반, 도서, 영화가 있고 이후 더 확장될 수 있다.
    + 상품 수정
    + 상품 조회
+ 주문 기능
    + 상품 주문
      + 상품을 주문할 때 배송정보를 입력할 수 있다. 주문과 배송은 1:1 관계다
    + 주문 내역 조회
    + 주문 취소

<br>
<br>

### 사용기술 스택 ###
+ JPA(Hibernate 6.2.2)
+ SpringBoot 3.1
+ Maven
+ 
  <br>
  <br>

### UML(클래스 다이어그램) ###


![초기 UML](https://github.com/sungwoon129/JPA-Practical-Example/assets/43958570/08a718c2-e78d-4758-b2a4-64d0f2e2dadb)

1차 버전.데이터 중심의 설계
<hr>
<br>
<br>
<br>

![버전2 UML](https://github.com/sungwoon129/JPA-Practical-Example/assets/43958570/1d696057-4faf-435b-bfef-017f04ba1620)

2차 버전. 외래키를 참조하는 방식의 DB 중심적 설계에서 객체를 참조하는 객체지향적 방법으로 변경
<hr>
<br>
<br>
<br>



![ver 3-다양한 연관관계 매핑 drawio](https://github.com/sungwoon129/JPA-Practical-Example/assets/43958570/c9ca3b87-99bd-4708-a73a-e35a40a6d124)

3차 버전. 새로운 요구사항이 생겨 배송과 카테고리 도메인을 추가
<hr>
<br>
<br>
<br>


![ver 4 - 상속관계 매핑](https://github.com/sungwoon129/JPA-Practical-Example/assets/43958570/bc82f573-71b4-4db7-99d0-5107a2478ef0)

4차 버전. 추가 요구사항(상품의 종류, 등록일과 수정일)에 맞춰 도메인 모델 변화
<hr>
<br>
<br>
<br>



![ver 5 - 값 타입](https://github.com/sungwoon129/JPA-Practical-Example/assets/43958570/83854198-4d4e-4c98-a183-993825010e39)

5차 버전. Member 와 Delivery 테이블에서 공통적으로 사용되는 주소관련 속성을 묶어서 Address라는 Embedded Type으로 표현
<hr>
<br>
<br>
<br>


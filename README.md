# Fashion_search

## 개요

Fashion_search 는 패션 상품 검색 서비스 입니다.
각 상품은 아래와 같은 정보를 가지고 있습니다.
* 상품일련번호
* 카테고리
* 브랜드
* 가격
* 생성일
* 수정일

## 개발환경
* JDK : 21
* Gradle : 8.10
* Spring Boot : 3.2.3

## 빌드
    ./gradlew :fashion-search-api:clean :fashion-search-api:build -Dspring.profiles.active=local

## 실행
    java -jar ./fashion-search-api/build/libs/fashion-search-api.jar --spring.profiles.active=local

## API 명세
Swagger : <http://localhost:8082/swagger-ui/index.html>

## Test
    ./gradlew :fashion-search-api:clean :fashion-search-api:test -Dspring.profiles.active=local

## 주요 Package 구조

* com.fashion_search.api : API 관련 패키지
  * com.fashion_search.api.controller : API 의 입력과 출력 값을 정의
  * com.fashion_search.api.controller.dto : API 의 요청(Request)과 응답(Response)시 사용되는 Data Transfer Object
  * com.fashion_search.api.facade : API 가 수행하는 기능의 명세를 정의
  * com.fashion_search.api.facade.mapper : 외부에서 전달받은 DTO 를 내부에서 사용하는 DTO 로 변환하거나 그 반대로 변환하는 Mapper
  * com.fashion_search.api.service : 비지니스 로직
  * com.fashion_search.api.config : API 관련 설정
* com.fashion_search.domain.h2 : H2 DB 관련 패키지
  * com.fashion_search.domain.h2.entity : JPA Entities
  * com.fashion_search.domain.h2.repository : JPA Repository
  * com.fashion_search.domain.h2.dto.command : 내부에 사용되는 요청 DTO
  * com.fashion_search.domain.h2.dto.info : 내부에서 사용되는 응답 DTO
  * com.fashion_search.domain.h2.config : DB 관련 설정




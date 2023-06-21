# 프로젝트명
> 배치프로그램 예제(+스케줄러)
[![Java Version](https://img.shields.io/badge/JDK-17-red)](https://spring.io)
[![Spring Boot](https://img.shields.io/badge/springboot-3.1.0-green)](https://spring.io)
[![Gradle Package](https://img.shields.io/badge/gradle-7.6.1-blue)](https://gradle.org)

음식의 유통기한이 지나면 데이터의 상태를 변경해주는 배치프로그램 예제.

![](../header.png)

## 기술 스택
#JAVA
#SPRING BOOT
#Gradle
#Postgresql
#JPA
#Spring-batch

## 운영 체제
MAC OS:

## PostgreSQL 설치
```sh
brew install postgresql
```


## 사용 예제

스크린 샷 & 코드 첨부

_더 많은 예제와 사용법은 [Wiki][wiki]를 참고하세요._

## 의존성

```sh
    implementation 'org.springframework.boot:spring-boot-starter-batch'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    compileOnly 'org.projectlombok:lombok'
    developmentOnly 'org.springframework.boot:spring-boot-devtools'
    runtimeOnly 'org.postgresql:postgresql'
    annotationProcessor 'org.projectlombok:lombok'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.springframework.batch:spring-batch-test'
```

## 업데이트 내역

* 0.2.1
    * 수정: 문서 업데이트 (모듈 코드 동일)
* 0.2.0
    * 수정: `setDefaultXYZ()` 메서드 제거
    * 추가: `init()` 메서드 추가
* 0.1.1
    * 버그 수정: 
* 0.1.0
    * 첫 출시
    * 수정: 
* 0.0.1
    * 작업 진행 중

## 정보

이름 – [@트위터 주소](https://twitter.com/dbader_org) – wn1331@gmail.com



<!-- Markdown link & img dfn's -->
[npm-image]: https://img.shields.io/npm/v/datadog-metrics.svg?style=flat-square
[npm-url]: https://npmjs.org/package/datadog-metrics
[npm-downloads]: https://img.shields.io/npm/dm/datadog-metrics.svg?style=flat-square
[travis-image]: https://img.shields.io/travis/dbader/node-datadog-metrics/master.svg?style=flat-square
[travis-url]: https://travis-ci.org/dbader/node-datadog-metrics
[wiki]: https://spring.io/projects/spring-batch

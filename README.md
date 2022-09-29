## 오디AD User API
오디AD User API 입니다.

DEV API URL : [관리자 API](http://dev.nextnow.kr:28090/swagger-ui/index.html)
## Style Guide

오디AD API 는 google java style guide 를 따릅니다.

IDE(Integrated Development Environment) 별 적용 방법은 아래 포스팅을 참고해 주세요

Eclipse : https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=rlagyska3319&logNo=221038715725

IntelliJ : https://velog.io/@injoon2019/IntelliJ%EC%97%90-Google-Java-Style-Guide-%EC%A0%81%EC%9A%A9%ED%95%98%EA%B8%B0

# Protocol
아래 protocol 로 구현을 해야 합니다.
- Restful-API Level2
- Json
- JWT

## Environment
- JAVA 11 
- Spring Boot 2.0.5
- Maven
- Spring JPA
- Mybatis
- MySQL
- Lombok

## API Document
Swagger 를 이용하여 자동으로 API 문서를 생성하게 합니다.
[swagger-ui.md](./Swagger-UI.md)

## Configuration
application의 공통적인 configuration 은 application.yml 로 작성을 합니다.

application.yml 에서 구분자(---) 를 통해 profile 구분을 할 수 있지만 되도록 파일로 구분하여 사용 해야 합니다.

-- application.yml

-- application-dev.yml

-- application-local.yml

-- application-op.yml



## Log

기본은 logback-spring.xml 을 이용한다.

profile 별 로그 설정이 필요 할 경우 application.yml 과 같이 환경별 파일 생성 합니다.

그 후 아래와 같이 환경별 application.yml 에 logging.config 를 명시 합니다.

application-dev.yml

```java
logging:
	config: classpath:logback-prod.xml
```

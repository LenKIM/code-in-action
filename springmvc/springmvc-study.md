```java
package com.example.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Slf4j
@RestController
public class RequestHeaderController {

    @RequestMapping("/headers")
    public String headers(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpMethod httpMethod,
                          Locale locale,
                          @RequestHeader MultiValueMap<String, String> headerMap,
                          @RequestHeader("host") String host,
                          @CookieValue(value = "myCookie", required = false) String cookie) {
        log.info("request={}", request);
        log.info("response={}", response);
        log.info("httpMethod={}", httpMethod);
        log.info("locale={}", locale);
        log.info("headerMap={}", headerMap);
        log.info("header host={}", host);
        log.info("myCookie={}", cookie);
        return "ok";
    }
}
```



```
2021-08-09 23:12:51.637  INFO 58364 --- [nio-8080-exec-1] c.e.s.b.request.RequestHeaderController  : request=org.apache.catalina.connector.RequestFacade@482de95b
2021-08-09 23:12:51.638  INFO 58364 --- [nio-8080-exec-1] c.e.s.b.request.RequestHeaderController  : response=org.apache.catalina.connector.ResponseFacade@66304524
2021-08-09 23:12:51.638  INFO 58364 --- [nio-8080-exec-1] c.e.s.b.request.RequestHeaderController  : httpMethod=GET
2021-08-09 23:12:51.638  INFO 58364 --- [nio-8080-exec-1] c.e.s.b.request.RequestHeaderController  : locale=en_KR
2021-08-09 23:12:51.638  INFO 58364 --- [nio-8080-exec-1] c.e.s.b.request.RequestHeaderController  : headerMap={content-type=[application/json], user-agent=[PostmanRuntime/7.28.3], accept=[*/*], postman-token=[f5dd09dd-d03b-481e-9493-ca19d3783774], host=[localhost:8080], accept-encoding=[gzip, deflate, br], connection=[keep-alive]}
2021-08-09 23:12:51.638  INFO 58364 --- [nio-8080-exec-1] c.e.s.b.request.RequestHeaderController  : header host=localhost:8080
2021-08-09 23:12:51.638  INFO 58364 --- [nio-8080-exec-1] c.e.s.b.request.RequestHeaderController  : myCookie=null
```

> 참고
> @Conroller 의 사용 가능한 파라미터 목록은 다음 공식 메뉴얼에서 확인할 수 있다.
> https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-annarguments

> 참고
> @Conroller 의 사용 가능한 응답 값 목록은 다음 공식 메뉴얼에서 확인할 수 있다.
> https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-annreturn-types

---

HTTP 메세지 컨버터 스터디



![image-20210810211302263](https://tva1.sinaimg.cn/large/008i3skNgy1gtbydv48jzj30my0csab0.jpg)

@ResponseBody 사용

-  HTTP의 BODY에 문자 내용을 직접 반환
-  `viewResolver` 대신에 `HttpMessageConverter` 가 동작
-  기본문자처리: `StringHttpMessageConverter`
-  기본객체처리: `MappingJackson2HttpMessageConverter`
-  byte처리 등등 기타 여러 HttpMessageConverter가 기본으로 등록되어 있음



**응답의 경우 클라이언트의 HTTP Accept 해더와 서버의 컨트롤러 반환 타입 정보 둘을 조합해서 HttpMessageConverter 가 선택된다**



**스프링MVC는 다음의 경우에 HTTP 메시지 컨버터를 적용한다.**

-  HTTP요청: `@RequestBody`,`HttpEntity(RequestEntity)`,
-  HTTP응답: `@ResponseBody`,`HttpEntity(ResponseEntity)`,
   

스프링 부트는 다양한 메시지 컨버터를 제공하는데, `대상 클래스 타입과 미디어 타입` 둘을 체크해서 사용여부를 결정한다. 만약 만족하지 않으면 다음 메시지 컨버터로 우선순위가 넘어간다.



Class Type 과 미디어타입으로 구분한다.

우선순위는 ClassType 1순위



그러나 안되는 케이스가 존재한다.

```
content-type: text/html

@RequestMapping void hello(@RequetsBody HelloData data) {}
```



HandlerMethodArgumentResolver 를 통해서 파라미터를 유연하게 처리합니다.

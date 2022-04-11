# 스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술

## 웹 서버 (Web Server)

- HTTP 기반
- 정적 리소스만 표현할 수 있다.
  - HTML, IMG, JS, CSS ...
- 예) NGINX, APACHE

## 웹 애플리케이션 서버 (WAS : Web Application Server)

- HTTP 기반
- 웹 서버 기능 + 프로그램 코드를 실행하는 애플리케이션 로직 추가
  - 동적 HTML, HTTP API (JSON)
  - 서블릿, JSP, 스프링 MVC
- 예) 톰캣, Jetty, Undertow
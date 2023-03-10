# study_java_exception

## 서버에서 예외발생시에 흐름
1. WAS(여기까지 전파) <- 필터 <- 서블릿 <- 인터셉터 <- 컨트롤러(예외발생)
2. WAS `/error-page/500` 다시 요청 -> 필터 -> 서블릿 -> 인터셉터 -> 컨트롤러(/error- page/500) -> View 


- 과거에는 뷰의 위치를 xml로 작성하였으나, 스프링 부트 사용후에는 디폴트위치가 템플릿 아래 error폴더이다.


----


## 뷰 선택 우선순위는 다음과 같다. [BasicErrorController]
1.뷰템플릿 
- resources/templates/error/500.html 
- resources/templates/error/5xx.html


2.정적리소스(static,public) 
- resources/static/error/400.html 
- resources/static/error/404.html
- resources/static/error/4xx.html 


3.적용 대상이 없을 때 뷰 이름(error) 
- resources/templates/error.html


----


## 결론 - 
에러 공통 처리 기능을 변경하고 싶으면 ErrorController 인터페이스를 상속 받아서 구현하거나, 
BasicErrorController 상속 받아서 기능을 추가하면 된다. 



스프링 부트가 기본으로 제공하는 ExceptionResolver 는 다음과 같다. 
HandlerExceptionResolverComposite 에 다음 순서로 등록되어있다.

1. ExceptionHandlerExceptionResolver


@ExceptionHandler(IllegalArgumentException.class)


특정 패키지 지정 - 제일 자주 사용
@RestControllerAdvice("hello.exception.api")


2. ResponseStatusExceptionResolver


어노테이션으로 지정가능한 ExceptionResolver
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "error.bad")
  
  
3. DefaultHandlerExceptionResolver 우선 순위가 가장 낮다  


스프링에서 자체적으로 사용하는 ExceptionResolver
DefaultHandlerExceptionResolver


-참고
https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-ann-exceptionhandler-args

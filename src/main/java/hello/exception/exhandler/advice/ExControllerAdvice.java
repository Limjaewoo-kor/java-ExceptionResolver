package hello.exception.exhandler.advice;

import hello.exception.api.ApiExceptionController;
import hello.exception.api.ApiExceptionV2Controller;
import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
//@RestControllerAdvice(annotations = RestController.class)
//어노테이션을 통으로 지정한다.
//@RestControllerAdvice("hello.exception.api")
//특정 패키지 지정 - 제일 자주 사용
@RestControllerAdvice(assignableTypes = {ApiExceptionController.class, ApiExceptionV2Controller.class})
//특정 컨트롤러 지정
//대상을 지정하지않으면 모든 컨트롤러에 적용된다.
public class ExControllerAdvice {

    //아래와 같이 하면 해당 컨트롤러 내에서 해당 에러가 발생할시 그 에러를 잡아서 modelandview를 만들지않고 json형식으로 리턴한다.
    // @ResponseStatus를 하지않으면 json으로 에러 메시지는 나가지만, 흐름자체는 정상흐름으로 돌아와서 httpstatus가 200이 나오는 현상이 발생한다.
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e){
        log.info("[exceptionHandler] ex : ",e);
        return new ErrorResult("BAD",e.getMessage());
    }

    //어노테이션에 UserException.class를 생략하고 아래만 넣어도 된다.
    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandler(UserException e) {
        log.error("[exceptionHandler] ex : ",e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity(errorResult,HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e){
        log.error("[exceptionHandler] ex : ",e);
        return new ErrorResult("EX","내부 오류");
    }
}

package hello.exception.api;

import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class ApiExceptionV2Controller {


//    //아래와 같이 하면 해당 컨트롤러 내에서 해당 에러가 발생할시 그 에러를 잡아서 modelandview를 만들지않고 json형식으로 리턴한다.
//    // @ResponseStatus를 하지않으면 json으로 에러 메시지는 나가지만, 흐름자체는 정상흐름으로 돌아와서 httpstatus가 200이 나오는 현상이 발생한다.
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ErrorResult illegalExHandler(IllegalArgumentException e){
//        log.info("[exceptionHandler] ex : ",e);
//        return new ErrorResult("BAD",e.getMessage());
//    }
//
//    //어노테이션에 UserException.class를 생략하고 아래만 넣어도 된다.
//    @ExceptionHandler
//    public ResponseEntity<ErrorResult> userExHandler(UserException e) {
//        log.error("[exceptionHandler] ex : ",e);
//        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
//        return new ResponseEntity(errorResult,HttpStatus.BAD_REQUEST);
//    }
//
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler
//    public ErrorResult exHandler(Exception e){
//        log.error("[exceptionHandler] ex : ",e);
//        return new ErrorResult("EX","내부 오류");
//    }

    @GetMapping("/api2/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {

        if(id.equals("ex")) {
            throw new RuntimeException("잘못된 사용자");
            // 스프링 부트는 BasicErrorController 를 이용하여 기본 에러페이지를 만들어준다. json / model and view 상황에 따라 둘다 생성해줌
            // BasicErrorController 확장하면 json메시지도 변경할수있지만, @ExceptionHandler 를 이용하는것을 추천한다.
        }
        if(id.equals("bad")){
            throw new IllegalArgumentException("잘못된 아규먼트");
        }
        if(id.equals("bad1")){
            throw new IndexOutOfBoundsException("아웃오브바운드 에러");
        }
        if(id.equals("bad2")){
            throw new IllegalStateException("스테이트 에러");
        }

        if(id.equals("user-ex")) {
            throw new UserException("사용자 오류");
        }

        return new MemberDto(id,"hello" + id);
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String memberId;
        private String name;
    }
}

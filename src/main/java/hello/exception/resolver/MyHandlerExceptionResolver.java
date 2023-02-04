package hello.exception.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {



    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        try {
            if(ex instanceof IllegalArgumentException){
                log.info("IllegalArgumentException resolver to 400");
//                Exception을 캐치해서 sendError로 바꿔치기 하여 비어있는 모델엔뷰를 반환한다.
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,ex.getMessage());
                return new ModelAndView();
            }
            if(ex instanceof IndexOutOfBoundsException){
                log.info("IndexOutOfBoundsException resolver to json");
//                Exception을 캐치해서 json을 반환한다.
                response.getWriter().println("hello json error");
                return new ModelAndView();

            }
            if(ex instanceof IllegalStateException){
                log.info("IllegalStateException resolver to index11");
//                Exception을 캐치해서 새로운 화면으로 반환한다.
                return new ModelAndView("index11");
            }

        }catch (IOException e) {
            log.error("resolver ex",e);
        }
        return null;
    }
}

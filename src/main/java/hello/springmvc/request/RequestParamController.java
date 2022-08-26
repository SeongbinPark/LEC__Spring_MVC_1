package hello.springmvc.request;

import hello.springmvc.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


// HTTP 요청 파라미터 - 쿼리 파라미터, HTML Form

@Slf4j
@Controller
public class RequestParamController {

    /**
     * 서블릿 시절 사용하던 쿼리 스트링 추출 방식
     */
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}", username, age);

        response.getWriter().write("ok");
    }

    /**
     * RequestParam 애노테이션을 활용해 내부 속성으로 쿼리 스트링의 Key를 작성해서 해당 key 의 value 추출
     */
    @RequestMapping("/request-param-v2")
    @ResponseBody
    public String requestParamV2(@RequestParam("username") String memberName, @RequestParam("age") int memberAge) {
        log.info("username={}, age={}", memberName, memberAge);
        return "ok";
    }

    /**
     * 매개변수의 이름이 쿼리파라미터의 key와 이름이 동일하면 속성을 빼도 동작한다.
     */
    @RequestMapping("/request-param-v3")
    @ResponseBody
    public String requestParamV3(@RequestParam String username, @RequestParam int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * 쿼리 파라미터의 Key가 일치하면 @RequestParam을 제거해도 동작한다.
     */
    @RequestMapping("/request-param-v4")
    @ResponseBody
    public String requestParamV4(String username, int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @RequestMapping("/request-param-required")
    @ResponseBody
    public String requestParamRequired(@RequestParam(required = true) String username,
                                       @RequestParam(required = false) Integer age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @RequestMapping("/request-param-default")
    @ResponseBody
    public String requestParamDefault(@RequestParam(defaultValue = "park") String username,
                                      @RequestParam(defaultValue = "20") int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * 지금까지 요청 파라미터를 하나하나씩 받고 있는데 Map을 이용해 한 번에 받을 수도 있다.
     * 파라미터의 값이 1개가 확실하면 Map을 써도 되지만 그렇지 않다면 MultiValueMap을 사용하자.
     */
    @RequestMapping("/request-param-map")
    @ResponseBody
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }

    @RequestMapping("/model-attribute-v1")
    @ResponseBody
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
//        HelloData helloData = new HelloData();
//        helloData.setUsername(username);
//        helloData.setAge(age);
//        하나하나 get, set 하기 귀찮다.

        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        return helloData.toString();
    }


    /**
     * @ModelAttribute는 생략할 수 있는데 @RequestParam도 생략이 가능하다.
     * 그럼 이 HelloData는 어느 애노테이션이 생략되었는지 어떻게 판단해야 할까?
     * 스프링은 해당 생략시 다음과 같은 규칙을 적용한다.
     * ⇒ String, int, Integer 같은 단순 타입 = @RequestParam
     * ⇒ 나머지 = @ModelAttribute
     */
    @RequestMapping("/model-attribute-v2")
    @ResponseBody
    public String modelAttributeV2(HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        return helloData.toString();
    }
}

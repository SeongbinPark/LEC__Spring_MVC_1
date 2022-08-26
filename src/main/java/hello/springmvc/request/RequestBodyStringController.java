package hello.springmvc.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;


// HTTP message body에 데이터를 직접 담아서 요청하는 방법


@Slf4j
@Controller
public class RequestBodyStringController {


    /**
     * Body-Text
     */
    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String string = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", string);

        response.getWriter().write("ok");
    }

    /**
     *  매개변수에서 바로 inputStream 과 writer를 받을수도 있다.
     */
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer writer) throws IOException {
        String string = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", string);
//        InputStream(Reader): HTTP 요청 메세지 바디의 내용을 직접 조회
//        OutputStream(Writer): HTTP 응답 메세지의 바디에 직접 결과 출력
        writer.write("ok");
    }


    /**
     * HttpEntity: HTTP header, body 정보를 편리하게 조회할 수 있게 해준다.
     * ⇒ 메세지 바디 정보를 직접 조회 가능(getBody())
     * ⇒ 요청 파라미터를 조회하는 기능과 관계 없다.(@RequestParam, @ModelAttribute)
     * ⇒ 응답에서도 사용할 수 있다.
     */
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) {

        log.info("messageBody={}", httpEntity.getBody());

        return new HttpEntity<>("ok");
    }

    /**
     * @RequestBody라는 애노테이션을 이용해 더 간편하게 요청 메세지 바디를 받을수도 있다.
     * 헤더정보 필요하면 @RequestHeader
     */
    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String body) {

        log.info("messageBody={}", body);

        return "ok";
    }
}

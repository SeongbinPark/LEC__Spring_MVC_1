package hello.springmvc.requestmapping;

import hello.springmvc.LogTestController;
import lombok.Value;
import org.springframework.web.bind.annotation.*;

// 요청이 왔을때 어떤 컨트롤러에서 매핑을 할지 조사해서 매핑을 진행하는 것.

@RestController
public class MappingController {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MappingController.class);

    /**
     * 기본 요청
     * 둘 다 허용한다. /hello-basic, /hello-basic/
     * HTTP 메서드 모두 허용
     */
    @RequestMapping("/hello-basic")
    public String helloBasic() {
        log.info("helloBasic");
        return "ok";
    }

    /**
     * method 특정 HTTP 메서드 요청만 허용
     */
    @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)
    public String mappingGetv1() {
        log.info("mappingGetV1");
        return "ok";
    }

    /**
     * 메서드별 축약 어노테이션
     */
    @GetMapping(value = "/mapping-get-v2")
    public String mappingGetV2() {
        log.info("mapping-get-v2");
        return "ok";
    }

    /**
     * PathVariable 사용 -> 파라미터명 같으면 생략가능
     *
     * @PathVariable("userId") String userid -> @PathVariable userId
     */
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable String userId) {
        log.info("mappginPath userId={}", userId);
        return "ok";
    }

    /**
     * PathVariable 다중 사용
     */
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable String orderId) {
        log.info("mappginPath userId={}, orderId={}", userId, orderId);
        return "ok";
    }

    /**
     * 파라미터로 추가 매핑
     * params="mode"
     * params="mode=debug"
     * params={"mode=debug", "data=good"}
     */
    @GetMapping(value = "/mapping-param", params = "mode=debug")
    public String mappingParam() {
        log.info("mappingParam");
        return "ok";
    }

    /**
     * 특정 헤더로 추가 매핑
     * headers="mode"
     * headers="mode=debug"
     */
    @GetMapping(value = "/mapping-header", headers = "mode=debug")
    public String mappingHeader() {
        log.info("mappingHeader");
        return "ok";
    }

    /**
     * Content-Type 헤더 기반 추가 매핑 Media Type
     * consumes="application/json",
     * consumes="application/*",
     */
    @PostMapping(value = "/mapping-consume", consumes = "application/json")
    public String mappingConsumes() {
        log.info("mappingConsumes");
        return "ok";
    }

    /**
     * Accept 헤더 기반 Media Type
     * produces="text/html",
     * produces="text/*",
     */
    @PostMapping(value = "/mapping-produces", produces = "text/html")
    public String mappingProduces() {
        log.info("mappingProduces");
        return "ok";
    }
}

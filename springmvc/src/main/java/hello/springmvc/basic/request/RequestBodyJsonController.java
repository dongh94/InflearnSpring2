package hello.springmvc.basic.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyJsonController {

    /**
     * 요청 파라미터 vs HTTP 메시지 바디
     * 요청 파라미터를 조회하는 기능: @RequestParam , @ModelAttribute
     * HTTP 메시지 바디를 직접 조회하는 기능: @RequestBody, @ResponseBody
     * 이들을 사용하면 응답 결과를 HTTP 메시지 바디에 직접 담아서 전달할 수 있다. Object로 직접!!!
     * 물론 이 경우에도 view를 사용하지 않는다.
     *
     * @RequestBody (생략 불가능)
     * - 메시지 바디 정보를 직접 조회(@RequestParam X, @ModelAttribute X)
     * - HttpMessageConverter 사용 ->  MappingJackson2HttpMessageConverter 적용
     * (contenttype: application/json)
     *
     * @ResponseBody
     * - 메시지 바디 정보 직접 반환(view 조회X)
     * - HttpMessageConverter 사용 ->  MappingJackson2HttpMessageConverter 적용
     * (Accept: application/json)
     *
     * HTTP 요청시에 content-type이 application/json인지 꼭! 확인해야 한다.
     * 그래야 JSON을 처리할 수 있는 HTTP 메시지 컨버터가 실행된다.
     */


    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody = {}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());

        response.getWriter().write("ok");
    }

    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws JsonProcessingException {

        log.info("messageBody = {}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }
    // RequestBody 생략 불가능 하다 -> 생략하면 ModelAttribute 로 해석해서 요청 파라미터만을 바라본다.
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData helloData) {

        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }
    /**
     * HttpEntity: HTTP header, body 정보를 편리하게 조회
     * - 메시지 바디 정보를 직접 조회(@RequestParam X, @ModelAttribute X)
     * - HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter 적용 (String, Object 모두 사용가능)
     *
     * 응답에서도 HttpEntity 사용 가능
     * - 메시지 바디 정보 직접 반환(view 조회X)
     * - HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter 적용 (String, Object 모두 사용가능)
     */
    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity) {
        HelloData helloData = httpEntity.getBody();
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    // RequestBody 생략 불가능 하다 -> 생략하면 ModelAttribute 로 해석해서 요청 파라미터만을 바라본다.
    // RequestBody 자체를 객체로 설정하능하고,
    // Response 도 객체로 설정 가능하다.
    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData helloData) {

        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());

        return helloData;
    }
}

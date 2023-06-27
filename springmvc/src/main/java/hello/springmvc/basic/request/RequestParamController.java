package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {
    /**
     * 반환 타입이 없으면서 이렇게 응답에 값을 직접 집어넣으면, view 조회X
     */
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}", username, age);
        response.getWriter().write("ok");
    }

    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String username,
            @RequestParam("age") int age) {
        log.info("username = {}, age = {}", username, age);
        return "ok";
    }

    // 생략 가능 !!
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age) {
        log.info("username = {}, age = {}", username, age);
        return "ok";
    }

    // 더 생략 가능 !! 근데 너무 과해..
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {
        log.info("username = {}, age = {}", username, age);
        return "ok";
    }

    // 반드시 필요한 파라미터
    // "" != null
    // int 초깃값은 0 이라 null이 안되므로 500 에러 => Integer (Wrapper Class 초깃값은 null)
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = true) String username,
            @RequestParam(required = false) Integer age) {
        log.info("username = {}, age = {}", username, age);
        return "ok";
    }

    // Default 설정도 가능
    // 빈문자는 "" != null 이지만 Defalut의 경우 빈문자를 Default로 처리해준다.
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(required = true, defaultValue = "guest") String username,
            @RequestParam(required = false, defaultValue = "-1") int age) {
        log.info("username = {}, age = {}", username, age);
        return "ok";
    }

    // Map 활용 가능
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(
            @RequestParam Map<String, Object> paramMap) {
        log.info("username = {}, age = {}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }

    // Data -> ModelAttribute
    @ResponseBody
    @RequestMapping("/model-attribute-v0")
    public String modelAttributeV0(@RequestParam String username, @RequestParam int age) {
        HelloData helloData = new HelloData();
        helloData.setUsername(username);
        helloData.setAge(age);
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());
        log.info("helloData = {}", helloData);
        return "ok";
    }
    // @ModelAttribute 활용 -> Object + Param (Property)
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
//        HelloData helloData = new HelloData();
//        helloData.setUsername(username);
//        helloData.setAge(age);
//        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());
        log.info("helloData = {}", helloData);
        return "ok";
    }
    // @ModelAttribute 마저 생략
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        log.info("helloData = {}", helloData);
        return "ok";
    }
}

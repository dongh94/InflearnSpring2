package hello.springmvc.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogTestController {
    //getClass() == LogTestController.class
    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/log-test")
    public String logTest() {
        String name = "Spring Log Test";
        System.out.println("name = " + name);

        // LEVEL: TRACE > DEBUG > INFO > WARN > ERROR
        // 개발 서버는 debug 출력
        // 운영 서버는 info 출력
        log.trace("log trace = {}", name);
        log.debug("log debug = {}", name);
        log.info("log info = {}", name);
        log.warn("log warn = {}", name);
        log.error("log error = {}", name);

        // + 연산자 쓰면 안되는 이유 : 쓸데없는 메모리사용
        log.trace("log trace = " + name);

        return "ok";
    }
}

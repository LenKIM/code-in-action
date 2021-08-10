package com.example.springmvc.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogTestController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/log-test")
    public String logTest(){
        String name = "Spring";

        System.out.println("name = " + name);

        logger.trace("trace log = {}", name); // 로그에 남지 않음
        logger.debug("debug log = {}", name); // 로그에 남지 않음
        logger.info("info log = {}", name);
        logger.warn("warn log = {}", name);
        logger.error("error log = {}", name);
        return "ok";
    }
}

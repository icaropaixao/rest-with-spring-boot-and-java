package br.com.icaro.paixao.controllers;

import br.com.icaro.paixao.services.PersonServices;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;



@RequestMapping("/api/test/v1")
@RestController
public class TestoLogController {

    private Logger logger = LoggerFactory.getLogger(TestoLogController.class.getName());


    @GetMapping("/test")
    public String testLog(){

        logger.debug("This is an DEBUG message");
        logger.info("This is an INFO message");
        logger.warn("This is an WARN message");
        logger.error("This is an ERROR message");

        return "Logs generated successfully";
    }


}

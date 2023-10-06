package com.safetynet.safetynetalerts.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class CustomErrorController implements ErrorController {

    private final Logger logger = LoggerFactory.getLogger(CustomErrorController.class);

    @RequestMapping("/error")
    public String handlerError(HttpServletResponse response){
        int code  = response.getStatus();
        logger.error("Error with code "+ code +". Be sure that your request is correct.");
        return "error.html";
    }

    public String getErrorPath() {
        return "error";
    }
}

package net.adsService.controller.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class ExceptionCatchController {

    private static final Logger LOGGER = LogManager.getLogger();

    @ExceptionHandler(Exception.class)
    public void exceptionCatch(Exception e, HttpServletResponse response) throws IOException {
        LOGGER.error("System error !!! ",e);
        response.setStatus(500);
        response.sendRedirect("/error-500");
    }
}

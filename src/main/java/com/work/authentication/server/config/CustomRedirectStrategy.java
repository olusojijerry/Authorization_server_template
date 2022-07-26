package com.work.authentication.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.work.authentication.server.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.DefaultRedirectStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomRedirectStrategy extends DefaultRedirectStrategy {


    private boolean wantsJson(HttpServletRequest request){
        String acceptHeader = request.getHeader("accept");

        if(acceptHeader != null && acceptHeader.trim().contains(Constant.JSON_APPLICATION_TYPE)){
            return true;
        }

        return false;
    }

    private void handleJsonResponse(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
        int status = response.getStatus();

        log.info("Response status ======" + status);

        //create response json object
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode res = objectMapper.createObjectNode();
        response.getStatus();
        res.put("success", true);
        res.put("message", "Authentication success");
        res.put("redirectUrl", url);

        response.setContentType(Constant.JSON_APPLICATION_TYPE);
        response.getWriter().write(res.toString());
    }

    @Override
    public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
        log.info("===== Custom redirect strategy =====");

        boolean wantsJson = wantsJson(request);

        log.info("=== Wants Json ===" + wantsJson);

        String redirectUrl = this.calculateRedirectUrl(request.getContextPath(), url);
        redirectUrl = response.encodeRedirectURL(redirectUrl);

        if(this.logger.isDebugEnabled()){
            this.logger.debug("Redirecting to '" + redirectUrl + "'" );
        }

        if(wantsJson){
            log.info("Handle wants json");
            this.handleJsonResponse(request, response, url);
        }else{
            response.sendRedirect(redirectUrl);
        }
    }
}

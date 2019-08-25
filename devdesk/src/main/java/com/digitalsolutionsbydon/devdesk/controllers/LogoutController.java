package com.digitalsolutionsbydon.devdesk.controllers;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LogoutController
{
    private static final Logger logger = LoggerFactory.getLogger(LogoutController.class);
    @Autowired
    private TokenStore tokenStore;
    @ApiOperation(value = "Revoke Token on Server Side", response=void.class)
    @GetMapping(value = "/logout")
    public ResponseEntity<?> logout(HttpServletRequest request)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null)
        {
            String tokenValue = authHeader.replace("Bearer", "")
                                          .trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

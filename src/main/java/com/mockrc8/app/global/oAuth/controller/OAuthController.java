package com.mockrc8.app.global.oAuth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oAuth")
public class OAuthController {

    @GetMapping()
    public ModelAndView socialLogin(ModelAndView mav){
        mav.setViewName("login");
        return mav;
    }

}

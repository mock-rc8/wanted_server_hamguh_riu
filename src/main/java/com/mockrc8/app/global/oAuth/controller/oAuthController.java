package com.mockrc8.app.global.oAuth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class oAuthController {

    @GetMapping()
    public String socialLogin(){
        return "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=e913a5ec326dd4fce079b58b11d899ba&redirect_uri=http://localhost:9000/login/kakao";
    }
//    @GetMapping("/login")
//    public ModelAndView socialLogin(ModelAndView mav){
//        mav.setViewName("login");
//        return mav;
//    }
}

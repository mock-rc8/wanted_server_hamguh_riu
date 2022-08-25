package com.mockrc8.app.domain.resume.controller;

import com.mockrc8.app.domain.resume.service.ResumeService;
import com.mockrc8.app.global.error.ErrorCode;
import com.mockrc8.app.global.error.exception.User.UserNotFoundException;
import com.mockrc8.app.global.oAuth.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/resumes")
public class ResumeController {

    private final ResumeService resumeService;

    @GetMapping()
    public ResponseEntity<Object> getUserResumes(@CurrentUser String userEmail){
        if(userEmail == null){
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        return resumeService.getResumes(userEmail);
    }

    @GetMapping("/{resumeId}")
    public ResponseEntity<Object> getUserResumeByResumeId(@CurrentUser String userEmail, @PathVariable Integer resumeId){
        if(userEmail == null){
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        return resumeService.getResumeDetailById(userEmail,resumeId);
    }
}

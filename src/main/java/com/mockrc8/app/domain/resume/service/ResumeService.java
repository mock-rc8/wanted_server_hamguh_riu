package com.mockrc8.app.domain.resume.service;

import com.mockrc8.app.domain.resume.mapper.ResumeMapper;
import com.mockrc8.app.domain.resume.vo.*;
import com.mockrc8.app.domain.user.service.UserService;
import com.mockrc8.app.domain.user.vo.User;
import com.mockrc8.app.global.config.BaseResponse;
import com.mockrc8.app.global.error.ErrorCode;
import com.mockrc8.app.global.error.exception.BusinessException;
import com.mockrc8.app.global.error.exception.User.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final UserService userService;
    private final ResumeMapper resumeMapper;
    public ResponseEntity<Object> getResumes(String userEmail) {
        final User user = userService.findUserByEmail(userEmail);
        if (user == null){
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        final List<Resume> resumes = resumeMapper.getResumesByUserId(user.getUser_id());
        final BaseResponse<List<Resume>> response = new BaseResponse<>(resumes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Object> getResumeDetailById(String userEmail, Integer resumeId) {
        final User user = userService.findUserByEmail(userEmail);
        if (user == null){
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        final List<Resume_language_skill> language = getLanguage(resumeId);

        return new ResponseEntity<>(language,HttpStatus.OK);
    }

    public Resume getResumeById(Integer resumeId){
        return resumeMapper.getResumeById(resumeId);
    }

    public List<Resume_award> getAward(Integer resumeId){
        return resumeMapper.getAward(resumeId);
    }
    public List<Resume_career> getCareer(Integer resumeId){
        return resumeMapper.getCareer(resumeId);
    }

    public List<Resume_education_degree> getDegree(Integer resumeId){
        return resumeMapper.getDegree(resumeId);
    }

    public List<Resume_tech_skill> getTechSkills(Integer resumeId){
        return resumeMapper.getTechSkills(resumeId);
    }
    public List<Resume_language_skill> getLanguage(Integer resumeId){
        return resumeMapper.getLanguage(resumeId);
    }




}

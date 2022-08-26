package com.mockrc8.app.domain.resume.service;

import com.mockrc8.app.domain.resume.dto.Award.AwardListDto;
import com.mockrc8.app.domain.resume.dto.Career.CareerDto;
import com.mockrc8.app.domain.resume.dto.Career.Career_accomplishmentDto;
import com.mockrc8.app.domain.resume.dto.Degree.DegreeListDto;
import com.mockrc8.app.domain.resume.dto.Language.Language_skillDto;
import com.mockrc8.app.domain.resume.dto.Language.Language_testDto;
import com.mockrc8.app.domain.resume.dto.TechSkill.PostResume_tech_skillDto;
import com.mockrc8.app.domain.resume.dto.TechSkill.Resume_tech_skillDto;
import com.mockrc8.app.domain.resume.mapper.ResumeMapper;
import com.mockrc8.app.domain.resume.vo.*;
import com.mockrc8.app.domain.user.service.UserService;
import com.mockrc8.app.domain.user.vo.User;
import com.mockrc8.app.global.config.BaseResponse;
import com.mockrc8.app.global.error.ErrorCode;
import com.mockrc8.app.global.error.exception.User.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Transactional
    public ResponseEntity<BaseResponse<Long>> postResumeCareer(CareerDto dto) {
        resumeMapper.postResumeCareer(dto);
        final Long career_id = dto.getCareer_id();
        dto.getAccomplishmentDtoList().forEach(acc ->
                acc.setCareer_id(career_id));
        postResumeCareer_accomplishment(dto.getAccomplishmentDtoList());
        final BaseResponse<Long> response = new BaseResponse<>("이력서 경력 저장 요청에 성공했습니다", career_id);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Transactional
    public void postResumeCareer_accomplishment(List<Career_accomplishmentDto> dto){
        resumeMapper.postResumeCareer_accomplishment(dto);
    }

    @Transactional
    public ResponseEntity<BaseResponse<ArrayList<Long>>> postResumeAward(AwardListDto dtos) {
        dtos.getAwardDtoList().forEach(resumeMapper::postResumeAward);
        final ArrayList<Long> IdList = new ArrayList<>();
        dtos.getAwardDtoList().forEach(acc -> IdList.add(acc.getResume_award_id()));
        final BaseResponse<ArrayList<Long>> response = new BaseResponse<>("이력서 수상 및 기타 저장 요청에 성공했습니다", IdList);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @Transactional
    public ResponseEntity<BaseResponse<ArrayList<Long>>> postResumeDegree(DegreeListDto dtos) {
        dtos.getDegreeDtoList().forEach(resumeMapper::postResumeDegree);
        final ArrayList<Long> IdList = new ArrayList<>();
        dtos.getDegreeDtoList().forEach(acc -> IdList.add(acc.getResume_education_degree_id()));
        final BaseResponse<ArrayList<Long>> response = new BaseResponse<>("이력서 학력 저장 요청에 성공했습니다.", IdList);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<BaseResponse<Long>> postResumeLanguage(Language_skillDto dtos) {
        resumeMapper.postLanguageSkill(dtos);
        final Long language_skill_id = dtos.getResume_language_skill_id();
        dtos.getLanguageTest().forEach(acc ->
                acc.setResume_language_skill_id(language_skill_id));
        postResumeLanguageTest(dtos.getLanguageTest());
        final BaseResponse<Long> response = new BaseResponse<>("이력서 외국어 저장 요청에 성공했습니다.", language_skill_id);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Transactional
    public void postResumeLanguageTest(List<Language_testDto> dto){
        resumeMapper.postResumeLanguageTest(dto);
    }

    public ResponseEntity<BaseResponse<Long>> postResumeTechSkills(Resume_tech_skillDto dtos) {
        final Long resumeId = dtos.getResume_id();
        dtos.getSkillName().forEach(name -> {
            final Long resumeTechSkillId = resumeMapper.getTechSkillId(name);
            resumeMapper.postResumeTechSkills(resumeId,resumeTechSkillId);
        });

        final BaseResponse<Long> response = new BaseResponse<>("이력서 개발 스킬 저장 요청에 성공했습니다", resumeId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}

package com.mockrc8.app.domain.resume.service;

import com.mockrc8.app.domain.resume.dto.Award.AwardListDto;
import com.mockrc8.app.domain.resume.dto.Career.CareerDto;
import com.mockrc8.app.domain.resume.dto.Career.Career_accomplishmentDto;
import com.mockrc8.app.domain.resume.dto.Degree.DegreeListDto;
import com.mockrc8.app.domain.resume.dto.Language.Language_skillDto;
import com.mockrc8.app.domain.resume.dto.Language.Language_testDto;
import com.mockrc8.app.domain.resume.dto.TechSkill.PostResume_tech_skillDto;
import com.mockrc8.app.domain.resume.dto.TechSkill.Resume_tech_skillDto;
import com.mockrc8.app.domain.resume.dto.file.PostResumeFileDto;
import com.mockrc8.app.domain.resume.mapper.ResumeMapper;
import com.mockrc8.app.domain.resume.vo.*;
import com.mockrc8.app.domain.user.service.UserService;
import com.mockrc8.app.domain.user.vo.User;
import com.mockrc8.app.global.config.BaseResponse;
import com.mockrc8.app.global.error.ErrorCode;
import com.mockrc8.app.global.error.exception.User.UserNotFoundException;
import com.mockrc8.app.global.error.exception.User.UserNotMatchedException;
import com.mockrc8.app.global.infra.AwsS3Service;
import com.mockrc8.app.global.util.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final UserService userService;
    private final ResumeMapper resumeMapper;
    private final AwsS3Service awsS3Service;
    public ResponseEntity<Object> getResumes(String userEmail) {
        final User user = userService.findUserByEmail(userEmail);
        if (user == null){
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        final List<Resume> resumes = resumeMapper.getResumesByUserId(user.getUser_id());
        final BaseResponse<List<Resume>> response = new BaseResponse<>(resumes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public Resume getResumeById(Integer resumeId){
        return resumeMapper.getResumeById(resumeId);
    }

    public ResponseEntity<Object> getAward(String userEmail ,Integer resumeId){
        final User user = userService.findUserByEmail(userEmail);
        final Resume resume = getResumeById(resumeId);
        if(Objects.equals(user.getUser_id(), resume.getUser_id())){
            final List<Resume_award> result = resumeMapper.getAward(resumeId);
            final BaseResponse<List<Resume_award>> baseResponse = new BaseResponse<>("수상경력 가져오기 요청에 성공했습니다.", result);
            return new ResponseEntity<>(baseResponse,HttpStatus.OK);
        }
        throw new UserNotMatchedException(ErrorCode.USER_NOT_MATCH);
    }
    public ResponseEntity<Object> getCareer(String userEmail, Integer resumeId){
        final User user = userService.findUserByEmail(userEmail);
        final Resume resume = getResumeById(resumeId);
        if(Objects.equals(user.getUser_id(), resume.getUser_id())){
            final List<Resume_career> result = resumeMapper.getCareer(resumeId);
            final BaseResponse<List<Resume_career>> baseResponse = new BaseResponse<>("경력 가져오기 요청에 성공했습니다.", result);
            return new ResponseEntity<>(baseResponse,HttpStatus.OK);
        }
        throw new UserNotMatchedException(ErrorCode.USER_NOT_MATCH);
    }

    public ResponseEntity<Object> getDegree(String userEmail ,Integer resumeId){
        final User user = userService.findUserByEmail(userEmail);
        final Resume resume = getResumeById(resumeId);
        if(Objects.equals(user.getUser_id(), resume.getUser_id())){
            final List<Resume_education_degree> result = resumeMapper.getDegree(resumeId);
            final BaseResponse<List<Resume_education_degree>> baseResponse = new BaseResponse<>("학위 가져오기 요청에 성공했습니다.", result);
            return new ResponseEntity<>(baseResponse,HttpStatus.OK);
        }
        throw new UserNotMatchedException(ErrorCode.USER_NOT_MATCH);
    }

    public ResponseEntity<Object> getTechSkills(String userEmail,Integer resumeId){
        final User user = userService.findUserByEmail(userEmail);
        final Resume resume = getResumeById(resumeId);
        if(Objects.equals(user.getUser_id(), resume.getUser_id())){
            final List<Resume_tech_skill> result = resumeMapper.getTechSkills(resumeId);
            final BaseResponse<List<Resume_tech_skill>> baseResponse = new BaseResponse<>("기술 스킬 가져오기 요청에 성공했습니다.", result);
            return new ResponseEntity<>(baseResponse,HttpStatus.OK);
        }
        throw new UserNotMatchedException(ErrorCode.USER_NOT_MATCH);
    }
    public ResponseEntity<Object> getLanguage(String userEmail,Integer resumeId){
        final User user = userService.findUserByEmail(userEmail);
        final Resume resume = getResumeById(resumeId);
        if(Objects.equals(user.getUser_id(), resume.getUser_id())){
            final List<Resume_language_skill> result = resumeMapper.getLanguage(resumeId);
            final BaseResponse<List<Resume_language_skill>> baseResponse = new BaseResponse<>("어학 경력 가져오기 요청에 성공했습니다.", result);
            return new ResponseEntity<>(baseResponse,HttpStatus.OK);
        }
        throw new UserNotMatchedException(ErrorCode.USER_NOT_MATCH);
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

    public BaseResponse<PostResumeFileDto> uploadResume(String userEmail, String category, MultipartFile multipartFile) {
        final User user = userService.findUserByEmail(userEmail);
        final String link = awsS3Service.uploadFileV1(category, multipartFile);
        String resumeName = user.getName() + "/upload/" + UUID.randomUUID();
        final PostResumeFileDto postResumeFileDto = new PostResumeFileDto();
        postResumeFileDto.setUser_id(user.getUser_id());
        postResumeFileDto.setName(resumeName);
        postResumeFileDto.setResume_link(link);
        resumeMapper.postResumeFile(postResumeFileDto);
        return new BaseResponse<>("이력서 파일 등록에 성공했습니다.", postResumeFileDto);
    }

    public ResponseEntity<ByteArrayResource> downloadResume(String userEmail, String resourcePath) {
        final User user = userService.findUserByEmail(userEmail);
        final ResumeWithLink resume = resumeMapper.findResumeByResourcePath(resourcePath);
        if(!Objects.equals(user.getUser_id(), resume.getUser_id())){
            throw new UserNotMatchedException(ErrorCode.USER_NOT_MATCH);
        }
        byte[] data = awsS3Service.downloadFileV1(resourcePath);
        ByteArrayResource resource = new ByteArrayResource(data);
        HttpHeaders headers = buildHeaders(resourcePath, data);

        return ResponseEntity
                .ok().headers(headers).body(resource);
    }
    private HttpHeaders buildHeaders(String resourcePath, byte[] data) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(data.length);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(CommonUtils.createContentDisposition(resourcePath));
        return headers;
    }

    public ResponseEntity<Object> patchResumeCareer(CareerDto dto) {
        resumeMapper.patchResumeCareer(dto);
        final BaseResponse<CareerDto> baseResponse = new BaseResponse<>("커리어 업데이트에 성공했습니다.", dto);
        return ResponseEntity.ok(baseResponse);
    }

    @Transactional
    public ResponseEntity<BaseResponse<ArrayList<Long>>> patchResumeAwards(AwardListDto dtos) {
        dtos.getAwardDtoList().forEach(resumeMapper::patchResumeAward);
        final ArrayList<Long> IdList = new ArrayList<>();
        dtos.getAwardDtoList().forEach(acc -> IdList.add(acc.getResume_award_id()));
        final BaseResponse<ArrayList<Long>> response = new BaseResponse<>("이력서 수상 및 기타 저장 요청에 성공했습니다", IdList);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}

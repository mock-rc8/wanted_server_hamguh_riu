package com.mockrc8.app.domain.resume.controller;

import com.mockrc8.app.domain.resume.dto.Award.AwardListDto;
import com.mockrc8.app.domain.resume.dto.Career.CareerDto;
import com.mockrc8.app.domain.resume.dto.Degree.DegreeListDto;
import com.mockrc8.app.domain.resume.dto.Language.Language_skillDto;
import com.mockrc8.app.domain.resume.dto.TechSkill.PostResume_tech_skillDto;
import com.mockrc8.app.domain.resume.dto.TechSkill.Resume_tech_skillDto;
import com.mockrc8.app.domain.resume.dto.file.PostResumeFileDto;
import com.mockrc8.app.domain.resume.service.ResumeService;
import com.mockrc8.app.global.config.BaseResponse;
import com.mockrc8.app.global.error.ErrorCode;
import com.mockrc8.app.global.error.exception.User.UserNotFoundException;
import com.mockrc8.app.global.infra.AwsS3Service;
import com.mockrc8.app.global.oAuth.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/{resumeId}/career")
    public ResponseEntity<Object> getCareer(@CurrentUser String userEmail,
                                                          @PathVariable Integer resumeId){
        if(userEmail == null){
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        return resumeService.getCareer(userEmail,resumeId);
    }

    @GetMapping("/{resumeId}/degree")
    public ResponseEntity<Object> getDegree(@CurrentUser String userEmail,
                                                          @PathVariable Integer resumeId){
        if(userEmail == null){
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        return resumeService.getDegree(userEmail,resumeId);
    }
    @GetMapping("/{resumeId}/award")
    public ResponseEntity<Object> getAward(@CurrentUser String userEmail,
                                            @PathVariable Integer resumeId){
        if(userEmail == null){
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        return resumeService.getAward(userEmail,resumeId);
    }

    @GetMapping("/{resumeId}/tech-skill")
    public ResponseEntity<Object> getTechSkill(@CurrentUser String userEmail,
                                           @PathVariable Integer resumeId){
        if(userEmail == null){
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        return resumeService.getTechSkills(userEmail,resumeId);
    }

    @GetMapping("/{resumeId}/language")
    public ResponseEntity<Object> getLanguage(@CurrentUser String userEmail,
                                               @PathVariable Integer resumeId){
        if(userEmail == null){
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        return resumeService.getLanguage(userEmail,resumeId);
    }

    @PostMapping("/{resumeId}/career")
    public ResponseEntity<BaseResponse<Long>> postResumeCareer(@CurrentUser String userEmail,
                                                               @PathVariable Integer resumeId,
                                                               @RequestBody CareerDto dto){
        if(userEmail == null){
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        dto.setResume_id(resumeId.longValue());
        return resumeService.postResumeCareer(dto);
    }
    @PostMapping("/{resumeId}/awards")
    public ResponseEntity<BaseResponse<ArrayList<Long>>> postResumeAwards(@CurrentUser String userEmail,
                                                                         @PathVariable Integer resumeId,
                                                                         @RequestBody AwardListDto dto){
        if(userEmail == null){
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        dto.getAwardDtoList().forEach(acc -> acc.setResume_id(resumeId.longValue()));
        return resumeService.postResumeAward(dto);
    }

    @PostMapping("/{resumeId}/degrees")
    public ResponseEntity<BaseResponse<ArrayList<Long>>> postResumeDegrees(@CurrentUser String userEmail,
                                                                         @PathVariable Integer resumeId,
                                                                         @RequestBody DegreeListDto dto){
        if(userEmail == null){
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        dto.getDegreeDtoList().forEach(acc -> acc.setResume_id(resumeId.longValue()));
        return resumeService.postResumeDegree(dto);
    }

    @PostMapping("/{resumeId}/language")
    public ResponseEntity<BaseResponse<Long>> postResumeLanguage(@CurrentUser String userEmail,
                                                                          @PathVariable Integer resumeId,
                                                                          @RequestBody Language_skillDto dto){
        if(userEmail == null){
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        dto.setResume_id(resumeId.longValue());
        return resumeService.postResumeLanguage(dto);
    }

    @PostMapping("/{resumeId}/tech-skill")
    public ResponseEntity<BaseResponse<Long>> postResumeTechSkill(@CurrentUser String userEmail,
                                                                                      @PathVariable Integer resumeId,
                                                                                      @RequestBody Resume_tech_skillDto dto){
        if(userEmail == null){
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        dto.setResume_id(resumeId.longValue());
        return resumeService.postResumeTechSkills(dto);
    }

    @PostMapping("/upload")
    public BaseResponse<PostResumeFileDto> uploadResume(@CurrentUser String userEmail,
                                             @RequestParam("category") String category,
                                             @RequestPart(value = "file") MultipartFile multipartFile){
        if(userEmail == null){
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
    return resumeService.uploadResume(userEmail, category, multipartFile);
    }


}

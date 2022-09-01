package com.mockrc8.app.domain.company.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mockrc8.app.domain.company.mapper.CompanyMapper;
import com.mockrc8.app.domain.company.dto.*;
import com.mockrc8.app.domain.company.vo.CompanyDetailVo;
import com.mockrc8.app.domain.company.vo.CompanyListSearchedByTagVo;
import com.mockrc8.app.domain.company.vo.CompanyTagGroupedTopicVo;
import com.mockrc8.app.global.config.BaseResponse;
import com.mockrc8.app.global.error.exception.company.CompanyNotExistException;
import com.mockrc8.app.global.error.exception.company.CompanyTagNotExistException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.mockrc8.app.global.error.ErrorCode.*;

@Service
@AllArgsConstructor
public class CompanyService {

    private CompanyMapper companyMapper;


    // 나중에 교체

    public ResponseEntity<Object> getCompanyJoinedTableInfo(Long companyId) {
        if(companyMapper.checkCompanyId(companyId) == 0){
            throw new CompanyNotExistException(COMPANY_NOT_EXIST);
        }
        CompanyDetailVo companyJoinedTable = companyMapper.getCompanyJoinedTable(companyId);


        BaseResponse<Object> response = new BaseResponse<>(companyJoinedTable);

        return ResponseEntity.ok(response);
    }


    // 조회하는 태그 + 랜덤한 태그 4 목록 조회
    public List<CompanyTag> getCompanyTagListByIdAndRandomList(Long hashtagId){
        return companyMapper.getCompanyTagListByIdAndRandomList(hashtagId);
    }

    public List<CompanyListSearchedByTagVo> getCompanyListByTagId(Long hashtagId, Integer scrollCount){
        PageHelper.startPage(scrollCount, 10);    // 무한 스크롤 적용
        return companyMapper.getCompanyListByTagId(hashtagId);
    }

    public List<CompanyListSearchedByTagVo> getCompanyListByCompanyName(String companyName, Integer scrollCount){
        PageHelper.startPage(scrollCount, 6);
        return companyMapper.getCompanyListByCompanyName(companyName);
    }

    public Integer countCompanyListByCompanyName(String companyName){
        return companyMapper.countCompanyListByCompanyName(companyName);
    }

    public List<CompanyTagGroupedTopicVo> getCompanyTagGroupedByTopic(){
        return companyMapper.getCompanyTagGroupedByTopic();
    }

    public void checkCompanyTagId(Long companyTagId){
        System.out.println(companyTagId);
        Integer isExist = companyMapper.checkCompanyTagId(companyTagId);
        if(isExist == 0){
            throw new CompanyTagNotExistException(COMPANY_TAG_NOT_EXIST);
        }
    }

    public List<Company> getCompanyList(){
        List<Company> companyList = companyMapper.getCompanyList();
        return companyList;
    }


    public Company getCompanyById(Long companyId){
        if(companyMapper.checkCompanyId(companyId) == 0){
            throw new CompanyNotExistException(COMPANY_NOT_EXIST);
        }

        return companyMapper.getCompanyById(companyId);
    }


    public Long registerCompany(Company company){

        companyMapper.registerCompany(company);

        Long companyId = company.getCompanyId();
        if(companyMapper.checkCompanyId(companyId) == 0){
            throw new CompanyNotExistException(COMPANY_NOT_EXIST);
        }

        return companyId;
    }


//    public Long registerCompanyImage(CompanyImage companyImage){
//
//        Long companyImageId = companyDao.registerCompanyImage(companyImage);
//
//        if(companyDao.checkCompanyImageId(companyImageId) == 0){
//
//        }
//    }

    /*
    company_image && image
     */
    public List<Image> getCompanyImageListByCompanyId(Long companyId){

        List<CompanyImage> companyImageList = companyMapper.getCompanyImageListByCompanyId(companyId);

        Iterator<CompanyImage> it = companyImageList.iterator();
        List<Image> imageList = new ArrayList<>();
        while(it.hasNext()){
            CompanyImage companyImage = it.next();
            imageList.add(companyMapper.getImageById(companyImage.getImageId()));
        }

        return imageList;
    }

//
//    public List<Image> getImageListByCompanyId(Long companyId){
//        List<Image> imageList = companyMapper.getImageListByCompanyId(companyId);
//
//    }

    /*
    company_tag
     */
    public List<CompanyTag> getCompanyTagListByCompanyId(Long companyId){
        return companyMapper.getCompanyTagListByCompanyId(companyId);
    }

    public Long getCompanyTagId(String tagName){

        return companyMapper.getCompanyTagId(tagName);
    }

    /*
    company_news
     */
    public List<CompanyNews> getCompanyNewsListByCompanyId(Long companyId){
        return companyMapper.getCompanyNewsListByCompanyId(companyId);
    }

}

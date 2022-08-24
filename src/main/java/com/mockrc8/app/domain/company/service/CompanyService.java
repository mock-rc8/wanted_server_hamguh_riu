package com.mockrc8.app.domain.company.service;

import com.mockrc8.app.domain.company.mapper.CompanyMapper;
import com.mockrc8.app.domain.company.dto.*;
import com.mockrc8.app.domain.company.vo.CompanyDetailVo;
import com.mockrc8.app.domain.company.vo.CompanyListSearchedByTagVo;
import com.mockrc8.app.domain.company.vo.CompanyTagGroupedTopicVo;
import com.mockrc8.app.global.error.exception.company.CompanyNotExistException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.mockrc8.app.global.error.ErrorCode.*;

@Service
@AllArgsConstructor
public class CompanyService {

    private CompanyMapper companyMapper;


    // 나중에 교체
    public ResponseEntity<CompanyDetailVo> getCompanyJoinedTableInfo() {
        final CompanyDetailVo companyJoinedTable = companyMapper.getCompanyJoinedTable(1L);
        return new ResponseEntity<>(companyJoinedTable, HttpStatus.OK);
    }


    // 8-24 조회하는 태그 + 랜덤한 태그 4 목록 조회
    public List<CompanyTag> getCompanyTagListByIdAndRandomList(Long hashtagId){
        return companyMapper.getCompanyTagListByIdAndRandomList(hashtagId);
    }

    public List<CompanyListSearchedByTagVo> getCompanyListByTagId(Long hashtagId){
        return companyMapper.getCompanyListByTagId(hashtagId);
    }

    public List<CompanyTagGroupedTopicVo> getCompanyTagGroupedByTopic(){
        return companyMapper.getCompanyTagGroupedByTopic();
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


    /*
    company_tag
     */
    public List<CompanyTag> getCompanyTagListByCompanyId(Long companyId){
        return companyMapper.getCompanyTagListByCompanyId(companyId);
    }


    /*
    company_news
     */
    public List<CompanyNews> getCompanyNewsListByCompanyId(Long companyId){
        return companyMapper.getCompanyNewsListByCompanyId(companyId);
    }
}

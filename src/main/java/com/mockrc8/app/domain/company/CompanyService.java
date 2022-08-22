package com.mockrc8.app.domain.company;

import com.mockrc8.app.domain.company.model.*;
import com.mockrc8.app.domain.employment.model.Employment;
import com.mockrc8.app.global.error.exception.company.CompanyNotExistException;
import com.mockrc8.app.global.error.exception.company.CompanyTagNotExistException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.mockrc8.app.global.error.ErrorCode.*;

@Service
@AllArgsConstructor
public class CompanyService {

    private CompanyDao companyDao;

    public List<Company> getCompanyList(){
        if(companyDao.checkCompanyListExist() == 0){
            throw new CompanyNotExistException(COMPANY_NOT_EXIST);
        }

        return companyDao.getCompanyList();
    }


    public Company getCompanyById(Long companyId){
        if(companyDao.checkCompanyId(companyId) == 0){
            throw new CompanyNotExistException(COMPANY_NOT_EXIST);
        }

        return companyDao.getCompanyById(companyId);
    }


    public Long registerCompany(Company company){

        Long companyId = companyDao.registerCompany(company);

        if(companyDao.checkCompanyId(companyId) == 0){
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

    public List<Image> getCompanyImageListByCompanyId(Long companyId){
//        if(companyDao.checkCompanyImageByCompanyId(companyId) == 0){
//            throw new
//        }
        List<CompanyImage> companyImageList = companyDao.getCompanyImageListByCompanyId(companyId);

        Iterator<CompanyImage> it = companyImageList.iterator();
        List<Image> imageList = new ArrayList<>();
        while(it.hasNext()){
            CompanyImage companyImage = it.next();
            imageList.add(companyDao.getImageById(companyImage.getImageId()));
        }

        return imageList;
    }


    public List<CompanyTag> getCompanyTagListByCompanyId(Long companyId){

//        if(companyDao.checkCompanyTagByCompanyId(companyId) == 0){
//            throw new CompanyTagNotExistException(COMPANY_TAG_NOT_EXIST);
//        }

        return companyDao.getCompanyTagListByCompanyId(companyId);
    }


    /*
    company_news
     */
    public List<CompanyNews> getCompanyNewsListByCompanyId(Long companyId){

        return companyDao.getCompanyNewsListByCompanyId(companyId);
    }


    /*
    company가 등록한 employment 축약 정보 목록
     */
    public List<Employment> getEmploymentListByCompanyId(Long companyId){
        return companyDao.getEmploymentListByCompanyId(companyId);
    }
}

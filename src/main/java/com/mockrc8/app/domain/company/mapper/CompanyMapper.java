package com.mockrc8.app.domain.company.mapper;

import com.mockrc8.app.domain.company.dto.*;
import com.mockrc8.app.domain.company.vo.Company;
import com.mockrc8.app.domain.company.vo.CompanyInfo;
import com.mockrc8.app.domain.company.vo.CompanyTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CompanyMapper {
    Integer checkCompanyId(Long companyId);
    void registerCompany(@Param("company") Company company);
    List<Company> getCompanyList();
    Company getCompanyById(@Param("companyId") Long companyId);


    // company_image
    List<CompanyImage> getCompanyImageListByCompanyId(Long companyId);

    //image
    Image getImageById(Long imageId);
    Integer checkImageId(Long imageId);

    //company_tag
    List<CompanyTag> getCompanyTagListByCompanyId(Long companyId);

    //company_news
    List<CompanyNews> getCompanyNewsListByCompanyId(Long companyId);

    CompanyInfo getCompanyJoinedTable(Long companyId);

}

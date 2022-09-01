package com.mockrc8.app.domain.company.mapper;

import com.github.pagehelper.Page;
import com.mockrc8.app.domain.company.dto.*;
import com.mockrc8.app.domain.company.vo.CompanyDetailVo;
import com.mockrc8.app.domain.company.vo.CompanyListSearchedByTagVo;
import com.mockrc8.app.domain.company.vo.CompanyTagGroupedTopicVo;
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

    List<Image> getImageListByCompanyId(Long companyId);

    //company_tag
    CompanyTag getCompanyTagById(Long hashtagId);
    List<CompanyTag> getCompanyTagListByCompanyId(Long companyId);
    Integer checkCompanyTagId(Long companyTagId);
    Long getCompanyTagId(String tagName);
    List<CompanyTag> getCompanyTagListByIdAndRandomList(Long hashtagId);
    Page<CompanyListSearchedByTagVo> getCompanyListByTagId(Long hashtagId);

    Page<CompanyListSearchedByTagVo> getCompanyListByCompanyName(String companyName);
    Integer countCompanyListByCompanyName(String companyName);

    List<CompanyTagGroupedTopicVo> getCompanyTagGroupedByTopic();

    //company_news
    List<CompanyNews> getCompanyNewsListByCompanyId(Long companyId);



    CompanyDetailVo getCompanyJoinedTable(Long companyId);

}

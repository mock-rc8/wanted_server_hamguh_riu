package com.mockrc8.app.domain.company.vo;

import com.mockrc8.app.domain.company.vo.Company;
import com.mockrc8.app.domain.company.vo.CompanyTag;
import com.mockrc8.app.domain.employment.vo.Employment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class CompanyInfo {
    private Company company;
    private List<CompanyTag> companyTagList;
    private List<Employment> employmentList;
}

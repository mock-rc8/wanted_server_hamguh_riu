package com.mockrc8.app.domain.company.vo;


import com.mockrc8.app.domain.company.dto.Company;
import com.mockrc8.app.domain.company.dto.CompanyTag;
import com.mockrc8.app.domain.employment.dto.Employment;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDetailVo {

    private Long company_id;
    private String image_url;
    private String name;
    private String description;

    private List<CompanyTag> companyTagList;
    private List<Employment> employmentList;
}
package com.mockrc8.app.domain.company.vo;

import com.mockrc8.app.domain.company.dto.CompanyTag;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyListSearchedByTagVo {
    private Long company_id;
    private String image_url;
    private String name;
    private String description;
    private List<CompanyTag> companyTagList;
}

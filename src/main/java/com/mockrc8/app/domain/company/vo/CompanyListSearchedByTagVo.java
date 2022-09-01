package com.mockrc8.app.domain.company.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mockrc8.app.domain.company.dto.CompanyTag;
import lombok.*;
import org.springframework.lang.Nullable;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
@Builder
public class CompanyListSearchedByTagVo {
    private Long company_id;
    private String image_url;
    private String name;
    private String description;


    private List<CompanyTag> companyTagList;
}

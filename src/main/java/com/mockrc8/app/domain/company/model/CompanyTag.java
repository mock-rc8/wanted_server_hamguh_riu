package com.mockrc8.app.domain.company.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class CompanyTag {
    private Long companyTagId;
    private Long companyId;
    private String companyTagName;

    public CompanyTag(Long companyId, String companyTagName){
        this.companyId = companyId;
        this.companyTagName = companyTagName;
    }
}

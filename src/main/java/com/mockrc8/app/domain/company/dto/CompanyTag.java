package com.mockrc8.app.domain.company.dto;

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

//    public CompanyTag(Long companyId, String companyTagName){
//        this.companyId = companyId;
//        this.companyTagName = companyTagName;
//    }


    public CompanyTag(Long companyTagId, String companyTagName){
        this.companyTagId = companyTagId;
        this.companyTagName = companyTagName;
    }
}

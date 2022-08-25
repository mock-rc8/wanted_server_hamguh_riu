package com.mockrc8.app.domain.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class CompanyNews {
    private Long companyNewsId;
    private Long companyId;
    private String newsLink;

    public CompanyNews(Long companyNewsId, String newsLink){
        this.companyNewsId = companyNewsId;
        this.newsLink = newsLink;
    }
}

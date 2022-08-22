package com.mockrc8.app.domain.company.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyNews {
    private Long companyNewsId;
    private Long companyId;
    private String newsLink;

    public CompanyNews(Long companyId, String newsLink){
        this.companyId = companyId;
        this.newsLink = newsLink;
    }
}

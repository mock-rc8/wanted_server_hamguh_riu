package com.mockrc8.app.domain.company.vo;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class Company {
    private Long company_id;
    private String image_url;
    private String name;
    private String description;
}


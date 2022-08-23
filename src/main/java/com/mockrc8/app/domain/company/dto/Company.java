package com.mockrc8.app.domain.company.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    private Long companyId;
    private String imageUrl;
    private String name;
    private String description;

    public Company(String name, String imageUrl, String description){
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
    }

}


package com.mockrc8.app.domain.company.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyImage {
    private Long companyImageId;
    private Long companyId;
    private Long imageId;
}

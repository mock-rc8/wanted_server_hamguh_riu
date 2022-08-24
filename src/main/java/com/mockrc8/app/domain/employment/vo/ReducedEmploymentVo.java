package com.mockrc8.app.domain.employment.vo;

import com.mockrc8.app.domain.company.dto.CompanyImage;
import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReducedEmploymentVo {

    private Long employment_id;
    private String title;
    private String location;
    private String company_name;
    private Integer compensation;
    private String image_url;
}

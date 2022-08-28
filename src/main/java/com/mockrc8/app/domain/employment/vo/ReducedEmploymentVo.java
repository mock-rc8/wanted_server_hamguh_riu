package com.mockrc8.app.domain.employment.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mockrc8.app.domain.company.dto.CompanyImage;
import com.mockrc8.app.domain.employment.dto.Employment;
import lombok.*;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(NON_NULL)
public class ReducedEmploymentVo {
    private Long employment_id;
    private String title;
    private String location;
    private String company_name;
    private String logo_image_url;
    private Integer compensation;
    private String image_url;

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ReducedEmploymentVo && obj != null){
            ReducedEmploymentVo other = (ReducedEmploymentVo)obj;
            if(other.getEmployment_id().equals(this.employment_id)){
                return true;
            }
        }
        return false;
    }
}

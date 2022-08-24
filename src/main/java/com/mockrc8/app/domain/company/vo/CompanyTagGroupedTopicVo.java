package com.mockrc8.app.domain.company.vo;

import com.mockrc8.app.domain.company.dto.CompanyTag;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyTagGroupedTopicVo {
    private Long company_tag_topic_id;
    private String company_tag_topic_name;
    private List<CompanyTag> companyTagList;
}

package com.mockrc8.app.domain.resume.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeDetail {
    private Long resume_id;
    private Long user_id;
    private String name;
    private String introduction;


}

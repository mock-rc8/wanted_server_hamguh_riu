package com.mockrc8.app.domain.resume.dto.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResumeFileDto {
    private Long resume_id;
    private Long user_id;
    private Integer write_status = 1;
    private String name;
    private String resume_link;

}

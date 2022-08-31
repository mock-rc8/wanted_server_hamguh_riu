package com.mockrc8.app.domain.resume.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeWithLink {
    private Long resume_id;
    private Long user_id;
    private String name;
    private String introduction;
    private int write_status;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private int is_deleted;
    private String resume_link;
}

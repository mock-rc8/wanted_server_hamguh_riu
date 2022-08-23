package com.mockrc8.app.domain.employment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmploymentImage {
    private Long employmentImageId;
    private Long employmentId;
    private Long imageId;
}

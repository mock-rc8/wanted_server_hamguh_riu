package com.mockrc8.app.domain.insight.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsightsRequestDto {
    private int pageNum = 0;
    private int pageSize = 10;
    private Integer tagId;
}

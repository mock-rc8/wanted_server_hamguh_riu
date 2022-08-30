package com.mockrc8.app.domain.insight.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostInsightDto {
    private Long insight_id;
    private String insight_url;
    private List<Integer> tagIdList;
}

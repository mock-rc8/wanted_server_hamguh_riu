package com.mockrc8.app.domain.insight.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsightTag {
    private Long tag_id;
    private String name;
    private List<Insight> insights;
}

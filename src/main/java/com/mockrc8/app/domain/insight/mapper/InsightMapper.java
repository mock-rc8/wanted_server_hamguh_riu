package com.mockrc8.app.domain.insight.mapper;

import com.mockrc8.app.domain.insight.dto.InsightsRequestDto;
import com.mockrc8.app.domain.insight.vo.Insight;
import com.mockrc8.app.domain.insight.vo.InsightTag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InsightMapper {
    public List<Insight> getInsights();

    public InsightTag getInsightsByTagId(Integer tagId);
}

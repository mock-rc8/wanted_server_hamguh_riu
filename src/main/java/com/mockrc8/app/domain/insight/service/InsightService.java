package com.mockrc8.app.domain.insight.service;

import com.mockrc8.app.domain.event.dto.EventPostDto;
import com.mockrc8.app.domain.insight.dto.InsightsRequestDto;
import com.mockrc8.app.domain.insight.dto.PostInsightDto;
import com.mockrc8.app.domain.insight.mapper.InsightMapper;
import com.mockrc8.app.domain.insight.vo.Insight;
import com.mockrc8.app.domain.insight.vo.InsightTag;
import com.mockrc8.app.global.config.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InsightService {

    private final InsightMapper insightMapper;

    public ResponseEntity<Object> getInsights(InsightsRequestDto insightsRequestDto) {
        if (insightsRequestDto.getTagId() == null) {
            final List<Insight> insights = insightMapper.getInsights();
            final BaseResponse<List<Insight>> response = new BaseResponse<>(insights);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            final InsightTag insightsByTagId = insightMapper.getInsightsByTagId(insightsRequestDto.getTagId());
            final BaseResponse<InsightTag> response = new BaseResponse<>(insightsByTagId);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }

    }

    public ResponseEntity<Object> postInsight(PostInsightDto postInsightDto) {
        insightMapper.postInsight(postInsightDto);
        postInsightTags(postInsightDto.getTagIdList(),postInsightDto.getInsight_id());
        final BaseResponse<Long> baseResponse = new BaseResponse<>("인사이트 저장에 성공했습니다.", postInsightDto.getInsight_id());
        return ResponseEntity.ok().body(baseResponse);
    }

    private void postInsightTags(List<Integer> tagList, Long insightId){
        tagList.forEach(acc -> insightMapper.postEventTag(acc.longValue(),insightId));
    }
}

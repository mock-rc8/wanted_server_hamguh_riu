package com.mockrc8.app.domain.insight.controller;

import com.mockrc8.app.domain.insight.dto.InsightsRequestDto;
import com.mockrc8.app.domain.insight.dto.PostInsightDto;
import com.mockrc8.app.domain.insight.service.InsightService;
import com.mockrc8.app.domain.insight.vo.Insight;
import com.mockrc8.app.domain.insight.vo.InsightTag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("insights")
public class InsightController {

    private final InsightService insightService;

    @GetMapping()
    public ResponseEntity<Object> getInsights(InsightsRequestDto insightsRequestDto){
        return insightService.getInsights(insightsRequestDto);
    }

    @PostMapping()
    public ResponseEntity<Object> postInsights(PostInsightDto postInsightDto){
        return insightService.postInsight(postInsightDto);
    }

}

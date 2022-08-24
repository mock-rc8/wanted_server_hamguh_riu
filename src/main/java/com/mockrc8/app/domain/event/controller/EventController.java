package com.mockrc8.app.domain.event.controller;

import com.mockrc8.app.domain.event.dto.EventRequestDto;
import com.mockrc8.app.domain.event.service.EventService;
import com.mockrc8.app.domain.insight.dto.InsightsRequestDto;
import com.mockrc8.app.domain.insight.service.InsightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @GetMapping()
    public ResponseEntity<Object> getEvents(EventRequestDto eventRequestDto){
        return eventService.getInsights(eventRequestDto);
    }


}

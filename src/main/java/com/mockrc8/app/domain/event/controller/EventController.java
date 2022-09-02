package com.mockrc8.app.domain.event.controller;

import com.github.pagehelper.PageException;
import com.mockrc8.app.domain.event.dto.EventPostDto;
import com.mockrc8.app.domain.event.dto.EventRequestDto;
import com.mockrc8.app.domain.event.service.EventService;
import com.mockrc8.app.domain.insight.dto.InsightsRequestDto;
import com.mockrc8.app.domain.insight.service.InsightService;
import com.mockrc8.app.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @GetMapping()
    public ResponseEntity<Object> getEvents(EventRequestDto eventRequestDto){
        if(eventRequestDto.getPageNum() < 0){
            throw new PageException();
        }
        if(eventRequestDto.getPageSize() < 0){
            throw new PageException();
        }
        return eventService.getEvents(eventRequestDto);
    }

    @PostMapping()
    public ResponseEntity<Object> postEvents(@RequestBody EventPostDto eventPostDto){
        return eventService.postEvents(eventPostDto);
    }



}

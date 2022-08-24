package com.mockrc8.app.domain.event.service;

import com.mockrc8.app.domain.event.dto.EventRequestDto;
import com.mockrc8.app.domain.event.mapper.EventMapper;
import com.mockrc8.app.domain.event.vo.Event;
import com.mockrc8.app.domain.event.vo.EventTag;
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
public class EventService {

    private final EventMapper eventMapper;
    public ResponseEntity<Object> getInsights(EventRequestDto eventRequestDto) {
        if (eventRequestDto.getTagId() == null) {
            final List<Event> events = eventMapper.getEvents(eventRequestDto);
            final BaseResponse<List<Event>> response = new BaseResponse<>(events);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            final EventTag eventsByTagId = eventMapper.getEventsByTagId(eventRequestDto);
            final BaseResponse<EventTag> response = new BaseResponse<>(eventsByTagId);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }

    }

}


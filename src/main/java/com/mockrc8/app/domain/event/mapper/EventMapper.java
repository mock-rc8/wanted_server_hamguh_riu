package com.mockrc8.app.domain.event.mapper;

import com.mockrc8.app.domain.event.dto.EventRequestDto;
import com.mockrc8.app.domain.event.vo.Event;
import com.mockrc8.app.domain.event.vo.EventTag;
import com.mockrc8.app.domain.insight.vo.InsightTag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EventMapper {
    List<Event> getEvents(EventRequestDto eventRequestDto);

    EventTag getEventsByTagId(EventRequestDto eventRequestDto);
}

package com.mockrc8.app.domain.event.mapper;

import com.mockrc8.app.domain.event.dto.EventPostDto;
import com.mockrc8.app.domain.event.dto.EventRequestDto;
import com.mockrc8.app.domain.event.vo.Event;
import com.mockrc8.app.domain.event.vo.EventTag;
import com.mockrc8.app.domain.insight.vo.InsightTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Mapper
public interface EventMapper {
    List<Event> getEvents(EventRequestDto eventRequestDto);

    EventTag getEventsByTagId(EventRequestDto eventRequestDto);

    void postEvents(EventPostDto eventPostDto);

    void postEventTag(@Param("tagId") Long tagId, @Param("eventId") Long eventId);
}

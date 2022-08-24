package com.mockrc8.app.domain.event.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventTag {
    private Long event_tag_id;
    private String name;
    private List<Event> eventList;
}

package com.mockrc8.app.domain.event.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {
    private Long event_id;
    private String name;
    private int is_free;
    private String context;
    private Integer capacity;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private String event_thumbnail_image_url;

}

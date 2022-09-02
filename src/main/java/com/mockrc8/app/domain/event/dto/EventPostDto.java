package com.mockrc8.app.domain.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventPostDto {
    private Long event_id;
    private String name;
    private int is_free;
    private String context;
    private Integer capacity;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime start_time;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime end_time;
    private String event_thumbnail_image_url;
    private List<Integer> tagList;
}

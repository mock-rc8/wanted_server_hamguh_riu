package com.mockrc8.app.domain.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRequestDto {
    private int pageNum = 0;
    private int pageSize = 10;
    private Integer tagId;
    private Integer typeId;
    private Integer isFree;
}

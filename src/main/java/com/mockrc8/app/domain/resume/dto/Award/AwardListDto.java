package com.mockrc8.app.domain.resume.dto.Award;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AwardListDto {
    private List<AwardDto> awardDtoList;
}

package com.mockrc8.app.domain.resume.dto.Degree;


import com.mockrc8.app.domain.resume.dto.Award.AwardDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DegreeListDto {
    private List<DegreeDto> degreeDtoList;
}

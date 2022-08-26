package com.mockrc8.app.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(NON_NULL)
public class UserEmploymentBookmarkDto {
    private Long user_employment_bookmark_id;
    private Long user_id;
    private Long employment_id;

    public UserEmploymentBookmarkDto(Long user_id, Long employment_id){
        this.user_id = user_id;
        this.employment_id = employment_id;
    }
}

package com.mockrc8.app.domain.user.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(NON_NULL)
public class UserInterestTagDto {
    private Long user_interest_tag_id;
    private Long tag_id;
    private Long user_id;
    private Long community_tag_id;
    private String name;

    public UserInterestTagDto(Long user_id, Long tag_id){
        this.user_id = user_id;
        this.tag_id = tag_id;
    }
}

package com.mockrc8.app.domain.company.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class Image {
    private Long imageId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String imageUrl;

    public Image(Long imageId, String imageUrl){
        this.imageId = imageId;
        this.imageUrl = imageUrl;
    }
}

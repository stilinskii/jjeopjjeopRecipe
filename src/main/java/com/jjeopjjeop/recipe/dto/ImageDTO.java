package com.jjeopjjeop.recipe.dto;


import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO {
    private Integer id;
    private String filename;
    private Integer board_id;

    public ImageDTO(String filename) {
        this.filename = filename;
    }
}

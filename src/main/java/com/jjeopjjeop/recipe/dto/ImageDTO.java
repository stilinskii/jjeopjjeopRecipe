package com.jjeopjjeop.recipe.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
public class ImageDTO {
    private Integer id;
    private String filename;
    private Integer board_id;

    public ImageDTO(String filename) {
        this.filename = filename;
    }
}

package com.uma.wiki.dto;

import com.uma.wiki.entity.UserEntity;
import com.uma.wiki.entity.WikiEntity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class EntryCreateDto {

    @NotNull
    private String title;

    private String description;

    private List<String> texts;

    private List<String> urls;

    private List<String> files;

    private List<String> maps;

    //@NotNull desactivado porque todavia no esta modelado
    private UserEntity userEntity;

    //@NotNull desactivado porque todavia no esta modelado
    private WikiEntity wikiEntity;

}

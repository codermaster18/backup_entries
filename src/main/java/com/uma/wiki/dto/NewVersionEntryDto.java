package com.uma.wiki.dto;

import com.uma.wiki.entity.UserEntity;
import com.uma.wiki.entity.WikiEntity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewVersionEntryDto {

    @NotNull
    private String entryId;
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

package com.uma.wiki.dto;

import com.uma.wiki.entity.CommentEntity;
import com.uma.wiki.entity.UserEntity;
import com.uma.wiki.entity.WikiEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntryResponseDto {

    private String entryId;

    private String version;

    private String title;

    private String description;

    private List<String> texts;

    private List<String> urls;

    private List<String> files;

    private List<String> maps;

    private LocalDateTime creationDate;

    //@NotNull desactivado porque todavia no esta modelado
    private UserEntity userEntity;

    //@NotNull desactivado porque todavia no esta modelado
    private WikiEntity wikiEntity;

    private List<CommentEntity> commentEntities;

}

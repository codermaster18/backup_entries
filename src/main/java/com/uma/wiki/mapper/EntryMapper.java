package com.uma.wiki.mapper;

import com.uma.wiki.dto.EntryCreateDto;
import com.uma.wiki.dto.EntryResponseDto;
import com.uma.wiki.dto.NewVersionEntryDto;
import com.uma.wiki.entity.EntryEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EntryMapper {

    public static EntryResponseDto toResponseDto(EntryEntity entryEntity) {
        if (entryEntity == null) {
            return null;
        }

        EntryResponseDto dto = new EntryResponseDto();
        dto.setEntryId(entryEntity.getEntryId());
        dto.setVersion(entryEntity.getVersion());
        dto.setTitle(entryEntity.getTitle());
        dto.setDescription(entryEntity.getDescription());
        dto.setCreationDate(entryEntity.getCreationDate());
        dto.setUserEntity(entryEntity.getUserEntity());
        dto.setWikiEntity(entryEntity.getWikiEntity());
        dto.setTexts(entryEntity.getTexts());
        dto.setUrls(entryEntity.getUrls());
        dto.setFiles(entryEntity.getFiles());
        dto.setMaps(entryEntity.getMaps());

        // Si tienes comentarios, conviértelos aquí usando un método similar
        // dto.setCommentEntities(convertCommentsToDto(entryEntity.getCommentEntities()));

        return dto;
    }

    public static EntryEntity toEntityInUpdate(NewVersionEntryDto newVersionEntryDto, String version) {
        if (newVersionEntryDto == null) {
            return null;
        }

        EntryEntity entryEntity = new EntryEntity(
                newVersionEntryDto.getWikiEntity(),
                newVersionEntryDto.getUserEntity(),
                newVersionEntryDto.getTitle(),
                newVersionEntryDto.getDescription(),
                newVersionEntryDto.getMaps(),
                newVersionEntryDto.getFiles(),
                newVersionEntryDto.getTexts(),
                newVersionEntryDto.getUrls(),
                version,
                newVersionEntryDto.getEntryId()
        );

        return entryEntity;
    }

    public static EntryEntity toEntityInCreation(EntryCreateDto entryCreateDto) {
        if (entryCreateDto == null) {
            return null;
        }

        EntryEntity entryEntity = new EntryEntity(
                entryCreateDto.getWikiEntity(),
                entryCreateDto.getUserEntity(),
                entryCreateDto.getTitle(),
                entryCreateDto.getDescription(),
                entryCreateDto.getMaps(),
                entryCreateDto.getFiles(),
                entryCreateDto.getTexts(),
                entryCreateDto.getUrls()
        );

        return entryEntity;
    }

}

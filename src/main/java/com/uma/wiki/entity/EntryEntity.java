package com.uma.wiki.entity;

import com.uma.wiki.exception.InvalidVersionException;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "entries")
@CompoundIndex(name="entryId_1_version_1", def = "{'entryId':1, 'version':1}", unique = true)
public class EntryEntity {

    @MongoId
    @Getter
    @Builder.Default
    private ObjectId id = new ObjectId();

    @NotNull
    @Getter
    private String entryId;

    @NotNull
    @Getter
    private String version;

    @NotNull
    private String title; //el titulo puede cambiar entre las versiones?

    private String description;

    private List<String> texts; //para referenciar las url, HOW? posibilidad de poner como clave/valor

    private List<String> urls;

    private List<String> files;

    private List<String> maps;

    @NotNull
    @Getter
    @Builder.Default
    private LocalDateTime creationDate = LocalDateTime.now();

    @NotNull
    private UserEntity userEntity;

    @NotNull
    private WikiEntity wikiEntity;

    private List<CommentEntity> commentEntities;

    @NotNull
    private boolean active;

    public EntryEntity(WikiEntity wikiEntity, UserEntity userEntity, String title, String description, List<String> maps, List<String> files, List<String> texts, List<String> urls, String previousVersion, String entryId) {
        this.active = true;
        this.creationDate = LocalDateTime.now();
        this.entryId = validateEntryId(entryId) ? entryId : null;
        this.id = new ObjectId();
        this.wikiEntity = wikiEntity;
        this.userEntity = userEntity;
        this.maps = maps;
        this.files = files;
        this.texts = texts;
        this.urls = urls;
        this.commentEntities = null;
        this.title = title;
        this.description = description;

        if (!validateVersion(previousVersion))
            throw new InvalidVersionException("the previous version has to be of following format: 1.0.0");
        this.version = incrementVersion(previousVersion);
    }

    public EntryEntity(WikiEntity wikiEntity, UserEntity userEntity, String title, String description, List<String> maps, List<String> files, List<String> texts, List<String> urls) {
        this.active = true;
        this.version = "1.0.0";
        this.creationDate = LocalDateTime.now();
        this.entryId = UUID.randomUUID().toString();
        this.id = new ObjectId();
        this.wikiEntity = wikiEntity;
        this.userEntity = userEntity;
        this.maps = maps;
        this.files = files;
        this.texts = texts;
        this.urls = urls;
        this.title = title;
        this.description = description;
        this.commentEntities = null;
    }

    private String incrementVersion(String previousVersion) {
        if (previousVersion == null || previousVersion.isEmpty()) {
            throw new InvalidVersionException("Need the previousVersion to create the new one");
        }

        String[] parts = previousVersion.split("\\.");
        int firstPart = Integer.parseInt(parts[0]);
        int secondPart = Integer.parseInt(parts[1]);
        int thirdPart = Integer.parseInt(parts[2]);

        if (thirdPart == 9 && secondPart == 9) {
            firstPart++;
        } else if (thirdPart == 9) {
            secondPart++;
        } else {
            thirdPart++;
        }

        return firstPart + "." + secondPart + "." + thirdPart;
    }

    private boolean validateEntryId(String entryId) {
        try {
            UUID.fromString(entryId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("entryId must be a valid UUID");
        }

        return true;
    }

    private boolean validateVersion(String version){
        String regex = "^\\d+\\.\\d\\.\\d$";
        return Pattern.matches(regex, version);
    }

}

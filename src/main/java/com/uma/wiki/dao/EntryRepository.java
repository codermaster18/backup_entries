package com.uma.wiki.dao;

import com.uma.wiki.entity.EntryEntity;
import com.uma.wiki.entity.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface EntryRepository extends MongoRepository<EntryEntity, ObjectId>{
    EntryEntity findByEntryIdAndVersion(String entryId, String version);
    void deleteByEntryIdAndVersion(String entryId, String version);
    EntryEntity findTopByEntryIdOrderByCreationDateDesc(String entryId);

    EntryEntity findTopByEntryIdAndActiveIsTrue(String entryId);

}


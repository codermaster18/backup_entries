package com.uma.wiki.service;

import com.uma.wiki.dao.EntryRepository;
import com.uma.wiki.dto.EntryCreateDto;
import com.uma.wiki.dto.EntryResponseDto;
import com.uma.wiki.dto.NewVersionEntryDto;
import com.uma.wiki.entity.EntryEntity;
import com.uma.wiki.entity.UserEntity;
import com.uma.wiki.exception.EntryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.uma.wiki.mapper.EntryMapper.*;

@Service
public class EntryService {

    @Autowired
    private EntryRepository entryRepository;


    public EntryResponseDto getEntry(String entryId, String version){
        EntryEntity entryEntity = this.entryRepository.findByEntryIdAndVersion(entryId, version);

        if (entryEntity == null) {
            throw new EntryNotFoundException("Entry with ID " + entryId + " and version " + version + " doesn't exist in DB");
        }

        return toResponseDto(entryEntity);
    }

    public void deleteEntry(String entryId, String version){
        EntryEntity entryEntity = this.entryRepository.findByEntryIdAndVersion(entryId, version);
        if (entryEntity == null)
            throw new EntryNotFoundException("object doesn't exist in DB");

        this.entryRepository.deleteByEntryIdAndVersion(entryId, version);

        if (entryEntity.isActive() && version != "1.0.0"
                && entryRepository.findTopByEntryIdOrderByCreationDateDesc(entryId) != null)
        {
            activateBeforeVersionInDelete(entryId);
        }
    }

    public EntryResponseDto createEntry(EntryCreateDto entryCreateDto) {
        EntryEntity entryEntity = toEntityInCreation(entryCreateDto);
        EntryEntity savedEntryEntity = entryRepository.save(entryEntity);

        return toResponseDto(savedEntryEntity);
    }

    public EntryResponseDto newVersionEntry(NewVersionEntryDto newVersionEntryDto) {
        EntryEntity beforeEntryEntity = entryRepository.findTopByEntryIdOrderByCreationDateDesc(newVersionEntryDto.getEntryId());

        deactivateLastEntry(newVersionEntryDto.getEntryId());
        sendNotificationLastUserOfEntryDeactivation(beforeEntryEntity.getUserEntity());
        EntryEntity entryEntity = toEntityInUpdate(newVersionEntryDto, beforeEntryEntity.getVersion());
        EntryEntity savedEntryEntity = entryRepository.save(entryEntity);

        return toResponseDto(savedEntryEntity);
    }

    //todo devolver getActiveEntry

    private void activateBeforeVersionInDelete(String entryId){
        EntryEntity beforeEntryEntity = entryRepository.findTopByEntryIdOrderByCreationDateDesc(entryId);
        beforeEntryEntity.setActive(true);
        entryRepository.save(beforeEntryEntity);
    }

    private void deactivateLastEntry(String entryId){
        EntryEntity lastActiveEntryEntity = entryRepository.findTopByEntryIdAndActiveIsTrue(entryId);
        lastActiveEntryEntity.setActive(false);
        entryRepository.save(lastActiveEntryEntity);
    }

    private void sendNotificationLastUserOfEntryDeactivation(UserEntity userEntity) {
         //todo send notification to user
    }

}

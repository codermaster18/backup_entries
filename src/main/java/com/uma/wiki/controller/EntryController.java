package com.uma.wiki.controller;

import com.uma.wiki.dto.EntryCreateDto;
import com.uma.wiki.dto.EntryResponseDto;
import com.uma.wiki.dto.NewVersionEntryDto;
import com.uma.wiki.dto.ResponseWrapper;
import com.uma.wiki.service.EntryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/entry")
@RestController
public class EntryController {

    @Autowired
    EntryService entryService;

    @Operation(summary = "Get an entry by ID and version", description = "Returns an entry based on its ID and version")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entry found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    public ResponseEntity<ResponseWrapper<EntryResponseDto>> getEntry(@RequestParam("entryId") String entryId, @RequestParam("version") String version) {
        try {
            EntryResponseDto entryResponseDTO = entryService.getEntry(entryId, version);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseWrapper<>(entryResponseDTO, HttpStatus.OK.value(), "Entry retrieved successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(null, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error retrieving entry"));
        }
    }

    @Operation(summary = "Create a new entry", description = "Creates a new entry in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Entry created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input provided", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ResponseWrapper<EntryResponseDto>> createEntry(@Valid @RequestBody EntryCreateDto entryCreateDto) {
        EntryResponseDto newEntryResponseDTO = entryService.createEntry(entryCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseWrapper<>(newEntryResponseDTO, HttpStatus.CREATED.value(), "Entry created successfully"));
    }

    @Operation(summary = "Create a new version of an existing entry", description = "This endpoint allows clients to create a new version of an existing entry.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New version of entry created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input provided", content = @Content),
            @ApiResponse(responseCode = "404", description = "Entry not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PutMapping
    public ResponseEntity<ResponseWrapper<EntryResponseDto>> newVersionEntry(@Valid @RequestBody NewVersionEntryDto newVersionEntryDto) {
        EntryResponseDto newEntryResponseDTO = entryService.newVersionEntry(newVersionEntryDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseWrapper<>(newEntryResponseDTO, HttpStatus.CREATED.value(), "New version created successfully"));
    }

    @Operation(summary = "Delete an entry", description = "This endpoint allows clients to delete an entry by its ID and version.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entry deleted successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Entry not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping
    public ResponseEntity<ResponseWrapper<String>> deleteEntry(@RequestParam("entryId") String idEntry, @RequestParam("version") String version) {
        try {
            entryService.deleteEntry(idEntry, version);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseWrapper<>("Correctly deleted", HttpStatus.OK.value(), "Entry deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(null, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error deleting entry"));
        }
    }
}

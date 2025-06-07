package org.alvarub.fulbitoapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.alvarub.fulbitoapi.model.dto.ConfederationDTO;
import org.alvarub.fulbitoapi.model.dto.ConfederationResponseDTO;
import org.alvarub.fulbitoapi.model.dto.CountryResponseDTO;
import org.alvarub.fulbitoapi.model.dto.TeamResponseDTO;
import org.alvarub.fulbitoapi.service.ConfederationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Confederaciones")
@RequestMapping("/api/confederations")
public class ConfederationController {

    @Autowired
    private ConfederationService confederationService;

    @Operation(summary = "Registra una confederacion en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Confederacion registrada", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ConfederationDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Pais o equipo no encontrado", content = @Content)
    })
    @PostMapping
    public ResponseEntity<String> saveConfederation(@Valid @RequestBody ConfederationDTO confederationDTO) {
        confederationService.saveConfederation(confederationDTO);
        return new ResponseEntity<>("Registro exitoso", HttpStatus.CREATED);
    }

    @Operation(summary = "Busca una confederacion por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Confederacion encontrada", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ConfederationResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Confederacion no encontrada", content = @Content)
    })
    @GetMapping("/{id}") @ResponseBody
    public ResponseEntity<?> findConfederationById(@Parameter(description = "ID de la confederacion", example = "1")
                                                   @PathVariable Long id) {
        ConfederationResponseDTO confederationResponseDTO = confederationService.findConfederationById(id);
        return new ResponseEntity<>(confederationResponseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Busca una confederacion por su nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Confederacion encontrada", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ConfederationResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Confederacion no encontrada", content = @Content)
    })
    @GetMapping("/name/{name}") @ResponseBody
    public ResponseEntity<?> findConfederationByName(@Parameter(description = "Nombre de la confederacion", example = "CONMEBOL")
                                                   @PathVariable String name) {
        ConfederationResponseDTO confederationResponseDTO = confederationService.findConfederationByName(name);
        return new ResponseEntity<>(confederationResponseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Busca todas las confederaciones en la base de datos")
    @GetMapping @ResponseBody
    public List<ConfederationResponseDTO> findAllConfederations() {
        return confederationService.findAllConfederations();
    }

    @Operation(summary = "Edita una confederacion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Confederacion editada", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ConfederationDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Confederacion, pais o equipo no encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> editConfederation(@Parameter(description = "ID de la confederacion", example = "1")
                                                    @PathVariable Long id,
                                                    @Valid @RequestBody ConfederationDTO confederationDTO) {
        confederationService.editConfederation(id, confederationDTO);
        return new ResponseEntity<>("Edicion exitosa", HttpStatus.OK);
    }


}

package org.alvarub.fulbitoapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.alvarub.fulbitoapi.model.dto.LeagueDTO;
import org.alvarub.fulbitoapi.model.dto.LeagueResponseDTO;
import org.alvarub.fulbitoapi.service.LeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Ligas")
@RequestMapping("/api/leagues")
public class LeagueController {

    @Autowired
    private LeagueService leagueService;

    @Operation(summary = "Registra una liga en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Liga registrada", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LeagueDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Temporada no encontrada", content = @Content)
    })
    @PostMapping
    public ResponseEntity<String> saveLeague(@Valid @RequestBody LeagueDTO leagueDTO) {
        leagueService.saveLeague(leagueDTO);
        return new ResponseEntity<>("Registro exitoso", HttpStatus.CREATED);
    }

    @Operation(summary = "Busca una liga por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liga encontrada", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LeagueResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Liga no encontrada", content = @Content)
    })
    @GetMapping("/{id}") @ResponseBody
    public ResponseEntity<LeagueResponseDTO> findLeagueById(@Parameter(description = "ID de la liga", example = "1")
                                                            @PathVariable Long id) {
        LeagueResponseDTO leagueResponseDTO = leagueService.findLeagueById(id);
        return new ResponseEntity<>(leagueResponseDTO, HttpStatus.OK);

    }

    @Operation(summary = "Busca una liga por su nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liga encontrada", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LeagueResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Liga no encontrada", content = @Content)
    })
    @GetMapping("/{name}") @ResponseBody
    public ResponseEntity<LeagueResponseDTO> findLeagueByName(@Parameter(description = "Nombre de la liga", example = "Liga Profesional Argentina")
                                                            @PathVariable String name) {
        LeagueResponseDTO leagueResponseDTO = leagueService.findLeagueByName(name);
        return new ResponseEntity<>(leagueResponseDTO, HttpStatus.OK);

    }

    @Operation(summary = "Busca todas las ligas en la base de datos")
    @GetMapping @ResponseBody
    public List<LeagueResponseDTO> findAllLeagues() {
        return leagueService.findAllLeagues();
    }

    @Operation(summary = "Edita una liga")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liga editada", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LeagueDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Liga o temporada no encontrada", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> editLeague(@Parameter(description = "ID de la liga", example = "1")
                                             @PathVariable Long id,
                                             @Valid @RequestBody LeagueDTO leagueDTO) {
        leagueService.editLeague(id, leagueDTO);
        return new ResponseEntity<>("Edicion exitosa", HttpStatus.OK);
    }
}

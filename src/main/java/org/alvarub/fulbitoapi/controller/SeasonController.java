package org.alvarub.fulbitoapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.alvarub.fulbitoapi.model.dto.SeasonDTO;
import org.alvarub.fulbitoapi.model.dto.SeasonResponseDTO;
import org.alvarub.fulbitoapi.service.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Temporadas")
@RequestMapping("/api/seasons")
public class SeasonController {

    @Autowired
    private SeasonService seasonService;

    //
    @Operation(summary = "Registra una temporada en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Temporada registrada", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SeasonDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado", content = @Content)
    })
    @PostMapping
    public ResponseEntity<String> saveSeason(@Valid @RequestBody SeasonDTO seasonDTO) {
        seasonService.saveSeason(seasonDTO);
        return new ResponseEntity<>("Registro exitoso", HttpStatus.CREATED);
    }

    @Operation(summary = "Busca una temporada por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Temporada encontrada", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SeasonResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Temporada no encontrada", content = @Content)
    })
    @GetMapping("/{id}") @ResponseBody
    public ResponseEntity<?> findSeasonById(@Parameter(description = "ID de la temporada", example = "1")
                                            @PathVariable Long id) {
        SeasonResponseDTO seasonResponseDTO = seasonService.findSeasonById(id);
        return new ResponseEntity<>(seasonResponseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Busca una temporada por su codigo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Temporada encontrada", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SeasonResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Temporada no encontrada", content = @Content)
    })
    @GetMapping("/{code}") @ResponseBody
    public ResponseEntity<?> findSeasonByCode(@Parameter(description = "Codigo de la temporada", example = "ARG-2024")
                                              @PathVariable String code) {
        SeasonResponseDTO seasonResponseDTO = seasonService.findSeasonByCode(code);
        return new ResponseEntity<>(seasonResponseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Busca todas las temporadas en la base de datos")
    @GetMapping @ResponseBody
    public List<SeasonResponseDTO> findAllSeasons() {
        return seasonService.findAllSeasons();
    }

    @Operation(summary = "Edita una temporada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Temporada editada", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SeasonDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Temporada o equipo no encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> editSeason(@Parameter(description = "ID de la temporada", example = "1")
                                             @PathVariable Long id,
                                             @Valid @RequestBody SeasonDTO seasonDTO) {
        seasonService.editSeason(id, seasonDTO);
        return new ResponseEntity<>("Edicion exitosa", HttpStatus.OK);
    }

}

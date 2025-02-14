package org.alvarub.fulbitoapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.alvarub.fulbitoapi.model.dto.CountryDTO;
import org.alvarub.fulbitoapi.model.dto.CountryResponseDTO;
import org.alvarub.fulbitoapi.model.dto.LeagueDTO;
import org.alvarub.fulbitoapi.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Paises")
@RequestMapping("/api/countries")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @Operation(summary = "Registra un pais en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pais registrado", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CountryDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Liga o equipo no encontrado", content = @Content)
    })
    @PostMapping
    public ResponseEntity<String> saveCountry(@Valid @RequestBody CountryDTO countryDTO) {
        countryService.saveCountry(countryDTO);
        return new ResponseEntity<>("Registro exitoso", HttpStatus.CREATED);
    }

    @Operation(summary = "Busca un pais por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pais encontrado", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CountryResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Pais no encontrado", content = @Content)
    })
    @GetMapping("/{id}") @ResponseBody
    public ResponseEntity<?> findCountryById(@Parameter(description = "ID del pais", example = "1")
                                                              @PathVariable Long id) {
        CountryResponseDTO countryResponseDTO = countryService.findCountryById(id);
        return new ResponseEntity<>(countryResponseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Busca un pais por su nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pais encontrado", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CountryResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Pais no encontrado", content = @Content)
    })
    @GetMapping("/{name}") @ResponseBody
    public ResponseEntity<?> findCountryByName(@Parameter(description = "Nombre del pais", example = "Argentina")
                                                              @PathVariable String name) {
        CountryResponseDTO countryResponseDTO = countryService.findCountryByName(name);
        return new ResponseEntity<>(countryResponseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Busca todos los paises en la base de datos")
    @GetMapping @ResponseBody
    public List<CountryResponseDTO> findAllCountries() {
        return countryService.findAllCountries();
    }

    @Operation(summary = "Edita un Pais")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pais editado", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CountryDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Pais, liga o equipo no encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> editCountry(@Parameter(description = "ID del pais", example = "1")
                                              @PathVariable Long id,
                                              @Valid @RequestBody CountryDTO countryDTO) {
        countryService.editCountry(id, countryDTO);
        return new ResponseEntity<>("Edicion exitosa", HttpStatus.OK);
    }
}

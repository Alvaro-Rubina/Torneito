package org.alvarub.fulbitoapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.alvarub.fulbitoapi.model.dto.TeamDTO;
import org.alvarub.fulbitoapi.model.dto.TeamResponseDTO;
import org.alvarub.fulbitoapi.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Equipos")
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    //
    @Operation(summary = "Registra un equipo en la base de datos")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "Equipo registrada", content = {
                    @Content(mediaType = "application/json",
                            schema =  @Schema(implementation = TeamDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<String> saveTeam(@Valid @RequestBody TeamDTO teamDTO) {
        teamService.saveTeam(teamDTO);
        return new ResponseEntity<>("Registro exitoso", HttpStatus.CREATED);
    }

    @Operation(summary = "Busca un equipo por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipo encontrado", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TeamResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado", content = @Content)
    })
    @GetMapping("/{id}") @ResponseBody
    public ResponseEntity<?> findTeamById(@Parameter(description = "ID del equipo", example = "1")
                                              @PathVariable Long id) {
        TeamResponseDTO teamResponseDTO = teamService.findTeamById(id);
        return new ResponseEntity<>(teamResponseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Busca un equipo por su nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipo encontrado", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TeamResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado", content = @Content)
    })
    @GetMapping("/{name}") @ResponseBody
    public ResponseEntity<?> findTeamByName(@Parameter(description = "Nombre del equipo", example = "Boca Juniors")
                                          @PathVariable String name) {
        TeamResponseDTO teamResponseDTO = teamService.findTeamByName(name);
        return new ResponseEntity<>(teamResponseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Busca todos los equipos en la base de datos")
    @GetMapping @ResponseBody
    public List<TeamResponseDTO> findAllTeams() {
        return teamService.findAllTeams();
    }

    @Operation(summary = "Edita un equipo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipo editado", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TeamDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Parametros invalidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> editTeam(@Parameter(description = "ID del equipo", example = "1")
                                               @PathVariable Long id,
                                           @Valid @RequestBody TeamDTO teamDTO) {
        teamService.editTeam(id, teamDTO);
        return new ResponseEntity<>("Edicion exitosa", HttpStatus.OK);
    }

    @Operation(summary = "Obtiene un equipo aleatorio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipo encontrado", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TeamResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "No hay equipos registrados", content = @Content)
    })
    @GetMapping("/random") @ResponseBody
    public ResponseEntity<?> getRandomTeam() {
        TeamResponseDTO teamResponseDTO = teamService.getRandomTeam();
        return new ResponseEntity<>(teamResponseDTO, HttpStatus.OK);
    }
}

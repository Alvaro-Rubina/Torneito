package org.alvarub.fulbitoapi.service;

import org.alvarub.fulbitoapi.model.dto.team.TeamDTO;
import org.alvarub.fulbitoapi.model.dto.team.TeamResponseDTO;
import org.alvarub.fulbitoapi.model.entity.Team;
import org.alvarub.fulbitoapi.repository.ConfederationRepository;
import org.alvarub.fulbitoapi.repository.CountryRepository;
import org.alvarub.fulbitoapi.repository.TeamRepository;
import org.alvarub.fulbitoapi.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepo;

    @Autowired
    private ConfederationRepository confederationRepo;
    @Autowired
    private ConfederationService confederationService;

    @Autowired
    private CountryRepository countryRepo;
    @Autowired
    private CountryService countryService;

    @CacheEvict(value = "teams", allEntries = true)
    public void saveTeam(TeamDTO teamDTO) {
        Team team = toEntity(teamDTO);
        teamRepo.save(team);
    }

    @Cacheable(value = "teams", key = "#id")
    public TeamResponseDTO findTeamById(Long id) {
        Team team = teamRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Equipo con el id '" + id + "' no encontrado"));

        return toDto(team);
    }

    @Cacheable(value = "teams", key = "#name")
    public TeamResponseDTO findTeamByName(String name) {
        Team team = teamRepo.findByName(name)
                .orElseThrow(() -> new NotFoundException("Equipo '" + name + "' no encontrado"));

        return toDto(team);
    }

    @Cacheable("teams")
    public List<TeamResponseDTO> findAllTeams() {
        List<Team> teams = teamRepo.findAll();
        List<TeamResponseDTO> teamsDTO = new ArrayList<>();
        for (Team team: teams) {
            teamsDTO.add(toDto(team));
        }
        return teamsDTO;
    }

    @CachePut(value = "teams", key = "#id")
    public void editTeam(Long id, TeamDTO teamDTO) {
        Team team = teamRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Equipo con el id '" + id + "' no encontrado"));

        if (!team.getName().equals(teamDTO.getName())) {
            team.setName(teamDTO.getName());
        }

        if (!team.getLogo().equals(teamDTO.getLogo())) {
            team.setLogo(teamDTO.getLogo());
        }

        if (!team.getConfederation().getId().equals(teamDTO.getConfederationId())) {
            team.setConfederation(confederationRepo.findById(teamDTO.getConfederationId())
                    .orElseThrow(() -> new NotFoundException("Confederación con el id '" + teamDTO.getConfederationId() + "' no encontrada")));
        }

        if (!team.getCountry().getId().equals(teamDTO.getCountryId())) {
            team.setCountry(countryRepo.findById(teamDTO.getCountryId())
                    .orElseThrow(() -> new NotFoundException("País con el id '" + teamDTO.getCountryId() + "' no encontrado")));
        }

    }

    // Mappers
    private Team toEntity(TeamDTO teamDTO) {
        return Team.builder()
                .name(teamDTO.getName())
                .logo(teamDTO.getLogo())
                .confederation(confederationRepo.findById(teamDTO.getConfederationId())
                        .orElseThrow(() -> new NotFoundException("Confederación con el id '" + teamDTO.getConfederationId() + "' no encontrada")))
                .country(countryRepo.findById(teamDTO.getCountryId())
                        .orElseThrow(() -> new NotFoundException("País con el id '" + teamDTO.getCountryId() + "' no encontrado")))
                .build();
    }

    public TeamResponseDTO toDto(Team team) {
        return TeamResponseDTO.builder()
                .id(team.getId())
                .name(team.getName())
                .logo(team.getLogo())
                .confederation(confederationService.toDto(team.getConfederation()))
                .country(countryService.toDto(team.getCountry()))
                .build();
    }

}

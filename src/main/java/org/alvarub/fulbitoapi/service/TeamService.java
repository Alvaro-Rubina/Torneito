package org.alvarub.fulbitoapi.service;

import org.alvarub.fulbitoapi.model.dto.TeamDTO;
import org.alvarub.fulbitoapi.model.dto.TeamResponseDTO;
import org.alvarub.fulbitoapi.model.entity.Team;
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

    public boolean existsByName(String name) {
        return teamRepo.findByName(name).isPresent();
    }

    @CacheEvict(value = "teams", allEntries = true)
    public void saveTeam(TeamDTO teamDTO) {
        Team team = toEntity(teamDTO);
        teamRepo.save(team);
    }

    @CacheEvict(value = "teams", allEntries = true)
    public void saveAllTeams(List<TeamDTO> teamsDTO) {
        List<Team> teams = new ArrayList<>();
        for (TeamDTO teamDTO: teamsDTO) {
            teams.add(toEntity(teamDTO));
        }
        teamRepo.saveAll(teams);
    }

    @Cacheable("teams")
    public List<TeamResponseDTO> findAllTeams() {
        List<Team> teams = teamRepo.findAll();
        List<TeamResponseDTO> teamsResponse = new ArrayList<>();
        for (Team team: teams) {
            teamsResponse.add(toDto(team));
        }
        return teamsResponse;
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

    @CachePut(value = "teams", key = "#id")
    public void editTeam(Long id, TeamDTO teamDTO) {
        Team existingTeam = teamRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Equipo con el id '" + id + "' no encontrado"));

        if (teamDTO.getName() != null) {
            existingTeam.setName(teamDTO.getName());
        }
        if (teamDTO.getLogo() != null) {
            existingTeam.setLogo(teamDTO.getLogo());
        }
        if (teamDTO.getCountrieName() != null) {
            existingTeam.setCountrieName(teamDTO.getCountrieName());
        }

        teamRepo.save(existingTeam);
    }

    public TeamResponseDTO getRandomTeam() {
        List<TeamResponseDTO> teams = this.findAllTeams();
        if (teams.isEmpty()) {
            throw new NotFoundException("No hay equipos disponibles");
        }
        int randomIndex = (int) (Math.random() * teams.size());
        return teams.get(randomIndex);
    }

    // Mappers
    private Team toEntity(TeamDTO teamDTO) {
        return Team.builder()
                .name(teamDTO.getName())
                .logo(teamDTO.getLogo())
                .countrieName(teamDTO.getCountrieName())
                .build();
    }

    public TeamResponseDTO toDto(Team team) {
        return TeamResponseDTO.builder()
                .id(team.getId())
                .name(team.getName())
                .logo(team.getLogo())
                .countrieName(team.getCountrieName())
                .build();
    }

}

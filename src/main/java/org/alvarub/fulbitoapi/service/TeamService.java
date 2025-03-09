package org.alvarub.fulbitoapi.service;

import org.alvarub.fulbitoapi.model.dto.TeamDTO;
import org.alvarub.fulbitoapi.model.dto.TeamResponseDTO;
import org.alvarub.fulbitoapi.model.entity.Team;
import org.alvarub.fulbitoapi.repository.TeamRepository;
import org.alvarub.fulbitoapi.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepo;

    @Cacheable("teams")
    public boolean existsByName(String name) {
        return teamRepo.findByName(name).isPresent();
    }

    public void saveTeam(TeamDTO teamDTO) {
        Team team = toEntity(teamDTO);
        teamRepo.save(team);
    }

    public void saveAllTeams(List<TeamDTO> teamsDTO) {
        List<Team> teams = new ArrayList<>();
        for (TeamDTO teamDTO: teamsDTO) {
            teams.add(toEntity(teamDTO));
        }
        teamRepo.saveAll(teams);
    }

    public List<TeamResponseDTO> findAllTeams() {
        List<Team> teams = teamRepo.findAll();
        List<TeamResponseDTO> teamsResponse = new ArrayList<>();
        for (Team team: teams) {
            teamsResponse.add(toDto(team));
        }
        return teamsResponse;
    }

    public TeamResponseDTO findTeamById(Long id) {
        Team team = teamRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Equipo con el id '" + id + "' no encontrado"));

        return toDto(team);
    }

    public TeamResponseDTO findTeamByName(String name) {
        Team team = teamRepo.findByName(name)
                .orElseThrow(() -> new NotFoundException("Equipo '" + name + "' no encontrado"));

        return toDto(team);
    }

    public void editTeam(Long id, TeamDTO teamDTO) {
        if (teamRepo.existsById(id)) {
            Team team = toEntity(teamDTO);
            team.setId(id);
            teamRepo.save(team);
        } else {
            throw new NotFoundException("Equipo con el id '" + id + "' no encontrado");
        }
    }

    public TeamResponseDTO getRandomTeam() {
        List<Team> teams = teamRepo.findAll();
        if (teams.isEmpty()) {
            throw new NotFoundException("No hay equipos disponibles");
        }
        int randomIndex = (int) (Math.random() * teams.size());
        return toDto(teams.get(randomIndex));
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

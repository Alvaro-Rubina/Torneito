package org.alvarub.fulbitoapi.service;

import org.alvarub.fulbitoapi.model.dto.TeamDTO;
import org.alvarub.fulbitoapi.model.dto.TeamResponseDTO;
import org.alvarub.fulbitoapi.model.entity.Team;
import org.alvarub.fulbitoapi.repository.TeamRepository;
import org.alvarub.fulbitoapi.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepo;

    public void createTeam(TeamDTO teamDTO) {
        Team team = toEntity(teamDTO);
        teamRepo.save(team);
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
                .orElseThrow(() -> new NotFoundException("Equipo no encontrado"));

        return toDto(team);
    }

    public TeamResponseDTO findTeamByName(String name) {
        Team team = teamRepo.findByName(name)
                .orElseThrow(() -> new NotFoundException("Equipo " + name + " no encontrado"));

        return toDto(team);
    }

    public void editTeam(Long id, TeamDTO teamDTO) {
        if (teamRepo.existsById(id)) {
            Team team = toEntity(teamDTO);
            team.setId(id);
        } else {
            throw new NotFoundException("Equipo con el id " + id + " no encontrado");
        }
    }

    // Mappers
    private Team toEntity(TeamDTO teamDTO) {
        return Team.builder()
                .name(teamDTO.getName())
                .logo(teamDTO.getLogo())
                .build();
    }

    public TeamResponseDTO toDto(Team team) {
        return TeamResponseDTO.builder()
                .id(team.getId())
                .name(team.getName())
                .logo(team.getLogo())
                .build();
    }

}

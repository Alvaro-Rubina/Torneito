package org.alvarub.fulbitoapi.service;

import org.alvarub.fulbitoapi.model.dto.TeamDTO;
import org.alvarub.fulbitoapi.model.entity.Team;
import org.alvarub.fulbitoapi.repository.TeamRepository;
import org.alvarub.fulbitoapi.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepo;

    public void createTeam(TeamDTO teamDTO) {
        Team team = Team.builder()
                .name(teamDTO.getName())
                .logo(teamDTO.getLogo())
                .build();
        teamRepo.save(team);
    }

    public List<Team> findAllTeams() {
        return teamRepo.findAll();
    }

    public TeamDTO findTeamById(Long id) {
        Team team = teamRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Equipo no encontrado"));

        return TeamDTO.builder()
                .name(team.getName())
                .logo(team.getLogo())
                .build();
    }

    public Team findTeamByName(String name) {
        return teamRepo.findByName(name)
                .orElseThrow(() -> new NotFoundException("Equipo no encontrado"));
    }

    public void editTeam(Long id, TeamDTO teamDTO) {
        Team team = teamRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Equipo no encontrado"));

        team.setName(teamDTO.getName());
        team.setLogo(teamDTO.getLogo());

        teamRepo.save(team);
    }

}

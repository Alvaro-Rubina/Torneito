package org.alvarub.fulbitoapi.service;

import org.alvarub.fulbitoapi.model.dto.SeasonDTO;
import org.alvarub.fulbitoapi.model.dto.SeasonResponseDTO;
import org.alvarub.fulbitoapi.model.dto.TeamResponseDTO;
import org.alvarub.fulbitoapi.model.entity.Season;
import org.alvarub.fulbitoapi.model.entity.Team;
import org.alvarub.fulbitoapi.repository.LeagueRepository;
import org.alvarub.fulbitoapi.repository.SeasonRepository;
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
public class SeasonService {

    @Autowired
    private SeasonRepository seasonRepo;

    @Autowired
    private TeamRepository teamRepo;
    @Autowired
    private TeamService teamService;

    @Autowired
    private LeagueRepository leagueRepo;
    @Autowired
    private LeagueService leagueService;

    @CacheEvict(value = "seasons", allEntries = true)
    public void saveSeason(SeasonDTO seasonDTO) {
        Season season = toEntity(seasonDTO);
        seasonRepo.save(season);
    }

    @Cacheable(value = "seasons", key = "#id")
    public SeasonResponseDTO findSeasonById(Long id) {
        Season season = seasonRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Temporada con el id '" + id + "' no encontrada"));

        return toDto(season);
    }

    @Cacheable(value = "seasons", key = "#code")
    public SeasonResponseDTO findSeasonByCode(String code) {
        Season season = seasonRepo.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Temporada con el código '" + code + "' no encontrada"));

        return toDto(season);
    }

    @Cacheable("seasons")
    public List<SeasonResponseDTO> findAllSeasons() {
        List<Season> seasons = seasonRepo.findAll();
        List<SeasonResponseDTO> seasonsResponse = new ArrayList<>();
        for (Season season: seasons) {
            seasonsResponse.add(toDto(season));
        }
        return seasonsResponse;
    }

    @CachePut(value = "seasons", key = "#id")
    public void editSeason(Long id, SeasonDTO seasonDTO) {
        Season season = seasonRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Temporada con el id '" + id + "' no encontrada"));

        if (!season.getCode().equals(seasonDTO.getCode())) {
            season.setCode(seasonDTO.getCode());
        }

        if (!season.getYear().equals(seasonDTO.getYear())) {
            season.setYear(seasonDTO.getYear());
        }

        if (!season.getLeague().getId().equals(seasonDTO.getLeagueId())) {
            season.setLeague(leagueRepo.findById(seasonDTO.getLeagueId())
                    .orElseThrow(() -> new NotFoundException("Liga con el id '" + seasonDTO.getLeagueId() + "' no encontrada")));
        }

        // Actualizar equipos
        List<Team> currentTeams = season.getTeams();
        List<Long> newTeamIds = seasonDTO.getTeamsIds();

        // Equipos a eliminar
        currentTeams.removeIf(team -> !newTeamIds.contains(team.getId()));

        // Equipos a agregar
        for (Long teamId : newTeamIds) {
            if (currentTeams.stream().noneMatch(team -> team.getId().equals(teamId))) {
                Team team = teamRepo.findById(teamId)
                        .orElseThrow(() -> new NotFoundException("Equipo con el id '" + teamId + "' no encontrado"));
                currentTeams.add(team);
            }
        }

    }

    // Mappers
    private Season toEntity(SeasonDTO seasonDTO) {
        List<Team> teams = new ArrayList<>();
        for (Long teamId: seasonDTO.getTeamsIds()) {
            Team team = teamRepo.findById(teamId)
                    .orElseThrow(() -> new NotFoundException("Equipo con el id '" + teamId + "' no encontrado"));
            teams.add(team);
        }

        return Season.builder()
                .code(seasonDTO.getCode())
                .year(seasonDTO.getYear())
                .league(leagueRepo.findById(seasonDTO.getLeagueId())
                        .orElseThrow(() -> new NotFoundException("Liga con el id '" + seasonDTO.getLeagueId() + "' no encontrada")))
                .teams(teams)
                .build();
    }

    public SeasonResponseDTO toDto(Season season) {
        List<TeamResponseDTO> teamsDTO = new ArrayList<>();
        for (Team team: season.getTeams()) {
            teamsDTO.add(teamService.toDto(team));
        }

        return SeasonResponseDTO.builder()
                .id(season.getId())
                .code(season.getCode())
                .year(season.getYear())
                .league(leagueService.toDto(season.getLeague()))
                .teams(teamsDTO)
                .build();
    }
}

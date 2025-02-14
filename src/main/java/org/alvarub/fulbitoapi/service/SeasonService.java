package org.alvarub.fulbitoapi.service;

import org.alvarub.fulbitoapi.model.dto.SeasonDTO;
import org.alvarub.fulbitoapi.model.dto.SeasonResponseDTO;
import org.alvarub.fulbitoapi.model.dto.TeamDTO;
import org.alvarub.fulbitoapi.model.dto.TeamResponseDTO;
import org.alvarub.fulbitoapi.model.entity.Season;
import org.alvarub.fulbitoapi.model.entity.Team;
import org.alvarub.fulbitoapi.repository.SeasonRepository;
import org.alvarub.fulbitoapi.repository.TeamRepository;
import org.alvarub.fulbitoapi.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeasonService {

    @Autowired
    private SeasonRepository seasonRepo;

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamRepository teamRepo;

    public void saveSeason(SeasonDTO seasonDTO) {
        Season season = toEntity(seasonDTO);
        seasonRepo.save(season);
    }

    public List<SeasonResponseDTO> findAllSeasons() {
        List<Season> seasons = seasonRepo.findAll();
        List<SeasonResponseDTO> seasonsResponse = new ArrayList<>();
        for (Season season: seasons) {
            seasonsResponse.add(toDto(season));
        }
        return seasonsResponse;
    }

    public SeasonResponseDTO findSeasonById(Long id) {
        Season season = seasonRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Temporada con el id " + id + " no encontrada"));

        return toDto(season);
    }

    public SeasonResponseDTO findSeasonByName(String code) {
        Season season = seasonRepo.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Temporada con el código " + code + " no encontrada"));

        return toDto(season);
    }

    public void editSeason(Long id, SeasonDTO seasonDTO) {
        if (seasonRepo.existsById(id)) {
            Season season = toEntity(seasonDTO);
            season.setId(id);
        } else {
            throw new NotFoundException("Temporada con el id " + id + " no encontrada");
        }
    }

    // Mappers
    private Season toEntity(SeasonDTO seasonDTO) {
        List<Team> teams = new ArrayList<>();

        for (String team: seasonDTO.getTeams()) {
            teams.add(teamRepo.findByName(team)
                    .orElseThrow(() -> new NotFoundException("Equipo " + team + " no encontrado")));
        }

        return Season.builder()
                .code(seasonDTO.getCode())
                .year(seasonDTO.getYear())
                .teams(teams)
                .build();
    }

    private SeasonResponseDTO toDto(Season season) {
        List<TeamResponseDTO> teamsResponse = new ArrayList<>();
        for (Team team: season.getTeams()) {
            teamsResponse.add(teamService.toDto(team));
        }

        return SeasonResponseDTO.builder()
                .id(season.getId())
                .code(season.getCode())
                .year(season.getYear())
                .teams(teamsResponse)
                .build();
    }
}

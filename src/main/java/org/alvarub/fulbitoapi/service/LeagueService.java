package org.alvarub.fulbitoapi.service;

import org.alvarub.fulbitoapi.model.dto.LeagueDTO;
import org.alvarub.fulbitoapi.model.dto.LeagueResponseDTO;
import org.alvarub.fulbitoapi.model.dto.SeasonResponseDTO;
import org.alvarub.fulbitoapi.model.entity.League;
import org.alvarub.fulbitoapi.model.entity.Season;
import org.alvarub.fulbitoapi.repository.LeagueRepository;
import org.alvarub.fulbitoapi.repository.SeasonRepository;
import org.alvarub.fulbitoapi.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LeagueService {

    @Autowired
    private LeagueRepository leagueRepo;

    @Autowired
    private SeasonService seasonService;

    @Autowired
    private SeasonRepository seasonRepo;

    public void saveLeague(LeagueDTO leagueDTO) {
        League league = toEntity(leagueDTO);
        leagueRepo.save(league);

    }

    public List<LeagueResponseDTO> findAllLeagues() {
        List<League> leagues = leagueRepo.findAll();
        List<LeagueResponseDTO> leaguesResponse = new ArrayList<>();
        for (League league: leagues) {
            leaguesResponse.add(toDto(league));
        }
        return leaguesResponse;
    }

    public LeagueResponseDTO findLeagueById(Long id) {
        League league = leagueRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Liga con el id '" + id + "' no encontrada"));

        return toDto(league);
    }

    public LeagueResponseDTO findLeagueByName(String name) {
        League league = leagueRepo.findByName(name)
                .orElseThrow(() -> new NotFoundException("Liga con el nombre '" + name + "' no encontrada"));

        return toDto(league);
    }

    public void editLeague(Long id, LeagueDTO leagueDTO) {
        League existingLeague = leagueRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Liga con el id '" + id + "' no encontrada"));

        if (leagueDTO.getName() != null) {
            existingLeague.setName(leagueDTO.getName());
        }
        if (leagueDTO.getLogo() != null) {
            existingLeague.setLogo(leagueDTO.getLogo());
        }
        if (leagueDTO.getCountrieName() != null) {
            existingLeague.setCountrieName(leagueDTO.getCountrieName());
        }
        if (leagueDTO.getSeasons() != null && !leagueDTO.getSeasons().isEmpty()) {
            List<Season> seasons = new ArrayList<>();
            for (String seasonCode : leagueDTO.getSeasons()) {
                seasons.add(seasonRepo.findByCode(seasonCode)
                        .orElseThrow(() -> new NotFoundException("Temporada con código '" + seasonCode + "' no encontrada")));
            }
            existingLeague.setSeasons(seasons);
        }

        leagueRepo.save(existingLeague);
    }

    // Mappers
    private League toEntity(LeagueDTO leagueDTO) {
        List<Season> seasons = new ArrayList<>();

        for (String season: leagueDTO.getSeasons()) {
            seasons.add(seasonRepo.findByCode(season)
                    .orElseThrow(() -> new NotFoundException("Temporada con código '" + season + "' no encontrada")));
        }

        return League.builder()
                .name(leagueDTO.getName())
                .logo(leagueDTO.getLogo())
                .countrieName(leagueDTO.getCountrieName())
                .seasons(seasons)
                .build();
    }

    public LeagueResponseDTO toDto(League league) {
        List<SeasonResponseDTO> seasonsResponse = new ArrayList<>();
        for (Season season: league.getSeasons()) {
            seasonsResponse.add(seasonService.toDto(season));
        }

        return LeagueResponseDTO.builder()
                .id(league.getId())
                .name(league.getName())
                .logo(league.getLogo())
                .countrieName(league.getCountrieName())
                .seasons(seasonsResponse)
                .build();
    }
}

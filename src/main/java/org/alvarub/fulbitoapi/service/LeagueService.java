package org.alvarub.fulbitoapi.service;

import org.alvarub.fulbitoapi.model.dto.LeagueDTO;
import org.alvarub.fulbitoapi.model.dto.LeagueResponseDTO;
import org.alvarub.fulbitoapi.model.entity.League;
import org.alvarub.fulbitoapi.repository.CountryRepository;
import org.alvarub.fulbitoapi.repository.LeagueRepository;
import org.alvarub.fulbitoapi.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LeagueService {

    @Autowired
    private LeagueRepository leagueRepo;

    @Autowired
    private CountryRepository countryRepo;
    @Autowired
    private CountryService countryService;

    @CacheEvict(value = "leagues", allEntries = true)
    public void saveLeague(LeagueDTO leagueDTO) {
        League league = toEntity(leagueDTO);
        leagueRepo.save(league);

    }

    @Cacheable(value = "leagues", key = "#id")
    public LeagueResponseDTO findLeagueById(Long id) {
        League league = leagueRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Liga con el id '" + id + "' no encontrada"));

        return toDto(league);
    }

    @Cacheable(value = "leagues", key = "#name")
    public LeagueResponseDTO findLeagueByName(String name) {
        League league = leagueRepo.findByName(name)
                .orElseThrow(() -> new NotFoundException("Liga con el nombre '" + name + "' no encontrada"));

        return toDto(league);
    }

    @Cacheable("leagues")
    public List<LeagueResponseDTO> findAllLeagues() {
        List<League> leagues = leagueRepo.findAll();
        List<LeagueResponseDTO> leaguesDTO = new ArrayList<>();
        for (League league: leagues) {
            leaguesDTO.add(toDto(league));
        }
        return leaguesDTO;
    }

    @CachePut(value = "leagues", key = "#id")
    public void editLeague(Long id, LeagueDTO leagueDTO) {
        League league = leagueRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Liga con el id '" + id + "' no encontrada"));

        if (!league.getName().equals(leagueDTO.getName())) {
            league.setName(leagueDTO.getName());
        }

        if (!league.getLogo().equals(leagueDTO.getLogo())) {
            league.setLogo(leagueDTO.getLogo());
        }

        if (!league.getCountry().getId().equals(leagueDTO.getCountryId())) {
            league.setCountry(countryRepo.findById(leagueDTO.getCountryId())
                    .orElseThrow(() -> new NotFoundException("País con el id '" + leagueDTO.getCountryId() + "' no encontrado")));
        }

        leagueRepo.save(league);
    }

    // Mappers
    private League toEntity(LeagueDTO leagueDTO) {
        return League.builder()
                .name(leagueDTO.getName())
                .logo(leagueDTO.getLogo())
                .country(countryRepo.findById(leagueDTO.getCountryId())
                        .orElseThrow(() -> new NotFoundException("País con el id '" + leagueDTO.getCountryId() + "' no encontrado")))
                .build();
    }

    public LeagueResponseDTO toDto(League league) {
        return LeagueResponseDTO.builder()
                .id(league.getId())
                .name(league.getName())
                .logo(league.getLogo())
                .country(countryService.toDto(league.getCountry()))
                .build();
    }
}

package org.alvarub.fulbitoapi.service;

import org.alvarub.fulbitoapi.model.dto.*;
import org.alvarub.fulbitoapi.model.entity.*;
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
public class ConfederationService {

    @Autowired
    private ConfederationRepository confederationRepo;

    @Autowired
    private CountryService countryService;

    @Autowired
    private CountryRepository countryRepo;

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamRepository teamRepo;

    @CacheEvict(value = "confederations", allEntries = true)
    public void saveConfederation(ConfederationDTO confederationDTO) {
        Confederation confederation = toEntity(confederationDTO);
        confederationRepo.save(confederation);
    }

    @Cacheable("confederations")
    public List<ConfederationResponseDTO> findAllConfederations() {
        List<Confederation> confederations = confederationRepo.findAll();
        List<ConfederationResponseDTO> confederationsResponse = new ArrayList<>();

        for (Confederation confederation: confederations) {
            confederationsResponse.add(toDto(confederation));
        }

        return confederationsResponse;
    }

    @Cacheable(value = "confederations", key = "#id")
    public ConfederationResponseDTO findConfederationById(Long id) {
        Confederation confederation = confederationRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Confederacion con el id '" + id + "' no encontrada"));

        return toDto(confederation);
    }

    @Cacheable(value = "confederationes", key = "#name")
    public ConfederationResponseDTO findConfederationByName(String name) {
        Confederation confederation = confederationRepo.findByName(name)
                .orElseThrow(() -> new NotFoundException("Confederacion con el nombre '" + name + "' no encontrada"));

        return toDto(confederation);
    }

    @CachePut(value = "confederations", key = "#id")
    public void editConfederation(Long id, ConfederationDTO confederationDTO) {
        Confederation existingConfederation = confederationRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Confederacion con el id '" + id + "' no encontrada"));

        if (confederationDTO.getName() != null) {
            existingConfederation.setName(confederationDTO.getName());
        }
        if (confederationDTO.getLogo() != null) {
            existingConfederation.setLogo(confederationDTO.getLogo());
        }
        if (confederationDTO.getCountries() != null && !confederationDTO.getCountries().isEmpty()) {
            List<Country> countries = new ArrayList<>();
            for (String countryName : confederationDTO.getCountries()) {
                countries.add(countryRepo.findByName(countryName)
                        .orElseThrow(() -> new NotFoundException("País con el nombre '" + countryName + "' no encontrado")));
            }
            existingConfederation.setCountries(countries);
        }
        if (confederationDTO.getTeams() != null && !confederationDTO.getTeams().isEmpty()) {
            List<Team> teams = new ArrayList<>();
            for (String teamName : confederationDTO.getTeams()) {
                teams.add(teamRepo.findByName(teamName)
                        .orElseThrow(() -> new NotFoundException("Equipo con el nombre '" + teamName + "' no encontrado")));
            }
            existingConfederation.setTeams(teams);
        }

        confederationRepo.save(existingConfederation);
    }

    public CountryResponseDTO getRandomCountryByConfederation(Long id) {
        ConfederationResponseDTO confederation = this.findConfederationById(id);
        List<CountryResponseDTO> countries = confederation.getCountries();
        if (countries.isEmpty()) {
            throw new NotFoundException("No hay paises en la confederacion con id '" + id + "'");
        }
        int randomIndex = (int) (Math.random() * countries.size());
        return countries.get(randomIndex);
    }

    public TeamResponseDTO getRandomTeamByConfederation(Long id) {
        ConfederationResponseDTO confederation = this.findConfederationById(id);
        List<TeamResponseDTO> teams = confederation.getTeams();
        if (teams.isEmpty()) {
            throw new NotFoundException("No hay equipos en la confederacion con id '" + id + "'");
        }
        int randomIndex = (int) (Math.random() * teams.size());
        return teams.get(randomIndex);
    }

    // Mappers
    private Confederation toEntity(ConfederationDTO confederationDTO) {
        List<Country> countries = new ArrayList<>();
        List<Team> teams = new ArrayList<>();

        for (String country: confederationDTO.getCountries()) {
            countries.add(countryRepo.findByName(country)
                    .orElseThrow(() -> new NotFoundException("País con el nombre '" + country + "' no encontrado")));
        }
        for (String team: confederationDTO.getTeams()) {
            teams.add(teamRepo.findByName(team)
                    .orElseThrow(() -> new NotFoundException("Equipo con el nombre '" + team + "' no encontrado")));
        }

        return Confederation.builder()
                .name(confederationDTO.getName())
                .logo(confederationDTO.getLogo())
                .countries(countries)
                .teams(teams)
                .build();
    }

    private ConfederationResponseDTO toDto(Confederation confederation) {
        List<CountryResponseDTO> countriesResponse = new ArrayList<>();
        List<TeamResponseDTO> teamsResponse = new ArrayList<>();

        for (Country country: confederation.getCountries()) {
            countriesResponse.add(countryService.toDto(country));
        }
        for (Team team: confederation.getTeams()) {
            teamsResponse.add(teamService.toDto(team));
        }

        return ConfederationResponseDTO.builder()
                .id(confederation.getId())
                .name(confederation.getName())
                .logo(confederation.getLogo())
                .countries(countriesResponse)
                .teams(teamsResponse)
                .build();
    }

}

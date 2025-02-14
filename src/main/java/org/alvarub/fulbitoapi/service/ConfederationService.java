package org.alvarub.fulbitoapi.service;

import org.alvarub.fulbitoapi.model.dto.*;
import org.alvarub.fulbitoapi.model.entity.*;
import org.alvarub.fulbitoapi.repository.ConfederationRepository;
import org.alvarub.fulbitoapi.repository.CountryRepository;
import org.alvarub.fulbitoapi.repository.TeamRepository;
import org.alvarub.fulbitoapi.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void saveConfederation(ConfederationDTO confederationDTO) {
        Confederation confederation = toEntity(confederationDTO);
        confederationRepo.save(confederation);
    }

    public List<ConfederationResponseDTO> findAllConfederations() {
        List<Confederation> confederations = confederationRepo.findAll();
        List<ConfederationResponseDTO> confederationsResponse = new ArrayList<>();

        for (Confederation confederation: confederations) {
            confederationsResponse.add(toDto(confederation));
        }

        return confederationsResponse;
    }

    public ConfederationResponseDTO findConfederationById(Long id) {
        Confederation confederation = confederationRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Confederacion con el id '" + id + "' no encontrada"));

        return toDto(confederation);
    }

    public ConfederationResponseDTO findConfederationByName(String name) {
        Confederation confederation = confederationRepo.findByName(name)
                .orElseThrow(() -> new NotFoundException("Confederacion con el nombre '" + name + "' no encontrada"));

        return toDto(confederation);
    }

    public void editConfederation(Long id, ConfederationDTO confederationDTO) {
        if (confederationRepo.existsById(id)) {
            Confederation confederation = toEntity(confederationDTO);
            confederation.setId(id);
            confederationRepo.save(confederation);

        } else {
            throw new NotFoundException("Confederacion con el id '" + id + "' no encontrada");
        }
    }

    public CountryResponseDTO getRandomCountryByConfederation(Long id) {
        Confederation confederation = confederationRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Confederacion con el id '" + id + "' no encontrada"));
        List<Country> countries = confederation.getCountries();
        if (countries.isEmpty()) {
            throw new NotFoundException("No hay paises disponibles");
        }
        int randomIndex = (int) (Math.random() * countries.size());
        return countryService.toDto(countries.get(randomIndex));
    }

    public TeamResponseDTO getRandomTeamByConfederation(Long id) {
        Confederation confederation = confederationRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Confederacion con el id '" + id + "' no encontrada"));
        List<Team> teams = confederation.getTeams();
        if (teams.isEmpty()) {
            throw new NotFoundException("No hay equipos disponibles");
        }
        int randomIndex = (int) (Math.random() * teams.size());
        return teamService.toDto(teams.get(randomIndex));
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

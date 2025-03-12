package org.alvarub.fulbitoapi.service;

import org.alvarub.fulbitoapi.model.dto.*;
import org.alvarub.fulbitoapi.model.entity.*;
import org.alvarub.fulbitoapi.repository.CountryRepository;
import org.alvarub.fulbitoapi.repository.LeagueRepository;
import org.alvarub.fulbitoapi.repository.TeamRepository;
import org.alvarub.fulbitoapi.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepo;

    @Autowired
    private  LeagueService leagueService;

    @Autowired
    private LeagueRepository leagueRepo;

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamRepository teamRepo;

    public void saveCountry(CountryDTO countryDTO) {
        Country country = toEntity(countryDTO);
        countryRepo.save(country);
    }

    public List<CountryResponseDTO> findAllCountries() {
        List<Country> countries = countryRepo.findAll();
        List<CountryResponseDTO> countriesResponse = new ArrayList<>();

        for (Country country: countries) {
            countriesResponse.add(toDto(country));
        }

        return countriesResponse;
    }

    public CountryResponseDTO findCountryById(Long id) {
        Country country = countryRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("País con el id '" + id + "' no encontrado"));

        return toDto(country);
    }

    public CountryResponseDTO findCountryByName(String name) {
        Country country = countryRepo.findByName(name)
                .orElseThrow(() -> new NotFoundException("País con el nombre '" + name + "' no encontrado"));

        return toDto(country);
    }

    public void editCountry(Long id, CountryDTO countryDTO) {
        Country existingCountry = countryRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("País con el id '" + id + "' no encontrado"));

        if (countryDTO.getName() != null) {
            existingCountry.setName(countryDTO.getName());
        }
        if (countryDTO.getFlag() != null) {
            existingCountry.setFlag(countryDTO.getFlag());
        }
        if (countryDTO.getContinent() != null) {
            existingCountry.setContinent(Continent.valueOf(countryDTO.getContinent()));
        }
        if (countryDTO.getConfederationName() != null) {
            existingCountry.setConfederationName(countryDTO.getConfederationName());
        }
        if (countryDTO.getLeagues() != null && !countryDTO.getLeagues().isEmpty()) {
            List<League> leagues = new ArrayList<>();
            for (String leagueName : countryDTO.getLeagues()) {
                leagues.add(leagueRepo.findByName(leagueName)
                        .orElseThrow(() -> new NotFoundException("Liga con el nombre '" + leagueName + "' no encontrada")));
            }
            existingCountry.setLeagues(leagues);
        }
        if (countryDTO.getTeams() != null && !countryDTO.getTeams().isEmpty()) {
            List<Team> teams = new ArrayList<>();
            for (String teamName : countryDTO.getTeams()) {
                teams.add(teamRepo.findByName(teamName)
                        .orElseThrow(() -> new NotFoundException("Equipo con el nombre '" + teamName + "' no encontrado")));
            }
            existingCountry.setTeams(teams);
        }

        countryRepo.save(existingCountry);
    }

    public TeamResponseDTO getRandomTeamByCountry(Long id) {
        Country country = countryRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("País con el id '" + id + "' no encontrado"));
        List<Team> teams = country.getTeams();
        if (teams.isEmpty()) {
            throw new NotFoundException("No hay equipos disponibles");
        }
        int randomIndex = (int) (Math.random() * teams.size());
        return teamService.toDto(teams.get(randomIndex));
    }

    public CountryResponseDTO getRandomCountry() {
        List<Country> countries = countryRepo.findAll();
        if (countries.isEmpty()) {
            throw new NotFoundException("No hay países disponibles");
        }
        int randomIndex = (int) (Math.random() * countries.size());
        return toDto(countries.get(randomIndex));
    }

    // Mappers
    private Country toEntity(CountryDTO countryDTO) {
        List<League> leagues = new ArrayList<>();
        List<Team> teams = new ArrayList<>();

        for (String league: countryDTO.getLeagues()) {
            leagues.add(leagueRepo.findByName(league)
                    .orElseThrow(() -> new NotFoundException("Liga con el nombre '" + league + "' no encontrada")));
        }
        for (String team: countryDTO.getTeams()) {
            teams.add(teamRepo.findByName(team)
                    .orElseThrow(() -> new NotFoundException("Equipo con el nombre '" + team  + "' no encontrada")));
        }

        return Country.builder()
                .name(countryDTO.getName())
                .flag(countryDTO.getFlag())
                .continent(Continent.valueOf(countryDTO.getContinent()))
                .confederationName(countryDTO.getConfederationName())
                .leagues(leagues)
                .teams(teams)
                .build();
    }

    public CountryResponseDTO toDto(Country country) {
        List<LeagueResponseDTO> leaguesResponse = new ArrayList<>();
        List<TeamResponseDTO> teamsResponse = new ArrayList<>();

        for (League league: country.getLeagues()) {
            leaguesResponse.add(leagueService.toDto(league));
        }
        for (Team team: country.getTeams()) {
            teamsResponse.add(teamService.toDto(team));
        }

        return CountryResponseDTO.builder()
                .id(country.getId())
                .name(country.getName())
                .flag(country.getFlag())
                .continent(String.valueOf(country.getContinent()))
                .confederationName(country.getConfederationName())
                .leagues(leaguesResponse)
                .teams(teamsResponse)
                .build();
    }
}

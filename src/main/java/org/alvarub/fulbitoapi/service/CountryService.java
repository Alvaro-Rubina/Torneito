package org.alvarub.fulbitoapi.service;

import org.alvarub.fulbitoapi.model.dto.*;
import org.alvarub.fulbitoapi.model.entity.*;
import org.alvarub.fulbitoapi.repository.ConfederationRepository;
import org.alvarub.fulbitoapi.repository.CountryRepository;
import org.alvarub.fulbitoapi.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepo;

    @Autowired
    private ConfederationRepository confederationRepo;
    @Autowired
    private ConfederationService confederationService;

    @CacheEvict(value = "countries", allEntries = true)
    public void saveCountry(CountryDTO countryDTO) {
        Country country = toEntity(countryDTO);
        countryRepo.save(country);
    }

    @Cacheable(value = "countries", key = "#id")
    public CountryResponseDTO findCountryById(Long id) {
        Country country = countryRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("País con el id '" + id + "' no encontrado"));

        return toDto(country);
    }

    @Cacheable(value = "countries", key = "#name")
    public CountryResponseDTO findCountryByName(String name) {
        Country country = countryRepo.findByName(name)
                .orElseThrow(() -> new NotFoundException("País con el nombre '" + name + "' no encontrado"));

        return toDto(country);
    }

    @Cacheable("countries")
    public List<CountryResponseDTO> findAllCountries() {
        List<Country> countries = countryRepo.findAll();
        List<CountryResponseDTO> countriesDTO = new ArrayList<>();

        for (Country country: countries) {
            countriesDTO.add(toDto(country));
        }

        return countriesDTO;
    }

    @CachePut(value = "countries", key = "#id")
    public void editCountry(Long id, CountryDTO countryDTO) {
        Country country = countryRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("País con el id '" + id + "' no encontrado"));

        if (!country.getName().equals(countryDTO.getName())) {
            country.setName(countryDTO.getName());
        }

        if (!country.getFlag().equals(countryDTO.getFlag())) {
            country.setFlag(countryDTO.getFlag());
        }

        if (!country.getContinent().name().equals(countryDTO.getContinent())) {
            country.setContinent(Continent.valueOf(countryDTO.getContinent()));
        }

        if (!country.getConfederation().getId().equals(countryDTO.getConfederationId())) {
            country.setConfederation(confederationRepo.findById(countryDTO.getConfederationId())
                    .orElseThrow(() -> new NotFoundException("Confederacion con el id '" + countryDTO.getConfederationId() + "' no encontrada")));
        }

        countryRepo.save(country);
    }

    // Mappers
    private Country toEntity(CountryDTO countryDTO) {
        return Country.builder()
                .name(countryDTO.getName())
                .flag(countryDTO.getFlag())
                .continent(Continent.valueOf(countryDTO.getContinent()))
                .confederation(confederationRepo.findById(countryDTO.getConfederationId())
                        .orElseThrow(() -> new NotFoundException("Confederacion con el id '" + countryDTO.getConfederationId() + "' no encontrada")))
                .build();
    }

    public CountryResponseDTO toDto(Country country) {
        return CountryResponseDTO.builder()
                .id(country.getId())
                .name(country.getName())
                .flag(country.getFlag())
                .continent(country.getContinent().name())
                .confederation(confederationService.toDto(country.getConfederation()))
                .build();
    }
}

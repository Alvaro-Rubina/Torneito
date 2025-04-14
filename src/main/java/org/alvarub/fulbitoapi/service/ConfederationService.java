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

    @Cacheable("confederations")
    public List<ConfederationResponseDTO> findAllConfederations() {
        List<Confederation> confederations = confederationRepo.findAll();
        List<ConfederationResponseDTO> confederationsDTO = new ArrayList<>();
        for (Confederation confederation : confederations) {
            confederationsDTO.add(toDto(confederation));
        }
        return confederationsDTO;
    }

    @CachePut(value = "confederations", key = "#id")
    public void editConfederation(Long id, ConfederationDTO confederationDTO) {
        Confederation confederation = confederationRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Confederacion con el id '" + id + "' no encontrada"));

        if (!confederation.getName().equals(confederationDTO.getName())) {
            confederation.setName(confederationDTO.getName());
        }

        if (!confederation.getLogo().equals(confederationDTO.getLogo())) {
            confederation.setLogo(confederationDTO.getLogo());
        }

        confederationRepo.save(confederation);
    }

    // Mappers
    private Confederation toEntity(ConfederationDTO confederationDTO) {
         return Confederation.builder()
                 .name(confederationDTO.getName())
                 .logo(confederationDTO.getLogo())
                 .build();
    }

    private ConfederationResponseDTO toDto(Confederation confederation) {
        return ConfederationResponseDTO.builder()
                .id(confederation.getId())
                .name(confederation.getName())
                .logo(confederation.getLogo())
                .build();
    }

}

package org.alvarub.fulbitoapi.repository;

import org.alvarub.fulbitoapi.model.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}

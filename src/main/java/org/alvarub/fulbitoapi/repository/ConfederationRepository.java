package org.alvarub.fulbitoapi.repository;


import org.alvarub.fulbitoapi.model.entity.Confederation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfederationRepository extends JpaRepository<Confederation, Long> {

    Optional<Confederation> findByName(String name);
}

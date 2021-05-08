package iu3.rpo.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import iu3.rpo.spring.models.Museum;

@Repository
public interface MuseumRepository extends JpaRepository<Museum, Long> {
}
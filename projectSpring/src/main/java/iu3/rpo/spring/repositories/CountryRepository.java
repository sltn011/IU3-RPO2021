package iu3.rpo.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import iu3.rpo.spring.models.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
}

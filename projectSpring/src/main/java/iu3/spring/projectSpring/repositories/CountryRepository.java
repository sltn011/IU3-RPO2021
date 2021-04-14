package iu3.spring.projectSpring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import iu3.spring.projectSpring.models.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

}

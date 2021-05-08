package iu3.rpo.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import iu3.rpo.spring.models.Painting;

@Repository
public interface PaintingRepository extends JpaRepository<Painting, Long> {
}
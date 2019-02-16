
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Procession;

@Repository
public interface ProcessionRepository extends JpaRepository<Procession, Integer> {

	@Query("select b.processions from Brotherhood b where b.id = ?1")
	Collection<Procession> findAllProcessionsOfOneBrotherhood(int brotherhoodId);
}

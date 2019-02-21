
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Procession;

@Repository
public interface ProcessionRepository extends JpaRepository<Procession, Integer> {

	@Query("select p from Procession p where p.brotherhood.id = ?1")
	Collection<Procession> findAllProcessionsOfOneBrotherhood(int brotherhoodId);

	@Query("select p from Procession p where p.isDraft = 0")
	Collection<Procession> findAllProcessionsFinal();
	
}

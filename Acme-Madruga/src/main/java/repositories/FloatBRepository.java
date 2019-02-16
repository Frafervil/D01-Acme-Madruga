
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FloatB;

@Repository
public interface FloatBRepository extends JpaRepository<FloatB, Integer> {

	@Query("select b.floatBs from Brotherhood b where b.id = ?1")
	Collection<FloatB> findAllProcessionsOfOneBrotherhood(int brotherhoodId);
}

package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FloatB;

@Repository
public interface FloatBRepository extends JpaRepository<FloatB, Integer> {

	@Query("select f from FloatB f where f.brotherhood.id = ?1")
	Collection<FloatB> findByBrotherhoodId(int brotherhoodId);

	@Query("select f from FloatB f where f.procession.id = ?1")
	Collection<FloatB> findByProcessionId(int processionId);
}

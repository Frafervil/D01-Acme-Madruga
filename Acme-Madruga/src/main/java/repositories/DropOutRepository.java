
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.DropOut;

@Repository
public interface DropOutRepository extends JpaRepository<DropOut, Integer> {

	@Query("select d from Enrolment d where d.brotherhood.id = ?1")
	Collection<DropOut> findByBrotherhoodId(int brotherhoodId);

	@Query("select d from Enrolment d where d.member.id = ?1")
	Collection<DropOut> findByMemberId(int memberId);

	@Query("select d from Enrolment d where d.brotherhood.id = ?1 and d.member.id = ?2")
	Collection<DropOut> findByBrotherhoodIdAndMemberId(int brotherhoodId, int memberId);

}

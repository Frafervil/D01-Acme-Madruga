
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

	@Query("select m.member from Enrolment m where m.brotherhood.id = ?1")
	Collection<Member> findAllMembersOfOneBrotherhood(int brotherhoodId);
}
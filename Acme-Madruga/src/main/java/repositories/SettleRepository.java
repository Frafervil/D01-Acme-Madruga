
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Settle;

@Repository
public interface SettleRepository extends JpaRepository<Settle, Integer> {

	@Query("select s.settle from Brotherhood s where s.settle = NULL")
	Collection<Settle> findSettlesWithNoBrotherhood();

	@Query("select count(s) from Brotherhood s where s.settle.id = ?1")
	Integer findUsedSettle(int settleId);
}

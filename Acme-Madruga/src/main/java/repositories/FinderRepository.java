
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;
import domain.Procession;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select distinct p from Procession p where p.isDraft=0 AND ?1 = '' OR (p.ticker LIKE CONCAT('%',?1,'%')) OR (p.description LIKE CONCAT('%',?1,'%')) OR (p.title LIKE CONCAT('%',?1,'%')) AND (?3 = null OR (p.moment >= ?3)) AND (?4 = null OR (p.moment <= ?4)) AND ?2='' OR (p.brotherhood.settle.area = ?2)")
	Collection<Procession> searchProcessions(String keyword, String area, Date dateMin, Date dateMax);

}

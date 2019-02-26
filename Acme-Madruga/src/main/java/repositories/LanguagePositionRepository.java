
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.LanguagePosition;

@Repository
public interface LanguagePositionRepository extends JpaRepository<LanguagePosition, Integer> {

}

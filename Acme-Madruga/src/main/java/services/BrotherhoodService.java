
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BrotherhoodRepository;
import domain.Brotherhood;

@Service
@Transactional
public class BrotherhoodService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private BrotherhoodRepository	brotherhoodRepository;


	// Supporting services ----------------------------------------------------

	// Additional functions

	// Simple CRUD Methods
	public Brotherhood findOne(final int brotherhoodId) {
		Brotherhood result;

		result = this.brotherhoodRepository.findOne(brotherhoodId);
		Assert.notNull(result);
		return result;

	}

	public Collection<Brotherhood> findAll() {
		Collection<Brotherhood> result;

		result = this.brotherhoodRepository.findAll();
		Assert.notNull(result);
		return result;
	}

}

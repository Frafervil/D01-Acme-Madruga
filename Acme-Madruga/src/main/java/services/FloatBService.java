
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FloatBRepository;
import domain.FloatB;

@Service
@Transactional
public class FloatBService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private FloatBRepository	floatBRepository;


	// Supporting services ----------------------------------------------------

	// Additional functions

	// Simple CRUD Methods
	public FloatB findOne(final int floatBId) {
		FloatB result;

		result = this.floatBRepository.findOne(floatBId);
		Assert.notNull(result);
		return result;

	}

	public Collection<FloatB> findAll() {
		Collection<FloatB> result;

		result = this.floatBRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	// Business Method

	public Collection<FloatB> findAllFloatsOfOneBrotherhood(final int brotherhoodId) {
		Collection<FloatB> result;

		result = this.floatBRepository.findAllProcessionsOfOneBrotherhood(brotherhoodId);
		Assert.notNull(result);
		return result;
	}
}

package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FloatBRepository;
import domain.Brotherhood;
import domain.FloatB;
import domain.Procession;

@Service
@Transactional
public class FloatBService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private FloatBRepository floatBRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private BrotherhoodService brotherhoodService;

	// Additional functions

	// Simple CRUD Methods

	public FloatB create() {

		Brotherhood principal;
		FloatB result;
		Collection<String> pictures;

		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);

		result = new FloatB();
		pictures = new ArrayList<String>();

		result.setPictures(pictures);
		result.setBrotherhood(principal);

		return result;
	}

	public FloatB save(final FloatB floatB) {

		Brotherhood principal;
		FloatB result;

		Assert.notNull(floatB);

		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(floatB.getBrotherhood().equals(principal));

		result = this.floatBRepository.save(floatB);
		Assert.notNull(result);

		return result;
	}

	public void delete(final FloatB floatB) {

		Brotherhood principal;

		Assert.notNull(floatB);

		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(floatB.getBrotherhood().equals(principal));

		this.floatBRepository.delete(floatB);
	}

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

	// Other business methods

	public Collection<FloatB> findByBrotherhoodId(final int brotherhoodId) {
		Collection<FloatB> result;

		result = this.floatBRepository.findByBrotherhoodId(brotherhoodId);
		Assert.notNull(result);
		return result;
	}
}

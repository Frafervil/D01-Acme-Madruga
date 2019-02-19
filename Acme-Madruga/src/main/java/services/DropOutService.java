
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import repositories.DropOutRepository;
import domain.Brotherhood;
import domain.DropOut;
import domain.Member;

public class DropOutService {

	// Managed repository -----------------------------------------------------

	private DropOutRepository	dropOutRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private BrotherhoodService	brotherhoodService;


	// Simple CRUD Methods

	public DropOut create(final Member member) {

		Brotherhood principal;
		DropOut result;
		Date moment;

		Assert.notNull(member);

		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);
		result = new DropOut();
		moment = new Date(System.currentTimeMillis() - 1000);

		result.setMember(member);
		result.setMoment(moment);
		result.setBrotherhood(principal);

		return result;
	}

	public DropOut save(final DropOut dO) {

		Brotherhood principal;
		DropOut result;

		Assert.notNull(dO);

		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(dO.getBrotherhood().getId() == principal.getId());

		result = this.dropOutRepository.save(dO);
		Assert.notNull(result);

		return result;
	}

	public DropOut findOne(final int dropOutId) {
		DropOut result;

		result = this.dropOutRepository.findOne(dropOutId);
		Assert.notNull(result);
		return result;
	}

	public Collection<DropOut> findAll() {
		Collection<DropOut> result;

		result = this.dropOutRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	// Other business methods

	public Collection<DropOut> findByBrotherhoodId(final int brotherhoodId) {
		Collection<DropOut> result;

		result = this.dropOutRepository.findByBrotherhoodId(brotherhoodId);
		Assert.notNull(result);
		return result;
	}

	public Collection<DropOut> findByMemberId(final int memberId) {
		Collection<DropOut> result;

		result = this.dropOutRepository.findByMemberId(memberId);
		Assert.notNull(result);
		return result;
	}

	public Collection<DropOut> findByBrotherhoodIdAndMemberId(final int brotherhoodId, final int memberId) {
		Collection<DropOut> result;

		result = this.dropOutRepository.findByBrotherhoodIdAndMemberId(brotherhoodId, memberId);
		Assert.notNull(result);
		return result;
	}
}

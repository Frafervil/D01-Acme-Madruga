package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.util.Assert;

import repositories.EnrolmentRepository;
import domain.Brotherhood;
import domain.DropOut;
import domain.Enrolment;

public class EnrolmentService {

	// Managed repository -----------------------------------------------------

	private EnrolmentRepository enrolmentRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private DropOutService		dropOutService;


	// Simple CRUD Methods

	public Enrolment create() {

		Brotherhood principal;
		Enrolment result;
		Date moment;

		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);
		result = new Enrolment();
		moment = new Date(System.currentTimeMillis() - 1000);

		result.setBrotherhood(principal);
		result.setMoment(moment);

		return result;
	}

	public Enrolment save(final Enrolment enrolment) {

		Brotherhood principal;
		Enrolment result;

		Assert.notNull(enrolment);

		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(enrolment.getBrotherhood().getId() == principal.getId());

		result = this.enrolmentRepository.save(enrolment);
		Assert.notNull(result);

		return result;
	}

	public void delete(final Enrolment enrolment) {

		Brotherhood principal;
		DropOut dO;

		Assert.notNull(enrolment);

		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(enrolment.getBrotherhood().getId() == principal.getId());

		dO = this.dropOutService.create(enrolment.getMember());
		dO = this.dropOutService.save(dO);
	}

	public Enrolment findOne(final int enrolmentId) {
		Enrolment result;

		result = this.enrolmentRepository.findOne(enrolmentId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Enrolment> findAll() {
		Collection<Enrolment> result;

		result = this.enrolmentRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	// Other business methods

	public Collection<Enrolment> findByBrotherhoodId(final int brotherhoodId) {
		Collection<Enrolment> result;

		result = this.enrolmentRepository.findByBrotherhoodId(brotherhoodId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Enrolment> findByMemberId(final int memberId) {
		Collection<Enrolment> result;

		result = this.enrolmentRepository.findByMemberId(memberId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Enrolment> findByBrotherhoodIdAndMemberId(final int brotherhoodId, final int memberId) {
		Collection<Enrolment> result;

		result = this.enrolmentRepository.findByBrotherhoodIdAndMemberId(
				brotherhoodId, memberId);
		Assert.notNull(result);
		return result;
	}
}

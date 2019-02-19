package services;

import java.util.Collection;

import org.springframework.util.Assert;

import repositories.EnrolmentRepository;
import domain.Enrolment;

public class EnrolmentService {

	// Managed repository -----------------------------------------------------

	private EnrolmentRepository enrolmentRepository;

	// Supporting services ----------------------------------------------------

	// Simple CRUD Methods

	public Enrolment create() {

		Enrolment result;

		result = new Enrolment();

		return result;
	}

	public Enrolment save(final Enrolment enrolment) {

		Enrolment result;

		Assert.notNull(enrolment);

		result = this.enrolmentRepository.save(enrolment);
		Assert.notNull(result);

		return result;
	}

	public void delete(final Enrolment enrolment) {

		Assert.notNull(enrolment);

		this.enrolmentRepository.delete(enrolment);
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

	public Enrolment findByBrotherhoodIdAndMemberId(final int brotherhoodId,
			final int memberId) {
		Enrolment result;

		result = this.enrolmentRepository.findByBrotherhoodIdAndMemberId(
				brotherhoodId, memberId);
		Assert.notNull(result);
		return result;
	}
}

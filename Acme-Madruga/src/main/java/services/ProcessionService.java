package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BrotherhoodRepository;
import repositories.ProcessionRepository;
import domain.Brotherhood;
import domain.Enrolment;
import domain.Procession;
import domain.Request;

@Service
@Transactional
public class ProcessionService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ProcessionRepository processionRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private BrotherhoodService brotherhoodService;

	// Simple CRUD Methods
	public Procession create() {
		Procession result;

		result = new Procession();

		result.setRequests(new ArrayList<Request>());
		result.setIsDraft(true);
		return result;
	}

	public Procession findOne(final int processionId) {
		Procession result;

		result = this.processionRepository.findOne(processionId);
		Assert.notNull(result);
		return result;

	}

	public Collection<Procession> findAll() {
		Collection<Procession> result;

		result = this.processionRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	// Business Methods
	public Collection<Procession> findAllProcessionsOfOneBrotherhood(
			final int brotherhoodId) {
		Collection<Procession> result;

		result = this.processionRepository
				.findAllProcessionsOfOneBrotherhood(brotherhoodId);
		Assert.notNull(result);
		return result;
	}
}

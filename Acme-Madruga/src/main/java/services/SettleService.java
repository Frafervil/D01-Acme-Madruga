
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SettleRepository;
import domain.Administrator;
import domain.Settle;

@Service
@Transactional
public class SettleService {

	// Managed Repository

	@Autowired
	SettleRepository		settleRepository;

	// Services

	@Autowired
	AdministratorService	administratorService;


	//Simple CRUD methods
	public Settle create() {
		final Settle result;
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = new Settle();
		Assert.notNull(result);

		return result;
	}

	public void delete(final Settle settle) {
		Integer settles;

		settles = this.settleRepository.findUsedSettle(settle.getId());

		if (settles == 0)
			this.settleRepository.delete(settle);
		else
			Assert.isTrue(settles > 0);
	}

	public void save(final Settle settle) {
		Settle result;
		result = this.settleRepository.save(settle);
		Assert.notNull(result);

	}

	public Settle findOne(final int settleId) {
		Settle result;

		result = this.settleRepository.findOne(settleId);
		Assert.notNull(result);
		return result;

	}

	public Collection<Settle> findAll() {
		Collection<Settle> result;

		result = this.settleRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	//Other bussiness methods

	public Collection<Settle> findSettlesWithNoBrotherhood() {
		Collection<Settle> result;

		result = this.settleRepository.findAll();
		Assert.notNull(result);
		return result;
	}
}


package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SettleRepository;
import domain.Settle;

@Service
@Transactional
public class SettleService {

	// Managed Repository

	@Autowired
	SettleRepository	settleRepository;


	// Services

	//Simple CRUD methods
	public Settle create() {
		final Settle result;
		result = new Settle();

		return result;
	}

	public void delete(final Settle settle) {
		this.settleRepository.delete(settle);
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

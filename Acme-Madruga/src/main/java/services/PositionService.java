
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PositionRepository;
import domain.Administrator;
import domain.Position;

@Service
@Transactional
public class PositionService {

	// Managed Repository
	@Autowired
	private PositionRepository		positionRepository;

	// Supporting services

	@Autowired
	private AdministratorService	administratorService;


	// Simple CRUD methods

	public Position create(final Position parent) {
		Position result;
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = new Position();
		Assert.notNull(result);

		return result;
	}

	public Collection<Position> findAll() {
		Collection<Position> result;
		result = this.positionRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Position findOne(final int positionId) {
		Position result;

		result = this.positionRepository.findOne(positionId);

		Assert.notNull(result);

		return result;
	}

	public Position save(final Position position) {
		Position result;
		Administrator principal;

		Assert.notNull(position);
		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = this.positionRepository.save(position);
		Assert.notNull(result);

		return result;
	}

	public void delete(final Position position) {
		Administrator principal;

		Assert.notNull(position);
		Assert.isTrue(position.getId() != 0);

		principal = this.administratorService.findByPrincipal();

		Assert.notNull(principal);

		this.positionRepository.delete(position);
	}
}

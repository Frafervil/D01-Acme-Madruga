
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.LanguagePositionRepository;
import domain.Administrator;
import domain.LanguagePosition;

@Service
@Transactional
public class LanguagePositionService {

	// Managed Repository
	@Autowired
	private LanguagePositionRepository	languagePositionRepository;

	// Supporting services

	@Autowired
	private AdministratorService		administratorService;


	// Simple CRUD methods

	public LanguagePosition create() {
		LanguagePosition result;
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		result = new LanguagePosition();
		Assert.notNull(result);

		return result;
	}
}

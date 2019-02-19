package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BrotherhoodRepository;
import security.LoginService;
import security.UserAccount;
import domain.Brotherhood;

@Service
@Transactional
public class BrotherhoodService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private BrotherhoodRepository brotherhoodRepository;

	// Supporting services ----------------------------------------------------

	// Simple CRUD Methods

	public Brotherhood create() {
		Brotherhood result;
		Date moment;

		result = new Brotherhood();

		moment = new Date(System.currentTimeMillis() - 1);
		Assert.notNull(moment);
		result.setEstablishmentDate(moment);
		return result;
	}

	public Brotherhood save(final Brotherhood brotherhood) {
		Brotherhood saved;
		Assert.notNull(brotherhood);

		if (brotherhood.getId() == 0) {

			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			brotherhood.getUserAccount().setPassword(
					passwordEncoder.encodePassword(brotherhood.getUserAccount()
							.getPassword(), null));

		} else {
			Brotherhood principal;
			principal = this.findByPrincipal();
			Assert.notNull(principal);

		}

		saved = this.brotherhoodRepository.save(brotherhood);

		return saved;
	}

	public Brotherhood findOne(final int brotherhoodId) {
		Brotherhood result;

		result = this.brotherhoodRepository.findOne(brotherhoodId);
		Assert.notNull(result);
		return result;

	}

	public Collection<Brotherhood> findAll() {
		Collection<Brotherhood> result;

		result = this.brotherhoodRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	// Other business methods

	public Brotherhood findByPrincipal() {
		Brotherhood result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;

	}

	public Brotherhood findByUserAccountId(final int userAccountId) {
		Assert.notNull(userAccountId);
		Brotherhood result;
		result = this.brotherhoodRepository.findByUserAccountId(userAccountId);
		return result;
	}
}

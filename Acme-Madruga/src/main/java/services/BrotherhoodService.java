
package services;

import java.util.ArrayList;
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
import domain.DropOut;
import domain.Enrolment;
import domain.FloatB;
import domain.Procession;

@Service
@Transactional
public class BrotherhoodService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private BrotherhoodRepository	brotherhoodRepository;


	// Supporting services ----------------------------------------------------

	// Additional functions

	// Simple CRUD Methods

	public Brotherhood create() {
		Brotherhood result;

		result = new Brotherhood();

		result.setEnrolments(new ArrayList<Enrolment>());
		result.setDropOuts(new ArrayList<DropOut>());
		result.setProcessions(new ArrayList<Procession>());
		result.setFloatBs(new ArrayList<FloatB>());

		return result;
	}

	public Brotherhood save(final Brotherhood brotherhood) {
		Brotherhood saved;
		Assert.notNull(brotherhood);
		Date moment;

		if (brotherhood.getId() == 0) {

			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			brotherhood.getUserAccount().setPassword(passwordEncoder.encodePassword(brotherhood.getUserAccount().getPassword(), null));

			moment = new Date(System.currentTimeMillis() - 1);
			brotherhood.setEstablishmentDate(moment);
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

	public Brotherhood findByPrincipal() {
		Brotherhood result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;

	}
	public Brotherhood findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Brotherhood result;
		result = this.brotherhoodRepository.findByUserAccountId(userAccount.getId());
		return result;
	}

}

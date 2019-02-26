
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import forms.AdministratorForm;

@Service
@Transactional
public class AdministratorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AdministratorRepository	administratorRepository;

	// Supporting services-------------------------------------------
	@Autowired
	private ActorService			actorService;

	@Autowired
	private Validator				validator;


	public Administrator findByPrincipal() {
		Administrator res;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		if (userAccount == null)
			res = null;
		else
			res = this.administratorRepository.findByUserAccountId(userAccount.getId());
		return res;
	}

	public boolean exists(final Integer arg0) {
		return this.administratorRepository.exists(arg0);
	}

	public Administrator create() {
		Administrator principal;
		Administrator result;
		UserAccount userAccount;

		principal = this.findByPrincipal();
		Assert.notNull(principal);

		result = new Administrator();
		userAccount = this.actorService.createUserAccount(Authority.ADMIN);
		result.setUserAccount(userAccount);
		return result;
	}

	public Administrator save(final Administrator administrator) {
		Administrator result, saved;
		final UserAccount logedUserAccount;
		Authority authority;
		Md5PasswordEncoder encoder;

		encoder = new Md5PasswordEncoder();
		authority = new Authority();
		authority.setAuthority("ADMINISTRATOR");
		Assert.notNull(administrator, "administrator.not.null");

		if (this.exists(administrator.getId())) {
			logedUserAccount = LoginService.getPrincipal();
			Assert.notNull(logedUserAccount, "administrator.notLogged ");
			Assert.isTrue(logedUserAccount.equals(administrator.getUserAccount()), "administrator.notEqual.userAccount");
			saved = this.administratorRepository.findOne(administrator.getId());
			Assert.notNull(saved, "administrator.not.null");
			Assert.isTrue(saved.getUserAccount().getUsername().equals(administrator.getUserAccount().getUsername()), "administrator.notEqual.username");
			Assert.isTrue(administrator.getUserAccount().getPassword().equals(saved.getUserAccount().getPassword()), "administrator.notEqual.password");

		} else
			administrator.getUserAccount().setPassword(encoder.encodePassword(administrator.getUserAccount().getPassword(), null));

		result = this.administratorRepository.save(administrator);

		return result;

	}

	public Administrator findOne(final int adminId) {
		Administrator result;

		result = this.administratorRepository.findOne(adminId);
		Assert.notNull(result);
		return result;

	}

	public Administrator reconstruct(final AdministratorForm adminForm, final BindingResult binding) {
		Assert.notNull(adminForm);
		final Administrator result = this.create();
		UserAccount userAccount;

		result.setName(adminForm.getName());
		result.setMiddleName(adminForm.getMiddleName());
		result.setSurname(adminForm.getSurname());
		result.setPhoto(adminForm.getPhoto());
		result.setEmail(adminForm.getEmail());
		result.setPhone(adminForm.getPhone());
		result.setAddress(adminForm.getAddress());

		if (adminForm.getIdAdministrator() == 0) {
			userAccount = result.getUserAccount();
			userAccount.setPassword(this.actorService.hashPassword(adminForm.getPassword()));
			userAccount.setUsername(adminForm.getUsername());
		}

		//Comprobamos los errores
		this.validator.validate(adminForm, binding);
		return result;
	}
}


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
		Administrator result;
		UserAccount userAccount;
		Authority authority;

		result = new Administrator();
		userAccount = new UserAccount();
		authority = new Authority();

		authority.setAuthority("ADMIN");
		userAccount.addAuthority(authority);
		result.setUserAccount(userAccount);
		return result;

	}

	public Administrator save(final Administrator administrator) {
		Administrator result, saved;
		UserAccount logedUserAccount;
		Md5PasswordEncoder encoder;

		encoder = new Md5PasswordEncoder();
		logedUserAccount = this.actorService.createUserAccount(Authority.ADMIN);
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

	public AdministratorForm construct(final Administrator administrator) {
		final AdministratorForm administratorForm = new AdministratorForm();
		administratorForm.setAddress(administrator.getAddress());
		administratorForm.setEmail(administrator.getEmail());
		administratorForm.setIdAdministrator(administrator.getId());
		administratorForm.setMiddleName(administrator.getMiddleName());
		administratorForm.setName(administrator.getName());
		administratorForm.setPhone(administrator.getPhone());
		administratorForm.setPhoto(administrator.getPhoto());
		administratorForm.setSurname(administrator.getSurname());
		administratorForm.setUsername(administrator.getUserAccount().getUsername());
		return administratorForm;

	}

	public Administrator reconstruct(final AdministratorForm administratorForm, final BindingResult binding) {
		Administrator result;

		if (administratorForm.getIdAdministrator() == 0)
			result = this.create();
		else
			result = this.administratorRepository.findOne(administratorForm.getIdAdministrator());

		//Crear un objeto nuevo, no setear sobre el resultado

		result.setAddress(administratorForm.getAddress());
		result.setEmail(administratorForm.getEmail());
		result.setMiddleName(administratorForm.getMiddleName());
		result.setName(administratorForm.getName());
		result.setPhone(administratorForm.getPhone());
		result.setPhoto(administratorForm.getPhoto());
		result.setSurname(administratorForm.getSurname());
		result.getUserAccount().setUsername(administratorForm.getUsername());
		result.getUserAccount().setPassword(administratorForm.getPassword());
		this.validator.validate(result, binding);
		this.administratorRepository.flush();
		return result;
	}

}

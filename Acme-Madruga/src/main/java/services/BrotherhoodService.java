
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.BrotherhoodRepository;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Brotherhood;
import domain.Enrolment;
import forms.BrotherhoodForm;

@Service
@Transactional
public class BrotherhoodService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private BrotherhoodRepository	brotherhoodRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private EnrolmentService		enrolmentService;

	@Autowired
	private Validator				validator;


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
			brotherhood.getUserAccount().setPassword(passwordEncoder.encodePassword(brotherhood.getUserAccount().getPassword(), null));

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

	public BrotherhoodForm construct(final Brotherhood brotherhood) {
		final BrotherhoodForm brotherhoodForm = new BrotherhoodForm();
		brotherhoodForm.setAddress(brotherhood.getAddress());
		brotherhoodForm.setEmail(brotherhood.getEmail());
		brotherhoodForm.setId(brotherhood.getId());
		brotherhoodForm.setMiddleName(brotherhood.getMiddleName());
		brotherhoodForm.setName(brotherhood.getName());
		brotherhoodForm.setPhone(brotherhood.getPhone());
		brotherhoodForm.setPhoto(brotherhood.getPhoto());
		//		result.getPictures().removeAll(result.getPictures());
		//		result.getPictures().addAll(brotherhood.getPictures());
		brotherhoodForm.setPictures(brotherhood.getPictures());
		brotherhoodForm.setSurname(brotherhood.getSurname());
		brotherhoodForm.setTitle(brotherhood.getTitle());
		brotherhoodForm.setUsername(brotherhood.getUserAccount().getUsername());
		//En los construct no coger la contraseña
		return brotherhoodForm;

	}

	public Brotherhood findByUserAccountId(final int userAccountId) {
		Assert.notNull(userAccountId);
		Brotherhood result;
		result = this.brotherhoodRepository.findByUserAccountId(userAccountId);
		return result;
	}

	public Collection<Brotherhood> findAllBrotherhoodsOfOneMember(final int memberId) {
		Assert.notNull(memberId);
		Collection<Brotherhood> result;
		result = this.brotherhoodRepository.findAllBrotherhoodsOfOneMember(memberId);
		return result;
	}

	public Brotherhood reconstruct(final BrotherhoodForm brotherhoodForm, final BindingResult binding) {
		Brotherhood result;

		if (brotherhoodForm.getId() == 0)
			result = this.create();
		else
			result = this.brotherhoodRepository.findOne(brotherhoodForm.getId());
		result.setAddress(brotherhoodForm.getAddress());
		result.setEmail(brotherhoodForm.getEmail());
		result.setMiddleName(brotherhoodForm.getMiddleName());
		result.setName(brotherhoodForm.getName());
		result.setPhone(brotherhoodForm.getPhone());
		result.setPhoto(brotherhoodForm.getPhoto());
		result.setPictures(brotherhoodForm.getPictures());
		result.setSurname(brotherhoodForm.getSurname());
		result.setTitle(brotherhoodForm.getTitle());
		//		result.getUserAccount().setUsername(brotherhoodForm.getUsername());
		//		result.getUserAccount().setPassword(brotherhoodForm.getPassword());

		// NO SE PONEN LAS COSAS DEL USERACCOUNT PORQUE EN EL EDIT NO SE PIDEN USERNAME NI PASSWORD

		this.validator.validate(result, binding);
		this.brotherhoodRepository.flush();

		return result;

	}

	public Brotherhood largestBrotherhood() {
		Brotherhood result = null;
		Administrator principal;
		Collection<Brotherhood> brotherhoods;
		Collection<Enrolment> enrolments;
		int i = 1;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		brotherhoods = this.findAll();
		Assert.notNull(brotherhoods);
		for (final Brotherhood b : brotherhoods) {
			enrolments = this.enrolmentService.findAllActiveEnrolmentsByBrotherhoodId(b.getId());
			if (i == 1)
				result = b;
			if (this.enrolmentService.findByBrotherhoodId(result.getId()).size() < enrolments.size())
				result = b;
			i++;
		}
		return result;
	}
	public Brotherhood smallestBrotherhood() {
		Brotherhood result = null;
		Administrator principal;
		Collection<Brotherhood> brotherhoods;
		Collection<Enrolment> enrolments;
		int i = 1;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		brotherhoods = this.findAll();
		Assert.notNull(brotherhoods);
		for (final Brotherhood b : brotherhoods) {
			enrolments = this.enrolmentService.findAllActiveEnrolmentsByBrotherhoodId(b.getId());
			if (i == 1)
				result = b;
			if (this.enrolmentService.findByBrotherhoodId(result.getId()).size() > enrolments.size())
				result = b;
			i++;
		}
		return result;
	}
}

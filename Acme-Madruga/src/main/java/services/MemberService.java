
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MemberRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import domain.Administrator;
import domain.Brotherhood;
import domain.Enrolment;
import domain.Member;
import domain.Request;
import forms.MemberForm;

@Service
@Transactional
public class MemberService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private MemberRepository		memberRepository;

	@Autowired
	private UserAccountRepository	useraccountRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private EnrolmentService		enrolmentService;

	@Autowired
	private RequestService			requestService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private Validator				validator;


	// Additional functions

	// Simple CRUD Methods

	public Member create() {
		Member result;

		result = new Member();

		//Nuevo userAccount con Member en la lista de authorities
		final UserAccount userAccount = this.actorService.createUserAccount(Authority.MEMBER);

		result.setUserAccount(userAccount);

		return result;
	}

	public Member save(final Member member) {
		Member result, saved;
		UserAccount logedUserAccount;

		final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
		logedUserAccount = this.actorService.createUserAccount(Authority.MEMBER);
		Assert.notNull(member, "member.not.null");

		if (member.getId() == 0)
			member.getUserAccount().setPassword(passwordEncoder.encodePassword(member.getUserAccount().getPassword(), null));
		else {
			logedUserAccount = LoginService.getPrincipal();
			Assert.notNull(logedUserAccount, "member.notLogged");
			Assert.isTrue(logedUserAccount.equals(member.getUserAccount()), "memer.notEqual.userAccount");
			saved = this.memberRepository.findOne(member.getId());
			Assert.notNull(saved, "member.not.null");
			Assert.isTrue(saved.getUserAccount().getUsername().equals(member.getUserAccount().getUsername()));
			Assert.isTrue(saved.getUserAccount().getPassword().equals(member.getUserAccount().getPassword()));
		}

		result = this.memberRepository.save(member);

		return result;
	}

	public Member findOne(final int memberId) {
		Member result;

		result = this.memberRepository.findOne(memberId);
		Assert.notNull(result);
		return result;

	}

	public Collection<Member> findAll() {
		Collection<Member> result;

		result = this.memberRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Member findByPrincipal() {
		Member result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.memberRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;

	}

	public Member findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Member result;
		result = this.memberRepository.findByUserAccountId(userAccount.getId());
		return result;
	}

	public boolean exists(final Integer arg0) {
		return this.memberRepository.exists(arg0);
	}

	public MemberForm construct(final Member member) {
		final MemberForm memberForm = new MemberForm();
		memberForm.setAddress(member.getAddress());
		memberForm.setEmail(member.getEmail());
		memberForm.setIdMember(member.getId());
		memberForm.setMiddleName(member.getMiddleName());
		memberForm.setName(member.getName());
		memberForm.setPhone(member.getPhone());
		memberForm.setPhoto(member.getPhoto());
		memberForm.setSurname(member.getSurname());
		memberForm.setCheckBox(memberForm.getCheckBox());
		memberForm.setUsername(member.getUserAccount().getUsername());
		return memberForm;
	}

	public Member reconstruct(final MemberForm memberForm, final BindingResult binding) {
		Member result;

		result = this.create();
		result.getUserAccount().setUsername(memberForm.getUsername());
		result.getUserAccount().setPassword(memberForm.getPassword());
		result.setAddress(memberForm.getAddress());
		result.setEmail(memberForm.getEmail());
		result.setMiddleName(memberForm.getMiddleName());
		result.setName(memberForm.getName());
		result.setPhone(memberForm.getPhone());
		result.setPhoto(memberForm.getPhoto());
		result.setSurname(memberForm.getSurname());

		if (!memberForm.getPassword().equals(memberForm.getPasswordChecker()))
			binding.rejectValue("passwordChecker", "member.validation.passwordsNotMatch", "Passwords doesnt match");
		if (!this.useraccountRepository.findUserAccountsByUsername(memberForm.getUsername()).isEmpty())
			binding.rejectValue("username", "member.validation.usernameExists", "This username already exists");
		if (memberForm.getCheckBox() == false)
			binding.rejectValue("checkBox", "member.validation.checkBox", "This checkbox must be checked");

		this.validator.validate(result, binding);
		this.memberRepository.flush();

		return result;
	}

	public Member reconstructPruned(final Member member, final BindingResult binding) {
		Member result;

		if (member.getId() == 0)
			result = member;
		else
			result = this.memberRepository.findOne(member.getId());
		result.setAddress(member.getAddress());
		result.setEmail(member.getEmail());
		result.setMessageBoxes(member.getMessageBoxes());
		result.setMiddleName(member.getMiddleName());
		result.setName(member.getName());
		result.setPhone(member.getPhone());
		result.setPhoto(member.getPhoto());
		result.setSurname(member.getSurname());
		this.validator.validate(result, binding);
		this.memberRepository.flush();
		return result;
	}

	// Business Method
	public Collection<Member> findAllMembersOfOneBrotherhood(final int brotherhoodId) {
		Collection<Member> result;

		result = this.memberRepository.findAllMembersOfOneBrotherhood(brotherhoodId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Member> findAllActiveMembersOfOneBrotherhood(final int brotherhoodId) {
		Collection<Member> result;

		result = this.memberRepository.findAllActiveMembersOfOneBrotherhood(brotherhoodId);
		Assert.notNull(result);
		return result;
	}

	// Dashboard

	public Double averageMemberPerBrotherhood() {
		Administrator principal;
		Collection<Brotherhood> brotherhoods;
		Collection<Enrolment> enrolments;
		int total = 0;
		final Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		brotherhoods = this.brotherhoodService.findAll();
		Assert.notNull(brotherhoods);
		for (final Brotherhood b : brotherhoods) {
			enrolments = this.enrolmentService.findAllActiveEnrolmentsByBrotherhoodId(b.getId());
			total = total + enrolments.size();
		}
		result = (double) (total / (brotherhoods.size()));
		return result;
	}
	public Double minMemberPerBrotherhood() {
		Administrator principal;
		Collection<Brotherhood> brotherhoods;
		Collection<Enrolment> enrolments;
		int i = 1;
		Double result = 0.0;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		brotherhoods = this.brotherhoodService.findAll();
		Assert.notNull(brotherhoods);
		for (final Brotherhood b : brotherhoods) {
			enrolments = this.enrolmentService.findAllActiveEnrolmentsByBrotherhoodId(b.getId());
			if (i == 1)
				result = (double) enrolments.size();
			if (enrolments.size() < result)
				result = (double) enrolments.size();
			i++;
		}
		return result;
	}

	public Double maxMemberPerBrotherhood() {
		Administrator principal;
		Collection<Brotherhood> brotherhoods;
		Collection<Enrolment> enrolments;
		int i = 1;
		Double result = 0.0;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		brotherhoods = this.brotherhoodService.findAll();
		Assert.notNull(brotherhoods);
		for (final Brotherhood b : brotherhoods) {
			enrolments = this.enrolmentService.findAllActiveEnrolmentsByBrotherhoodId(b.getId());
			if (i == 1)
				result = (double) enrolments.size();
			if (result < enrolments.size())
				result = (double) enrolments.size();
			i++;
		}
		return result;
	}

	public Double stddevMemberPerBrotherhood() {
		Administrator principal;
		Collection<Brotherhood> brotherhoods;
		Collection<Enrolment> enrolments;
		int total = 0;
		final Double result;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		brotherhoods = this.brotherhoodService.findAll();
		Assert.notNull(brotherhoods);
		for (final Brotherhood b : brotherhoods) {
			enrolments = this.enrolmentService.findAllActiveEnrolmentsByBrotherhoodId(b.getId());
			total = total + enrolments.size();
		}
		result = (double) (total / (brotherhoods.size()));
		return result;
	}

	public Collection<Member> mostapprovedMembers() {
		final Collection<Member> members;
		final Collection<Member> result = new ArrayList<Member>();
		Collection<Request> requests;
		int i = 0;
		double p = 0.0;

		members = this.findAll();
		for (final Member m : members) {
			requests = this.requestService.findAllByMember(m.getId());
			i = 0;
			p = 0.0;
			for (final Request r : requests) {
				i++;
				if (r.getStatus().toString() == "APPROVED")
					p = p + 1;
			}
			if (p / i > 0.1)
				result.add(m);
		}

		return result;
	}

}

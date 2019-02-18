
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MemberRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Member;

@Service
@Transactional
public class MemberService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private MemberRepository	memberRepository;


	// Supporting services ----------------------------------------------------

	// Additional functions

	// Simple CRUD Methods
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
		Member res;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		if (userAccount == null)
			res = null;
		else
			res = this.memberRepository.findByUserAccountId(userAccount.getId());
		return res;
	}

	public boolean exists(final Integer arg0) {
		return this.memberRepository.exists(arg0);
	}

	public Member save(final Member member) {
		final Member result, saved;
		final UserAccount logedUserAccount;
		Authority authority;
		Md5PasswordEncoder encoder;

		encoder = new Md5PasswordEncoder();
		authority = new Authority();
		authority.setAuthority("MEMBER");
		Assert.notNull(member, "member.not.null");

		if (this.exists(member.getId())) {
			logedUserAccount = LoginService.getPrincipal();
			Assert.notNull(logedUserAccount, "member.notLogged");
			Assert.isTrue(logedUserAccount.equals(member.getUserAccount()));
			saved = this.memberRepository.findOne(member.getId());
			Assert.notNull(saved, "member.not.null");
			Assert.isTrue(saved.getUserAccount().getUsername().equals(member.getUserAccount().getUsername()), "member.notEqual.username");
			Assert.isTrue(member.getUserAccount().getPassword().equals(saved.getUserAccount().getPassword()), "member.notEqual.password");
		} else
			member.getUserAccount().setPassword(encoder.encodePassword(member.getUserAccount().getPassword(), null));
		result = this.memberRepository.save(member);

		return result;
	}

	// Business Method
	public Collection<Member> findAllMembersOfOneBrotherhood(final int brotherhoodId) {
		Collection<Member> result;

		result = this.memberRepository.findAllMembersOfOneBrotherhood(brotherhoodId);
		Assert.notNull(result);
		return result;
	}
}

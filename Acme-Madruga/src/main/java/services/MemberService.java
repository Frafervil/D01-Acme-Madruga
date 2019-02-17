
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MemberRepository;
import security.LoginService;
import security.UserAccount;
import domain.DropOut;
import domain.Enrolment;
import domain.Member;
import domain.Request;

@Service
@Transactional
public class MemberService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private MemberRepository	memberRepository;


	// Supporting services ----------------------------------------------------

	// Additional functions

	// Simple CRUD Methods

	public Member create() {
		Member result;

		result = new Member();

		result.setEnrolments(new ArrayList<Enrolment>());
		result.setDropOuts(new ArrayList<DropOut>());
		result.setRequests(new ArrayList<Request>());

		return result;
	}

	public Member save(final Member member) {
		Member saved;
		Assert.notNull(member);

		if (member.getId() == 0) {

			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			member.getUserAccount().setPassword(passwordEncoder.encodePassword(member.getUserAccount().getPassword(), null));
		} else {
			Member principal;
			principal = this.findByPrincipal();
			Assert.notNull(principal);

		}

		saved = this.memberRepository.save(member);

		return saved;
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
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;

	}
	public Member findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Member result;
		result = this.memberRepository.findByUserAccountId(userAccount.getId());
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

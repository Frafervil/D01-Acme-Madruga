
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MemberRepository;
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

	// Business Method
	public Collection<Member> findAllMembersOfOneBrotherhood(final int brotherhoodId) {
		Collection<Member> result;

		result = this.memberRepository.findAllMembersOfOneBrotherhood(brotherhoodId);
		Assert.notNull(result);
		return result;
	}
}

package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Member;
import domain.Request;

import repositories.RequestRepository;

@Service
@Transactional
public class RequestService {

	// Managed Repository

	@Autowired
	private RequestRepository requestRepository;

	// Supporting services

	@Autowired
	private MemberService memberService;

	// Simple CRUD methods

	public Collection<Request> findAll() {
		Collection<Request> result;

		result = this.requestRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public void delete(final Request request) {

		Assert.notNull(request);
		Assert.isTrue(request.getId() != 0);

		this.requestRepository.delete(request);
	}

	public Request create() {
		Request result;
		Member principal;

		principal = this.memberService.findByPrincipal();
		Assert.notNull(principal);

		result = new Request();
		Assert.notNull(result);

		return result;
	}

	public Collection<Request> findByPrincipal() {
		Member principal;

		principal = this.memberService.findByPrincipal();
		Assert.notNull(principal);

		Collection<Request> result;
		result = this.requestRepository.findByMember(principal.getId());
		Assert.notNull(result);
		return result;

	}

	// Other business methods

	public Map<String, List<Request>> groupByStatus() {
		final Map<String, List<Request>> result = new HashMap<String, List<Request>>();
		final Member principal;
		final Collection<Request> requests;

		principal = this.memberService.findByPrincipal();
		Assert.notNull(principal);

		requests = this.findByPrincipal();
		Assert.notNull(requests);
		for (final Request r : requests)
			if (result.containsKey(r.getStatus()))
				result.get(r.getStatus()).add(r);
			else {
				final List<Request> l = new ArrayList<Request>();
				l.add(r);
				result.put(r.getStatus(), l);
			}

		if (!result.containsKey("ACCEPTED"))
			result.put("APROVED", new ArrayList<Request>());
		if (!result.containsKey("PENDING"))
			result.put("PENDING", new ArrayList<Request>());
		if (!result.containsKey("REJECTED"))
			result.put("REJECTED", new ArrayList<Request>());

		return result;
	}

}

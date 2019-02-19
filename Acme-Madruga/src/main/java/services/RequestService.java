
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

import repositories.RequestRepository;
import domain.Member;
import domain.Place;
import domain.Request;

@Service
@Transactional
public class RequestService {

	// Managed Repository

	@Autowired
	RequestRepository		requestRepository;

	// Supporting services
	@Autowired
	private MemberService	memberService;

	@Autowired
	private PlaceService	placeService;


	// Simple CRUD methods

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

	public Request findOne(final int requestId) {
		Request result;

		result = this.requestRepository.findOne(requestId);
		Assert.notNull(result);
		return result;

	}

	public void delete(final Request request) {
		Member principal;
		final Place place;

		Assert.notNull(request);
		Assert.isTrue(request.getId() != 0);

		principal = this.memberService.findByPrincipal();
		Assert.notNull(principal); // Checks if the principal is a manager

		Assert.isTrue(principal.getRequests().contains(request));

		place = request.getPlace();

		this.placeService.delete(place);

		this.requestRepository.delete(request);

	}
}

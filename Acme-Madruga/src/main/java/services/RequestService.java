
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
import domain.Procession;
import domain.Request;

@Service
@Transactional
public class RequestService {

	// Managed Repository

	@Autowired
	private RequestRepository	requestRepository;

	// Supporting services

	@Autowired
	private MemberService		memberService;

	@Autowired
	private PlaceService		placeService;

	@Autowired
	private ProcessionService	processionService;


	// Simple CRUD methods

	public Collection<Request> findAll() {
		Collection<Request> result;

		result = this.requestRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Request create(final int processionId) {
		Request result;
		Member principal;
		Place place;
		Procession procession;

		result = new Request();

		principal = this.memberService.findByPrincipal();
		Assert.notNull(principal);
		result.setMember(principal);

		procession = this.processionService.findOne(processionId);
		Assert.notNull(procession);
		result.setProcession(procession);

		place = this.placeService.create(processionId);
		Assert.notNull(place);
		this.placeService.save(place);
		result.setPlace(place);

		result.setStatus("PENDING");

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
	public void delete(final Request request) {
		Member principal;
		final Place place;

		Assert.notNull(request);
		Assert.isTrue(request.getId() != 0);

		principal = this.memberService.findByPrincipal();
		Assert.notNull(principal);

		place = request.getPlace();

		this.placeService.delete(place);

		this.requestRepository.delete(request);

	}
	public Request findOne(final int requestId) {
		Request result;

		result = this.requestRepository.findOne(requestId);
		Assert.notNull(result);
		return result;

	}
	public void save(final Request request) {
		Request result;
		this.placeService.save(request.getPlace());
		result = this.requestRepository.save(request);
		Assert.notNull(result);
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

	public Collection<Request> findAllByProcession(final int processionId) {
		Collection<Request> result;

		result = this.requestRepository.findAllByProcession(processionId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Request> findAllByMember(final int memberId) {
		Collection<Request> result;

		result = this.requestRepository.findByMember(memberId);
		Assert.notNull(result);

		return result;
	}

}


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
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PlaceRepository;
import repositories.RequestRepository;
import domain.Brotherhood;
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

	@Autowired
	private PlaceRepository		placeRepository;

	@Autowired
	private Validator			validator;

	// Supporting services

	@Autowired
	private MemberService		memberService;

	@Autowired
	private PlaceService		placeService;

	@Autowired
	private ProcessionService	processionService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


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

		//Comprobamos si principal tiene alguna request pendiente o aceptada con esa procession
		//En ese caso no podria crear mas
		//Assert.isTrue(!(this.findRepeated(principal.getId(), procession.getId()) > 1), "request.commit.error.repeated");
		//

		result = new Request();
		result.setMember(principal);
		result.setProcession(procession);

		place = this.placeService.create(processionId);
		Assert.notNull(place);
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

	public Collection<Request> findByPrincipalBrotherhood(final Procession procession) {
		Brotherhood principal;

		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);

		Collection<Request> result;
		result = this.requestRepository.findAllByProcession(procession.getId());
		Assert.notNull(result);
		return result;

	}
	public void delete(final Request request) {
		final Place place;

		Assert.notNull(request);
		Assert.isTrue(request.getId() != 0);

		place = request.getPlace();

		if (place != null)
			this.placeService.delete(place);

		this.requestRepository.delete(request);

	}
	public Request findOne(final int requestId) {
		Request result;

		result = this.requestRepository.findOne(requestId);
		Assert.notNull(result);
		return result;

	}
	public Request save(final Request request) {
		Request result;
		result = this.requestRepository.save(request);
		Assert.notNull(result);
		return result;
	}

	// Other business methods

	public Map<String, List<Request>> groupByStatus(final Collection<Request> requests) {
		final Map<String, List<Request>> result = new HashMap<String, List<Request>>();
		Assert.notNull(requests);
		for (final Request r : requests)
			if (result.containsKey(r.getStatus()))
				result.get(r.getStatus()).add(r);
			else {
				final List<Request> l = new ArrayList<Request>();
				l.add(r);
				result.put(r.getStatus(), l);
			}

		if (!result.containsKey("APPROVED"))
			result.put("APPROVED", new ArrayList<Request>());
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

	public Request findByProcession(final int processionId) {
		Request result;
		result = this.requestRepository.findByProcession(processionId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Request> findAllByMember(final int memberId) {
		Collection<Request> result;

		result = this.requestRepository.findByMember(memberId);
		Assert.notNull(result);

		return result;
	}

	public Double ratioapprovedRequest() {
		final Double result;
		result = this.requestRepository.ratioapprovedRequest();
		Assert.notNull(result);
		return result;
	}
	public Double ratioRejectedRequest() {
		final Double result;
		result = this.requestRepository.ratioRejectedRequest();
		Assert.notNull(result);
		return result;
	}
	public Double ratioPendingRequest() {
		final Double result;
		result = this.requestRepository.ratioPendingRequest();
		Assert.notNull(result);
		return result;
	}

	public Collection<Request> findAllByBrotherhood(final int brotherhoodId) {
		final Collection<Request> result;
		Collection<Request> requests;
		final Collection<Procession> processions;

		result = new ArrayList<Request>();
		processions = this.processionService.findAllProcessionsOfOneBrotherhood(brotherhoodId);

		for (final Procession p : processions) {
			requests = this.findAllByProcession(p.getId());
			result.addAll(requests);
		}

		return result;

	}

	// Brotherhood must be able to change the status of a request they manage from "PENDING" to "REJECTED"
	public void reject(final Request r) {
		Brotherhood principal;

		Assert.notNull(r);
		Assert.isTrue(r.getId() != 0);

		final String reason = r.getRejectionReason();

		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(this.findByPrincipalBrotherhood(r.getProcession()).contains(r));
		Assert.isTrue(r.getStatus().equals("PENDING"));

		Assert.isTrue(!reason.isEmpty());
		r.setStatus("REJECTED");

		this.placeRepository.delete(r.getPlace().getId());
	}

	// Brotherhood must be able to change the status of a request they manage from "PENDING" to "APPROVED"
	public void approve(final Request r) {
		Brotherhood principal;

		Assert.notNull(r);
		Assert.isTrue(r.getId() != 0);

		principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(this.findByPrincipalBrotherhood(r.getProcession()).contains(r));
		Assert.isTrue(r.getStatus().equals("PENDING"));

		r.setStatus("APPROVED");

		this.requestRepository.save(r);
	}

	public void flushRequest() {
		this.requestRepository.flush();
	}

	public Request reconstruct(final Request request, final Procession procession, final BindingResult binding) {
		Request result;
		result = request;
		result.setMember(this.memberService.findByPrincipal());
		result.setStatus("PENDING");
		result.setPlace(request.getPlace());
		result.setProcession(procession);

		this.validator.validate(result, binding);
		this.requestRepository.flush();
		return result;
	}

	public Request reconstructApprove(final Request request, final BindingResult binding) {
		Request result;
		result = this.requestRepository.findOne(request.getId());
		result.getPlace().setcolumnP(request.getPlace().getcolumnP());
		result.getPlace().setrowP(request.getPlace().getrowP());
		//		if (this.placeService.findRepeated(result.getProcession().getId(), request.getPlace().getrowP(), request.getPlace().getcolumnP()) > 0)
		//			binding.addError(new ObjectError("request.commit.error.busy", "request.commit.error.busy"));
		this.validator.validate(result, binding);
		this.requestRepository.flush();
		return result;
	}

	public Request reconstructReject(final Request request, final BindingResult binding) {
		final Request result;
		result = this.requestRepository.findOne(request.getId());
		result.setRejectionReason(request.getRejectionReason());
		//		if (request.getRejectionReason().isEmpty())
		//			binding.rejectValue("rejectionReason", "request.commit.error.rejectionReason", "There must be a reason");
		//binding.addError(new ObjectError("rejectionReason", "request.commit.error.rejectionReason"));
		this.validator.validate(result, binding);
		this.requestRepository.flush();
		return result;
	}
}

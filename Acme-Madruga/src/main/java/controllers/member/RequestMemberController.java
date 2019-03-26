
package controllers.member;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.MemberService;
import services.ProcessionService;
import services.RequestService;
import controllers.AbstractController;
import domain.Member;
import domain.Procession;
import domain.Request;

@Controller
@RequestMapping("/request/member")
public class RequestMemberController extends AbstractController {

	// Services

	@Autowired
	private RequestService		requestService;

	@Autowired
	private MemberService		memberService;

	@Autowired
	private ProcessionService	processionService;


	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Request> requests;
		Collection<Procession> processions;

		requests = this.requestService.findByPrincipal();
		processions = this.processionService.findAllFinal();

		result = new ModelAndView("request/list");
		result.addObject("requests", requests);
		result.addObject("processions", processions);
		result.addObject("requestURI", "request/member/list.do");

		return result;

	}

	public ModelAndView listByStatus(@RequestParam final int requestStatus) {
		final ModelAndView result;
		Map<String, List<Request>> groupedRequest;
		Collection<Request> requests;
		Collection<Procession> processions;
		Member principal;

		principal = this.memberService.findByPrincipal();
		processions = this.processionService.findAllAvailableRequest(principal.getId());

		requests = this.requestService.findAllByMember(principal.getId());

		groupedRequest = this.requestService.groupByStatus(requests);

		if (requestStatus == 0)
			requests = this.requestService.findAllByMember(principal.getId());
		else if (requestStatus == 1)
			requests = new ArrayList<Request>(groupedRequest.get("ACCEPTED"));
		else if (requestStatus == 2)
			requests = new ArrayList<Request>(groupedRequest.get("PENDING"));
		else if (requestStatus == 3)
			requests = new ArrayList<Request>(groupedRequest.get("REJECTED"));
		else
			requests = null;

		result = new ModelAndView("request/list");
		result.addObject("requests", requests);
		result.addObject("requestURI", "request/member/list.do");

		return result;

	}

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int requestId) {
		final ModelAndView result;
		Request request;
		Member member;
		boolean permission = false;

		request = this.requestService.findOne(requestId);
		member = this.memberService.findByPrincipal();

		if (this.requestService.findAllByMember(member.getId()).contains(request))
			permission = true;

		result = new ModelAndView("request/display");
		result.addObject("request", request);
		result.addObject("member", member);
		result.addObject("permission", permission);

		return result;
	}

	// Delete
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int requestId) {
		ModelAndView result;
		Request request;
		Member member;
		request = this.requestService.findOne(requestId);
		member = this.memberService.findByPrincipal();
		if (request.getMember().getId() == member.getId())
			try {
				this.requestService.delete(request);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {

				result = this.createEditModelAndView(request, "request.commit.error");
				result.addObject("permission", true);

			}
		else
			result = this.createEditModelAndView(request, "request.commit.error");

		return result;
	}

	// Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int processionId) {
		ModelAndView result;
		final Request request;

		request = this.requestService.create(processionId);

		result = this.createEditModelAndView(request);

		return result;

	}

	// Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int processionId) {
		ModelAndView result;
		Request request;

		request = this.requestService.findByProcession(processionId);
		Assert.notNull(request);
		result = this.createEditModelAndView(request);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@RequestParam final int processionId, Request request, final BindingResult binding) {
		ModelAndView result;
		final Procession procession = this.processionService.findOne(processionId);
		try {
			request = this.requestService.reconstruct(request, procession, binding);
			if (binding.hasErrors()) {
				result = this.createEditModelAndView(request);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
			} else {
				request = this.requestService.save(request);
				result = new ModelAndView("redirect:list.do");
			}

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(request, "request.commit.error");
		}
		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Request request) {
		ModelAndView result;

		result = this.createEditModelAndView(request, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Request request, final String message) {
		ModelAndView result;

		result = new ModelAndView("request/edit");
		result.addObject("request", request);
		result.addObject("message", message);

		return result;
	}
}

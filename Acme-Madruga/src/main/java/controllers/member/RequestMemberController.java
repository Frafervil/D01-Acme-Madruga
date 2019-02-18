
package controllers.member;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RequestService;
import controllers.AbstractController;
import domain.Request;

@Controller
@RequestMapping("/request/member")
public class RequestMemberController extends AbstractController {

	// Services

	@Autowired
	private RequestService	requestService;


	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Request> requests;

		requests = this.requestService.findByPrincipal();

		result = new ModelAndView("request/list");
		result.addObject("requests", requests);
		result.addObject("requestURI", "request/member/list.do");

		return result;

	}

	public ModelAndView listByStatus(@RequestParam final int requestStatus) {
		final ModelAndView result;
		Map<String, List<Request>> groupedRequest;
		final Collection<Request> requests;

		groupedRequest = this.requestService.groupByStatus();

		if (requestStatus == 0)
			requests = this.requestService.findByPrincipal();
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

}

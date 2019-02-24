
package controllers.administrator;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.MemberService;
import services.PositionService;
import services.ProcessionService;
import services.RequestService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.Member;
import domain.Procession;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController extends AbstractController {

	// Services

	@Autowired
	private MemberService		memberService;

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private ProcessionService	processionService;

	@Autowired
	private RequestService		requestService;

	@Autowired
	private PositionService		positionService;


	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		final ModelAndView result;
		final Double avgMemberPerBrotherhood, minMemberPerBrotherhood, maxMemberPerBrotherhood, stddevMemberPerBrotherhood;

		final Brotherhood largestBrotherhood;
		final Brotherhood smallestBroterhood;

		final Collection<Procession> startingSoonProcession;

		final Double ratioPendingRequest, ratioAprovedRequest, ratioRejectedRequest;

		final Collection<Member> membersRequestAproved;

		final Map<String, Integer> positionStats;

		// Stadistics

		// avg
		avgMemberPerBrotherhood = this.memberService.averageMemberPerBrotherhood();

		// min
		minMemberPerBrotherhood = this.memberService.minMemberPerBrotherhood();

		// max
		maxMemberPerBrotherhood = this.memberService.maxMemberPerBrotherhood();

		// standard Deviation
		stddevMemberPerBrotherhood = this.memberService.stddevMemberPerBrotherhood();

		// largest Brotherhood
		largestBrotherhood = this.brotherhoodService.largestBrotherhood();

		// smallest Brotherhood
		smallestBroterhood = this.brotherhoodService.smallestBrotherhood();

		// Starting Soon
		startingSoonProcession = this.processionService.startingSoonProcessions();

		// Ratios
		ratioPendingRequest = this.requestService.ratioPendingRequest();

		ratioAprovedRequest = this.requestService.ratioAprovedRequest();

		ratioRejectedRequest = this.requestService.ratioRejectedRequest();

		// Listing of members 10% aproved

		membersRequestAproved = this.memberService.mostAprovedMembers();

		//Histogram

		positionStats = this.positionService.positionStats();

		//
		result = new ModelAndView("administrator/dashboard");
		result.addObject("avgMemberPerBrotherhood", avgMemberPerBrotherhood);
		result.addObject("minMemberPerBrotherhood", minMemberPerBrotherhood);
		result.addObject("maxMemberPerBrotherhood", maxMemberPerBrotherhood);
		result.addObject("stddevMemberPerBrotherhood", stddevMemberPerBrotherhood);

		result.addObject("largestBrotherhood", largestBrotherhood);
		result.addObject("smallestBroterhood", smallestBroterhood);

		result.addObject("startingSoonProcession", startingSoonProcession);

		result.addObject("ratioPendingRequest", ratioPendingRequest);
		result.addObject("ratioAprovedRequest", ratioAprovedRequest);
		result.addObject("ratioRejectedRequest", ratioRejectedRequest);

		result.addObject("membersRequestAproved", membersRequestAproved);

		result.addObject("positionStats", positionStats);

		return result;

	}
}

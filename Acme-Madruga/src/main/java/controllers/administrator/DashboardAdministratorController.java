
package controllers.administrator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.FinderService;
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

	@Autowired
	private FinderService		finderService;


	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		final ModelAndView result;
		final Double avgMemberPerBrotherhood, minMemberPerBrotherhood, maxMemberPerBrotherhood, stddevMemberPerBrotherhood;

		final Brotherhood largestBrotherhood;
		final Brotherhood smallestBroterhood;

		final Collection<Procession> startingSoonProcession;

		final Double ratioPendingRequest, ratioapprovedRequest, ratioRejectedRequest;

		final Double ratioEmptyFinders, ratioCompletedFinders;

		final HashMap<String, Integer> countBrotherhoodsPerSettle;
		final HashMap<String, Double> ratioBrotherhoodsPerSettle;
		final Integer minBrotherhoodsPerSettle, maxBrotherhoodsPerSettle;
		final Double avgBrotherhoodsPerSettle, stddevBrotherhoodsPerSettle;

		final Collection<Member> membersRequestapproved;

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

		ratioapprovedRequest = this.requestService.ratioapprovedRequest();

		ratioRejectedRequest = this.requestService.ratioRejectedRequest();

		// Listing of members 10% approved

		membersRequestapproved = this.memberService.mostapprovedMembers();

		//Histogram

		positionStats = this.positionService.positionStats();
		final ArrayList<String> position = new ArrayList<>();
		final ArrayList<Integer> count = new ArrayList<>();

		for (final Entry<String, Integer> entry : positionStats.entrySet()) {
			position.add(entry.getKey());
			count.add(entry.getValue());
		}
		// Ratios finder

		ratioEmptyFinders = (double) ((this.finderService.countEmptyFinders()) / this.finderService.findAll().size());

		ratioCompletedFinders = 1.0 - ratioEmptyFinders;
		// Seetle stats
		countBrotherhoodsPerSettle = this.brotherhoodService.countBrotherhoodsPerSettle();
		ratioBrotherhoodsPerSettle = this.brotherhoodService.ratioBrotherhoodsPerSettle();
		minBrotherhoodsPerSettle = this.brotherhoodService.minBrotherhoodsPerSettle();
		maxBrotherhoodsPerSettle = this.brotherhoodService.maxBrotherhoodsPerSettle();
		avgBrotherhoodsPerSettle = this.brotherhoodService.avgBrotherhoodsPerSettle();
		stddevBrotherhoodsPerSettle = this.brotherhoodService.stddevBrotherhoodsPerSettle();
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
		result.addObject("ratioapprovedRequest", ratioapprovedRequest);
		result.addObject("ratioRejectedRequest", ratioRejectedRequest);

		result.addObject("membersRequestapproved", membersRequestapproved);

		result.addObject("position", position);
		result.addObject("count", count);

		result.addObject("ratioEmptyFinders", ratioEmptyFinders);
		result.addObject("ratioCompletedFinders", ratioCompletedFinders);

		result.addObject("countBrotherhoodsPerSettle", countBrotherhoodsPerSettle);
		result.addObject("ratioBrotherhoodsPerSettle", ratioBrotherhoodsPerSettle);
		result.addObject("minBrotherhoodsPerSettle", minBrotherhoodsPerSettle);
		result.addObject("maxBrotherhoodsPerSettle", maxBrotherhoodsPerSettle);
		result.addObject("avgBrotherhoodsPerSettle", avgBrotherhoodsPerSettle);
		result.addObject("stddevBrotherhoodsPerSettle", stddevBrotherhoodsPerSettle);

		return result;

	}
}


package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.BrotherhoodService;
import services.MemberService;
import controllers.AbstractController;
import domain.Brotherhood;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController extends AbstractController {

	//Services

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private MemberService			memberService;

	@Autowired
	private BrotherhoodService		brotherhoodService;


	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		final ModelAndView result;
		final Double avgMemberPerBrotherhood, minMemberPerBrotherhood, maxMemberPerBrotherhood, stddevMemberPerBrotherhood;

		final Brotherhood largestBrotherhood;
		final Brotherhood smallestBroterhood;

		//Stadistics

		//avg
		avgMemberPerBrotherhood = this.memberService.averageMemberPerBrotherhood();

		//min
		minMemberPerBrotherhood = this.memberService.minMemberPerBrotherhood();

		//max
		maxMemberPerBrotherhood = this.memberService.maxMemberPerBrotherhood();

		//standard Deviation
		stddevMemberPerBrotherhood = this.memberService.stddevMemberPerBrotherhood();

		//largest Brotherhood
		largestBrotherhood = this.brotherhoodService.largestBrotherhood();

		//smallest Brotherhood
		smallestBroterhood = this.brotherhoodService.smallestBrotherhood();

		//Ratios

		//result

		result = new ModelAndView("administrator/dashboard");

		return result;

	}
}

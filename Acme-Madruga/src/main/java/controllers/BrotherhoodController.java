
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.FloatBService;
import services.MemberService;
import services.ProcessionService;
import domain.Brotherhood;
import domain.FloatB;
import domain.Member;
import domain.Procession;

@Controller
@RequestMapping("/brotherhood")
public class BrotherhoodController extends AbstractController {

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private MemberService		memberService;

	@Autowired
	private ProcessionService	processionService;

	@Autowired
	private FloatBService		floatBService;


	// List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Brotherhood> brotherhoods;

		brotherhoods = this.brotherhoodService.findAll();

		result = new ModelAndView("brotherhood/list");
		result.addObject("brotherhoods", brotherhoods);
		result.addObject("requestURI", "brotherhood/list.do");

		return result;
	}

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int brotherhoodId) {
		final ModelAndView result;
		Brotherhood brotherhood;
		Collection<Member> members;
		final Collection<Procession> processions;
		final Collection<FloatB> floats;

		brotherhood = this.brotherhoodService.findOne(brotherhoodId);
		members = this.memberService.findAllMembersOfOneBrotherhood(brotherhoodId);
		processions = this.processionService.findAllProcessionsOfOneBrotherhood(brotherhoodId);
		floats = this.floatBService.findAllFloatsOfOneBrotherhood(brotherhoodId);

		result = new ModelAndView("brotherhood/display");
		result.addObject("brotherhood", brotherhood);
		result.addObject("members", members);
		result.addObject("processions", processions);
		result.addObject("floats", floats);

		return result;

	}
}

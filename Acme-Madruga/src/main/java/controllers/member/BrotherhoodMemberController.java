
package controllers.member;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.EnrolmentService;
import services.MemberService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.Member;

@Controller
@RequestMapping("/brotherhood/member")
public class BrotherhoodMemberController extends AbstractController {

	// Servicios

	@Autowired
	private EnrolmentService	enrolmentService;

	@Autowired
	private MemberService		memberService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	// Drop Out

	@RequestMapping(value = "/dropOut", method = RequestMethod.GET)
	public ModelAndView enrol(@RequestParam final int brotherhoodId) {
		final ModelAndView result;

		this.enrolmentService.dropOut(brotherhoodId);

		result = new ModelAndView("redirect:/brotherhood/member/list.do");

		return result;
	}

	// List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Brotherhood> brotherhoods;

		try {
			final Member principal = this.memberService.findByPrincipal();
			Assert.notNull(principal);

			brotherhoods = this.brotherhoodService.findAllBrotherhoodsOfOneMember(principal.getId());

			result = new ModelAndView("brotherhood/member/list");
			result.addObject("brotherhoods", brotherhoods);
			result.addObject("requestURI", "brotherhood/member/list.do");

		} catch (final Throwable oops) {
			oops.printStackTrace();
			result = new ModelAndView("brotherhood/member/list");
			result.addObject("message", "brotherhood.retrieve.error");
			result.addObject("brotherhoods", new ArrayList<Brotherhood>());
		}

		return result;
	}

}

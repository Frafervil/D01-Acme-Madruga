
package controllers.member;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.EnrolmentService;
import services.MemberService;
import services.PositionService;
import controllers.AbstractController;
import domain.Enrolment;
import domain.Member;
import domain.Position;

@Controller
@RequestMapping("/brotherhood/member")
public class BrotherhoodMemberController extends AbstractController {

	// Servicios

	@Autowired
	private EnrolmentService	enrolmentService;

	@Autowired
	private MemberService		memberService;

	@Autowired
	private PositionService		positionService;


	// Drop Out

	@RequestMapping(value = "/dropOut", method = RequestMethod.GET)
	public ModelAndView dropOut(@RequestParam final int brotherhoodId) {
		ModelAndView result;
		Enrolment enrolment;
		Member principal;

		try {

			this.enrolmentService.dropOut(brotherhoodId);
			result = new ModelAndView("redirect:/enrolment/member/list.do");

		} catch (final Exception oops) {
			principal = this.memberService.findByPrincipal();
			enrolment = this.enrolmentService.findActiveEnrolmentByBrotherhoodIdAndMemberId(brotherhoodId, principal.getId());
			oops.printStackTrace();
			result = this.createEditModelAndView(enrolment, "enrolment.commit.error");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Enrolment enrolment) {
		return this.createEditModelAndView(enrolment, null);
	}

	protected ModelAndView createEditModelAndView(final Enrolment enrolment, final String messageCode) {
		ModelAndView result;
		final Collection<Position> positions;

		positions = this.positionService.findAll();

		result = new ModelAndView("enrolment/edit");
		result.addObject("enrolment", enrolment);
		result.addObject("positions", positions);
		result.addObject("message", messageCode);

		return result;
	}
}

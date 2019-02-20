
package controllers.brotherhood;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.EnrolmentService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.Enrolment;
import domain.FloatB;
import domain.Member;

@Controller
@RequestMapping("/member/brotherhood")
public class MemberBrotherhoodController extends AbstractController {

	// Servicios

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private EnrolmentService	enrolmentService;


	// List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Member> members;
		Collection<Enrolment> enrolments;

		try {
			final Brotherhood hood = this.brotherhoodService.findByPrincipal();
			Assert.notNull(hood);

			members = new ArrayList<Member>();
			enrolments = this.enrolmentService.findByBrotherhoodId(hood.getId());

			for (final Enrolment e : enrolments)
				if (e.getDropOutMoment() == null)
					members.add(e.getMember());

			result = new ModelAndView("member/list");
			result.addObject("members", members);
			result.addObject("requestURI", "member/brotherhood/list.do");

		} catch (final Throwable oops) {
			oops.printStackTrace();
			result = new ModelAndView("member/list");
			result.addObject("message", "member.retrieve.error");
			result.addObject("members", new ArrayList<FloatB>());
		}

		return result;
	}

}

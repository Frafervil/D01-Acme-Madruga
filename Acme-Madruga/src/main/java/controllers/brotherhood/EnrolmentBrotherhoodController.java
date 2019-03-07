
package controllers.brotherhood;

import java.util.Arrays;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.EnrolmentService;
import services.MemberService;
import services.PositionService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.Enrolment;
import domain.Member;
import domain.Position;

@Controller
@RequestMapping("/enrolment/brotherhood")
public class EnrolmentBrotherhoodController extends AbstractController {

	// Servicios

	@Autowired
	private EnrolmentService	enrolmentService;

	@Autowired
	private PositionService		positionService;

	@Autowired
	private MemberService		memberService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	// Create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int memberId) {
		ModelAndView result;
		Enrolment enrolment;
		Member member;

		member = this.memberService.findOne(memberId);

		enrolment = this.enrolmentService.create(member);

		result = this.createEditModelAndView(enrolment);

		return result;
	}

	// Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int enrolmentId) {
		ModelAndView result;

		try {
			final Enrolment e = this.enrolmentService.findOne(enrolmentId);

			result = this.createEditModelAndView(e, null);

		} catch (final Exception oops) {
			oops.printStackTrace();
			result = new ModelAndView("welcome/index");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("enrolment") @Valid Enrolment enrolment, final BindingResult binding) {
		ModelAndView result;

		try {
			enrolment = this.enrolmentService.reconstruct(enrolment, binding);

			if (binding.hasErrors()) {
				result = this.createEditModelAndView(enrolment);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
			} else {
				final Brotherhood principal = this.brotherhoodService.findByPrincipal();
				enrolment = this.enrolmentService.save(enrolment);
				result = new ModelAndView("redirect:/member/brotherhood/list.do?brotherhoodId=" + principal.getId());
			}
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(enrolment, "enrolment.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Enrolment enrolment, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = this.createEditModelAndView(enrolment);
		} else
			try {
				Assert.isTrue(enrolment.getId() != 0);
				this.enrolmentService.delete(enrolment);
				final Brotherhood principal = this.brotherhoodService.findByPrincipal();
				result = new ModelAndView("redirect:/member/brotherhood/list.do?brotherhoodId=" + principal.getId());
			} catch (final Exception oops) {
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

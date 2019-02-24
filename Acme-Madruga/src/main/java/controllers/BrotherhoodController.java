
package controllers;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.CustomisationService;
import services.EnrolmentService;
import services.FloatBService;
import services.MemberService;
import services.ProcessionService;
import domain.Brotherhood;
import domain.Enrolment;
import domain.FloatB;
import domain.Member;
import domain.Procession;

@Controller
@RequestMapping("/brotherhood")
public class BrotherhoodController extends AbstractController {

	// Services

	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private MemberService			memberService;

	@Autowired
	private ProcessionService		processionService;

	@Autowired
	private FloatBService			floatBService;

	@Autowired
	private CustomisationService	customisationService;

	@Autowired
	private EnrolmentService		enrolmentService;


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
		Enrolment enrolment;
		Member principal;

		brotherhood = this.brotherhoodService.findOne(brotherhoodId);
		members = this.memberService.findAllMembersOfOneBrotherhood(brotherhoodId);
		processions = this.processionService.findAllProcessionsOfOneBrotherhood(brotherhoodId);
		floats = this.floatBService.findByBrotherhoodId(brotherhoodId);
		principal = this.memberService.findByPrincipal();
		enrolment = this.enrolmentService.findActiveEnrolmentByBrotherhoodIdAndMemberId(brotherhoodId, principal.getId());

		result = new ModelAndView("brotherhood/display");
		result.addObject("brotherhood", brotherhood);
		result.addObject("members", members);
		result.addObject("processions", processions);
		result.addObject("floats", floats);
		result.addObject("enrolment", enrolment);

		return result;

	}

	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Brotherhood brotherhood;

		brotherhood = this.brotherhoodService.create();
		result = this.createEditModelAndView(brotherhood);

		return result;
	}
	//Edit
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(@Valid final Brotherhood brotherhood, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(brotherhood);
			System.out.println(binding.getAllErrors());
		} else
			try {
				this.brotherhoodService.save(brotherhood);
				result = new ModelAndView("welcome/index");

				SimpleDateFormat formatter;
				String moment;
				final String welcomeMessage;

				formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				moment = formatter.format(new Date());

				welcomeMessage = "welcome.greeting.signUp.brotherhood";

				result.addObject("welcomeMessage", welcomeMessage);
				result.addObject("moment", moment);
				result.addObject("signUp", true);

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(brotherhood, "brotherhood.commit.error");

			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Brotherhood brotherhood;

		brotherhood = this.brotherhoodService.findByPrincipal();

		result = this.createEditModelAndView(brotherhood);

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Brotherhood brotherhood) {
		ModelAndView result;

		result = this.createEditModelAndView(brotherhood, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Brotherhood brotherhood, final String message) {
		ModelAndView result;
		String countryCode;

		countryCode = this.customisationService.find().getCountryCode();

		result = new ModelAndView("brotherhood/edit");
		result.addObject("brotherhood", brotherhood);
		result.addObject("actionURI", "brotherhood/edit.do");
		result.addObject("redirectURI", "welcome/index.do");
		result.addObject("countryCode", countryCode);
		result.addObject("permission", true);

		result.addObject("message", message);

		return result;
	}
}

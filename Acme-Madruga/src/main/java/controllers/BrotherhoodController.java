
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
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
import forms.BrotherhoodForm;

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
	public ModelAndView show() {
		final ModelAndView result;
		Brotherhood brotherhood;
		Collection<Member> members;
		final Collection<Procession> processions;
		final Collection<FloatB> floats;
		Enrolment enrolment;
		Member principal;

		int brotherhoodId;
		brotherhoodId = LoginService.getPrincipal().getId();
		brotherhood = this.brotherhoodService.findByPrincipal();
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
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("brotherhoodForm") final BrotherhoodForm brotherhoodForm, final BindingResult binding) {
		ModelAndView result;
		Brotherhood brotherhood;
		try {
			brotherhood = this.brotherhoodService.reconstruct(brotherhoodForm, binding);
			if (binding.hasErrors()) {
				result = this.createEditModelAndView(brotherhood);
				System.out.println(binding.getAllErrors());
			} else
				brotherhood = this.brotherhoodService.save(brotherhood);
			// AQUI NO SE PONE EL RECONTRUCT DEL USERACCOUNT PORQUE ESTE SAVE NO GUARDA EL USUARIO Y LA CONTRASEÑA
			// SI HUBIERA QUE GUARDAR ALGO DE OTRA ENTIDAD, HABRIA QUE PONER EL RECONSTRUCT DE ESA ENTIDAD
			result = new ModelAndView("welcome/index");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(brotherhoodForm, "brotherhood.commit.error");

		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Brotherhood brotherhood;
		brotherhood = this.brotherhoodService.findByPrincipal();
		Assert.notNull(brotherhood);
		result = this.createEditModelAndView(brotherhood);

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Brotherhood brotherhood) {
		ModelAndView result;
		BrotherhoodForm brotherhoodForm;
		brotherhoodForm = this.brotherhoodService.construct(brotherhood);
		result = this.createEditModelAndView(brotherhoodForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final BrotherhoodForm brotherhoodForm, final String message) {
		ModelAndView result;
		String countryCode;

		countryCode = this.customisationService.find().getCountryCode();
		result = new ModelAndView("brotherhood/edit");
		result.addObject("brotherhoodForm", brotherhoodForm);
		result.addObject("actionURI", "brotherhood/edit.do");
		result.addObject("redirectURI", "welcome/index.do");
		result.addObject("countryCode", countryCode);
		result.addObject("permission", true);

		result.addObject("message", message);

		return result;
	}
}

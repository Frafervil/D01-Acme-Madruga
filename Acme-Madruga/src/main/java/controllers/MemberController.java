
package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CustomisationService;
import services.MemberService;
import domain.Member;

@Controller
@RequestMapping("/member")
public class MemberController extends AbstractController {

	// Services
	@Autowired
	private MemberService			memberService;

	@Autowired
	private CustomisationService	customisationService;


	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Member member;

		member = this.memberService.create();
		result = this.createEditModelAndView(member);

		return result;
	}
	//Edit
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(@Valid final Member member, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(member);
			System.out.println(binding.getAllErrors());
		} else
			try {
				this.memberService.save(member);
				result = new ModelAndView("welcome/index");

				SimpleDateFormat formatter;
				String moment;
				final String welcomeMessage;

				formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				moment = formatter.format(new Date());

				welcomeMessage = "welcome.greeting.signUp.member";

				result.addObject("welcomeMessage", welcomeMessage);
				result.addObject("moment", moment);
				result.addObject("signUp", true);

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(member, "member.commit.error");

			}

		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Member member) {
		ModelAndView result;

		result = this.createEditModelAndView(member, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Member member, final String message) {
		ModelAndView result;
		String countryCode;

		countryCode = this.customisationService.find().getCountryCode();

		result = new ModelAndView("member/edit");
		result.addObject("member", member);
		result.addObject("actionURI", "member/edit.do");
		result.addObject("redirectURI", "welcome/index.do");
		result.addObject("countryCode", countryCode);
		result.addObject("permission", true);

		result.addObject("message", message);

		return result;
	}

}

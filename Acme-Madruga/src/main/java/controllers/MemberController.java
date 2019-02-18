
package controllers;

import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.MemberService;
import domain.Member;

@Controller
@RequestMapping("/member")
public class MemberController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public MemberController() {
		super();
	}


	@Autowired
	private MemberService	memberservice;


	@RequestMapping("/display")
	public ModelAndView view() {
		ModelAndView result;

		result = new ModelAndView("member/display");
		result.addObject("actor", this.memberservice.findByPrincipal());

		return result;
	}

	protected ModelAndView createEditModelAndView(final Member member) {
		ModelAndView result;

		result = this.createEditModelAndView(member, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Member member, final String messageCode) {
		ModelAndView result;

		if (member.getId() > 0)
			result = new ModelAndView("member/edit");
		else
			result = new ModelAndView("member/registerMember");

		result.addObject("actor", member);
		result.addObject("message", messageCode);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Member member;

		member = this.memberservice.findByPrincipal();

		result = this.createEditModelAndView(member);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Member member, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(member);
			for (final ObjectError e : binding.getAllErrors())
				System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
		} else
			try {
				this.memberservice.save(member);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(member, "member.commit.error");
			}
		return result;
	}

}

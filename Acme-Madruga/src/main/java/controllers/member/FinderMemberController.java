
package controllers.member;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.FinderService;
import services.MemberService;
import controllers.AbstractController;
import domain.Finder;
import domain.Member;

@Controller
@RequestMapping("/finder/member")
public class FinderMemberController extends AbstractController {

	// Services

	@Autowired
	private FinderService	finderService;

	@Autowired
	private MemberService	memberService;


	//Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Finder finder;

		finder = this.finderService.findByPrincipal();
		Assert.notNull(finder);
		result = this.createEditModelAndView(finder);

		return result;
	}

	// Search

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "search")
	public ModelAndView search(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = this.createEditModelAndView(finder);
		} else {
			this.finderService.save(finder);
			result = this.createEditModelAndView(finder, null);
			//			try {
			//				this.finderService.save(finder);
			//				this.finderService.deleteOldFinder();
			//				result = new ModelAndView("redirect:/finder/member/edit.do");
			//			} catch (final Throwable oops) {
			//				result = this.createEditModelAndView(finder, "finder.commit.error");
			//			}
		}
		return result;
	}

	// Clear

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "clear")
	public ModelAndView clear(@Valid Finder finder, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = this.createEditModelAndView(finder);
		} else
			try {
				finder = this.finderService.clear(finder);
				result = this.createEditModelAndView(finder, null);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(finder, "finder.commit.error");
			}

		return result;
	}
	//Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Finder finder) {
		ModelAndView result;

		result = this.createEditModelAndView(finder, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Finder finder, final String messageCode) {
		final ModelAndView result;
		boolean permission = false;
		Member principal;
		principal = this.memberService.findByPrincipal();

		if (finder.getMember() == principal)
			permission = true;

		result = new ModelAndView("finder/edit");
		result.addObject("finder", finder);
		result.addObject("permission", permission);
		result.addObject("processions", finder.getResults());
		result.addObject("message", messageCode);

		return result;

	}

}

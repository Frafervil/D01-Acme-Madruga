
package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SettleService;
import controllers.AbstractController;
import domain.Settle;

@Controller
@RequestMapping("/settle/administrator")
public class SettleAdministratorController extends AbstractController {

	// Services

	@Autowired
	private SettleService	settleService;


	// Listing
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Settle> settles;
		settles = this.settleService.findAll();

		result = new ModelAndView("settle/list");
		result.addObject("settles", settles);

		return result;

	}

	// Creation 

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Settle settle;

		settle = this.settleService.create();
		result = this.createEditModelAndView(settle);

		return result;
	}

	// Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int settleId) {
		ModelAndView result;
		final Settle settle;

		settle = this.settleService.findOne(settleId);
		Assert.notNull(settle);
		result = this.createEditModelAndView(settle);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Settle settle, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(settle);
		else
			try {
				this.settleService.save(settle);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(settle, "settle.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Settle settle, final BindingResult binding) {
		ModelAndView result;

		try {
			this.settleService.delete(settle);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(settle, "settle.commit.error");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Settle settle) {
		ModelAndView result;

		result = this.createEditModelAndView(settle, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Settle settle, final String message) {
		ModelAndView result;

		result = new ModelAndView("settle/edit");
		result.addObject("settle", settle);
		result.addObject("message", message);

		return result;
	}
}

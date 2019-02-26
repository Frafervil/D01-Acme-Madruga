
package controllers.brotherhood;

import java.util.ArrayList;
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

import services.BrotherhoodService;
import services.FloatBService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.FloatB;

@Controller
@RequestMapping("/floatB/brotherhood")
public class FloatBBrotherhoodController extends AbstractController {

	// Servicios

	@Autowired
	private FloatBService		floatBService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	// List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int brotherhoodId) {
		ModelAndView result;
		Collection<FloatB> floats;

		try {
			final Brotherhood hood = this.brotherhoodService.findOne(brotherhoodId);
			Assert.notNull(hood);

			floats = this.floatBService.findByBrotherhoodId(brotherhoodId);

			result = new ModelAndView("floatB/list");
			result.addObject("floatBs", floats);
			result.addObject("requestURI", "floatB/brotherhood/list.do");

		} catch (final Throwable oops) {
			oops.printStackTrace();
			result = new ModelAndView("floatB/list");
			result.addObject("message", "floatB.retrieve.error");
			result.addObject("floatBs", new ArrayList<FloatB>());
		}

		return result;
	}

	// Create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		FloatB floatB;

		floatB = this.floatBService.create();

		result = this.createEditModelAndView(floatB);

		return result;
	}

	// Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int floatBId) {
		ModelAndView result;

		try {
			final FloatB f = this.floatBService.findOne(floatBId);

			result = this.createEditModelAndView(f, null);

		} catch (final Exception oops) {
			oops.printStackTrace();
			result = new ModelAndView("welcome/index");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid FloatB floatB, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = this.createEditModelAndView(floatB);
		} else
			try {
				floatB = this.floatBService.save(floatB);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = this.createEditModelAndView(floatB, "floatB.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final FloatB floatB) {
		ModelAndView result;
		try {
			Assert.isTrue(floatB.getId() != 0);
			this.floatBService.delete(floatB);
			result = new ModelAndView("redirect:../brotherhood/list.do");
		} catch (final Exception oops) {
			oops.printStackTrace();
			result = this.createEditModelAndView(floatB, "floatB.commit.error");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final FloatB floatB) {
		return this.createEditModelAndView(floatB, null);
	}

	protected ModelAndView createEditModelAndView(final FloatB floatB, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("floatB/edit");
		result.addObject("floatB", floatB);
		result.addObject("message", messageCode);

		return result;
	}

}

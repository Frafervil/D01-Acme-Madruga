package controllers.brotherhood;

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
import services.ProcessionService;

import controllers.AbstractController;
import domain.Brotherhood;
import domain.Procession;

@Controller
@RequestMapping("/procession/brotherhood")
public class ProcessionBrotherhoodController extends AbstractController {

	@Autowired
	private ProcessionService processionService;

	@Autowired
	private BrotherhoodService brotherhoodService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Procession> processions;

		processions = processionService.findAll();

		result = new ModelAndView("procession/list");
		result.addObject("processions", processions);
		result.addObject("requestURI", "procession/brotherhood/list.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Procession procession;

		procession = this.processionService.create();
		result = this.createEditModelAndView(procession);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int processionId) {
		ModelAndView result;
		Procession procession;

		procession = processionService.findOne(processionId);
		Assert.notNull(procession);
		result = createEditModelAndView(procession);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Procession procession, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(procession);
		} else {
			try {
				processionService.save(procession);
				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(procession,
						"procession.commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Procession procession, BindingResult binding) {
		ModelAndView result;

		try {
			processionService.delete(procession);
			result = new ModelAndView("redirect:list.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(procession,
					"procession.commit.error");
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(Procession procession) {
		ModelAndView result;

		result = createEditModelAndView(procession, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Procession procession,
			String messageCode) {
		ModelAndView result;
		Brotherhood brotherhood;
		Collection<Brotherhood> brotherhoods;

		brotherhoods = brotherhoodService.findAll();
		if (procession.getBrotherhood() == null) {
			brotherhood = null;
		} else {
			brotherhood = procession.getBrotherhood();
		}

		result = new ModelAndView("procession/edit");
		result.addObject("procession", procession);
		result.addObject("brotherhoods", brotherhoods);

		result.addObject("message", messageCode);

		return result;
	}

}

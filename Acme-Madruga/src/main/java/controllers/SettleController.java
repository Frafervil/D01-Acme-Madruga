
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.SettleService;
import domain.Brotherhood;
import domain.Settle;

@Controller
@RequestMapping("/settle")
public class SettleController extends AbstractController {

	// Servicios

	@Autowired
	private SettleService		settleService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int settleId) {
		ModelAndView result;
		Settle settle;
		Brotherhood brotherhood;

		brotherhood = this.brotherhoodService.findBySettleId(settleId);

		// Busca en el repositorio
		settle = this.settleService.findOne(settleId);
		Assert.notNull(settle);

		// Crea y añade objetos a la vista
		result = new ModelAndView("settle/display");
		result.addObject("requestURI", "settle/display.do");
		result.addObject("settle", settle);
		result.addObject("brotherhood", brotherhood);

		// Envía la vista
		return result;
	}
}

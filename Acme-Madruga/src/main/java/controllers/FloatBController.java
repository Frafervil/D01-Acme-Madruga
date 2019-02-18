
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.FloatBService;
import domain.FloatB;

@Controller
@RequestMapping("/floatB")
public class FloatBController extends AbstractController {

	// Servicios

	@Autowired
	private FloatBService	floatBService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int floatBId) {
		// Inicializa resultado
		ModelAndView result;
		FloatB floatB;

		// Busca en el repositorio
		floatB = this.floatBService.findOne(floatBId);
		Assert.notNull(floatB);

		// Crea y añade objetos a la vista
		result = new ModelAndView("floatB/display");
		result.addObject("requestURI", "floatB/display.do");
		result.addObject("floatB", floatB);

		// Envía la vista
		return result;
	}

}

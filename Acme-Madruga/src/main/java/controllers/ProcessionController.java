package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ProcessionService;
import domain.Procession;

@Controller
@RequestMapping("/procession")
public class ProcessionController extends AbstractController {

	// Servicios

	@Autowired
	private ProcessionService processionService;

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int processionId) {
		// Inicializa resultado
		ModelAndView result;
		Procession procession;

		// Busca en el repositorio
		procession = this.processionService.findOne(processionId);
		Assert.notNull(procession);

		// Crea y añade objetos a la vista
		result = new ModelAndView("procession/display");
		result.addObject("requestURI", "procession/display.do");
		result.addObject("procession", procession);

		// Envía la vista
		return result;
	}

}

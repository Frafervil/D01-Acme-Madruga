package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import repositories.FloatBRepository;
import services.ProcessionService;
import domain.FloatB;
import domain.Procession;

@Controller
@RequestMapping("/procession")
public class ProcessionController extends AbstractController {

	// Servicios

	@Autowired
	private ProcessionService processionService;

	// Repositorios

	@Autowired
	private FloatBRepository floatBRepository;

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int processionId) {
		// Inicializa resultado
		ModelAndView result;
		Procession procession;
		Collection<FloatB> floatbs;

		// Busca en el repositorio
		procession = this.processionService.findOne(processionId);
		Assert.notNull(procession);

		floatbs = this.floatBRepository.findByProcessionId(processionId);

		// Crea y añade objetos a la vista
		result = new ModelAndView("procession/display");
		result.addObject("requestURI", "procession/display.do");
		result.addObject("procession", procession);
		result.addObject("floatbs", floatbs);

		// Envía la vista
		return result;
	}

}

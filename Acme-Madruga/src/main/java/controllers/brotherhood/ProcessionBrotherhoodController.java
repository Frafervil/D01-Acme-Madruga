
package controllers.brotherhood;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ProcessionService;
import controllers.AbstractController;
import domain.FloatB;
import domain.Procession;

@Controller
@RequestMapping("/procession/brotherhood")
public class ProcessionBrotherhoodController extends AbstractController {

	// Servicios
	@Autowired
	private ProcessionService	processionService;


	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int brotherhoodId) {
		ModelAndView result;
		Collection<Procession> processions;
		try {
			processions = new ArrayList<Procession>();
			processions = this.processionService.findAllProcessionsOfOneBrotherhood(brotherhoodId);
			result = new ModelAndView("procession/list");
			result.addObject("processions", processions);
			result.addObject("requestURI", "procession/brotherhood/list.do");
		} catch (final Throwable oops) {
			oops.printStackTrace();
			result = new ModelAndView("procession/list");
			result.addObject("message", "procession.retrieve.error");
			result.addObject("processions", new ArrayList<FloatB>());
		}
		return result;
	}
}

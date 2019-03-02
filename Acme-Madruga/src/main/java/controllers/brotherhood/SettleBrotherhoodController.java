
package controllers.brotherhood;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.SettleService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.Settle;

@Controller
@RequestMapping("/settle/brotherhood")
public class SettleBrotherhoodController extends AbstractController {

	@Autowired
	private SettleService		settleService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Settle> settles;
		final Brotherhood principal;
		boolean selected = false;

		settles = new ArrayList<Settle>();
		principal = this.brotherhoodService.findByPrincipal();

		if (principal.getSettle() != null) {
			settles.add(principal.getSettle());
			selected = true;
		} else
			settles = this.settleService.findSettlesWithNoBrotherhood();

		result = new ModelAndView("settle/list");
		result.addObject("settles", settles);
		result.addObject("selected", selected);
		result.addObject("requestURI", "settle/brotherhood/list.do");

		return result;
	}
}

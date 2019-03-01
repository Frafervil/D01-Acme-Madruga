
package controllers.administrator;

import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import controllers.AbstractController;
import domain.Administrator;
import forms.AdministratorForm;

@Controller
@RequestMapping("/administrator/administrator")
public class AdministratorAdministratorController extends AbstractController {

	@Autowired
	private AdministratorService	administratorService;


	//Register----------------------------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView res;
		Administrator administrator;
		administrator = this.administratorService.create();
		res = this.createRegisterModelAndView(administrator);
		res.addObject("formURI", "administrator/administrator/register.do");
		return res;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("administratorForm") @Valid final AdministratorForm administratorForm, final BindingResult binding) {
		ModelAndView res;
		Administrator admin;

		try {
			admin = this.administratorService.reconstruct(administratorForm, binding);
			if (binding.hasErrors()) {
				res = this.createRegisterModelAndView(admin);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));

			} else {
				admin = this.administratorService.save(admin);
				res = new ModelAndView("welcome/index");
			}
		} catch (final Throwable oops) {
			res = this.createRegisterModelAndView(administratorForm, "administrator.commit.error");
		}
		return res;
	}

	//Ancillary Methods------------------------------------------------------------------

	private ModelAndView createRegisterModelAndView(final Administrator admin) {
		ModelAndView result;
		AdministratorForm administratorForm;
		administratorForm = this.administratorService.construct(admin);
		result = this.createRegisterModelAndView(administratorForm, null);
		return result;
	}

	private ModelAndView createRegisterModelAndView(final AdministratorForm administratorForm, final String messageCode) {
		ModelAndView res;

		res = new ModelAndView("administrator/administrator/register");
		res.addObject("administratorForm", administratorForm);
		res.addObject("message", messageCode);
		return res;
	}
}

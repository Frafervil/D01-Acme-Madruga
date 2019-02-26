
package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
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
		Administrator administratorForm;
		administratorForm = this.administratorService.create();
		res = this.createRegisterModelAndView(administratorForm, null);
		res.addObject("formURI", "administrator/administrator/register.do");
		return res;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView register(@Valid final AdministratorForm administratorForm, final BindingResult binding) {
		ModelAndView res;
		Administrator admin;
		admin = this.administratorService.reconstruct(administratorForm, binding);
		if (binding.hasErrors())
			res = this.createRegisterModelAndView(administratorForm, null);
		else
			try {
				this.administratorService.save(admin);
				res = new ModelAndView("redirect:../");
			} catch (final Throwable oops) {
				res = this.createRegisterModelAndView(admin, "admin.commit.error");
			}
		return res;
	}

	//Ancillary Methods------------------------------------------------------------------

	private ModelAndView createRegisterModelAndView(final Administrator admin, final String messageCode) {
		AdministratorForm administratorForm;

		administratorForm = new AdministratorForm();
		//Hidden Attributes
		administratorForm.setIdAdministrator(admin.getId());

		administratorForm.setName(admin.getName());
		administratorForm.setMiddleName(admin.getMiddleName());
		administratorForm.setSurname(admin.getSurname());
		administratorForm.setEmail(admin.getEmail());
		administratorForm.setPhone(admin.getPhone());
		administratorForm.setAddress(admin.getAddress());
		administratorForm.setPhoto(admin.getPhoto());

		administratorForm.setUsername(admin.getUserAccount().getUsername());
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		administratorForm.setAuthority(authority);

		return this.createRegisterModelAndView(administratorForm, messageCode);
	}

	private ModelAndView createRegisterModelAndView(final AdministratorForm administratorForm, final String messageCode) {
		ModelAndView res;

		res = new ModelAndView("administrator/administrator/register");
		res.addObject("administratorForm", administratorForm);
		return res;
	}
}

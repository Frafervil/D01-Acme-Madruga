/*
 * AdministratorController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.CustomisationService;
import domain.Administrator;
import forms.AdministratorForm;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}


	@Autowired
	private AdministratorService	administratorservice;

	@Autowired
	private CustomisationService	customisationService;


	@RequestMapping("/viewProfile")
	public ModelAndView view() {
		ModelAndView result;

		result = new ModelAndView("administrator/viewProfile");
		result.addObject("actor", this.administratorservice.findByPrincipal());

		return result;
	}

	protected ModelAndView createEditModelAndView(final Administrator administrator) {
		ModelAndView result;
		AdministratorForm administratorForm;
		administratorForm = this.administratorservice.construct(administrator);
		result = this.createEditModelAndView(administratorForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final AdministratorForm administratorForm, final String messageCode) {
		ModelAndView result;
		String countryCode;

		countryCode = this.customisationService.find().getCountryCode();

		result = new ModelAndView("administrator/edit");
		result.addObject("administratorForm", administratorForm);
		result.addObject("countryCode", countryCode);
		result.addObject("message", messageCode);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Administrator administrator;
		AdministratorForm administratorForm;

		administrator = this.administratorservice.findByPrincipal();
		Assert.notNull(administrator);
		administratorForm = this.administratorservice.construct(administrator);
		result = this.createEditModelAndView(administrator);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("administratorForm") @Valid final AdministratorForm administratorForm, final BindingResult binding) {
		ModelAndView result;
		Administrator administrator;

		try {
			administrator = this.administratorservice.reconstruct(administratorForm, binding);
			if (binding.hasErrors()) {
				result = this.createEditModelAndView(administrator);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
			} else {
				administrator = this.administratorservice.save(administrator);
				result = new ModelAndView("redirect:/welcome/index.do");
			}
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(administratorForm, "administrator.commit.error");
		}
		return result;
	}

}

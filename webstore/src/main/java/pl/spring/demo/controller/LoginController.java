package pl.spring.demo.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import pl.spring.demo.constants.ViewNames;

/**
 * MVC Controller for login tasks
 * 
 * @author AWOZNICA
 *
 */
@Controller
public class LoginController {

	/**
	 * shows login page
	 * 
	 * @return logic name of view to display
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return ViewNames.LOGIN;
	}

	/**
	 * executes wrong credentials to make operation redirect
	 * 
	 * @param model
	 *            model from MVC controller
	 * @return logic name of view to display
	 */
	@RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
	public String loginerror(Model model) {
		model.addAttribute("error", "true");
		return ViewNames.LOGIN;

	}

	/**
	 * executes logout operation
	 * 
	 * @param model
	 *            model from MVC controller
	 * @return logic name of view to display
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model model) {
		return ViewNames.LOGIN;
	}

	/**
	 * executes redirecting to error page
	 * 
	 * @param user
	 *            user credentials
	 * @return model with data in in and logical view name to display
	 */
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied(Principal user) {
		ModelAndView model = new ModelAndView();
		String errorMessage = "User : " + user.getName() + " have no permission to access page";
		model.addObject("errorMessage", errorMessage);
		return model;
	}
}

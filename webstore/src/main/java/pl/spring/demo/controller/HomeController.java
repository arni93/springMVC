package pl.spring.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.spring.demo.constants.ModelConstants;
import pl.spring.demo.constants.ViewNames;

/**
 * MVC controller to perform operation in main page
 * 
 * @author AWOZNICA
 *
 */
@Controller
public class HomeController {

	private static final String INFO_TEXT = "Here You shall display information containing informations about newly created TO";
	private static final String WELCOME = "This is a welcome page";

	/**
	 * shows main page
	 * 
	 * @param model
	 *            model in MVC controller to contain data
	 * @return logic name of view to display
	 */
	@RequestMapping("/")
	public String welcome(Model model) {
		model.addAttribute(ModelConstants.GREETING, WELCOME);
		model.addAttribute(ModelConstants.INFO, INFO_TEXT);
		return ViewNames.WELCOME;
	}
}

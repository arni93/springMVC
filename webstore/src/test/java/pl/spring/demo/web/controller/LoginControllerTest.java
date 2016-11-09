package pl.spring.demo.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.security.Principal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import pl.spring.demo.constants.ModelConstants;
import pl.spring.demo.constants.ViewNames;
import pl.spring.demo.controller.LoginController;

public class LoginControllerTest {

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/templates/");
		viewResolver.setSuffix(".jsp");
		mockMvc = MockMvcBuilders.standaloneSetup(new LoginController()).setViewResolvers(viewResolver).build();
	}

	@Test
	public void testShowLoginPage() throws Exception {
		// when
		ResultActions resultActions = mockMvc.perform(get("/login"));
		// then
		resultActions.andExpect(view().name(ViewNames.LOGIN));
	}

	@Test
	public void testLoginError() throws Exception {
		// when
		ResultActions resultActions = mockMvc.perform(get("/loginfailed"));
		// then
		resultActions.andExpect(view().name(ViewNames.LOGIN))
				.andExpect(model().attribute(ModelConstants.ERROR, "true"));
	}

	@Test
	public void testLogout() throws Exception {
		// when
		ResultActions resultActions = mockMvc.perform(get("/logout"));
		// then
		resultActions.andExpect(view().name(ViewNames.LOGIN));
	}

	@Test
	public void testAccessDenied() throws Exception {
		// when
		Principal testPrincipal = new Principal() {
			@Override
			public String getName() {
				return "testUser";
			}
		};
		ResultActions resultActions = mockMvc.perform(get("/403").principal(testPrincipal));
		// then
		resultActions.andExpect(view().name(ViewNames._403))
				.andExpect(model().attribute(ModelConstants.ERROR_MESSAGE, new ArgumentMatcher<Object>() {

					@Override
					public boolean matches(Object argument) {
						String message = (String) argument;
						return message.length() > 0;
					}
				}));
	}

}

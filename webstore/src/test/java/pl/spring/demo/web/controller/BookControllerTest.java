package pl.spring.demo.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import pl.spring.demo.constants.ModelConstants;
import pl.spring.demo.constants.ViewNames;
import pl.spring.demo.controller.BookController;
import pl.spring.demo.enumerations.BookStatus;
import pl.spring.demo.service.BookService;
import pl.spring.demo.to.BookTo;

/**
 * Tests BookController
 * 
 * @author AWOZNICA
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "controller-test-configuration.xml")
@WebAppConfiguration
public class BookControllerTest {

	@Autowired
	private BookService bookService;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		Mockito.reset(bookService);

		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/"); // can be default
		viewResolver.setSuffix(".jsp"); // can be default

		BookController bookController = new BookController();
		mockMvc = MockMvcBuilders.standaloneSetup(bookController).setViewResolvers(viewResolver).build();
		// Due to fact, that We are trying to construct real Bean - Book
		// Controller, we have to use reflection to mock existing field book
		// service
		ReflectionTestUtils.setField(bookController, "bookService", bookService);
	}

	/**
	 * tests method with declaration below
	 * 
	 * @see @RequestMapping(value = "/add", method = RequestMethod.GET) public
	 *      ModelAndView showAddBookView()
	 */
	@Test
	public void testAddBookPage() throws Exception {
		// given
		BookTo testBook = new BookTo(1L, "Test title", "Test Author", BookStatus.FREE);
		Mockito.when(bookService.saveBook(Mockito.any())).thenReturn(testBook);
		// when
		ResultActions resultActions = mockMvc.perform(post("/books/add").flashAttr("newBook", testBook));
		// then
		resultActions.andExpect(view().name(ViewNames.ADD_BOOK))
				.andExpect(model().attribute(ModelConstants.NEW_BOOK, new ArgumentMatcher<Object>() {
					@Override
					public boolean matches(Object argument) {
						BookTo book = (BookTo) argument;
						return null != book && book.getAuthors() == null && book.getTitle() == null;
					}
				}));
	}

	/**
	 * tests method with declaration below
	 * 
	 * @see @RequestMapping() public String list(Model model)
	 */
	@Test
	public void testDefaultViewPage() throws Exception {
		// given
		BookTo testBook = new BookTo(1L, "Test title", "Test Author", BookStatus.FREE);
		List<BookTo> testBookList = new ArrayList<>();
		testBookList.add(testBook);
		testBookList.add(testBook);
		// when
		Mockito.when(bookService.findAllBooks()).thenReturn(testBookList);
		ResultActions resultActions = mockMvc.perform(get("/books/"));
		// then
		resultActions.andExpect(view().name(ViewNames.BOOKS))
				.andExpect(model().attribute(ModelConstants.BOOK_LIST, new ArgumentMatcher<Object>() {
					@Override
					public boolean matches(Object argument) {
						@SuppressWarnings("unchecked")
						List<BookTo> bookList = (List<BookTo>) argument;
						return bookList != null && bookList.size() == testBookList.size();
					}
				}));

	}

	/**
	 * tests method with declaration below
	 * 
	 * @see @RequestMapping(value = "/all", method = RequestMethod.GET) public
	 *      ModelAndView allBooks()
	 */
	@Test
	public void testShowAllBooks() throws Exception {
		// given
		BookTo testBook = new BookTo(1L, "Test title", "Test Author", BookStatus.FREE);
		List<BookTo> testBookList = new ArrayList<>();
		testBookList.add(testBook);
		testBookList.add(testBook);
		Mockito.when(bookService.findAllBooks()).thenReturn(testBookList);
		// when
		ResultActions resultActions = mockMvc.perform(get("/books/all"));
		// then
		resultActions.andExpect(view().name(ViewNames.BOOKS))
				.andExpect(model().attribute(ModelConstants.BOOK_LIST, new ArgumentMatcher<Object>() {
					@Override
					public boolean matches(Object argument) {
						@SuppressWarnings("unchecked")
						List<BookTo> bookList = (List<BookTo>) argument;
						return bookList != null && bookList.size() == testBookList.size();
					}
				}));
	}

	/**
	 * tests method with declaration below
	 * 
	 * @see @RequestMapping(value = "/findBook", method = RequestMethod.GET)
	 *      public String showFindBookView(Model model)
	 */
	@Test
	public void testShowFindBookView() throws Exception {
		// given
		BookTo emptyBook = new BookTo();
		// when
		ResultActions resultActions = mockMvc.perform(get("/books/findBook"));
		// then
		resultActions.andExpect(view().name(ViewNames.FIND_BOOK))
				.andExpect(model().attribute(ModelConstants.FOUND_BOOK, emptyBook));
	}

	/**
	 * tests method with declaration below
	 * 
	 * @see @RequestMapping(value = "/findBook", method = RequestMethod.POST)
	 *      public ModelAndView findBooks(@ModelAttribute(ModelConstants.
	 *      FOUND_BOOK)
	 */
	@Test
	public void testShowFoundBooks() throws Exception {
		// given
		BookTo testBook = new BookTo(1L, "Test title", "Test Author", BookStatus.MISSING);
		List<BookTo> testBookList = new ArrayList<>();
		testBookList.add(testBook);
		testBookList.add(testBook);
		Mockito.when(bookService.findBooksByParams(testBook)).thenReturn(testBookList);
		// when
		ResultActions resultActions = mockMvc
				.perform(post("/books/findBook").flashAttr(ModelConstants.FOUND_BOOK, testBook));
		// then
		resultActions.andExpect(view().name(ViewNames.BOOKS))
				.andExpect(model().attribute(ModelConstants.BOOK_LIST, new ArgumentMatcher<Object>() {
					@Override
					public boolean matches(Object argument) {
						@SuppressWarnings("unchecked")
						List<BookTo> resultBookList = (List<BookTo>) argument;
						return null != resultBookList && resultBookList.size() == testBookList.size();
					}
				}));
	}

	/**
	 * tests method with declaration below
	 * 
	 * @see @RequestMapping("/book") public ModelAndView
	 *      showBookDetails(@RequestParam("id") String bookId)
	 */
	@Test
	public void testShowBookDetailsView() throws Exception {
		// given
		Long givenId = 2L;
		BookTo givenBook = new BookTo();
		givenBook.setId(givenId);
		Mockito.when(bookService.findBookById(givenId)).thenReturn(givenBook);
		// when
		ResultActions resultActions = mockMvc.perform(get("/books/book").param("id", givenId.toString()));
		// then
		resultActions.andExpect(view().name(ViewNames.BOOK))
				.andExpect(model().attribute(ModelConstants.BOOK, givenBook));
	}

	/**
	 * tests method with declaration below
	 * 
	 * @see @RequestMapping(value = "/add", method = RequestMethod.GET) public
	 *      ModelAndView showAddBookView()
	 */
	@Test
	public void testShowAddBookView() throws Exception {
		// given
		BookTo expectedBook = new BookTo();
		// when
		ResultActions resultActions = mockMvc.perform(get("/books/add"));
		// then
		resultActions.andExpect(view().name(ViewNames.ADD_BOOK))
				.andExpect(model().attribute(ModelConstants.NEW_BOOK, expectedBook));
	}

	/**
	 * tests method with declaration below
	 * 
	 * @see @RequestMapping(value = "/add", method = RequestMethod.POST) public
	 *      ModelAndView addBook(@ModelAttribute(ModelConstants.NEW_BOOK) BookTo
	 *      newBook)
	 */
	@Test
	public void testAddBook() throws Exception {
		// given
		BookTo givenBook = new BookTo();
		givenBook.setAuthors("test author");
		Mockito.when(bookService.saveBook(givenBook)).thenReturn(givenBook);
		// when
		ResultActions resultActions = mockMvc.perform(post("/books/add").flashAttr(ModelConstants.NEW_BOOK, givenBook));
		// then
		resultActions.andExpect(view().name(ViewNames.ADD_BOOK))
				.andExpect(model().attribute(ModelConstants.NEW_BOOK, new BookTo()));

	}

	/**
	 * tests method with declaration below
	 * 
	 * @see @RequestMapping(value = "/delete", method = RequestMethod.GET)
	 *      public ModelAndView deleteBook(@RequestParam("id") long bookId)
	 */
	@Test
	public void testDeleteBook() throws Exception {
		// given
		Long givenId = 2L;
		BookTo givenResultBook = new BookTo();
		givenResultBook.setId(givenId);
		Mockito.when(bookService.findBookById(givenId)).thenReturn(givenResultBook);
		// when
		ResultActions resultActions = mockMvc.perform(get("/books/delete").param("id", givenId.toString()));
		// then
		resultActions.andExpect(view().name(ViewNames.BOOK))
				.andExpect(model().attribute(ModelConstants.DESCRIPTION, new ArgumentMatcher<Object>() {
					@Override
					public boolean matches(Object argument) {
						String decription = (String) argument;
						return decription.length() > 0;
					}
				})).andExpect(model().attribute(ModelConstants.BOOK, givenResultBook));
	}

	/**
	 * (Example) Sample method which convert's any object from Java to String
	 */
	private static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

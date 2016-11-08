package pl.spring.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import pl.spring.demo.constants.ModelConstants;
import pl.spring.demo.constants.ViewNames;
import pl.spring.demo.service.BookService;
import pl.spring.demo.to.BookTo;

/**
 * Book controller
 * 
 * @author mmotowid
 * @author AWOZNICA
 */
@Controller
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookService bookService;
	private ModelAndView modelAndView;

	/**
	 * default page for default URL
	 * 
	 * @param model
	 *            model in MVC controller that contains data
	 * @return logic name of view to display
	 */
	@RequestMapping
	public String list(Model model) {
		modelAndView = this.allBooks();
		Map<String, Object> copiedModel = modelAndView.getModel();
		model.addAllAttributes(copiedModel);
		return ViewNames.BOOKS;
	}

	/**
	 * Method collects info about all books
	 * 
	 * @return data to display on page and logic name of page to display
	 */
	@RequestMapping("/all")
	public ModelAndView allBooks() {
		ModelAndView modelAndView = new ModelAndView();
		List<BookTo> allBooks = bookService.findAllBooks();
		modelAndView.addObject("bookList", allBooks);
		modelAndView.setViewName("books");
		return modelAndView;
	}

	/**
	 * show page on which finding book with given arguments is performed
	 * 
	 * @param model
	 *            model in MVC controller that contains data
	 * @return logic name of view to display
	 */
	@RequestMapping(value = "/findBook", method = RequestMethod.GET)
	public String showFindBookView(Model model) {
		model.addAttribute(ModelConstants.FOUND_BOOK, new BookTo());
		return ViewNames.FIND_BOOK;
	}

	/**
	 * perform finding books with given criteria in formular and showing them as
	 * list on another page
	 * 
	 * @param bookTo
	 *            object that is tied with formular page
	 * @return model with data of found books and logical name of view to
	 *         display
	 */
	@RequestMapping(value = "/findBook", method = RequestMethod.POST)
	public ModelAndView findBooks(@ModelAttribute(ModelConstants.FOUND_BOOK) final BookTo bookTo) {
		// TODO refactor to service method
		ModelAndView modelAndView = new ModelAndView();
		final String authors = bookTo.getAuthors();
		final String title = bookTo.getTitle();
		List<BookTo> booksByAuthor = this.bookService.findBooksByAuthor(bookTo.getAuthors());
		List<BookTo> booksByTitle = this.bookService.findBooksByTitle(bookTo.getTitle());
		if (!authors.equals("") || !title.equals("")) {
			if (authors.equals("")) {
				modelAndView.addObject("bookList", booksByTitle);
			} else if (title.equals("")) {
				modelAndView.addObject("bookList", booksByAuthor);
			} else {
				List<BookTo> retainedList = new ArrayList<>();
				retainedList.addAll(booksByAuthor);
				retainedList.retainAll(booksByTitle);
				modelAndView.addObject("bookList", retainedList);
			}
		}
		modelAndView.setViewName(ViewNames.BOOKS);
		return modelAndView;
	}

	/**
	 * redirects page to page with detailed info about book
	 * 
	 * @param bookId
	 *            id of book
	 * @return book info with given id contained in model and logic name of page
	 *         to display
	 */
	@RequestMapping("/book")
	public ModelAndView showBookDetails(@RequestParam("id") String bookId) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("description", "book info");
		int id = Integer.parseInt(bookId);
		BookTo foundBook = this.bookService.findBookById(id);
		modelAndView.addObject("book", foundBook);
		modelAndView.setViewName(ViewNames.BOOK);
		return modelAndView;
	}

	/**
	 * redirects page to page on which adding new book is available
	 * 
	 * @return model with no data inside and logical name of page to display
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView showAddBookView() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject(ModelConstants.NEW_BOOK, new BookTo());
		modelAndView.setViewName(ViewNames.ADD_BOOK);
		return modelAndView;
	}

	/**
	 * adds new Book
	 * 
	 * @param newBook
	 *            object tied with formular and data from formular inside it
	 * @return modelAndView with new BookTo object to be tied with formular on
	 *         view that will be displayed
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView addBook(@ModelAttribute(ModelConstants.NEW_BOOK) BookTo newBook) {
		this.bookService.saveBook(newBook);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(ViewNames.ADD_BOOK);
		modelAndView.addObject(ModelConstants.NEW_BOOK, new BookTo());
		return modelAndView;
	}

	/**
	 * performs deleting of book with given id
	 * 
	 * @param bookId
	 *            id of book to delete
	 * @return ModelAndView object with deletedBook in model and logical view to
	 *         display that deleted book info
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteBook(@RequestParam("id") long bookId) {
		ModelAndView modelAndView = new ModelAndView();
		BookTo removedBook = this.bookService.findBookById(bookId);
		this.bookService.deleteBook(bookId);
		modelAndView.addObject("description", "Removed book info");
		modelAndView.addObject("book", removedBook);
		modelAndView.setViewName(ViewNames.BOOK);
		return modelAndView;
	}

	/**
	 * Binder initialization
	 */
	@InitBinder
	public void initialiseBinder(WebDataBinder binder) {
		binder.setAllowedFields("id", "title", "authors", "status");
	}

}

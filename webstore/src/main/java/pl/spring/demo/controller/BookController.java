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
 *
 */
@Controller
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookService bookService;
	private ModelAndView modelAndView;

	@RequestMapping
	public String list(Model model) {
		modelAndView = this.allBooks();
		Map<String, Object> copiedModel = modelAndView.getModel();
		model.addAllAttributes(copiedModel);
		return ViewNames.BOOKS;
	}

	/**
	 * Method collects info about all books
	 */
	@RequestMapping("/all")
	public ModelAndView allBooks() {
		ModelAndView modelAndView = new ModelAndView();
		List<BookTo> allBooks = bookService.findAllBooks();
		modelAndView.addObject("bookList", allBooks);
		modelAndView.setViewName("books");
		// TODO: implement method gathering and displaying all books
		return modelAndView;
	}

	@RequestMapping(value = "/findBook", method = RequestMethod.GET)
	public String showFindBookView(Model model) {
		model.addAttribute(ModelConstants.FOUND_BOOK, new BookTo());
		return ViewNames.FIND_BOOK;
	}

	@RequestMapping(value = "/findBook", method = RequestMethod.POST)
	public ModelAndView findBooks(@ModelAttribute(ModelConstants.FOUND_BOOK) final BookTo bookTo) {
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

	// TODO: here implement methods which displays book info based on query
	// arguments
	@RequestMapping("/book")
	public ModelAndView showBookDetails(@RequestParam("id") String bookId) {
		ModelAndView modelAndView = new ModelAndView();
		int id = Integer.parseInt(bookId);
		BookTo foundBook = this.bookService.findBookById(id);
		modelAndView.addObject("book", foundBook);
		modelAndView.setViewName("book");
		return modelAndView;
	}

	// TODO: Implement GET / POST methods for "add book" functionality
	/**
	 * Method collects info about all books
	 */
	@RequestMapping("/add")
	public ModelAndView addBook(BookTo book) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("newBook", bookService.saveBook(book));
		modelAndView.setViewName(ViewNames.ADD_BOOK);
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

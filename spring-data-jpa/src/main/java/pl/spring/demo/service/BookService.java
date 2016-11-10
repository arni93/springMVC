package pl.spring.demo.service;

import java.util.List;

import pl.spring.demo.to.BookTo;

//TODO(mmotowid) add java docs
public interface BookService {

	BookTo findBookById(long id);

	List<BookTo> findAllBooks();

	List<BookTo> findBooksByTitle(String title);

	List<BookTo> findBooksByAuthor(String author);

	/**
	 * find books by authors and/or title
	 * 
	 * @author AWOZNICA
	 * @param bookTo
	 *            bookTo object
	 * @return returns list of book that specify criteria in it(authors and
	 *         title), when both specified then looks for books with this title
	 *         and authors, when only when specified, then the second "" is
	 *         ignored in searching. Never returns null.
	 */
	List<BookTo> findBooksByParams(BookTo bookTo);

	BookTo saveBook(BookTo book);

	void deleteBook(Long id);
}

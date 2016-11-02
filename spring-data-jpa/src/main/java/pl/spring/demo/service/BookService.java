package pl.spring.demo.service;

import java.util.List;

import pl.spring.demo.to.BookTo;

public interface BookService {

	BookTo findBookById(long id);

	List<BookTo> findAllBooks();

	List<BookTo> findBooksByTitle(String title);

	List<BookTo> findBooksByAuthor(String author);

	BookTo saveBook(BookTo book);

	void deleteBook(Long id);
}

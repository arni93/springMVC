package pl.spring.demo.service;

import java.util.List;

import pl.spring.demo.to.BookTo;

//TODO(mmotowidlo) dodac java doc, ale interfejs nie byl pisany przeze mnie wiec ich brak to nie moja wina :)
public interface BookService {

	BookTo findBookById(long id);

	List<BookTo> findAllBooks();

	List<BookTo> findBooksByTitle(String title);

	List<BookTo> findBooksByAuthor(String author);

	List<BookTo> findBooksByParams(BookTo bookTo);

	BookTo saveBook(BookTo book);

	void deleteBook(Long id);
}

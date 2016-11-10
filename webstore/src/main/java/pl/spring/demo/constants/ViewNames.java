package pl.spring.demo.constants;

import java.io.Serializable;

/**
 * Class containing view names for Model, names means files in
 * src/main/webapp/WEB-INF/views/
 * 
 * @author mmotowid
 *
 */
public final class ViewNames implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String ADD_BOOK = "addBook";
	public static final String BOOKS = "books";
	public static final String BOOK = "book";
	public static final String LOGIN = "login";
	public static final String WELCOME = "welcome";
	public static final String _403 = "403";
	public static final String FIND_BOOK = "findBook";
}

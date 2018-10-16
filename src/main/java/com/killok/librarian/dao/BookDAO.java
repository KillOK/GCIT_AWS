package com.killok.librarian.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.killok.librarian.entity.Book;

	@Component
	public class BookDAO extends BaseDAO<Book> implements ResultSetExtractor<List<Book>>{

		public void addBook(Book book)
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			libraryTemplate.update("insert into tbl_book (title) values(?)", new Object[] { book.getTitle() });
		}
		
		public Integer addBookWithID(Book book)
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			return libraryTemplate.update("insert into tbl_book (title) values(?)", new Object[] { book.getTitle() });
		}

		public void editBook(Book book)
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			libraryTemplate.update("update tbl_book set title = ? where bookId = ?", new Object[]{book.getTitle(), book.getBookId()});
		}

		public void deleteBook(Book book)
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			libraryTemplate.update("delete from tbl_book where bookId = ?", new Object[]{book.getBookId()});
		}

		public List<Book> readAllBooks()
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			return libraryTemplate.query("Select * from tbl_book", this);
		}
		
		public Book readBookbyPk(int id)
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			List<Book> bookList = new ArrayList<>();
			bookList = libraryTemplate.query("Select * from tbl_book where bookId = ?",new Object[]{id}, this);
			if(!bookList.isEmpty())
			return bookList.get(0);
			else return null;
		}

		@Override
		public List<Book> extractData(ResultSet rs) throws SQLException {
			List<Book> books = new ArrayList<>();
			while (rs.next()) {
				Book book = new Book();
				book.setBookId(rs.getInt("bookId"));
				book.setTitle(rs.getString("title"));
				books.add(book);
			}
			return books;
		}

		public List<Book> getBooksByAuthorID(Integer authorId) {
			return libraryTemplate.query("Select * from tbl_book where bookId IN (select bookId from tbl_book_authors where authorId = ?)", new Object[]{authorId}, this);
			
		}

	}


//	public BookDAO(Connection connection) {
//		super(connection);
//	}
//
//	public void addBook(Book book)
//			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
//		save("insert into tbl_book (title) values(?)", new Object[] { book.getTitle() });
//	}
//	
//	public Integer addBookWithID(Book book)
//			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
//		Integer i = saveWithID("insert into tbl_book (title) values(?)", new Object[] { book.getTitle() });
//		return i;
//	}
//
//	public void editBook(Book book)
//			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
//		save("update tbl_book set title = ? where bookId = ?", new Object[]{book.getTitle(), book.getBookId()});
//	}
//	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	/**
//	 * Only insert new relarions into the table, to delete use {@link #deleteAuthor(Author author, Book book)}
//	 * @param book
//	 * @throws InstantiationException
//	 * @throws IllegalAccessException
//	 * @throws ClassNotFoundException
//	 * @throws SQLException
//	 */
//	public void updateBookRelations(Book book) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
//		updateAuthorBookRelations(book);
//		updateGenreBookRelations(book);
//		updatePublisherBookRelations(book);
//	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
//	public void updateAuthorBookRelations(Book book) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
//		Map<Integer, Integer> hm = new HashMap<>();
//		if(!book.getAuthors().isEmpty()) {
//			for(Author a:getBookById(book.getBookId()).getAuthors()) {
//				hm.put(a.getAuthorId(), book.getBookId());
//			}		
//			for(Author a:book.getAuthors()) {
//				if(!hm.containsKey(a.getAuthorId())) {
//					save("insert into tbl_book_authors (authorId, bookId) values (?, ?)",new Object[]{a.getAuthorId(), book.getBookId()});
//					System.out.println("INSERTED");
//				}
//			}
//		}
//	}
//	
//	public void updateGenreBookRelations(Book book) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
//		Map<Integer, Integer> hm = new HashMap<>();
//		if(!book.getGenres().isEmpty()) {
//			for(Genre g:getBookById(book.getBookId()).getGenres()) {
//				hm.put(g.getGenreId(), book.getBookId());
//			}
//			for(Genre g:book.getGenres()) {
//				if(!hm.containsKey(g.getGenreId()))save("insert into tbl_book_genres (genre_id, bookId) values (?, ?)", new Object[] { g.getGenreId(), book.getBookId() });					
//			}
//		}
//	}
//	
//	public void updatePublisherBookRelations(Book book) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
//		if (book.getPublisher() != null) {
//			save("update tbl_book set pubId = ? where bookId = ?", new Object[] { book.getPublisher().getPublisherId(), book.getBookId() });
//		}
//	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
//	public void deleteBook(Book book) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
//				save("delete from tbl_book where bookId = ?", new Object[]{book.getBookId()});
//	}
//	
//	public void deleteAuthor(Author author, Book book) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
//			save("delete from tbl_book_authors where bookId = ? and authorId = ?", new Object[]{book.getBookId(), author.getAuthorId()});
//	} 
//	
//	public void deleteGenre(Genre genre, Book book) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
//			save("delete from tbl_book_genres where bookId = ? and genreId = ?", new Object[]{book.getBookId(), genre.getGenreId()});
//	} 
//	
//	public void deletePublisher(Book book) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
//			save("update tbl_book set pubId = ? where bookId = ?", new Object[]{null, book.getBookId()});
//	} 
//	
//	public Book getBookById(int id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
//		Book book = null;
//		List<Book> books = read("select * from tbl_book where bookId = ?", new Object[]{id});
//		if(!books.isEmpty())book = books.get(0);
//		return book;
//	}
//
//	public List<Book> readAllBooks() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
//		List<Book> books = new ArrayList<>();
//			books =  read("Select * from tbl_book", null);
//		return books;
//	}
//	
//	public List<Book> getBooksByAuthor(Author author) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
//		BookDAO bdao = new BookDAO(conn);
//		List<Book> books = new ArrayList<>();
//				books.addAll(bdao.read("select * from tbl_book where bookId IN (select bookId from tbl_book_authors where authorId = ?)", new Object[]{author.getAuthorId()}));
//		return books;
//	}
//	
//	public List<Book> getBooksByAuthor(String authorName) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
//		BookDAO bdao = new BookDAO(conn);
//		AuthorDAO adao = new AuthorDAO(conn);
//		List<Book> books = new ArrayList<>();
//		List<Author> authors = new ArrayList<>();
//			authors =adao.readAllAuthorsByName(authorName);
//			for(Author a : authors) {
//				books.addAll(bdao.read("select * from tbl_book where bookId IN (select bookId from tbl_book_authors where authorId = ?)", new Object[]{a.getAuthorId()}));
//			}
//		return books;
//	}
//	
//	public List<Book> getBooksByGenres(String... genres) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
//		BookDAO bdao = new BookDAO(conn);
//		GenreDAO gdao = new GenreDAO(conn);
//		List<Book> books = new ArrayList<>();
//		List<Genre> genreList = new ArrayList<>();
//				for(String s:genres) {
//					genreList=gdao.readAllgenresByName(s);
//				}
//				for(Genre g : genreList) {
//					books.addAll(bdao.read("select * from tbl_book where bookId IN (select bookId from tbl_book_genres where genre_id = ?)", new Object[]{g.getGenreId()}));
//				}		
//		return books;
//	}
//
//	@Override
//	public List<Book> extractData(ResultSet rs) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
//		AuthorDAO adao = new AuthorDAO(conn);
//		PublisherDAO pdao = new PublisherDAO(conn);
//		GenreDAO gdao = new GenreDAO(conn);
//		List<Book> books = new ArrayList<>();
//			while (rs.next()) {
//				Book book = new Book();
//				book.setBookId(rs.getInt("bookId"));
//				book.setTitle(rs.getString("title"));
//				book.setAuthors(adao.readFirstLevel("select * from tbl_author where authorId IN (select authorId from tbl_book_authors where bookId = ?)", new Object[]{rs.getInt("bookId")}));
//				book.setGenres(gdao.readFirstLevel("select * from tbl_genre where genre_id IN (select genre_id from tbl_book_genres where bookId = ?)", new Object[]{book.getBookId()}));
//				List<Publisher> publishers =pdao.readFirstLevel("select * from tbl_publisher where publisherId IN (select pubId from tbl_book where bookId = ?)",new Object[]{book.getBookId()});
//				book.setPublisher(publishers.isEmpty()?null:publishers.get(0));
//				books.add(book);
//			}
//		return books;
//	}
//	
//	@Override
//	public List<Book> extractDataFirstLevel(ResultSet rs) throws SQLException {
//		List<Book> books = new ArrayList<>();
//			while (rs.next()) {
//				Book book = new Book();
//				book.setBookId(rs.getInt("bookId"));
//				book.setTitle(rs.getString("title"));
//				books.add(book);
//			}
//		return books;
//	}
//	
//	public void commit() throws SQLException {
//		conn.commit();
//	}
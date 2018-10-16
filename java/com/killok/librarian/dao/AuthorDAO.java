package com.killok.librarian.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.killok.librarian.entity.Author;

@Component
public class AuthorDAO extends BaseDAO<Author> implements ResultSetExtractor<List<Author>>{

	public void addAuthor(Author author)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("insert into tbl_author (authorName) values(?)", new Object[] { author.getAuthorName() });
	}

	public void editAuthor(Author author)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("update tbl_author set authorName = ? where authorId = ?", new Object[]{author.getAuthorName(), author.getAuthorId()});
	}

	public void deleteAuthor(Author author)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("delete from tbl_author where authorId = ?", new Object[]{author.getAuthorId()});
	}

	public List<Author> readAllAuthors()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return libraryTemplate.query("Select * from tbl_author", this);
	}
	
	public List<Author> readAllAuthorsByName(String searchString)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return libraryTemplate.query("Select * from tbl_author where authorName like ?", new Object[]{searchString}, this);
	}
	
	public Author readAuthorByPK(Integer pk)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<Author> authors = libraryTemplate.query("Select * from tbl_author where authorId = ?", new Object[]{pk}, this);
		if(authors!=null){
			return authors.get(0);
		}else{
			return null;
		}
	}

	@Override
	public List<Author> extractData(ResultSet rs) throws SQLException {
		List<Author> authors = new ArrayList<>();
		while (rs.next()) {
			Author author = new Author();
			author.setAuthorId(rs.getInt("authorId"));
			author.setAuthorName(rs.getString("authorName"));
			authors.add(author);
		}
		return authors;
	}
}






















//	public AuthorDAO(Connection connection) {
//		super(connection);
//	}
//	
//	public Integer addAuthorWithID(Author author)
//			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
//		return saveWithID("insert into tbl_author (authorName) values(?)", new Object[] { author.getAuthorName() });
//	}
//
//	public void addAuthor(Author author)
//			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
//		save("insert into tbl_author (authorName) values(?)", new Object[] { author.getAuthorName() });
//	}
//
//	public void editAuthor(Author author)
//			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
//		save("update tbl_author set authorName = ? where authorId = ?", new Object[]{author.getAuthorName(), author.getAuthorId()});
//	}
//
//	public void deleteAuthor(Author author)
//			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
//		save("delete from tbl_author where authorId = ?", new Object[]{author.getAuthorId()});
//	}
//
//	public List<Author> readAllAuthors()
//			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
//		return read("Select * from tbl_author", null);
//	}
//	
//	public List<Author> readAllAuthorsByName(String searchString)
//			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
//		return read("Select * from tbl_author where authorName like ?", new Object[]{searchString});
//	}
//	
//	public Author readAuthorByPK(Integer pk)
//			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, NullPointerException {
//		List<Author> authors = read("Select * from tbl_author where authorId = ?", new Object[]{pk});
//		if(!authors.isEmpty()){
//			return authors.get(0);
//		}else{
//			return null;
//		}
//	}
//
//	@Override
//	public List<Author> extractData(ResultSet rs) throws SQLException {
//		List<Author> authors = new ArrayList<>();
//		while (rs.next()) {
//			Author author = new Author();
//			author.setAuthorId(rs.getInt("authorId"));
//			author.setAuthorName(rs.getString("authorName"));
//			authors.add(author);
//		}
//		return authors;
//	}
//	
//	public void updateAuthorBookRelations(Author author) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
//		Map<Integer, Integer> hm = new HashMap<>();
//		if(author.getBooks()!=null&&!author.getBooks().isEmpty()) {
//			for(Book b:readAuthorByPK(author.getAuthorId()).getBooks()) {
//				hm.put(b.getBookId(), author.getAuthorId());
//			}		
//			for(Book b:author.getBooks()) {
//				if(!hm.containsKey(b.getBookId())) {
//					save("insert into tbl_book_authors (bookId, authorId) values (?, ?)",new Object[]{b.getBookId(), author.getAuthorId()});
//					System.out.println("INSERTED");
//				}
//			}
//		}
//	}
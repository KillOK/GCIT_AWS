	package com.killok.librarian.dao;
	
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.util.ArrayList;
	import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
	import org.springframework.stereotype.Component;

import com.killok.librarian.entity.Book;
import com.killok.librarian.entity.BookCopy;
import com.killok.librarian.entity.LibBranch;
	
	@Component
	public class BookCopyDAO extends BaseDAO<BookCopy> implements ResultSetExtractor<List<BookCopy>>{
		
		@Autowired
		BookDAO bdao;
		
		@Autowired 
		LibBranchDAO branchdao;
	
		public void addBookCopy(BookCopy bookCopy) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			libraryTemplate.update("insert into tbl_book_copies (bookId, branchId, noOfCopies) values(?,?,?)", new Object[] { bookCopy.getBook().getBookId(), bookCopy.getBranch().getBranchId(), bookCopy.getCopieNumbersInBranch() });
		}
	
		public void editBookCopy(BookCopy bookCopy) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			libraryTemplate.update("update tbl_book_copies set noOfCopies = ? where bookId = ? and branchId=?", new Object[]{ bookCopy.getCopieNumbersInBranch(),  bookCopy.getBook().getBookId(), bookCopy.getBranch().getBranchId() });
		}
	
		public void deleteBookCopy(BookCopy bookCopy)
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			libraryTemplate.update("delete from tbl_book_copies where bookId = ?", new Object[]{bookCopy.getBook().getBookId()});
		}
	
		public List<BookCopy> readAllBookCopies()
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			List<BookCopy> copies = libraryTemplate.query("Select * from tbl_book_copies", this);
			
			return copies;
		}
		
		public List<BookCopy> readAllBookCopiesInBranch(LibBranch libBranch)
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			return libraryTemplate.query("Select * from tbl_book_copies where branchId = ? ", new Object[]{libBranch.getBranchId()}, this);
		}
		
		public List<BookCopy> readAllbookCopiesByName(Book book, LibBranch libBranch)
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			return libraryTemplate.query("Select * from tbl_book_copies where bookId = ? and branchId = ? ", new Object[]{book.getBookId(), libBranch.getBranchId()}, this);
		}
		
		public BookCopy readBookCopyByPK(Integer bookId, Integer branchId)
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			List<BookCopy> bookCopies = libraryTemplate.query("Select * from tbl_book_copies where bookId = ? and branchId = ?", new Object[]{bookId, branchId}, this);
			if(!bookCopies.isEmpty()){
				return bookCopies.get(0);
			}else{
				return null;
			}
		}
	
		@Override
		public List<BookCopy> extractData(ResultSet rs) throws SQLException{
			List<BookCopy> bookCopies = new ArrayList<>();
			while (rs.next()) {
				BookCopy bookCopy = new BookCopy();
				bookCopy.setCopieNumbersInBranch(rs.getInt("noOfCopies"));
				try {
					bookCopy.setBook(bdao.readBookbyPk(rs.getInt("bookId")));
					bookCopy.setBranch(branchdao.readLibBranchByPK(rs.getInt("branchId")));
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
					e.printStackTrace();
				}
				bookCopies.add(bookCopy);
			}
			return bookCopies;
		}
		
	
	}

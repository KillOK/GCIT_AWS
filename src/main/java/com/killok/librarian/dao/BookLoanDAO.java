	package com.killok.librarian.dao;
	
	import java.sql.Date;

	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.util.ArrayList;
	import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
	import org.springframework.stereotype.Component;
	
	import com.killok.librarian.entity.BookLoan;
	
	
	@Component
	public class BookLoanDAO extends BaseDAO<BookLoan> implements ResultSetExtractor<List<BookLoan>>{
		
		@Autowired
		BookDAO bdao;

		@Autowired
		AuthorDAO adao;
		
		@Autowired
		LibBranchDAO branchdao;
		
		@Autowired
		BookCopyDAO copydao;
		
		@Autowired
		BookLoanDAO loandao;

		@Autowired
		BorrowerDAO bordao;
	
		public void addBookLoan(BookLoan bookLoan)
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			libraryTemplate.update("insert into tbl_book_loans (bookId, branchId, cardNo, dueDate, dateOut) values(?,?,?,?,?)", new Object[] { bookLoan.getBook().getBookId(),bookLoan.getBranch().getBranchId(),bookLoan.getBorrower().getCardNo(),bookLoan.getDueDate(),bookLoan.getDateOut() });
		}
	
		public void editBookLoanDate(BookLoan bookLoan)
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			libraryTemplate.update("update tbl_book_loans set dueDate=?, dateOut=?, dateIn=? where bookId=? and branchId=? and cardNo=? and dateOut=?", new Object[]{bookLoan.getDueDate(), bookLoan.getDateOut(), bookLoan.getDateIn(),bookLoan.getBook().getBookId(),bookLoan.getBranch().getBranchId(),bookLoan.getBorrower().getCardNo(),bookLoan.getDateOut() });
		}
	
		public void deleteBookLoan(BookLoan bookLoan)
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			libraryTemplate.update("delete from tbl_book_loans where bookId=? and branchId=? and cardNo=? and dueDate=? ", new Object[]{ bookLoan.getBook().getBookId(),bookLoan.getBranch().getBranchId(),bookLoan.getBorrower().getCardNo(),bookLoan.getDueDate() });
		}
	
		public List<BookLoan> readAllbookLoans()
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			return libraryTemplate.query("Select * from tbl_book_loans", this);
		}
		
		public List<BookLoan> readAllbookLoansByBorrower(int borrowerId)
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			
			return libraryTemplate.query("Select * from tbl_book_loans where cardNo = ?", new Object[]{borrowerId}, this);
		}
		
		public List<BookLoan> readAllbookLoansByBranch(int branchId)
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			return libraryTemplate.query("Select * from tbl_book_loans where branchId = ?", new Object[]{branchId}, this);
		}
		
		public BookLoan readBookLoanByPK(int bookId, int branchId, int cardNo, Date dueDate)
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			List<BookLoan> bookLoans = libraryTemplate.query("Select * from tbl_book_loans where bookId=? and branchId=? and cardNo=? and dueDate=?", new Object[]{bookId, branchId, cardNo, dueDate}, this);
			if(bookLoans!=null){
				return bookLoans.get(0);
			}else{
				return null;
			}
		}
	
		@Override
		public List<BookLoan> extractData(ResultSet rs) throws SQLException {
			List<BookLoan> bookLoans = new ArrayList<>();
			while (rs.next()) {
				BookLoan bookLoan = new BookLoan();
				bookLoan.setDateOut(rs.getDate("dateOut"));
				bookLoan.setDueDate(rs.getDate("dueDate"));
				bookLoan.setDateIn(rs.getDate("dateIn"));
				try {
					bookLoan.setBook(bdao.readBookbyPk(rs.getInt("bookId")));
					bookLoan.setBorrower(bordao.readBorrowerByPK(rs.getInt("cardNo")));
					bookLoan.setBranch(branchdao.readLibBranchByPK(rs.getInt("branchId")));
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				bookLoans.add(bookLoan);
			}
			return bookLoans;
		}
		
	
	}

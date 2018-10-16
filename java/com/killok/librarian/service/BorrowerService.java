package com.killok.librarian.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.killok.librarian.dao.AuthorDAO;
import com.killok.librarian.dao.BookCopyDAO;
import com.killok.librarian.dao.BookDAO;
import com.killok.librarian.dao.BookLoanDAO;
import com.killok.librarian.dao.BorrowerDAO;
import com.killok.librarian.dao.LibBranchDAO;
import com.killok.librarian.entity.BookCopy;
import com.killok.librarian.entity.BookLoan;
import com.killok.librarian.entity.Borrower;
import com.killok.librarian.entity.LibBranch;

@RestController
public class BorrowerService {
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
	
	///////////////////////////////////Borrower by Id////////////////////////////////
	
	@RequestMapping(value = "/lms/getBorrowerByCardNo/{id}", method = RequestMethod.GET, produces = "application/json")
	public List<Borrower> getBorrowerById(@PathVariable(value = "id", required=false) Integer id) {
		List<Borrower> borrowers = new ArrayList<>();
		try {
			if (id==null) {
				borrowers = bordao.readAllBorrowers();
			} else {
				borrowers.add(bordao.readBorrowerByPK(id));
			}
			for (Borrower a : borrowers) {
				a.setBookLoans(loandao.readAllbookLoansByBorrower(id));
			}
			return borrowers;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "/lms/getBorrowerByCardNo", method = RequestMethod.GET, produces = "application/json")
	public List<Borrower> getBorrowerById() {
		List<Borrower> borrowers = new ArrayList<>();
		try {
			borrowers = bordao.readAllBorrowers();
			return borrowers;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	///////////////////////////////////CheckIn////////////////////////////////////////////////////////////////////
	
//	Select LibBranch from librarian service
	
//	Show bookCopies in Branch
	
	@RequestMapping(value = "/lms/getBookCopiesInBranch", method = RequestMethod.GET, produces = "application/json")
	public List<BookCopy> getBookCopiesOnSite(@RequestParam(required=false) Integer branchId,@RequestParam(required=false) Integer bookId) {
		List<BookCopy> copies = new ArrayList<>();
		try {
			if ((branchId!=null/*&&branchId!=0*/)&&(/*bookId==0||*/bookId==null)) {
				copydao.readAllBookCopiesInBranch(branchdao.readLibBranchByPK(branchId)).stream().filter(p -> p.getCopieNumbersInBranch()>0).forEach(copies::add);
			} else if((branchId!=null&&branchId!=0)&&(bdao.readBookbyPk(bookId)!=null)){
				copies= copydao.readAllbookCopiesByName(bdao.readBookbyPk(bookId), branchdao.readLibBranchByPK(branchId));
			}else {
				copies=copydao.readAllBookCopies();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return copies;
	}
	
	
	///////////////////////////////////CheckOut///////////////////////////////////////////////////////////////////
	@Transactional
	@RequestMapping(value = "/lms/checkOut", method = RequestMethod.GET)
	public String saveBookCopy(@RequestParam(value= "bookId", required=false) Integer bookId, @RequestParam(value= "branchId", required=false) Integer branchId, @RequestParam(value= "cardNo", required=false) Integer cardNo) {
		String returnString = "";
		List<BookLoan> loans;
		BookLoan loan;
		try {
			Borrower borrower = bordao.readBorrowerByPK(cardNo);
			BookCopy copy = copydao.readBookCopyByPK(bookId, branchId);
			if (bookId!=null&&branchId!=null&&cardNo!=null) {
				if(copy!=null&&copy.getCopieNumbersInBranch()!=0) {
					loans = loandao.readAllbookLoansByBorrower(cardNo);
					boolean haveLoan = false;
					for(BookLoan l:loans) {
						if (l.getDateIn()==null&&l.getBook().equals(copy.getBook())) {
							haveLoan=true;
							returnString = "You already have this book";
							break;
						}
					}
					if(!haveLoan) {
						loan = new BookLoan(bdao.readBookbyPk(bookId), branchdao.readLibBranchByPK(branchId), borrower, new Date(new java.util.Date().getTime()+24*7*3600*1000), new Date(new java.util.Date().getTime()));
						copy.setCopieNumbersInBranch(copy.getCopieNumbersInBranch()-1);
						loandao.addBookLoan(loan);
						copydao.editBookCopy(copy);
					}
					returnString = "CheckOut Successful";
					
				}
				
			}else{
				returnString = "Enter Correct Data";
			}
		} catch (Exception e) {
			returnString="Something went wrong...";
			e.printStackTrace();
		}
		return returnString;
	}	
	
	////////////////////////////////////////////////////CheckIn/////////////////////////////////////////////////////////////////////
	
	
	@Transactional
	@RequestMapping(value = "/lms/checkIn", method = RequestMethod.GET)
	public String saveCheckIn(@RequestParam(value= "bookId", required=false) Integer bookId, @RequestParam(value= "branchId", required=false) Integer branchId, @RequestParam(value= "cardNo", required=false) Integer cardNo) {
		String returnString = "";
		List<BookLoan> loans;
		BookLoan loan;
		Date dueDate = null;
		try {
			Borrower borrower = bordao.readBorrowerByPK(cardNo);
			BookCopy copy = copydao.readBookCopyByPK(bookId, branchId);
			if (bookId!=null&&branchId!=null&&cardNo!=null) {
					loans = loandao.readAllbookLoansByBorrower(cardNo);
					boolean haveLoan = false;
					for(BookLoan l:loans) {
						System.out.println(l);
						if (l.getDateIn()==null&&l.getBook().getBookId().equals(copy.getBook().getBookId())) {
							haveLoan=true;
							dueDate=l.getDueDate();
							break;
						}
					}
					if(haveLoan) {
						loan = loandao.readBookLoanByPK(bookId, branchId, cardNo, dueDate);
						loan.setDateIn(new Date(new java.util.Date().getTime()));
						copy.setCopieNumbersInBranch(copy.getCopieNumbersInBranch()+1);
						loandao.editBookLoanDate(loan);
						copydao.editBookCopy(copy);
						returnString = "CheckIn Successful";
					}
				
			}else{
				returnString = "Enter Correct Data";
			}
		} catch (Exception e) {
			returnString="Something went wrong...";
			e.printStackTrace();
		}
		return returnString;
	}	
}












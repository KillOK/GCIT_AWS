package com.killok.librarian.entity;

import java.sql.Date;

public class BookLoan {
	
	private Book book;
	private LibBranch branch;
	private Borrower borrower;
	private Date dateOut;
	private Date dueDate;
	private Date dateIn;
	public BookLoan() {}
	public BookLoan(Book book, LibBranch branch, Borrower borrower, Date dueDate, Date dateOut) {
		super();
		this.book = book;
		this.branch = branch;
		this.borrower = borrower;
		this.dateOut = new Date(new java.util.Date().getTime());
		this.dueDate = dueDate;
		this.dateOut = dateOut;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book bookId) {
		this.book = bookId;
	}
	public LibBranch getBranch() {
		return branch;
	}
	public void setBranch(LibBranch branch) {
		this.branch = branch;
	}
	public Borrower getBorrower() {
		return borrower;
	}
	public void setBorrower(Borrower borrower) {
		this.borrower = borrower;
	}
	public Date getDateOut() {
		return dateOut;
	}
	public void setDateOut(Date dateOut) {
		this.dateOut = dateOut;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public Date getDateIn() {
		return dateIn;
	}
	public void setDateIn(Date dateIn) {
		this.dateIn = dateIn;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((book == null) ? 0 : book.hashCode());
		result = prime * result + ((borrower == null) ? 0 : borrower.hashCode());
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + ((dateIn == null) ? 0 : dateIn.hashCode());
		result = prime * result + ((dateOut == null) ? 0 : dateOut.hashCode());
		result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookLoan other = (BookLoan) obj;
		if (book == null) {
			if (other.book != null)
				return false;
		} else if (!book.equals(other.book))
			return false;
		if (borrower == null) {
			if (other.borrower != null)
				return false;
		} else if (!borrower.equals(other.borrower))
			return false;
		if (branch == null) {
			if (other.branch != null)
				return false;
		} else if (!branch.equals(other.branch))
			return false;
		if (dateIn == null) {
			if (other.dateIn != null)
				return false;
		} else if (!dateIn.equals(other.dateIn))
			return false;
		if (dateOut == null) {
			if (other.dateOut != null)
				return false;
		} else if (!dateOut.equals(other.dateOut))
			return false;
		if (dueDate == null) {
			if (other.dueDate != null)
				return false;
		} else if (!dueDate.equals(other.dueDate))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "BookLoan [bookId=" + book + ", branch=" + branch + ", borrower=" + borrower + ", dateOut=" + (dateOut!=null?dateOut:"")
				+ ", dueDate=" + (dueDate!=null?dueDate:"") + ", dateIn=" + (dateIn!=null?dateIn:"") + "]";
	}
}

package com.killok.librarian.entity;

public class BookCopy {
	
	private Book book;
	private LibBranch branch;
	private int copieNumbersInBranch;
	
	public BookCopy() {}
	public BookCopy(Book book, LibBranch branch, int copieNumbersInBranch) {
		super();
		this.book = book;
		this.branch = branch;
		this.copieNumbersInBranch = copieNumbersInBranch;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public LibBranch getBranch() {
		return branch;
	}
	public void setBranch(LibBranch branch) {
		this.branch = branch;
	}
	public int getCopieNumbersInBranch() {
		return copieNumbersInBranch;
	}
	public void setCopieNumbersInBranch(int copieNumbersInBranch) {
		this.copieNumbersInBranch = copieNumbersInBranch;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((book == null) ? 0 : book.hashCode());
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + copieNumbersInBranch;
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
		BookCopy other = (BookCopy) obj;
		if (book == null) {
			if (other.book != null)
				return false;
		} else if (!book.equals(other.book))
			return false;
		if (branch == null) {
			if (other.branch != null)
				return false;
		} else if (!branch.equals(other.branch))
			return false;
		if (copieNumbersInBranch != other.copieNumbersInBranch)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "BookCopies [book=" + book.getTitle() + ", branch=" + branch.getBranchName() + ", copieNumbersInBranch=" + copieNumbersInBranch
				+ "]";
	}
	
	
}

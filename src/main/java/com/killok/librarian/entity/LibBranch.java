package com.killok.librarian.entity;

import java.util.List;

public class LibBranch {
	
	private int branchId;
	private String branchName;
	private String branchAdress;
	private List<BookCopy> bookCopies;
	private List<BookLoan> bookLoans;
	
	public LibBranch() {}
	public LibBranch(String branchName, String branchAdress) {
		this.branchName = branchName;
		this.branchAdress = branchAdress;
	}
	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getBranchAdress() {
		return branchAdress;
	}
	public void setBranchAdress(String branchAdress) {
		this.branchAdress = branchAdress;
	}
	public List<BookCopy> getBookCopies() {
		return bookCopies;
	}
	public void setBookCopies(List<BookCopy> bookCopies) {
		this.bookCopies = bookCopies;
	}
	public List<BookLoan> getBookLoans() {
		return bookLoans;
	}
	public void setBookLoans(List<BookLoan> bookLoans) {
		this.bookLoans = bookLoans;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((branchAdress == null) ? 0 : branchAdress.hashCode());
		result = prime * result + branchId;
		result = prime * result + ((branchName == null) ? 0 : branchName.hashCode());
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
		LibBranch other = (LibBranch) obj;
		if (branchAdress == null) {
			if (other.branchAdress != null)
				return false;
		} else if (!branchAdress.equals(other.branchAdress))
			return false;
		if (branchId != other.branchId)
			return false;
		if (branchName == null) {
			if (other.branchName != null)
				return false;
		} else if (!branchName.equals(other.branchName))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "LibBranch [branchId=" + branchId + ", branchName=" + branchName + ", branchAdress=" + branchAdress
				+ "]";
	}

}

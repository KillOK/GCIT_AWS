/**
 * 
 */
package com.killok.librarian.entity;

import java.util.List;

/**
 * @author ppradhan
 *
 */
public class Publisher {
	
	private Integer publisherId;
	private String pubName;
	private String pubPhone;
	private String pubAddress;
	private List<Book> books;
	
	
	public Publisher() {
		super();
	}
	public Publisher(String pubName) {
		this.pubName = pubName;
	}
	/**
	 * @return the publisherId
	 */
	public Integer getPublisherId() {
		return publisherId;
	}
	/**
	 * @param publisherId the publisherId to set
	 */
	public void setPublisherId(Integer publisherId) {
		this.publisherId = publisherId;
	}
	/**
	 * @return the pubName
	 */
	public String getPubName() {
		return pubName;
	}
	/**
	 * @param pubName the pubName to set
	 */
	public void setPubName(String pubName) {
		this.pubName = pubName;
	}
	/**
	 * @return the pubPhone
	 */
	public String getPubPhone() {
		return pubPhone;
	}
	/**
	 * @param pubPhone the pubPhone to set
	 */
	public void setPubPhone(String pubPhone) {
		this.pubPhone = pubPhone;
	}
	/**
	 * @return the pubAddress
	 */
	public String getPubAddress() {
		return pubAddress;
	}
	/**
	 * @param pubAddress the pubAddress to set
	 */
	public void setPubAddress(String pubAddress) {
		this.pubAddress = pubAddress;
	}
	/**
	 * @return the books
	 */
	public List<Book> getBooks() {
		return books;
	}
	/**
	 * @param books the books to set
	 */
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pubAddress == null) ? 0 : pubAddress.hashCode());
		result = prime * result + ((pubName == null) ? 0 : pubName.hashCode());
		result = prime * result + ((pubPhone == null) ? 0 : pubPhone.hashCode());
		result = prime * result + ((publisherId == null) ? 0 : publisherId.hashCode());
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
		Publisher other = (Publisher) obj;
		if (pubAddress == null) {
			if (other.pubAddress != null)
				return false;
		} else if (!pubAddress.equals(other.pubAddress))
			return false;
		if (pubName == null) {
			if (other.pubName != null)
				return false;
		} else if (!pubName.equals(other.pubName))
			return false;
		if (pubPhone == null) {
			if (other.pubPhone != null)
				return false;
		} else if (!pubPhone.equals(other.pubPhone))
			return false;
		if (publisherId == null) {
			if (other.publisherId != null)
				return false;
		} else if (!publisherId.equals(other.publisherId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Publisher [publisherId=" + publisherId + ", pubName=" + pubName + ", pubPhone=" + pubPhone
				+ ", pubAddress=" + pubAddress + "]";
	}
	
}

	package com.killok.librarian.service;
	
	import java.util.ArrayList;
	import java.util.List;
	
	import org.springframework.beans.factory.annotation.Autowired;
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
	import com.killok.librarian.dao.LibBranchDAO;
	import com.killok.librarian.entity.BookCopy;
	import com.killok.librarian.entity.LibBranch;
	
	@RestController
	public class LibrarianService {
	
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
		
		
	////////////////////////////////////Branch/////////////////////////////////////////////

		@RequestMapping(value = "/lms/getLibBranchById/{id}", method = RequestMethod.GET, produces = "application/json")
		public List<LibBranch> getBranchById(@PathVariable(value = "id", required=false) Integer id) {
			List<LibBranch> branches = new ArrayList<>();
			try {
				if (id==null) {
					branches = branchdao.readAllLibBranches();
				} else {
					branches.add(branchdao.readLibBranchByPK(id));
				}
				for (LibBranch a : branches) {
					a.setBookCopies(copydao.readAllBookCopiesInBranch(a));
				}
				for (LibBranch a : branches) {
					a.setBookLoans(loandao.readAllbookLoansByBranch(a.getBranchId()));
				}
				return branches;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		@RequestMapping(value = "/lms/getLibBranchById/", method = RequestMethod.GET, produces = "application/json")
		public List<LibBranch> getBranchById() {
			List<LibBranch> branches = new ArrayList<>();
			try {
				branches = branchdao.readAllLibBranches();
				return branches;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		@RequestMapping(value = "/lms/getLibBranchByName/", method = RequestMethod.GET, produces = "application/json")
		@ResponseBody
		public List<LibBranch> getLibBranchByName() {
			try {
				return branchdao.readAllLibBranches();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@RequestMapping(value = "/lms/getLibBranchByName/{searchString}", method = RequestMethod.GET, produces = "application/json")
		@ResponseBody
		public List<LibBranch> readLibBranchByName(@PathVariable(value = "searchString", required = false) String searchString) {
			List<LibBranch> branches = new ArrayList<>();
				try {
					if (searchString==null) {
						branches = branchdao.readAllLibBranches();
					} else {
						branches=branchdao.readAllLibBranchsByName(searchString);
					
						for (LibBranch a : branches) {
							a.setBookCopies(copydao.readAllBookCopiesInBranch(a));
						}
						for (LibBranch a : branches) {
							a.setBookLoans(loandao.readAllbookLoansByBranch(a.getBranchId()));
						}
					}
					return branches;
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		
		
		@RequestMapping(value = "/lms/updateBranch", method = RequestMethod.POST, consumes = "application/json")
		public String saveBranch(@RequestBody LibBranch branch) {
			String returnString = "";
			try {
				if (branchdao.readLibBranchByPK(branch.getBranchId())!=null&&(branch.getBranchName()!=null||branch.getBranchAdress()!=null)) {
					if(branch.getBranchName()==null||branch.getBranchName().isEmpty())branch.setBranchName(branchdao.readLibBranchByPK(branch.getBranchId()).getBranchName());
					if(branch.getBranchAdress()==null||branch.getBranchName().isEmpty())branch.setBranchName(branchdao.readLibBranchByPK(branch.getBranchId()).getBranchAdress());
					branchdao.editLibBranch(branch);
					returnString = "Branch updated sucessfully";
				} else if (branchdao.readLibBranchByPK(branch.getBranchId())!=null) {
					branchdao.deleteLibBranch(branch);
					returnString = "Branch deleted sucessfully";
				} else {
					branchdao.addLibBranch(branch);
					returnString = "Branch saved sucessfully";
				}
			} catch (Exception e) {
				returnString="Something went wrong...";
				e.printStackTrace();
			}
			return returnString;
		}	
		
		
		
		////////////////////////////////////////BookCopy///////////////////////////////////////////////////////////////////
		
		
		@RequestMapping(value = "/lms/getBookCopies", method = RequestMethod.GET, produces = "application/json")
		public List<BookCopy> getBookCopies(@RequestParam(required=false) Integer branchId,@RequestParam(required=false) Integer bookId) {
			List<BookCopy> copies = new ArrayList<>();
			try {
				if ((branchId!=null/*&&branchId!=0*/)&&(/*bookId==0||*/bookId==null)) {
					copies = copydao.readAllBookCopiesInBranch(branchdao.readLibBranchByPK(branchId));
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
		
		
		@RequestMapping(value = "/lms/updateBookCopy", method = RequestMethod.POST, consumes = "application/json")
		public String saveBookCopy(@RequestBody BookCopy copy) {
			String returnString = "";
			try {
				if (copydao.readBookCopyByPK(copy.getBook().getBookId(), copy.getBranch().getBranchId())!=null&&copy.getCopieNumbersInBranch()!=0) {
					copydao.editBookCopy(copy);
					returnString = "Number of copies was changed";
				}else if (copydao.readBookCopyByPK(copy.getBook().getBookId(), copy.getBranch().getBranchId())==null&&copy.getCopieNumbersInBranch()!=0) {
					copydao.addBookCopy(copy);
					returnString = "Branch deleted sucessfully";
				}
			} catch (Exception e) {
				returnString="Something went wrong...";
				e.printStackTrace();
			}
			return returnString;
		}	
		
	}
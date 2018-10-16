	package com.killok.librarian.dao;
	
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.util.ArrayList;
	import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
	import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.killok.librarian.entity.LibBranch;
	
	@Component
	public class LibBranchDAO extends BaseDAO<LibBranch> implements ResultSetExtractor<List<LibBranch>>{
		
		@Autowired
		LibBranchDAO branchdao;
		
		@Autowired
		BookCopyDAO copydao;
		
		@Autowired
		BookLoanDAO loandao;
	
		public Integer addLibBranchWithID(LibBranch libBranch)
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			return libraryTemplate.update("insert into tbl_library_branch (branchName,branchAddress) values(?,?)", new Object[] { libBranch.getBranchName(), libBranch.getBranchAdress() });
		}
		
		@Transactional
		public void addLibBranch(LibBranch libBranch)
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			libraryTemplate.update("insert into tbl_library_branch (branchName,branchAddress) values(?,?)", new Object[] { libBranch.getBranchName(), libBranch.getBranchAdress() });
			
		}
		
		@Transactional
		public void editLibBranch(LibBranch libBranch)
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			libraryTemplate.update("update tbl_library_branch set branchName = ?, branchAddress=? where branchId = ?", new Object[]{ libBranch.getBranchName(), libBranch.getBranchAdress(), libBranch.getBranchId() });
		}
	
		public void deleteLibBranch(LibBranch libBranch)
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			libraryTemplate.update("delete from tbl_library_branch where branchId = ?", new Object[]{libBranch.getBranchId()});
		}
	
		public List<LibBranch> readAllLibBranches()
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			return libraryTemplate.query("Select * from tbl_library_branch", this);
		}
		
		public List<LibBranch> readAllLibBranchsByName(String searchString)
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			return libraryTemplate.query("Select * from tbl_library_branch where branchName like ?", new Object[]{searchString}, this);
		}
		
		public LibBranch readLibBranchByPK(Integer pk)
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, NullPointerException {
			List<LibBranch> libBranches = libraryTemplate.query("Select * from tbl_library_branch where branchId = ?", new Object[]{pk}, this);
			if(!libBranches.isEmpty()){
				return libBranches.get(0);
			}else{
				return null;
			}
		}
	
		@Override
		public List<LibBranch> extractData(ResultSet rs) throws SQLException{
			List<LibBranch> libBranches = new ArrayList<>();
			while (rs.next()) {
				LibBranch libBranch = new LibBranch();
				libBranch.setBranchId(rs.getInt("branchId"));
				libBranch.setBranchName(rs.getString("branchName"));
				libBranch.setBranchAdress(rs.getString("branchAddress"));
				libBranches.add(libBranch);
			}
			return libBranches;
		}
		
	}

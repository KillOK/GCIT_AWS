package com.killok.librarian.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.killok.librarian.entity.Borrower;

@Component
public class BorrowerDAO extends BaseDAO<Borrower> implements ResultSetExtractor<List<Borrower>>{
	
	public Integer addBorrowerWithID(Borrower borrower)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return libraryTemplate.update("insert into tbl_borrower (name, address, phone) values(?,?,?)", new Object[] { borrower.getName(), borrower.getAdress(), borrower.getPhone() });
	}

	public void addBorrower(Borrower borrower)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("insert into tbl_borrower (name, address, phone) values(?,?,?)", new Object[] { borrower.getName(), borrower.getAdress(), borrower.getPhone() });
	}

	public void editBorrower(Borrower borrower)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("update tbl_borrower set name = ?, address =?, phone = ? where cardNo = ?", new Object[]{borrower.getName(),borrower.getAdress(),borrower.getPhone(),borrower.getCardNo()});
	}

	public void deleteBorrower(Borrower borrower)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("delete from tbl_borrower where cardNo = ?", new Object[]{borrower.getCardNo()});
	}

	public List<Borrower> readAllBorrowers()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return libraryTemplate.query("Select * from tbl_borrower", this);
	}
	
	public List<Borrower> readAllBorrowersByName(String searchString)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return libraryTemplate.query("Select * from tbl_borrower where name like ?", new Object[]{searchString}, this);
	}
	
	public Borrower readBorrowerByPK(Integer pk)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, NullPointerException {
		List<Borrower> borrowers = libraryTemplate.query("Select * from tbl_borrower where cardNo = ?", new Object[]{pk}, this);
		if(!borrowers.isEmpty()){
			return borrowers.get(0);
		}else{
			return null;
		}
	}

	@Override
	public List<Borrower> extractData(ResultSet rs) throws SQLException {
		List<Borrower> borrowers = new ArrayList<>();
		while (rs.next()) {
			Borrower borrower = new Borrower();
			borrower.setCardNo(rs.getInt("cardNo"));
			borrower.setName(rs.getString("name"));
			borrower.setAdress(rs.getString("address"));
			borrower.setPhone(rs.getString("phone"));
			borrowers.add(borrower);
		}
		return borrowers;
	}
	
}

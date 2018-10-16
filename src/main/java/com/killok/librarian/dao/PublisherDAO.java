	package com.killok.librarian.dao;
	
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.util.ArrayList;
	import java.util.List;
	
	import org.springframework.jdbc.core.ResultSetExtractor;
	import org.springframework.stereotype.Component;
	
	import com.killok.librarian.entity.Publisher;
	
	@Component
	public class PublisherDAO extends BaseDAO<Publisher> implements ResultSetExtractor<List<Publisher>>{
	
		public void addPublisher(Publisher publisher)
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			libraryTemplate.update("insert into tbl_publisher (publisherName, publisherAddress, publisherPhone) values(?,?,?)", new Object[] { publisher.getPubName(), publisher.getPubAddress(),  publisher.getPubPhone()});
			
		}
	
		public void editPublisher(Publisher publisher)
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			libraryTemplate.update("update tbl_publisher set PublisherName = ?, publisherAddress=?, publisherPhone=? where PublisherId = ?", new Object[]{publisher.getPubName(), publisher.getPubAddress(), publisher.getPubPhone(), publisher.getPublisherId()});
			
		}
	
		public void deletePublisher(Publisher publisher)
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			libraryTemplate.update("delete from tbl_publisher where PublisherId = ?", new Object[]{publisher.getPublisherId()});
			
		}
	
		public List<Publisher> readAllpublishers()
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			return libraryTemplate.query("Select * from tbl_publisher", this);
		}
		
		public List<Publisher> readAllpublishersByName(String searchString)
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			return libraryTemplate.query("Select * from tbl_publisher where publisherName like ?", new Object[]{searchString}, this);
		}
		
		public Publisher readPublisherByPK(Integer pk)
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			List<Publisher> publishers = libraryTemplate.query("Select * from tbl_publisher where publisherId = ?", new Object[]{pk}, this);
			if(!publishers.isEmpty()){
				return publishers.get(0);
			}else{
				return null;
			}
		}
		
		public Publisher readPublisherByName(String name)
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			List<Publisher> publishers = libraryTemplate.query("Select * from tbl_publisher where publisherName = ?", new Object[]{name}, this);
			if(!publishers.isEmpty()){
				return publishers.get(0);
			}else{
				return null;
			}
		}
	
		@Override
		public List<Publisher> extractData(ResultSet rs) throws SQLException {
			List<Publisher> publishers = new ArrayList<>();
			while (rs.next()) {
				Publisher publisher = new Publisher();
				publisher.setPublisherId(rs.getInt("PublisherId"));
				publisher.setPubName(rs.getString("PublisherName"));
				publisher.setPubAddress(rs.getString("PublisherAddress"));
				publisher.setPubPhone(rs.getString("PublisherPhone"));
				publishers.add(publisher);
			}
			return publishers;
		}
	
	}

package com.killok.librarian.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.killok.librarian.entity.Genre;


	@Component
public class GenreDAO extends BaseDAO<Genre> implements ResultSetExtractor<List<Genre>>{

	public void addGenre(Genre genre)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("insert into tbl_genre (genre_name) values(?)", new Object[] { genre.getGenreName() });
	}

	public void editGenre(Genre genre)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("update tbl_genre set genre_name = ? where genre_id = ?", new Object[]{genre.getGenreName(), genre.getGenreId()});
	}

	public void deleteGenre(Genre genre)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("delete from tbl_genre where genre_id = ?", new Object[]{genre.getGenreId()});
	}

	public List<Genre> readAllGenres()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return libraryTemplate.query("Select * from tbl_genre", this);
	}
	
	public List<Genre> readAllgenresByName(String searchString)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return libraryTemplate.query("Select * from tbl_genre where genre_name like ?", new Object[]{searchString}, this);
	}
	
	public Genre readGenreByPK(Integer pk)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<Genre> genres = libraryTemplate.query("Select * from tbl_genre where genre_id = ?", new Object[]{pk}, this);
		if(!genres.isEmpty()){
			return genres.get(0);
		}else{
			return null;
		}
	}

	@Override
	public List<Genre> extractData(ResultSet rs) throws SQLException {
		List<Genre> genres = new ArrayList<>();
		while (rs.next()) {
			Genre genre = new Genre();
			genre.setGenreId(rs.getInt("genre_id"));
			genre.setGenreName(rs.getString("genre_name"));
			genres.add(genre);
		}
		return genres;
	}
	
	
	

}

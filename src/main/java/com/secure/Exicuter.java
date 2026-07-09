package com.secure;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
@Repository
public class Exicuter {
	 @Autowired
	 private JdbcTemplate jdbc;
     public void create_table() {
    	 String query = """
 		        CREATE TABLE IF NOT EXISTS books (
 		            book_id INT PRIMARY KEY AUTO_INCREMENT,
 		            title VARCHAR(200) NOT NULL,
 		            author VARCHAR(100) NOT NULL,
 		            category VARCHAR(100),
 		            publisher VARCHAR(100),
 		            publication_year INT,
 		            price DECIMAL(10,2),
 		            total_copies INT NOT NULL,
 		            copies_available INT NOT NULL
 		        );
 		        """;
    	 jdbc.execute(query);
    		}
	 public void insert_values(Variables st) {
		String query="INSERT INTO books(title, author, category, publisher, publication_year, price, total_copies, copies_available) VALUES (?,?,?,?,?,?,?,?)";
		jdbc.update(
			    query,
			    st.getTitle(),
			    st.getAuthor(),
			    st.getCategory(),
			    st.getPublisher(),
			    st.getPublication_year(),
			    st.getPrice(),
			    st.getTotal_copies(),
			    st.getCopies_available()
			);
	 }
	 public List<Variables> show() {
		String query="SELECT * FROM books";
		return jdbc.query(query,new BeanPropertyRowMapper<>(Variables.class));
	 }
	 public int update(Variables st) {
	    String query="UPDATE books set publication_year=?,total_copies=?,copies_available=? where book_id=?";
	    return jdbc.update(query,st.getPublication_year(),st.getTotal_copies(),st.getCopies_available(),st.getBook_id());
	 }
	 public int delete(int book_id) {
		String query="DELETE from books where book_id=?";
		return jdbc.update(query,book_id);
	 }
	 public List<Variables> show_book_id(int book_id) {
		String query="SELECT * from books where book_id=?";
		return jdbc.query(query,new BeanPropertyRowMapper<>(Variables.class),book_id);
	 }
}

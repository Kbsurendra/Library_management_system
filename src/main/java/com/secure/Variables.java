package com.secure;

import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public class Variables {
    private int book_id;
    private String title;
    private String author;
    private String category;
    private String publisher;
    private int publication_year;
    private double price;
    private int total_copies;
    private int copies_available;
    public int getCopies_available() {
    	
		return copies_available;
	}
	public void setCopies_available(int copies_available) {
		this.copies_available = copies_available;
	}
	public int getBook_id(){
		return book_id;
	}
	public void setBook_id(int bookId) {
		this.book_id = bookId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public int getPublication_year() {
		return publication_year;
	}
	public void setPublication_year(int publication_year) {
		this.publication_year = publication_year;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getTotal_copies() {
		return total_copies;
	}
	public void setTotal_copies(int total_copies) {
		this.total_copies = total_copies;
	}
}

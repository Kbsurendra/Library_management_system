package com.secure;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
@Document(collection="books")
public class book {
	@Id
	 private String id;
     public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	 private String title;
     private List<String> author_name;
     //private int first_publish_year;
     @JsonProperty("first_publish_year")
     @Field("first_publish_year")
     private int firstPublishYear;
     public int getFirstPublishYear() {
		return firstPublishYear;
	}
	 public void setFirstPublishYear(int firstPublishYear) {
		 this.firstPublishYear = firstPublishYear;
	 }
	 private int edition_count;
     private String ebook_access;
     private List<String> language;
     private String key;
     private int cover_i;
//     public int getFirst_publish_year() {
//		return first_publish_year;
//	}
//	 public void setFirst_publish_year(int first_publish_year) {
//		 this.first_publish_year = first_publish_year;
//	 }
	 public int getEdition_count() {
		 return edition_count;
	 }
	 public void setEdition_count(int edition_count) {
		 this.edition_count = edition_count;
	 }
	 public String getEbook_access() {
		 return ebook_access;
	 }
	 public void setEbook_access(String ebook_access) {
		 this.ebook_access = ebook_access;
	 }
	 public List<String> getLanguage() {
		 return language;
	 }
	 public void setLanguage(List<String> language) {
		 this.language = language;
	 }
	 public String getKey() {
		 return key;
	 }
	 public void setKey(String key) {
		 this.key = key;
	 }
	 public int getCover_i() {
		 return cover_i;
	 }
	 public void setCover_i(int cover_i) {
		 this.cover_i = cover_i;
	 }
     public String getTitle() {
		return title;
	}
	 public void setTitle(String title) {
		 this.title = title;
	 }
	 public List<String> getAuthor_name() {
		 return author_name;
	 }
	 public void setAuthor_name(List<String> author_name) {
		 this.author_name = author_name;
	 }
}

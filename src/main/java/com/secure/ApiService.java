package com.secure;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service 
public class ApiService {
  private final RestClient restClient=RestClient.create();
  @Autowired
  private bookRepository repository;
  public List<book> external_books(){
	  OpenLibraryResponse response=restClient.get()
			  .uri("https://openlibrary.org/search.json?q=java")
			  .retrieve()
			  .body(OpenLibraryResponse.class);
	     
	  repository.saveAll(response.getDocs());
	  return response.getDocs();
	  
  }
  public List<book> external_title_books(String title) {
	OpenLibraryResponse response=restClient.get()
			.uri("https://openlibrary.org/search.json?title={title}",title)
			.retrieve()
			.body(OpenLibraryResponse.class);
	return response.getDocs();
  }
  public void save_books_data() {
	  OpenLibraryResponse response=restClient.get()
			  .uri("https://openlibrary.org/search.json?q=java")
			  .retrieve()
			  .body(OpenLibraryResponse.class);
	  System.out.println("books recived"+response.getDocs().size());
	  repository.saveAll(response.getDocs());
	  System.out.println("books in mongodb"+repository.count());
  }
  public int insert_into_mongodb(book b) {
	book save=repository.save(b);
	if(save !=null) return 1;
	else return 0;
  }
  public int delete_mongodb_data(String id) {
	  if(repository.existsById(id)) {
	repository.deleteById(id);
	return 1;
	  }
	return 0;
  }
  public int update_mongodb_data(String id, book b) {
    book exist=repository.findById(id).orElse(null);
	if(exist==null) {
		return 0;
	}
	if(b.getTitle()!=null)
	    exist.setTitle(b.getTitle());
	if (b.getAuthor_name() != null)
	    exist.setAuthor_name(b.getAuthor_name());
	if (b.getFirstPublishYear() != 0)
	    exist.setFirstPublishYear(b.getFirstPublishYear());
	if (b.getEdition_count() != 0)
		exist.setEdition_count(b.getEdition_count());
	if (b.getEbook_access() != null)
		exist.setEbook_access(b.getEbook_access());
	if (b.getLanguage() != null)
		exist.setLanguage(b.getLanguage());
	if (b.getKey() != null)
		exist.setKey(b.getKey());
	if (b.getCover_i() != 0)
		exist.setCover_i(b.getCover_i());
	repository.save(exist);
	return 1;
  }
  public List<book> show_mongodb_books() {
	return repository.findAll();
  }
  public List<book> show_mongodb_data_title(String title) {
	return repository.findByTitle(title);
  }
  public List<book> show_mongodb_data_language(String language) {
	return repository.findByLanguage(language);
  }
  public List<book> show_mongodb_data_year(int year) {
	return repository.findByFirstPublishYear(year);
  }
}


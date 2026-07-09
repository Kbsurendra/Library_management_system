package com.secure;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	@Autowired
    private final ApiService apiService;
	@Autowired
	private Exicuter exe;
    public Controller(ApiService apiService) {
        this.apiService = apiService;
    }
	@GetMapping("/create_table")
	public String create_table() {
		exe.create_table();
		return "table_crated";
	}
	@PostMapping("/insert")
	public String insert(@RequestBody Variables var) {
		exe.insert_values(var);
		return "data_inserted";
	}
	@GetMapping("/show")
	public List<Variables> getstd(){
		return exe.show();
	}
	@PutMapping("/update")
	public String update(@RequestBody Variables var) {
		int pos=exe.update(var);
		if(pos>0) return "data_updated";
		else return "data_not_updates";
	}
	@DeleteMapping("/delete/{book_id}")
	public String delete(@PathVariable int book_id) {
		int pos=exe.delete(book_id);
		if(pos>0) {
			return "book +{book_id}+ data deleted";
		}
		else return "data not deleted";
	}
	@GetMapping("/show/{book_id}")
	public List<Variables> show_book_id(@PathVariable int book_id){
		return exe.show_book_id(book_id);
	}
	@GetMapping("/external_books")
	public List<book> external_books(){
		return apiService.external_books();
		
	}
	@GetMapping("/external_books/{title}")
	public List<book> external_title_books(@PathVariable String title){
		return apiService.external_title_books(title);
	}
	@GetMapping("/save_books_data")
	public String save_books_data() {
		apiService.save_books_data();
		return "books saved";
	}
	@Autowired
	private bookRepository repository;

	@GetMapping("/mongo_books")
	public List<book> mongoBooks() {
	    return repository.findAll();
	}
	@Autowired
	private org.springframework.data.mongodb.core.MongoTemplate mongoTemplate;
	@GetMapping("/mongo-info")
	public String mongoInfo() {
	    return "Database: " + mongoTemplate.getDb().getName();
	}
	@PostMapping("/insert_into_mongodb")
	public String insert_into_mongodb(@RequestBody book b) {
		int k=apiService.insert_into_mongodb(b);
		if (k>0) return "inserted into mongodb";
		else return "data not inserted";
	}
	@DeleteMapping("/delete_mongodb_data/{id}")
	public String delete_mongodb_data(@PathVariable String id) {
		int k=apiService.delete_mongodb_data(id);
		if(k>0) return "mongodb data deleted";
		else return "mongodb data not deleted";
		
	}
	@PutMapping("/update_mongodb_data/{id}")
	public String update_mongodb_data(@PathVariable String id,@RequestBody book b) {
		int k=apiService.update_mongodb_data(id,b);
		if(k>0) return "mongodb data updated";
		else return "mongodb data not updated";
	}
	@GetMapping("/show_mongodb_data") 
	public List<book> show_mongodb_data(){
		return apiService.show_mongodb_books();
	}
	@GetMapping("/show_mongodb_data/title/{title}")
	public List<book> show_mongodb_data_title(@PathVariable String title){
		return apiService.show_mongodb_data_title(title);
	}
	@GetMapping("/show_mongodb_data/language/{language}")
	public List<book> show_mongodb_data_language(@PathVariable String language){
		return apiService.show_mongodb_data_language(language);
	}
	@GetMapping("/show_mongodb_data/year/{year}")
	public List<book> show_mongodb_data_year(@PathVariable int year){
		return apiService.show_mongodb_data_year(year);
	}
}
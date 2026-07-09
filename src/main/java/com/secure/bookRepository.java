package com.secure;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface bookRepository extends MongoRepository<book,String>{

	List<book> findByTitle(String title);
	List<book> findByLanguage(String language);
//	List<book> findByFirst_publish_year(int year);
	@Query("{'first_publish_year' : ?0 }")
	List<book> findByFirstPublishYear(int firstPublishYear);
}



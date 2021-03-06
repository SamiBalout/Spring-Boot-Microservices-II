package com.stackroute.newz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.newz.model.NewsSource;
import com.stackroute.newz.service.NewsSourceService;
import com.stackroute.newz.service.NewsSourceServiceImpl;
import com.stackroute.newz.util.exception.NewsSourceNotFoundException;

/*
 * As in this assignment, we are working with creating RESTful web service, hence annotate
 * the class with @RestController annotation.A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */
@RestController
public class NewsSourceController {

	/*
	 * Autowiring should be implemented for the NewsService. (Use Constructor-based
	 * autowiring) Please note that we should not create any object using the new
	 * keyword
	 */
	
	@Autowired
	NewsSourceServiceImpl service;
	
	public NewsSourceController(NewsSourceService newsSourceService) {
	
	}
	

	/*
	 * Define a handler method which will create a specific newssource by reading the
	 * Serialized object from request body and save the newssource details in the
	 * database.This handler method should return any one of the status messages
	 * basis on different situations: 
	 * 1. 201(CREATED) - If the newssource created successfully. 
	 * 2. 409(CONFLICT) - If the newssourceId conflicts with any existing user.
	 * 
	 * This handler method should map to the URL "/api/v1/newssource" using HTTP POST method
	 */

	@PostMapping("/api/v1/newssource")
	public ResponseEntity<?> addNewsSource(@RequestBody NewsSource newsSource) {
		
		boolean ns = service.addNewsSource(newsSource);
		//NewsSource checkns = service.getNewsSourceById(, 0)
		
		if(ns) {
			return new ResponseEntity<>(ns, HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>("News Source Already Exists", HttpStatus.CONFLICT);
		}
	}

	/*
	 * Define a handler method which will delete a newssource from a database.
	 * This handler method should return any one of the status messages basis 
	 * on different situations: 
	 * 1. 200(OK) - If the newssource deleted successfully from database. 
	 * 2. 404(NOT FOUND) - If the newssource with specified newsId is not found.
	 *
	 * This handler method should map to the URL "/api/v1/newssource/{newssourceId}" 
	 * using HTTP Delete method where "userId" should be replaced by a valid userId 
	 * without {} and "newssourceId" should be replaced by a valid newsId 
	 * without {}.
	 * 
	 */
	
	@DeleteMapping("/api/v1/newssource/{newsSourceId}")
	public ResponseEntity<?> deleteNewsSource(@PathVariable("newsSourceId") int newsSourceId) {
		
		boolean deleteNS = service.deleteNewsSource(newsSourceId);
		
		if(deleteNS) {
			return new ResponseEntity<>("Successfully Deleted", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("NewsSource Does Not Exist", HttpStatus.NOT_FOUND);
		}
	}
	
	/*
	 * Define a handler method which will update a specific newssource by reading the
	 * Serialized object from request body and save the updated newssource details in a
	 * database. This handler method should return any one of the status messages
	 * basis on different situations: 
	 * 1. 200(OK) - If the newssource updated successfully.
	 * 2. 404(NOT FOUND) - If the newssource with specified newssourceId is not found.
	 * 
	 * This handler method should map to the URL "/api/v1/newssource/{newssourceId}" using 
	 * HTTP PUT method where "newssourceId" should be replaced by a valid newssourceId
	 * without {}.
	 * 
	 */
	
	@PutMapping("/api/v1/newssource/{newsSourceId}")
	public ResponseEntity<?> updateNewsSourceById(@PathVariable("newsSourceId") int newsSourceId, NewsSource newsSource) throws NewsSourceNotFoundException {
		
		NewsSource updateNS = service.updateNewsSource(newsSource, newsSourceId);
		
		if(updateNS != null) {
			return new ResponseEntity<>(updateNS, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("No NewsSource Found", HttpStatus.NOT_FOUND);
		}
		
	}
	
	/*
	 * Define a handler method which will get us the specific newssource by a userId.
	 * This handler method should return any one of the status messages basis on
	 * different situations: 
	 * 1. 200(OK) - If the newssource found successfully. 
	 * 2. 404(NOT FOUND) - If the newssource with specified newsId is not found.
	 * 
	 * This handler method should map to the URL "/api/v1/newssource/{userId}/{newssourceId}" 
	 * using HTTP GET method where "userId" should be replaced by a valid userId 
	 * without {} and "newssourceId" should be replaced by a valid newsId without {}.
	 * 
	 */
	
	@GetMapping("/api/v1/newssource/{userId}/{newssourceId}")
	public ResponseEntity<?> getNewsSourcebyUserId(@PathVariable("userId") String userId, 
			@PathVariable("newssourceId") int newssourceId ) throws NewsSourceNotFoundException {
		
		NewsSource nsById = service.getNewsSourceById(userId, newssourceId);
		
		if(nsById != null) {
			return new ResponseEntity<>(nsById, HttpStatus.OK);
		}
		
		else {
			return new ResponseEntity<>("News Not Found", HttpStatus.NOT_FOUND);
		}
	}
	
	
	/*
	 * Define a handler method which will show details of all newssource created by specific 
	 * user. This handler method should return any one of the status messages basis on
	 * different situations: 
	 * 1. 200(OK) - If the newssource found successfully. 
	 * 2. 404(NOT FOUND) - If the newssource with specified newsId is not found.
	 * This handler method should map to the URL "/api/v1/newssource/{userId}" using HTTP GET method
	 * where "userId" should be replaced by a valid userId without {}.
	 * 
	 */
	
	@GetMapping("/api/v1/newssource/{userId}")
	public ResponseEntity<?> getNewsSourceByUserId(@PathVariable("userId") String userId) {
		
		List<NewsSource> allNS = service.getAllNewsSourceByUserId(userId);
		
		if(allNS != null) {
			return new ResponseEntity<>(allNS, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
		}
		
	}

    
}











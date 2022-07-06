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

import com.stackroute.newz.model.News;
import com.stackroute.newz.model.UserNews;
import com.stackroute.newz.repository.NewsRepository;
import com.stackroute.newz.service.NewsService;
import com.stackroute.newz.service.NewsServiceImpl;
import com.stackroute.newz.util.exception.NewsNotFoundException;

/*
 * As in this assignment, we are working with creating RESTful web service, hence annotate
 * the class with @RestController annotation.A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */

@RestController
public class NewsController {

	/*
	 * Autowiring should be implemented for the NewsService. (Use Constructor-based
	 * autowiring) Please note that we should not create any object using the new
	 * keyword
	 */
	
	@Autowired
	NewsServiceImpl service;
	
	

	public NewsController(NewsService newsService) {
		
	}
	
	

	/*
	 * Define a handler method which will create a specific news by reading the
	 * Serialized object from request body and save the news details in the
	 * database.This handler method should return any one of the status messages
	 * basis on different situations: 
	 * 1. 201(CREATED) - If the news created successfully. 
	 * 2. 409(CONFLICT) - If the newsId conflicts with any existing user.
	 * 
	 * This handler method should map to the URL "/api/v1/news" using HTTP POST method
	 */
	
	@PostMapping("/api/v1/news")
	public ResponseEntity<?> addNews(@RequestBody News news) throws NewsNotFoundException {
		
		//Optional<News> findNews = repo.findById()
		News newsById = service.getNewsByNewsId(news.getUserNews().getUserId(), news.getNewsId());
		
		if(newsById != null) {
			boolean ne = service.addNews(news);
			
			return new ResponseEntity<>("Created", HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>("News Already Exists", HttpStatus.CONFLICT);
		}
		
		
	}

	/*
	 * Define a handler method which will delete a news from a database.
	 * This handler method should return any one of the status messages basis 
	 * on different situations: 
	 * 1. 200(OK) - If the news deleted successfully from database. 
	 * 2. 404(NOT FOUND) - If the news with specified newsId is not found.
	 *
	 * This handler method should map to the URL "/api/v1/news/{userId}/{newsId}" 
	 * using HTTP Delete method where "userId" should be replaced by a valid userId 
	 * without {} and "newsId" should be replaced by a valid newsId 
	 * without {}.
	 * 
	 */
	
	@DeleteMapping("/api/v1/news/{userId}/{newsId}")
	public ResponseEntity<?> deleteNews(@PathVariable("userId") String userId, @PathVariable("newsId") int newsId) {
		
		List<News> unList = service.getAllNewsByUserId(userId);
		
		if(unList != null) {
			for(News ne : unList) {
				if(ne.getNewsId() == newsId) {
					unList.remove(newsId);
					service.deleteNews(userId, newsId);
					return new ResponseEntity<>("News Successfullt Deleted", HttpStatus.OK);
				}
			}
		}
		
	
		return new ResponseEntity<>("News Not Found", HttpStatus.NOT_FOUND);
		
	
		
	}

	/*
	 * Define a handler method which will delete all the news of a specific user from 
	 * a database. This handler method should return any one of the status messages 
	 * basis on different situations: 
	 * 1. 200(OK) - If the newsId deleted successfully from database. 
	 * 2. 404(NOT FOUND) - If the note with specified newsId is not found.
	 *
	 * This handler method should map to the URL "/api/v1/news/{userId}" 
	 * using HTTP Delete method where "userId" should be replaced by a valid userId 
	 * without {} and "newsid" should be replaced by a valid newsId 
	 * without {}.
	 * 
	 */
	
	@DeleteMapping("/api/v1/news/{userId}")
	public ResponseEntity<?> DeleteAllNewsById(@RequestBody News news, @PathVariable("userId") String userId) throws NewsNotFoundException {
		
		List<News> unById = service.getAllNewsByUserId(userId);
		
		if(unById != null) {
			service.deleteAllNews(userId);
			return new ResponseEntity<>("All news deleted", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}
		
	}
	
	/*
	 * Define a handler method which will update a specific news by reading the
	 * Serialized object from request body and save the updated news details in a
	 * database. 
	 * This handler method should return any one of the status messages
	 * basis on different situations: 
	 * 1. 200(OK) - If the news updated successfully.
	 * 2. 404(NOT FOUND) - If the news with specified newsId is not found.
	 * 
	 * This handler method should map to the URL "/api/v1/news/{userId}/{newsId}" using 
	 * HTTP PUT method where "userId" should be replaced by a valid userId 
	 * without {} and "newsid" should be replaced by a valid newsId without {}.
	 * 
	 */
	
	@PutMapping("/api/v1/news/{userId}/{newsId}")
	public ResponseEntity<?> updateNews(@PathVariable("userId") String userId, @PathVariable("newsId") int newsId) throws NewsNotFoundException {
		
		List<News> unList = service.getAllNewsByUserId(userId);
		
		if(unList != null) {
			for(News ne : unList) {
				if(ne.getNewsId() == newsId) {
					service.updateNews(ne, newsId, userId);
					return new ResponseEntity<>("News Updated", HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<>("News Does Not Exist", HttpStatus.NOT_FOUND);
		
	}
	
	/*
	 * Define a handler method which will get us the specific news by a userId.
	 * This handler method should return any one of the status messages basis on
	 * different situations: 
	 * 1. 200(OK) - If the news found successfully. 
	 * 2. 404(NOT FOUND) - If the news with specified newsId is not found.
	 * 
	 * This handler method should map to the URL "/api/v1/news/{userId}/{newsId}" 
	 * using HTTP GET method where "userId" should be replaced by a valid userId 
	 * without {} and "newsid" should be replaced by a valid newsId without {}.
	 * 
	 */
	
	@GetMapping("/api/v1/news/{userId}/{newsId}")
	public ResponseEntity<?> getNewsByUserId(@PathVariable("userId") String userId, @PathVariable("newsId") int newsId) throws NewsNotFoundException {
		
		News ne = service.getNewsByNewsId(userId, newsId);
		
		if(ne != null) {
			return new ResponseEntity<>(ne, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("News does not exist", HttpStatus.NOT_FOUND);
		}
	}
	

	/*
	 * Define a handler method which will show details of all news created by specific 
	 * user. This handler method should return any one of the status messages basis on
	 * different situations: 
	 * 1. 200(OK) - If the news found successfully. 
	 * 2. 404(NOT FOUND) - If the news with specified newsId is not found.
	 * This handler method should map to the URL "/api/v1/news/{userId}" using HTTP GET method
	 * where "userId" should be replaced by a valid userId without {}.
	 * 
	 */
	
	@GetMapping("/api/v1/news/{userId}")
	public ResponseEntity<?> getAllbyUserId(@RequestBody News news, @PathVariable("userId") String userId) {
		
		List<News> ne = service.getAllNewsByUserId(userId);
		
		if(ne != null) {
			return new ResponseEntity<>(ne, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}
		
	}



}

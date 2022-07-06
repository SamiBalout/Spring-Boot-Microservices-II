package com.stackroute.newz.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

/*
 * Please note that this class is annotated with @Document annotation
 * @Document identifies a domain object to be persisted to MongoDB.
 * 
 */
@Component
@Document(collection = "userNews")
public class UserNews {

	/*
	 * This class should have two fields (userId, newslist).Out of these two fields,
	 * the field userId should be annotated with @Id. This class should also contain
	 * the getters and setters for the fields.
	 */

	@Id
	private String userId;
	
	private List<News> newsList = new ArrayList<>();
	
	//Include parameterized constructor
	
	
	
	public UserNews() {
		
	}

	public UserNews(String userId, List<News> newsList) {
		super();
		this.userId = userId;
		this.newsList = newsList;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<News> getNewslist() {
		return newsList;
	}

	public void setNewslist(List<News> newsList) {
		this.newsList = newsList;
	}
	
	
	

	
	

}

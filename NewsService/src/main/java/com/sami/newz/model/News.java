package com.stackroute.newz.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Component
@Document(collection = "news4")
public class News {

	
	/*
	 * This class should have ten fields
	 * (newsId,title,author,description,publishedAt,content,url,urlToImage,Reminder,
	 * NewsSource). This class should also contain the getters and setters for the 
	 * fields along with the no-arg , parameterized	constructor and toString method.
	 * The value of publishedAt should not be accepted from the user but should be
	 * always initialized with the system date.
	 */

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int newsId; 
	private String title;
	private String author; 
	private String description;
	
	//@JsonSerialize(using = ToStringSerializer.class)
	private LocalDate publishedAt = LocalDate.now();
	
	private String content;
	private String url;
	private String urlToImage;
	
	//@ManyToOne
	@JsonIgnore
	private NewsSource newsSource;
	
	//@OneToOne
	//@JsonIgnore
	private Reminder reminder;
	
	private UserNews userNews;
	
	
	/**
	 * @return the newsID
	 */
	
	public News() {
		
		
	}
	


	public News(int newsId, String title, String author, String description, LocalDate publishedAt, String content,
			String url, String urlToImage, NewsSource newsSource, Reminder reminder, UserNews userNews) {
		super();
		this.newsId = newsId;
		this.title = title;
		this.author = author;
		this.description = description;
		this.publishedAt = publishedAt;
		this.content = content;
		this.url = url;
		this.urlToImage = urlToImage;
		this.newsSource = newsSource;
		this.reminder = reminder;
		this.userNews = userNews;
	}




	public int getNewsId() {
		return newsId;
	}


	public void setNewsId(int newsId) {
		this.newsId = newsId;
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


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public LocalDate getPublishedAt() {
		return publishedAt;
	}


	public void setPublishedAt(LocalDate publishedAt) {
		this.publishedAt = publishedAt;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getUrlToImage() {
		return urlToImage;
	}


	public void setUrlToImage(String urlToImage) {
		this.urlToImage = urlToImage;
	}


	public NewsSource getNewssource() {
		return newsSource;
	}


	public void setNewssource(NewsSource newsSource) {
		this.newsSource = newsSource;
	}


	public Reminder getReminder() {
		return reminder;
	}


	public void setReminder(Reminder reminder) {
		this.reminder = reminder;
	}


	public UserNews getUserNews() {
		return userNews;
	}


	public void setUserNews(UserNews userNews) {
		this.userNews = userNews;
	}





}

package com.stackroute.newz.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.stackroute.newz.model.News;
import com.stackroute.newz.model.UserNews;
import com.stackroute.newz.repository.NewsRepository;
import com.stackroute.newz.util.exception.NewsAlreadyExistsException;
import com.stackroute.newz.util.exception.NewsNotFoundException;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn't currently 
* provide any additional behavior over the @Component annotation, but it's a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */

@Service
public class NewsServiceImpl implements NewsService {

	/*
	 * Autowiring should be implemented for the NewsDao and MongoOperation. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */

	@Autowired
	NewsRepository repo;

	/*
	 * This method should be used to save a new news.
	 */
	@Override
	public boolean addNews(News news) {

		// repo.save(news);

		Optional<UserNews> us = repo.findById(news.getAuthor());

		if (us.isPresent()) {
			List<News> usList = us.get().getNewslist();
			usList.add(news);
			repo.insert(news.getUserNews());
			return true;
			
//			for (News ne : usList) {
//				if (ne.getNewsId() != news.getNewsId()) {
//					usList.add(news);
//				}
//			}
//			return true;
		}
		return false;
	}

	/* This method should be used to delete an existing news. */

	public boolean deleteNews(String userId, int newsId) {

		try {

			Optional<UserNews> un = repo.findById(userId);

			if (un.isPresent()) {
				List<News> unList = un.get().getNewslist();

				for (News ne : unList) {
					if (ne.getNewsId() == newsId) {
						unList.remove(ne);
						return true;
					}
				}
				

			}
		} catch (NullPointerException e) {
			throw new NullPointerException(e.toString());
		}
		// throw new NewsNotFoundException("News Not Found For id: " + newsId);
		throw new NullPointerException();

	}

	/* This method should be used to delete all news for a specific userId. */

	public boolean deleteAllNews(String userId) throws NewsNotFoundException {

		try {
			Optional<UserNews> un = repo.findById(userId);

			if (un.isPresent()) {
				repo.deleteById(userId);
				return true;
			}

		} catch (NoSuchElementException e) {
			throw new NewsNotFoundException(e.toString());
		}

		throw new NewsNotFoundException("News Not Found For id: " + userId);

	}

	/*
	 * This method should be used to update a existing news.
	 */

	public News updateNews(News news, int newsId, String userId) throws NewsNotFoundException {

		try {

			Optional<UserNews> un = repo.findById(userId);

			if (un.isPresent()) {
				List<News> unList = un.get().getNewslist();
				News newUpdate = null;

				for (News ne : unList) {
					if (ne.getNewsId() == newsId) {
						ne = news;
						newUpdate = ne;
					}
				}
				return newUpdate;

			}
		} catch (NoSuchElementException e) {
			throw new NewsNotFoundException(e.toString());
		}

		throw new NewsNotFoundException("News not found");

	}

	/*
	 * This method should be used to get a news by newsId created by specific user
	 */

	public News getNewsByNewsId(String userId, int newsId) throws NewsNotFoundException {

		try {
			Optional<UserNews> un = repo.findById(userId);

			News newsById = null;

			if (un.isPresent()) {
				List<News> unList = un.get().getNewslist();

				for (News ne : unList) {
					if (ne.getNewsId() == newsId) {
						newsById = ne;

					}
				}
				return newsById;
			}
		} catch (NoSuchElementException e) {
			throw new NewsNotFoundException(e.toString());
		}

		throw new NewsNotFoundException("");
	}

	/*
	 * This method should be used to get all news for a specific userId.
	 */

	public List<News> getAllNewsByUserId(String userId) {

		Optional<UserNews> un = repo.findById(userId);

		if (un.isPresent()) {

			List<News> unList = un.get().getNewslist();
			return unList;
		}
		return null;
	}

}

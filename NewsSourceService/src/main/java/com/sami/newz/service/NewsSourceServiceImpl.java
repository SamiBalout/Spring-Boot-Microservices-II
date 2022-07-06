package com.stackroute.newz.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.newz.model.NewsSource;
//import com.stackroute.newz.model.UserNews;
import com.stackroute.newz.repository.NewsSourceRepository;
import com.stackroute.newz.util.exception.NewsSourceNotFoundException;

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
public class NewsSourceServiceImpl implements NewsSourceService {

	/*
	 * Autowiring should be implemented for the NewsDao and MongoOperation. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */

	@Autowired
	NewsSourceRepository repo;

	/*
	 * This method should be used to save a newsSource.
	 */

	@Override
	public boolean addNewsSource(NewsSource newsSource) {

		// NewsSource ns = repo.findById(newsSource.getNewsSourceId()).get();
		NewsSource addNews = repo.insert(newsSource);

		if (addNews != null) {
			return true;
		}
		return false;

	}

	/* This method should be used to delete an existing newsSource. */

	@Override
	public boolean deleteNewsSource(int newsSourceId) {

		Optional<NewsSource> ns = repo.findById(newsSourceId);

		if (ns != null) {
			repo.deleteById(newsSourceId);
			return true;
		} else {
			return false;
		}

	}

	/* This method should be used to update an existing newsSource. */

	@Override
	public NewsSource updateNewsSource(NewsSource newsSource, int newsSourceId) throws NewsSourceNotFoundException {

		Optional<NewsSource> ns = repo.findById(newsSourceId);

		if (ns.isPresent()) {
			NewsSource ne = repo.save(newsSource);
			System.out.println(ne);
			return ns.get();
		} else {
			throw new NewsSourceNotFoundException("NewsSource Id: " + newsSourceId + "does not exist");
		}
	}

	/* This method should be used to get a specific newsSource for an user. */

	@Override
	public NewsSource getNewsSourceById(String userId, int newsSourceId) throws NewsSourceNotFoundException {

		try {
			List<NewsSource> nsList = repo.findAllNewsSourceByNewsSourceCreatedBy(userId);

			if (nsList != null) {
				for (NewsSource ns : nsList) {
					if (ns.getNewsSourceId() == newsSourceId) {
						return ns;
					}

				}

			}
		} catch (NoSuchElementException e) {
			//throw new NewsSourceNotFoundException(e.toString());
			return null;
		}

		throw new NewsSourceNotFoundException("");

	}

	/* This method should be used to get all newsSource for a specific userId. */

	@Override
	public List<NewsSource> getAllNewsSourceByUserId(String createdBy) {

		List<NewsSource> ns = repo.findAllNewsSourceByNewsSourceCreatedBy(createdBy);

		if (!ns.isEmpty()) {
			return ns;
		} else {
			return null;
		}

	}

}

package com.stackroute.newz.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.stackroute.newz.model.News;
import com.stackroute.newz.model.Newssource;
import com.stackroute.newz.model.UserNews;
import com.stackroute.newz.repository.NewsRepository;
import com.stackroute.newz.util.exception.NewsAlreadyExistsException;
import com.stackroute.newz.util.exception.NewsNotFoundExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsServiceImpl implements NewsService {
	private final NewsRepository newsRepository;
	public NewsServiceImpl(NewsRepository newsRepository){
		this.newsRepository=newsRepository;
	}

	@Override
	public boolean addNews(News news) {
		Optional<UserNews> userNewsOptional = newsRepository.findById(news.getAuthor());
		UserNews userN;
		if(userNewsOptional.isPresent()) {
			userN = userNewsOptional.get();
			List<News> list = userN.getNewslist();
			if(!list.isEmpty() && list != null) {
				Optional<News> any = list.stream().filter(p -> p.getNewsId() == news.getNewsId())
						.findFirst();
				if (any.isPresent()){
					return false;
				}
				list.add(news);
				userN.setNewslist(list);
				newsRepository.save(userN);
				return true;
			}
			//in the case if author doesn t exists
		} else {
			userN = new UserNews(news.getAuthor(), List.of(news));
			UserNews userNews = newsRepository.insert(userN);
			if(userNews == null) {
				return false;
			}
		}
		return true;
	}

	/* This method should be used to delete an existing news. */

	public boolean deleteNews(String userId, int newsId) {
		boolean newsDeleted = true;
		Optional<UserNews> userNews =newsRepository.findById(userId);
		if(userNews.isEmpty())
		{
			newsDeleted = false;
		}
		else {
			if(userNews.get().getNewslist().isEmpty())
			{
				newsDeleted = false;
			}
			else {
				UserNews savedUserNews = userNews.get();

				List<News> newsList = savedUserNews.getNewslist();
				newsList.removeIf( userNews2-> userNews2.getNewsId() == newsId);
				savedUserNews.setNewslist(newsList);
				newsRepository.save(savedUserNews);
			}
		}
		return newsDeleted;
	}

	/* This method should be used to delete all news for a  specific userId. */

	public boolean deleteAllNews(String userId) throws NewsNotFoundExeption {
		UserNews user;
		try {
			Optional<UserNews> existUser = newsRepository.findById(userId);
			user = existUser.get();
		} catch (NoSuchElementException e) {
			throw new NewsNotFoundExeption("The user with userId= " + userId + ", doesn't have any news");
		}
		user.setNewslist(new ArrayList<>());
		newsRepository.save(user);
		return true;
	}

	/*
	 * This method should be used to update a existing news.
	 */

	public News updateNews(News news, int newsId, String userId) throws NewsNotFoundExeption {
		UserNews foundUser;
		try {
			Optional<UserNews> optUser = newsRepository.findById(userId);
			foundUser = optUser.get();
		} catch (NoSuchElementException e) {
			throw new NewsNotFoundExeption("The user with userId= " + userId + ", doesn't exists");
		}

		List<News> newsList = foundUser.getNewslist();
		Optional<News> foundNews = newsList.stream().filter(news1 -> news1.getNewsId() == newsId).findFirst();
		if (foundNews.isPresent()) {
			newsList.removeIf(n -> n.getNewsId() == newsId);
			newsList.add(news);
			foundUser.setNewslist(newsList);
			newsRepository.save(foundUser);

			return news;
		}
		throw new NewsNotFoundExeption("The news with Id= " + newsId + ", doesn't exists");
	}


	/*
	 * This method should be used to get a news by newsId created by specific user
	 */

	public News getNewsByNewsId(String userId, int newsId) throws NewsNotFoundExeption {
		UserNews foundUser;
		try {
			Optional<UserNews> optUser = newsRepository.findById(userId);
			foundUser = optUser.get();
		} catch (NoSuchElementException e) {
			throw new NewsNotFoundExeption("The user with userId= " + userId + ", doesn't have any news");
		}

		Optional<News> foundNews = foundUser.getNewslist().stream().filter(n -> n.getNewsId() == newsId).findFirst();

//    If a value is present in curent optional foundNews, returns the value, otherwise returns other.
		return foundNews.orElseThrow(() -> new NewsNotFoundExeption("The news with Id= " + newsId + ", doesn't exists"));
	}
	/*
	 * This method should be used to get all news for a specific userId.
	 */

	public List<News> getAllNewsByUserId(String userId) {
		Optional<UserNews> foundUser = newsRepository.findById(userId);
		if (foundUser.isPresent()){
			return foundUser.get().getNewslist();
		}else {
			return null;
		}
	}

}

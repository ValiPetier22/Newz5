package com.stackroute.newz.service;

import com.stackroute.newz.model.NewsSource;
import com.stackroute.newz.repository.NewsSourceRepository;
import com.stackroute.newz.util.exception.NewsSourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
     * Autowiring should be implemented for the NewsDao and MongoOperation.
     * (Use Constructor-based autowiring) Please note that we should not create any
     * object using the new keyword.
     */
    private final NewsSourceRepository newsSourceRepository;

    public NewsSourceServiceImpl(NewsSourceRepository newsSourceRepository) {
        this.newsSourceRepository = newsSourceRepository;
    }

    /*
     * This method should be used to save a newsSource.
     */

    @Override
    public boolean addNewsSource(NewsSource newsSource) {
        if (newsSourceRepository.findById(newsSource.getNewsSourceId()).isPresent())
            return false;
        NewsSource savedNewsSource = newsSourceRepository.insert(newsSource);
        if (savedNewsSource != null) {
            return true;
        } else {
            return false;
        }
    }

    /* This method should be used to delete an existing newsSource. */

    @Override
    public boolean deleteNewsSource(int newsSourceId) {
        boolean response = true;
        if (newsSourceRepository.findById(newsSourceId) != null) {
            newsSourceRepository.deleteById(newsSourceId);
            return response;
        } else {
            response = false;
            return response;
        }
    }

    /* This method should be used to update an existing newsSource. */

    @Override
    public NewsSource updateNewsSource(NewsSource newsSource, int newsSourceId) throws NewsSourceNotFoundException {
        Optional<NewsSource> existNewsSource = newsSourceRepository.findById(newsSourceId);
        if (existNewsSource.isEmpty()) {
            throw new NewsSourceNotFoundException("News Source not found");
        } else {
            newsSourceRepository.save(newsSource);
            return newsSource;
        }
    }

    /* This method should be used to get a specific newsSource for an user. */

    @Override
    public NewsSource getNewsSourceById(String userId, int newsSourceId) throws NewsSourceNotFoundException {
        NewsSource result;
        try {
            List<NewsSource> newsSourceList =
                    newsSourceRepository.findAllNewsSourceByNewsSourceCreatedBy(userId);
            if (newsSourceList.isEmpty()) {
                throw new NewsSourceNotFoundException("News not found for user with this id");
            } else {
                Optional<NewsSource> existNewsSource = newsSourceList
                        .stream()
                        .filter(p -> p.getNewsSourceId() == newsSourceId)
                        .findAny();
                if (existNewsSource.isPresent()) {
                    result = existNewsSource.get();
                } else {
                    throw new NewsSourceNotFoundException("Not found any news by userId");
                }
            }
        } catch (NoSuchElementException e) {
            result = null;
        }
        return result;
    }



    /* This method should be used to get all newsSource for a specific userId.*/

    @Override
    public List<NewsSource> getAllNewsSourceByUserId(String createdBy) {
        return newsSourceRepository.findAllNewsSourceByNewsSourceCreatedBy(createdBy);
    }

}

package com.stackroute.newz.controller;

import com.stackroute.newz.model.NewsSource;
import com.stackroute.newz.service.NewsSourceService;
import com.stackroute.newz.util.exception.NewsSourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * As in this assignment, we are working with creating RESTful web service, hence annotate
 * the class with @RestController annotation.A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized
 * format. Starting from Spring 4 and above, we can use @RestController annotation which
 * is equivalent to using @Controller and @ResposeBody annotation
 */
@RestController
@RequestMapping("/api/v1")
public class NewsSourceController {

	/*
	 * Autowiring should be implemented for the NewsService. (Use Constructor-based
	 * autowiring) Please note that we should not create any object using the new
	 * keyword
	 */
	private NewsSourceService newsSourceService;
	public NewsSourceController(NewsSourceService newsSourceService) {
		this.newsSourceService=newsSourceService;
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

	@PostMapping("/newssource")
	public ResponseEntity<?> addNewsSource(@RequestBody NewsSource newsSource) {

		boolean added = newsSourceService.addNewsSource(newsSource);
		if (added) {
			return new ResponseEntity<String>("News was added", HttpStatus.CREATED);
		}
		return new ResponseEntity<String>("fail to add", HttpStatus.CONFLICT);
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
	@DeleteMapping("/newssource/{newssourceId}")
	public ResponseEntity<?> deleteNewsSource(@PathVariable("newssourceId") int newssourceId){
		if(newsSourceService.deleteNewsSource(newssourceId)){
			return new ResponseEntity<String>("delete success",HttpStatus.OK);
		}
		return new ResponseEntity<String>("delete failure",HttpStatus.NOT_FOUND);
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
	@PutMapping("/newssource/{newssourceId}")
	public ResponseEntity<?> updateNewsService(@PathVariable("newssourceId") int newsSourceId,
											   @RequestBody NewsSource newsSource){
		try{
			newsSourceService.updateNewsSource(newsSource,newsSourceId);
			return new ResponseEntity<String>("updated",HttpStatus.OK);
		}catch(NewsSourceNotFoundException e){
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
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
	@GetMapping("/newssource/{userId}/{newssourceId}")
	public ResponseEntity<?> getNewsSourceById(@PathVariable("userId") String userId,
											   @PathVariable("newssourceId") int newssourceId){
		try {
			newsSourceService.getNewsSourceById(userId,newssourceId);
			return new ResponseEntity<String>("user found", HttpStatus.OK);
		} catch (NewsSourceNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
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
	@GetMapping("/newssource/{userId}")
	public ResponseEntity<?> getAllNewsSourceByUserId(@PathVariable("userId") String userId){
		List<NewsSource> list= newsSourceService.getAllNewsSourceByUserId(userId);
		if(list!=null && !list.isEmpty()){
			return new ResponseEntity<List<NewsSource>>(list, HttpStatus.OK);
		}
		return new ResponseEntity<String>("NewsSource not found", HttpStatus.NOT_FOUND);
	}

}

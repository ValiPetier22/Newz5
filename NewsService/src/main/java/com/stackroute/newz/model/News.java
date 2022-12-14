package com.stackroute.newz.model;

import java.time.Instant;
import java.util.Date;

public class News {


	/*
	 * This class should have ten fields
	 * (newsId,title,author,description,publishedAt,content,url,urlToImage,Reminder,
	 * Newssource). This class should also contain the getters and setters for the
	 * fields along with the no-arg , parameterized    constructor and toString method.
	 * The value of publishedAt should not be accepted from the user but should be
	 * always initialized with the system date.
	 */

	private int newsId;
	private String title;
	private String author;
	private String description;
	private Date publishedAt;
	private String content;
	private String url;
	private String urlToImage;
	private Reminder reminder;
	private Newssource newssource;

	public News(int newsId, String title, String author, String description, Date publishedAt, String content,
				String url, String urlToImage, Reminder reminder, Newssource newssource) {
		this.newsId = newsId;
		this.title = title;
		this.author = author;
		this.description = description;
		this.publishedAt = Date.from(Instant.now());
		this.content = content;
		this.url = url;
		this.urlToImage = urlToImage;
		this.reminder = reminder;
		this.newssource = newssource;
	}

	public News() {}

	public Reminder getReminder() {
		return reminder;
	}

	public void setReminder(Reminder reminder) {
		this.reminder = reminder;
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

	public Date getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt() {
		this.publishedAt = Date.from(Instant.now());
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

	public Newssource getNewssource() {
		return newssource;
	}

	public void setNewssource(Newssource newssource) {
		this.newssource = newssource;
	}


	@Override
	public String toString() {
		return "News{" +
				"newsId=" + newsId +
				", title='" + title + '\'' +
				", author='" + author + '\'' +
				", description='" + description + '\'' +
				", publishedAt=" + publishedAt +
				", content='" + content + '\'' +
				", url='" + url + '\'' +
				", urlToImage='" + urlToImage + '\'' +
				", reminder=" + reminder +
				", newssource=" + newssource +
				'}';
	}
}
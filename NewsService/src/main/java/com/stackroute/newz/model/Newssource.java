package com.stackroute.newz.model;

import java.time.Instant;
import java.util.Date;

public class Newssource {

	/*
	 * This class should have five fields
	 * (newssourceId,newssourceName,newssourceDesc,newssourceCreatedBy,newssourceCreationDate).
	 * This class should also contain the getters and setters for the
	 * fields along with the parameterized constructor and toString method.
	 * The value of newssourceCreationDate should not be accepted from the user but should be
	 * always initialized with the system date.
	 */

	private String newssourceId;
	private String newssourceName;
	private String newssourceDesc;
	private String newssourceCreatedBy;
	private Date newssourceCreationDate;

	public String getNewssourceId() {
		return newssourceId;
	}

	public Newssource() {}

	public Newssource(String newssourceId, String newssourceName, String newssourceDesc, String newssourceCreatedBy,
					  Date newssourceCreationDate) {
		this.newssourceId = newssourceId;
		this.newssourceName = newssourceName;
		this.newssourceDesc = newssourceDesc;
		this.newssourceCreatedBy = newssourceCreatedBy;
		this.newssourceCreationDate = Date.from(Instant.now());
	}

	public void setNewssourceId(String newssourceId) {
		this.newssourceId = newssourceId;
	}

	public String getNewssourceName() {
		return newssourceName;
	}

	public void setNewssourceName(String newssourceName) {
		this.newssourceName = newssourceName;
	}

	public String getNewssourceDesc() {
		return newssourceDesc;
	}

	public void setNewssourceDesc(String newssourceDesc) {
		this.newssourceDesc = newssourceDesc;
	}

	public String getNewssourceCreatedBy() {
		return newssourceCreatedBy;
	}

	public void setNewssourceCreatedBy(String newssourceCreatedBy) {
		this.newssourceCreatedBy = newssourceCreatedBy;
	}

	public Date getNewssourceCreationDate() {
		return newssourceCreationDate;
	}

	public void setNewssourceCreationDate() {
		this.newssourceCreationDate = Date.from(Instant.now());
	}

	@Override
	public String toString() {
		return "Newssource{" +
				"newssourceId='" + newssourceId + '\'' +
				", newssourceName='" + newssourceName + '\'' +
				", newssourceDesc='" + newssourceDesc + '\'' +
				", newssourceCreatedBy='" + newssourceCreatedBy + '\'' +
				", newssourceCreationDate=" + newssourceCreationDate +
				'}';
	}
}
package domain;

import java.util.Date;

public class Tweet {
	private String idTweet;
	private String status;
	private String userTweet;
	private Date dateTime;

	public Tweet() {
	}

	public Tweet(String status, Date dateTime) {
		this.status = status;
		this.dateTime = dateTime;
	}

	public String getIdTweet() {
		return idTweet;
	}

	public void setIdTweet(String idTweet) {
		this.idTweet = idTweet;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getUserTweet() {
		return userTweet;
	}

	public void setUserTweet(String userTweet) {
		this.userTweet = userTweet;
	}
}

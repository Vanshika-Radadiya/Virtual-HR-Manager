package com.vanshika.springboot.model;

public class CandidateScoreResponse {
	private final int userId;
	private final String username;
	private final String emailId;
	private final int score;
	private final boolean hasResult;
	private final boolean completed;
	private final int oops;
	private final int os;
	private final int dbms;

	public CandidateScoreResponse(int userId, String username, String emailId, int score,
			boolean hasResult, boolean completed, int oops, int os, int dbms) {
		this.userId = userId;
		this.username = username;
		this.emailId = emailId;
		this.score = score;
		this.hasResult = hasResult;
		this.completed = completed;
		this.oops = oops;
		this.os = os;
		this.dbms = dbms;
	}

	public int getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public String getEmailId() {
		return emailId;
	}

	public int getScore() {
		return score;
	}

	public boolean isHasResult() {
		return hasResult;
	}

	public boolean isCompleted() {
		return completed;
	}

	public int getOops() {
		return oops;
	}

	public int getOs() {
		return os;
	}

	public int getDbms() {
		return dbms;
	}
}

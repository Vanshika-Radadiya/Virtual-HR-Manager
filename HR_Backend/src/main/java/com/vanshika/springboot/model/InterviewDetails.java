package com.vanshika.springboot.model;

import java.time.LocalDateTime;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "interview_details")
public class InterviewDetails {
	@EmbeddedId
	private CompId compId;
	private int reportId;
	private LocalDateTime startDate;

	private LocalDateTime endDate;

	protected InterviewDetails() {
	}

	public InterviewDetails(CompId compId, LocalDateTime startDate) {
		this.compId = compId;
		this.startDate = startDate;
	}

	public CompId getCompId() {
		return compId;
	}

	public void setCompId(CompId compId) {
		this.compId = compId;
	}

	public int getInterviewId() {
		return compId.getInterviewId();
	}

	public int getUserId() {
		return compId.getUserId();
	}

	public int getReportId() {
		return reportId;
	}

	public void setReportId(int reportId) {
		this.reportId = reportId;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
}

package com.vanshika.springboot.model;

import java.io.Serializable;

import org.hibernate.annotations.IdGeneratorType;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;



@Entity(name="Result")
//@IdClass(CompId.class)

public class Result {
	@EmbeddedId
	private CompId compId;
	private int oops;
	private int os;
	private int dbms;
		
	public Result() {
		super();
	}
	
	public Result(CompId compId, int oops, int os, int dbms) {
		super();
		this.compId = compId;
		this.oops = oops;
		this.os = os;
		this.dbms = dbms;
	}
	
	public CompId getCompId() {
		return compId;
	}
	public void setCompId(CompId compId) {
		this.compId = compId;
	}
	public int getOops() {
		return oops;
	}
	public void setOops(int oops) {
		this.oops = oops;
	}
	public int getOs() {
		return os;
	}
	public void setOs(int os) {
		this.os = os;
	}
	public int getDbms() {
		return dbms;
	}
	public void setDbms(int dbms) {
		this.dbms = dbms;
	}
	
	
	
	

	
	
}

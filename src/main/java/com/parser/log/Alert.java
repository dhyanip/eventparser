package com.parser.log;

import javax.persistence.Entity;
import javax.persistence.Id;

/*
 * Entity class to hold the event information
 */
@Entity
public class Alert {
	
	@Id
    private String id;
    private String type;
    private String host;
    private String processTime;
    
    
    public Alert() {}

	public Alert(String id, String type, String host) {
		super();
		this.id = id;
		this.type = type;
		this.host = host;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getProcessTime() {
		return processTime;
	}

	public void setProcessTime(String processTime) {
		this.processTime = processTime;
	}

	@Override
	public String toString() {
		return "Alert [id=" + id + ", type=" + type + ", host=" + host + ", processTime=" + processTime + "]";
	}
	
	

}

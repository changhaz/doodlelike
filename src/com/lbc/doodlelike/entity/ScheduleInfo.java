package com.lbc.doodlelike.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ScheduleInfo {
	public String email;
	public String name;
	public String event;
	public String location;
	public String[] time;

	public ScheduleInfo() {
	}

	public ScheduleInfo(String event, String location) {
		this.event = event;
		this.location = location;
	}

	public String toString() {
		return "{ 'event': '" + event + "', 'location': '" + location + "'}";
	}

}

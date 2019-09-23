package com.stackroute.datamunger.query.parser;
/*
 * This class is used for storing name of field, condition and value for
 * each conditions
 * generate getter and setter for this class,
 * Also override toString method
 * */
public class Restriction {
	// Write logic for constructor
	private String name;
	private String value;
	private String condition;
	public Restriction(String name, String value, String condition) {
		this.name=name;
		this.value=value;
		this.condition=condition;
		System.out.println(name+" "+value+" "+condition);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	@Override
	public String toString() {
		String temp=this.name+" "+this.value+" "+this.condition;
		return temp;
	}
}
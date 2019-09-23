package com.stackroute.datamunger.query.parser;

import java.util.List;

/* 
 * This class will contain the elements of the parsed Query String such as conditions,
 * logical operators,aggregate functions, file name, fields group by fields, order by
 * fields, Query Type
 * */

public class QueryParameter {
	private String fileName;
	private String baseQuery;
	private List<Restriction> restriction;
	private List<String> fields;
	private List<String> logicalOperators;
	private List<AggregateFunction> aggregateFunctions;
	private List<String> orderByField;
	private List<String> groupByField;

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setBaseQuery(String baseQuery) {
		this.baseQuery = baseQuery;
	}

	public void setRestriction(List<Restriction> restriction) {
		this.restriction = restriction;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	public void setLogicalOperators(List<String> logicalOperators) {
		this.logicalOperators = logicalOperators;
	}

	public void setAggregateFunctions(List<AggregateFunction> aggregateFunctions) {
		this.aggregateFunctions = aggregateFunctions;
	}

	public void setOrderByField(List<String> orderByField) {
		this.orderByField = orderByField;
	}

	public void setGroupByField(List<String> groupByField) {
		this.groupByField = groupByField;
	}

	public String getFileName() {
		return fileName;
	}

	public String getBaseQuery() {
		return baseQuery;
	}

	public List<Restriction> getRestrictions() {
		return restriction;
	}

	public List<String> getLogicalOperators() {
		return logicalOperators;
	}

	public List<String> getFields() {
		return fields;
	}

	public List<AggregateFunction> getAggregateFunctions() {
		return aggregateFunctions;
	}

	public List<String> getGroupByFields() {
		return groupByField;
	}

	public List<String> getOrderByFields() {
		return orderByField;
	}
}
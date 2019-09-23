package com.stackroute.datamunger.query.parser;

/*There are total 4 DataMungerTest file:
 * 
 * 1)DataMungerTestTask1.java file is for testing following 4 methods
 * a)getBaseQuery()  b)getFileName()  c)getOrderByClause()  d)getGroupByFields()
 * 
 * Once you implement the above 4 methods,run DataMungerTestTask1.java
 * 
 * 2)DataMungerTestTask2.java file is for testing following 2 methods
 * a)getFields() b) getAggregateFunctions()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask2.java
 * 
 * 3)DataMungerTestTask3.java file is for testing following 2 methods
 * a)getRestrictions()  b)getLogicalOperators()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask3.java
 * 
 * Once you implement all the methods run DataMungerTest.java.This test case consist of all
 * the test cases together.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueryParser {

	private QueryParameter queryParameter = new QueryParameter();

	/*
	 * This method will parse the queryString and will return the object of
	 * QueryParameter class
	 */
	public QueryParameter parseQuery(String queryString) {
		queryParameter.setFileName(getFileName(queryString));
		queryParameter.setBaseQuery(getBaseQuery(queryString));
		queryParameter.setFields(getFields(queryString));
		queryParameter.setGroupByField(getGroupByFields(queryString));
		queryParameter.setOrderByField(getOrderByFields(queryString));
		queryParameter.setLogicalOperators(getLogicalOperators(queryString));
		queryParameter.setAggregateFunctions(getAggregateFunctions(queryString));
		queryParameter.setRestriction(getRestrictions(queryString));
		return queryParameter;
	}



	/*
	 * Extract the name of the file from the query. File name can be found after the
	 * "from" clause.
	 */
	public String getFileName(String queryString) {
		queryString=queryString.toLowerCase();
		String[] split1= queryString.split(" ");
		int i;
		for(i=0;i<split1.length;i++){
			if(split1[i].equals("from")){
				break;
			}
		}
		String res=split1[i+1];
		return res;

	}

	/*
	 * 
	 * Extract the baseQuery from the query.This method is used to extract the
	 * baseQuery from the query string. BaseQuery contains from the beginning of the
	 * query till the where clause
	 */

	public String getBaseQuery(String queryString) {
		queryString=queryString.toLowerCase();
		String[] split1= queryString.split(" ");
		int i=0;
		for(i=0;i<split1.length;i++){
			if(split1[i].equals("where") || split1[i].equals("group")){
				break;
			}
		}
		String res="";
		for(int j=0;j<i;j++){
			res=res+split1[j];
			if(j!=i-1){
				res=res+" ";
			}
		}

		return res;
	}

	/*
	 * extract the order by fields from the query string. Please note that we will
	 * need to extract the field(s) after "order by" clause in the query, if at all
	 * the order by clause exists. For eg: select city,winner,team1,team2 from
	 * data/ipl.csv order by city from the query mentioned above, we need to extract
	 * "city". Please note that we can have more than one order by fields.
	 */
	public List<String> getOrderByFields(String queryString) {
		queryString=queryString.toLowerCase();
		if(queryString.contains("order")) {
			String[] split1 = queryString.split(" ");
			int i;
			for (i = 0; i < split1.length; i++) {
				if (split1[i].equals("order") && split1[i + 1].equals("by")) {
					break;
				}
			}
			String res = ""+split1[i+2];

			String[] split2 = res.split(",");
			List<String> list1=Arrays.asList(split2);
			return list1;
		}else{
			return null;
		}
	}
	/*
	 * Extract the group by fields from the query string. Please note that we will
	 * need to extract the field(s) after "group by" clause in the query, if at all
	 * the group by clause exists. For eg: select city,max(win_by_runs) from
	 * data/ipl.csv group by city from the query mentioned above, we need to extract
	 * "city". Please note that we can have more than one group by fields.
	 */
	public List<String> getGroupByFields(String queryString) {
		queryString=queryString.toLowerCase();
		if(queryString.contains("group")) {
			String[] split1 = queryString.split(" ");
			int i;
			for (i = 0; i < split1.length; i++) {
				if (split1[i].equals("group") && split1[i + 1].equals("by")) {
					break;
				}
			}

			String res = ""+split1[i+2];
			String[] split2 = res.split(",");
			List<String> list1=Arrays.asList(split2);
			return list1;
		}else{
			return null;
		}
	}
	/*
	 * Extract the selected fields from the query string. Please note that we will
	 * need to extract the field(s) after "select" clause followed by a space from
	 * the query string. For eg: select city,win_by_runs from data/ipl.csv from the
	 * query mentioned above, we need to extract "city" and "win_by_runs". Please
	 * note that we might have a field containing name "from_date" or "from_hrs".
	 * Hence, consider this while parsing.
	 */
	public List<String> getFields(String queryString) {
		queryString=queryString.toLowerCase();
		String[] split1= queryString.split(" ");
		String res=split1[1];
		String[] resArr=res.split(",");
		List<String> list1=Arrays.asList(resArr);
		return list1;
	}

	/*
	 * Extract the conditions from the query string(if exists). for each condition,
	 * we need to capture the following: 1. Name of field 2. condition 3. value
	 * 
	 * For eg: select city,winner,team1,team2,player_of_match from data/ipl.csv
	 * where season >= 2008 or toss_decision != bat
	 * 
	 * here, for the first condition, "season>=2008" we need to capture: 1. Name of
	 * field: season 2. condition: >= 3. value: 2008
	 * 
	 * the query might contain multiple conditions separated by OR/AND operators.
	 * Please consider this while parsing the conditions.
	 * 
	 */
	public List<Restriction> getRestrictions(String queryString) {
		if(queryString==null)
			return null;
		//queryString=queryString.toLowerCase();
		String conditionString="";
		if(queryString.contains("where"))
		{
			conditionString=queryString.split("where|group|order")[1];
		}
		if(conditionString.length()==0)
			return null;
		String []conditionWithOperator=conditionString.split(" and | or ");
		Restriction restriction=null;
		List<Restriction> restrictionList=new ArrayList<>();
		for(int index=0;index<conditionWithOperator.length;index++)
		{
			if(conditionWithOperator[index].contains("="))
			{
				String []temp=conditionWithOperator[index].split("=");
				restriction=new Restriction(temp[0].trim(),temp[1].trim().replaceAll("'",""),"=".trim());
				restrictionList.add(restriction);
			}
			else if(conditionWithOperator[index].contains(">"))
			{
				String []temp=conditionWithOperator[index].split(">");
				restriction=new Restriction(temp[0].trim(),temp[1].trim(),">".trim());
				restrictionList.add(restriction);
			}
			else if(conditionWithOperator[index].contains("<"))
			{
				String []temp=conditionWithOperator[index].split("<");
				restriction=new Restriction(temp[0].trim(),temp[1].trim(),"<".trim());
				restrictionList.add(restriction);
			}
			else if(conditionWithOperator[index].contains(">="))
			{
				String []temp=conditionWithOperator[index].split(">=");
				restriction=new Restriction(temp[0].trim(),temp[1].trim(),">=".trim());
				restrictionList.add(restriction);
			}
			else if(conditionWithOperator[index].contains("<="))
			{
				String []temp=conditionWithOperator[index].split("<=");
				restriction=new Restriction(temp[0].trim(),temp[1].trim(),"<=".trim());
				restrictionList.add(restriction);
			}
		}
		return restrictionList;
	}
	/*
	 * Extract the logical operators(AND/OR) from the query, if at all it is
	 * present. For eg: select city,winner,team1,team2,player_of_match from
	 * data/ipl.csv where season >= 2008 or toss_decision != bat and city =
	 * bangalore
	 * 
	 * The query mentioned above in the example should return a List of Strings
	 * containing [or,and]
	 */
	public ArrayList<String> getLogicalOperators(String queryString) {
		if(queryString==null)
		{
			return null;
		}
		queryString=queryString.toLowerCase();
		String []wordArray=queryString.split(" ");
		List<String> logicalOperator=new ArrayList<String>();
		for(int index=0;index<wordArray.length;index++)
		{
			if(wordArray[index].equals("and") || wordArray[index].equals("or"))
			{
				logicalOperator.add(wordArray[index]);
			}
		}
		if(logicalOperator.isEmpty())
			return null;
		return (ArrayList<String>) logicalOperator;
	}



	/*
	 * Extract the aggregate functions from the query. The presence of the aggregate
	 * functions can determined if we have either "min" or "max" or "sum" or "count"
	 * or "avg" followed by opening braces"(" after "select" clause in the query
	 * string. in case it is present, then we will have to extract the same. For
	 * each aggregate functions, we need to know the following: 1. type of aggregate
	 * function(min/max/count/sum/avg) 2. field on which the aggregate function is
	 * being applied.
	 * 
	 * Please note that more than one aggregate function can be present in a query.
	 * 
	 * 
	 */
	public List<AggregateFunction> getAggregateFunctions(String queryString) {
		if(queryString==null)
		{
			return null;
		}
		queryString=queryString.toLowerCase();
		String []wordArray=queryString.split(" ");
		boolean isFunction=false;
		for(int index=0;index<wordArray.length;index++)
		{
			if(wordArray[index].contains("min") || wordArray[index].contains("max") || wordArray[index].contains("sum") ||wordArray[index].contains("count") || wordArray[index].contains("avg"))
			{
				isFunction=true;
				break;
				//aggregateFunctionString=aggregateFunctionString+wordArray[index]+" ";
			}
		}
		if(!isFunction)
		{
			return null;
		}
		String []temp=wordArray[1].split(",");
		String temp1="";
		for(int index=0;index<temp.length;index++)
		{
			if(temp[index].contains("min") || temp[index].contains("max") || temp[index].contains("sum") ||temp[index].contains("count") || temp[index].contains("avg"))
			{
				temp1=temp1+temp[index]+" ";
			}
		}
		String []aggregateFunction=temp1.trim().split(" ");
		List<AggregateFunction> aggregateFunctionList = new ArrayList<>();
		AggregateFunction aggregateFunction1;
		for(int index=0;index<aggregateFunction.length;index++)
		{
			String []functionField=aggregateFunction[index].split("\\(|\\)");
			aggregateFunction1=new AggregateFunction(functionField[1],functionField[0]);
			aggregateFunctionList.add(aggregateFunction1);
		}
		return aggregateFunctionList;
	}


}
package org.datastorm.controller;

import junit.framework.TestCase;

public class SqlParserTest extends TestCase {
final String selectQry1 = "SELECT * FROM A";
final String selectQry2 = "SELECT * FROM B";

public void test_split_valid_sql_query_without_semicolon() {
	ISqlParser parser = new SqlParser();
	String[] result = parser.splitTextIntoSqlQueries(selectQry1);
	assertEquals(1, result.length);
	assertEquals(selectQry1, result[0]);
}

public void test_split_valid_sql_query() {
	ISqlParser parser = new SqlParser();
	String[] result = parser.splitTextIntoSqlQueries(selectQry1 + "; \n \n");
	assertEquals(1, result.length);
	assertEquals(selectQry1, result[0]);
}

public void test_split_2_valid_sql_query() {
	ISqlParser parser = new SqlParser();
	String[] result = parser.splitTextIntoSqlQueries(selectQry1 + ";" + selectQry2);
	assertEquals(2, result.length);
	assertEquals(selectQry1, result[0]);
	assertEquals(selectQry2, result[1]);
}
}

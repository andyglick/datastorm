package org.datastorm.controller;

import java.util.ArrayList;
import java.util.List;

public class SqlParser implements ISqlParser {

public String[] splitTextIntoSqlQueries(String text) {
	String[] potentialQueries = text.split(";");
	List queries = new ArrayList();
	for( int i = 0; i < potentialQueries.length; i++ ) {
		if( isWhiteSpace(potentialQueries[i]) == false ) {
			queries.add(potentialQueries[i]);
		}
	}
	
	return (String[]) queries.toArray(new String[queries.size()]);
}

private boolean isWhiteSpace(String string) {
	for( int i = 0; i < string.length(); i++ ) {
		if( Character.isWhitespace(string.charAt(i)) == false ) {
			return false;
		}
	}
	return true;
}

}

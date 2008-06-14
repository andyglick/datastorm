package org.datastorm.controller;

public interface ISqlParser {
String[] splitTextIntoSqlQueries(String text);
}

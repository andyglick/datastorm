package org.datastorm.controller.command;

public class ShowQueryCommand implements ICommand {
public String sql;

public ShowQueryCommand(String sql) {
	this.sql = sql;
}
}

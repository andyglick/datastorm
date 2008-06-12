package org.datastorm.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.datastorm.controller.command.ICommand;
import org.datastorm.controller.command.SetEditorTextCommand;
import org.datastorm.controller.command.ShowQueryCommand;
import org.datastorm.gui.swt.View;
import org.eclipse.swt.widgets.Shell;

public class Controller implements IController {
private final Connection conn;
private View view;
private final ISqlParser sqlParser;
private boolean stopApplication = false;

public Controller(Connection connection) {
	this.conn = connection;
	sqlParser = new SqlParser();
}

public void stopApplication() {
	stopApplication = true;
}

public void startUp(ICommand[] commands) throws SQLException {
	final Shell mainWindow = view.buildScreen();
	handleCommand(commands);
	
	while( !mainWindow.isDisposed() ) {
		if( !view.getDisplay().readAndDispatch() ) {
			view.getDisplay().sleep();
		}
		if( stopApplication ) {
			break;
		}
	}
	view.getDisplay().dispose();
}

public void handleCommand(ICommand[] commands) {
	for( int i = 0; i < commands.length; i++ ) {
		handleCommand(commands[i]);
	}
}

public void handleCommand(ICommand command) {
	if( command.getClass() == SetEditorTextCommand.class ) {
		view.setEditorText(((SetEditorTextCommand) command).text);
		return;
	}
	
	if( command.getClass() == ShowQueryCommand.class ) {
		try {
			ResultSet rs = doSql(((ShowQueryCommand) command).sql);
			view.drawResultSet(rs);
			rs.close();
		}
		catch(SQLException e) {
			// handle exceptions such that if other commands needs be run they will
			view.DialogOK("SQLException!", e.getMessage() + "\nSQL state: " + e.getSQLState());
		}
		return;
	}
	
	throw new IllegalStateException("This can never happen!!");
	
}

private ResultSet doSql(String sqlQquery) throws SQLException {
	// purposely don't close stmt in here as the build table needs it
	return conn.createStatement().executeQuery(sqlQquery);
}

public void setView(View view) {
	this.view = view;
}

public void parseAndDisplay(String sqlQueries) throws SQLException {
	String[] queries = sqlParser.splitTextIntoSqlQueries(sqlQueries);
	for( int i = 0; i < queries.length; i++ ) {
		handleCommand(new ShowQueryCommand(queries[i]));
	}
}

}

package org.datastorm;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.datastorm.controller.Controller;
import org.datastorm.controller.IController;
import org.datastorm.gui.swt.View;

/**
 * This is the main class for starting up Data Storm.
 * <P>
 * Any normal usage should only entail using this class.
 * 
 * @author Kasper B. Graversen, (c) 2007-2008
 */
public class DataStorm {

/**
 * Show the Data Storm window and fire a sql query automatically.
 */
public void show(DataSource dataSource, String sql) {
	try {
		show(dataSource.getConnection(), sql);
	}
	catch(SQLException e) {
		e.printStackTrace();
	}
}

/**
 * Show the Data Storm window
 */
public void show(Connection connection) {
	show(connection, "");
}

/**
 * Show the Data Storm window
 */
public void show(DataSource dataSource) {
	show(dataSource, "");
}

/**
 * Show the Data Storm window and fire a sql query automatically.
 */
public void show(Connection connection, String sql) {
	try {
		IController controller = new Controller(connection);
		View view = new View(controller);
		controller.setView(view);
		controller.startUp(sql);
	}
	catch(SQLException e) {
		e.printStackTrace();
	}
}
}

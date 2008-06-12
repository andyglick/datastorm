package org.datastorm;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.datastorm.controller.Controller;
import org.datastorm.controller.IController;
import org.datastorm.controller.command.ICommand;
import org.datastorm.controller.command.SetEditorTextCommand;
import org.datastorm.controller.command.ShowQueryCommand;
import org.datastorm.gui.swt.View;

public class DataStorm {

public void show(DataSource dataSource, String sql) {
	try {
		show(dataSource.getConnection(), sql);
	}
	catch(SQLException e) {
		e.printStackTrace();
	}
}

public void show(Connection connection) {
	show(connection, "");
}

public void show(Connection connection, String sql) {
	try {
		IController controller = new Controller(connection);
		View view = new View(controller);
		controller.setView(view);
		controller.startUp(new ICommand[] { new SetEditorTextCommand(sql), new ShowQueryCommand(sql) });
	}
	catch(SQLException e) {
		e.printStackTrace();
	}
}
}

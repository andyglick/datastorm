package org.datastorm.controller;

import java.sql.SQLException;

import org.datastorm.controller.command.ICommand;
import org.datastorm.gui.swt.View;

public interface IController {
void setView(View view);

void startUp(ICommand[] commands) throws SQLException;

void parseAndDisplay(String sqlQueries) throws SQLException;

void stopApplication();

void handleCommand(ICommand command);

void handleCommand(ICommand[] command);
}

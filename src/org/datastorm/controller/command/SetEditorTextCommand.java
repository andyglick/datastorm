package org.datastorm.controller.command;

public class SetEditorTextCommand implements ICommand {
public SetEditorTextCommand(String text) {
	this.text = text;
}

public String text;
}

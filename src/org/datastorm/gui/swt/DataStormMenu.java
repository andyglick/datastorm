package org.datastorm.gui.swt;

import org.datastorm.controller.Helper;
import org.datastorm.controller.IController;
import org.datastorm.controller.command.ShowQueryCommand;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

class DataStormMenu {
private final Text editor;
private final View view;
private final IController controller;

public DataStormMenu(View view, IController controller, Text editor) {
	if( editor == null ) {
		throw new RuntimeException("remember to define the editor first!");
	}
	
	this.editor = editor;
	this.view = view;
	this.controller = controller;
}

void makeMenu(Shell shell) {
	Menu bar = new Menu(shell, SWT.BAR);
	shell.setMenuBar(bar);
	makeMenuItemFile(shell, bar);
	makeMenuItemEdit(shell, bar);
	makeMenuItemEditor(shell, bar);
	makeMenuItemAbout(shell.getDisplay(), shell, bar);
}

private void makeMenuItemAbout(final Display display, Shell shell, Menu bar) {
	MenuItem aboutItem = new MenuItem(bar, SWT.CASCADE);
	aboutItem.setText("&About");
	Menu submenu = new Menu(shell, SWT.DROP_DOWN);
	aboutItem.setMenu(submenu);
	MenuItem item = new MenuItem(submenu, SWT.PUSH);
	item.setText("&About\tCtrl+H");
	item.setAccelerator(SWT.MOD1 + 'H');
	item.addListener(SWT.Selection, new Listener() {
		public void handleEvent(Event e) {
			new AboutDialog(display, editor);
			
		}
	});
}

private void makeMenuItemFile(Shell shell, Menu bar) {
	MenuItem fileItem = new MenuItem(bar, SWT.CASCADE);
	fileItem.setText("&File");
	Menu submenu = new Menu(shell, SWT.DROP_DOWN);
	fileItem.setMenu(submenu);
	{
		MenuItem newItem = new MenuItem(submenu, SWT.PUSH);
		newItem.setText("New\tCtrl+N");
		newItem.setAccelerator(SWT.MOD1 + 'N');
		newItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				view.setEditorText("");
			}
		});
	}
	
	MenuItem quitItem = new MenuItem(submenu, SWT.PUSH);
	quitItem.setText("Quit\tESC");// handled by keyboard handler
}

private void makeMenuItemEditor(Shell shell, Menu bar) {
	MenuItem fileItem = new MenuItem(bar, SWT.CASCADE);
	fileItem.setText("E&ditor");
	Menu submenu = new Menu(shell, SWT.DROP_DOWN);
	fileItem.setMenu(submenu);
	{
		MenuItem runItem = new MenuItem(submenu, SWT.PUSH);
		runItem.setText("Run query\tCtrl+ENTER");
		runItem.setAccelerator(SWT.MOD1 + SWT.CR);
		runItem.setImage(Helper.readImage(shell.getDisplay(), "refresh_22_22.png"));
		runItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				controller.handleCommand(new ShowQueryCommand(editor.getText()));
			}
		});
	}
	
}

private void makeMenuItemEdit(Shell shell, Menu bar) {
	MenuItem fileItem = new MenuItem(bar, SWT.CASCADE);
	fileItem.setText("&Edit");
	Menu submenu = new Menu(shell, SWT.DROP_DOWN);
	fileItem.setMenu(submenu);
	
	// cut
	MenuItem cutItem = new MenuItem(submenu, SWT.PUSH);
	cutItem.setText("&Cut\tCtrl+X");
	cutItem.setAccelerator(SWT.MOD1 + 'X');
	cutItem.addListener(SWT.Selection, new Listener() {
		public void handleEvent(Event e) {
			editor.cut();
		}
	});
	
	// copy
	MenuItem copyItem = new MenuItem(submenu, SWT.PUSH);
	copyItem.setText("&Copy\tCtrl+C");
	copyItem.setAccelerator(SWT.MOD1 + 'C');
	copyItem.addListener(SWT.Selection, new Listener() {
		public void handleEvent(Event e) {
			editor.copy();
		}
	});
	// paste
	MenuItem pasteItem = new MenuItem(submenu, SWT.PUSH);
	pasteItem.setText("&Paste\tCtrl+V");
	pasteItem.setAccelerator(SWT.MOD1 + 'V');
	pasteItem.addListener(SWT.Selection, new Listener() {
		public void handleEvent(Event e) {
			editor.paste();
		}
	});
}
}

package org.datastorm.gui.swt;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.datastorm.controller.Helper;
import org.datastorm.controller.IController;
import org.datastorm.controller.Prefs;
import org.datastorm.controller.command.ShowQueryCommand;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class View {
// private static final RGB SELECTED_FG_RGB = new RGB(200, 200, 200);
// private static final RGB SELECTED_BG_RGB = new RGB(10, 90, 10);
private static final int MAX_DB_CELL_WIDTH = 400;
Text editor; // available so we can add stuff to it on events
Table databaseCells;
TableCursor cursor;
private final Display display;
private final IController controller;
final int CONTROL_A = 'A' - 0x40; // the "control A" character

public View(IController controller) {
	this.controller = controller;
	display = new Display();
}

public Display getDisplay() {
	return display;
}

/**
 * Provide different resolutions for icons to get high quality rendering wherever the OS needs large icons. For example,
 * the ALT+TAB window on certain systems uses a larger icon.
 */
private Shell makeAppIcons() {
	Image large = Helper.readImage(display, "/datastorm.gif");
	
	Shell shell = new Shell(display);
	shell.setText("Small and Large icons");
	shell.setImages(new Image[] { large });
	return shell;
}

private void makeExecuteButton(Composite shell) {
	Button button = new Button(shell, SWT.PUSH);
	// button.setText("Execute");
	button.setImage(Helper.readImage(display, "/refresh.png"));
	
	// make editor greedy on space
	GridData data = new GridData();
	data.horizontalAlignment = SWT.BEGINNING;
	data.verticalAlignment = SWT.BEGINNING;
	button.setLayoutData(data);
	
	button.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			controller.handleCommand(new ShowQueryCommand(editor.getText()));
		}
	});
}

private void makeTableCells(Composite shell) {
	databaseCells = new Table(shell, SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION);
	databaseCells.setHeaderVisible(true);
	databaseCells.setLinesVisible(true);
	
	cursor = new TableCursor(databaseCells, SWT.NONE);
	cursor.addSelectionListener(new SelectionListener() {
		// see
		// http://help.eclipse.org/help31/index.jsp?topic=/org.eclipse.platform.doc.isv/reference/api/org/eclipse/swt/custom/TableCursor.html
		public void widgetDefaultSelected(SelectionEvent e) {
		}
		
		public void widgetSelected(SelectionEvent e) {
			databaseCells.setSelection(new TableItem[] { cursor.getRow() });
		}
	});
}

private void makeTextEditor(Composite shell) {
	editor = new Text(shell, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
	editor.setSize(Prefs.WINDOW_X - 5, Prefs.WINDOW_Y / 5);
	
	// make editor greedy on space
	GridData data = new GridData();
	data.horizontalAlignment = SWT.FILL;
	data.verticalAlignment = SWT.FILL;
	data.grabExcessHorizontalSpace = true;
	data.grabExcessVerticalSpace = true;
	editor.setLayoutData(data);
	
	// editor must quit on ESC
	editor.addKeyListener(new KeyListener() {
		public void keyReleased(KeyEvent e) {
			if( e.keyCode == SWT.ESC ) {
				controller.stopApplication();
			}
		}
		
		public void keyPressed(KeyEvent e) {
			if( e.character == CONTROL_A ) {
				editor.selectAll();
				editor.showSelection();
			}
		}
	});
	
}

/**
 * make application stop on ESC key
 */
private void makeKeyListeners(Shell shell) {
	shell.addKeyListener(new KeyListener() {
		public void keyReleased(KeyEvent e) {
			if( e.keyCode == SWT.ESC ) {
				controller.stopApplication();
			}
		}
		
		public void keyPressed(KeyEvent e) {
			System.out.println(e);
		}
	});
}

public void resetQueryResultTable() {
	
}

public void drawResultSet(ResultSet rs) throws SQLException {
	clearTable();
	
	// header
	ResultSetMetaData metaData = rs.getMetaData();
	for( int cols = 0; cols < metaData.getColumnCount(); cols++ ) {
		TableColumn column = new TableColumn(databaseCells, SWT.NONE);
		column.setText(metaData.getColumnLabel(cols + 1));
	}
	
	// cells
	for( int row = 1; rs.next(); row++ ) {
		TableItem item = new TableItem(databaseCells, SWT.NONE);
		for( int col = 0; col < metaData.getColumnCount(); col++ ) {
			String txt = rs.getString(col + 1);
			
			if( txt == null ) {
				txt = "<NULL>";
			}
			item.setText(col, txt);
			// item.setFont(new Font(databaseCells.getDisplay(),new FontData()))
		}
	}
	
	// resize
	for( int i = 0; i < metaData.getColumnCount(); i++ ) {
		databaseCells.getColumn(i).pack();
		// ensure not crazy wide
		if( databaseCells.getColumn(i).getWidth() > MAX_DB_CELL_WIDTH ) {
			databaseCells.getColumn(i).setWidth(MAX_DB_CELL_WIDTH);
		}
	}
	databaseCells.layout();
}

private void clearTable() {
	databaseCells.removeAll();
	TableColumn[] columns = databaseCells.getColumns();
	for( int i = 0; i < columns.length; i++ ) {
		columns[i].dispose();
	}
}

public Shell buildScreen() {
	final Shell mainWindow = makeAppIcons();
	mainWindow.setSize(Prefs.WINDOW_X, Prefs.WINDOW_Y);
	mainWindow.setText("Data Storm " + Prefs.VERSION + " by Kasper B. Graversen (c) 2008");
	mainWindow.setLayout(new FillLayout());
	makeKeyListeners(mainWindow);
	
	// compose editor and button together
	SashForm sash = new SashForm(mainWindow, SWT.VERTICAL);
	sash.setSize(Prefs.WINDOW_X, 20);
	
	Composite topRow = new Composite(sash, SWT.NONE);
	GridLayout topRowLayout = new GridLayout(2, false);
	topRowLayout.marginWidth = 2;
	
	topRow.setLayout(topRowLayout);
	makeTextEditor(topRow);
	makeExecuteButton(topRow);
	
	makeTableCells(sash);
	new DataStormMenu(this, controller, editor).makeMenu(mainWindow);
	mainWindow.open();
	return mainWindow;
}

public void DialogOK(String title, String message) {
	Shell shell = new Shell(display);
	MessageBox dialog = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
	dialog.setText(title);
	dialog.setMessage(message);
	dialog.open();
	editor.setFocus();
}

public void setEditorText(String text) {
	editor.setText(text);
}

}

package org.datastorm.gui.swt;

import org.datastorm.controller.Helper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class AboutDialog {
private static final String ABOUT_PIC_NAME = "dsintroscreen.gif";

AboutDialog(final Display display, final Control onFocusItemOnClose) {
	final Shell aboutWin = new Shell(display, SWT.ON_TOP);
	GridLayout aboutWinLayout = new GridLayout(2, false);
	aboutWinLayout.horizontalSpacing = 20;
	aboutWin.setLayout(aboutWinLayout); // 2 columns => img, text + button
	// add image
	final Image img = Helper.readImage(display, ABOUT_PIC_NAME);
	{
		Label imgLbl = new Label(aboutWin, SWT.BORDER);
		GridData imgLayout = new GridData(img.getBounds().width, img.getBounds().height);
		imgLayout.verticalAlignment = SWT.TOP;
		imgLbl.setLayoutData(imgLayout);
		imgLbl.setImage(img);
	}
	
	// add text + button => needs its own composite container
	Composite textAndButton = new Composite(aboutWin, SWT.NONE);
	textAndButton.setLayout(new GridLayout(1, false)); // need 1 column for two items
	Label aboutText = new Label(textAndButton, SWT.NONE);
	aboutText.setText("D A T A   S T O R M\n" + //
		"------------------------\n" //
		+ "\nBy Kasper B. Graversen (c) 2008\n" //
		+ "\nData Storm is under the\n" //
		+ "Apache 2.0 License" //
		+ "\n\n" //
		+ "All graphics are from the game Datastorm \n" //
		+ "by Søren Grønbech (used with permission)\n\n" //
		+ "game freely available at \n" //
		+ "http://www.sodan.dk/oldbits/oldbits.html\n"//
		//
	);
	GridData textAndButtonLayout = new GridData(260, img.getBounds().height - 30);
	textAndButtonLayout.verticalAlignment = SWT.CENTER;
	aboutText.setLayoutData(textAndButtonLayout);
	
	Button button = new Button(textAndButton, SWT.NONE);
	button.setText("Fantastic!");
	button.setLayoutData(new GridData(SWT.END));
	button.setFocus();
	button.addSelectionListener(new SelectionListener() {
		public void widgetDefaultSelected(SelectionEvent e) {
		}
		
		public void widgetSelected(SelectionEvent e) {
			aboutWin.dispose();
			onFocusItemOnClose.setFocus();
		}
	});
	aboutWin.setSize(600, 270);
	aboutWin.open();
}

}

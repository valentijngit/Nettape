package net.nettape.client.gui.ui;

/**
 * @autor Adi Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Mar 27, 2010
 * @description
 */

import java.lang.reflect.Method;
import java.net.SocketException;
import java.util.Set;

import net.nettape.client.Global;
import net.nettape.client.MessageHandler;
import net.nettape.client.command.ReceiveBackupsCommand;
import net.nettape.client.command.SendBackupCommand;
import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.admin.Language;
import net.nettape.client.gui.ui.util.WidgetUtils;
import net.nettape.object.BackupWithItems;
import net.nettape.object.Constants;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;

public class GetBackupsWindow implements Listener {

	private static final Logger logger = Logger.getLogger(GetBackupsWindow.class);

	private Label labelAction;
	private Label labelAction2;

	private Button buttonCancel;

	boolean isCancelled = false;

	private final boolean showCancelButton;

	private ProgressBar progressBar;
	private ProgressBar progressBar2;

	public final Shell shell;

	private int exitChoiceAction = SWT.CANCEL;

	private ExecuteThread executeThread;
	
	private Object window;
	
	public boolean errors;
	public boolean relogin;

	public GetBackupsWindow(final Shell parent, final boolean showCancelButton) {
		this.shell = new Shell(parent, SWT.BORDER | SWT.CLOSE
				| SWT.APPLICATION_MODAL);
		this.shell.setText(Language.getText(Language.KEY_GENERAL,
				Language.GENERAL_TEXT_MESSAGE_TITLE_WAITING));
		this.shell.setImage(AppImages.getImage16(AppImages.IMG_REFRESH));
		this.showCancelButton = showCancelButton;

		this.shell.setLayout(new GridLayout(2, false));
		this.shell.addShellListener(new ShellAdapter() {
			public void shellClosed(ShellEvent e) {
				GetBackupsWindow.this.isCancelled = true;
				GetBackupsWindow.this.buttonCancel.setEnabled(false);
				GetBackupsWindow.this.exitChoiceAction = SWT.CANCEL;
			}
		});
		this.createGUI();

	}

	private void addCancelButton(boolean flag) {
		if (flag && (this.buttonCancel == null)) {
			Composite comp = new Composite(this.shell, SWT.NONE);
			GridLayout lay = new GridLayout(1, false);
			lay.marginWidth = lay.marginHeight = 0;
			comp.setLayout(lay);
			GridData data = new GridData(SWT.RIGHT, SWT.FILL, true, true);
			data.horizontalSpan = 2;

			comp.setLayoutData(data);
			this.buttonCancel = new Button(comp, SWT.PUSH);
			this.buttonCancel.setText(Language.getText(Language.KEY_GENERAL,
					Language.GENERAL_TEXT_BUTTON_CANCEL));
			this.buttonCancel.setImage(AppImages.getImage16(AppImages.IMG_REMOVE));
			this.buttonCancel.addListener(SWT.Selection, this);
			WidgetUtils.addImageChangeListener(this.buttonCancel,
					AppImages.getImage16(AppImages.IMG_REMOVE),
					AppImages.getImage16Focus(AppImages.IMG_REMOVE));
			this.shell.pack();
		}
	}

	public boolean isCancelled() {
		return this.isCancelled;
	}

	public void close() {
		this.shell.close();
		this.shell.dispose();
	}

	public void enableCancelButton(boolean flag) {
		if (this.buttonCancel != null) {
			this.buttonCancel.setEnabled(flag);
		}
	}

	public void createGUI() {
		this.labelAction = new Label(this.shell, SWT.HORIZONTAL);
		this.labelAction.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
				| GridData.VERTICAL_ALIGN_FILL));

		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.horizontalSpan = 2;
		data.widthHint = 350;
		this.progressBar = new ProgressBar(this.shell, SWT.SMOOTH);
		this.progressBar.setLayoutData(data);

		this.labelAction2 = new Label(this.shell, SWT.HORIZONTAL);
		this.labelAction2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
				| GridData.VERTICAL_ALIGN_FILL));

		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.horizontalSpan = 2;
		data.widthHint = 350;
		this.progressBar2 = new ProgressBar(this.shell, SWT.SMOOTH);
		this.progressBar2.setLayoutData(data);

		this.addCancelButton(this.showCancelButton);

	}

	public void handleEvent(Event event) {
		try {
			Widget src = event.widget;
			if (src == this.buttonCancel) {
				synchronized (src) {

					this.isCancelled = true;
					this.buttonCancel.setEnabled(false);
					this.exitChoiceAction = SWT.CANCEL;
				}
			}
		} catch (Exception e) {
			logger.fatal(e, e);
		}
	}

	public void setMaximumProgressBar(int max) {
		if (this.progressBar != null && !this.progressBar.isDisposed())
			this.progressBar.setMaximum(max);
	}
	public void setMaximumSubProgressBar(int max) {
		if (this.progressBar2 != null && !this.progressBar2.isDisposed())
			this.progressBar2.setMaximum(max);
	}

	public void setSelectionProgressBar(int value) {
		if (this.progressBar != null && !this.progressBar.isDisposed())
			this.progressBar.setSelection(value);
	}
	public void setSelectionSubProgressBar(int value) {
		if (this.progressBar2 != null && !this.progressBar2.isDisposed())
			this.progressBar2.setSelection(value);
	}

	public void setTextLabelAction(String text) {
		this.labelAction.setText(text);
	}

	public void setTextSubLabelAction(String text) {
		this.labelAction2.setText(text);
	}

	public Set<BackupWithItems> open() {
		try 
		{
			this.shell.setLocation(WidgetUtils.centerInWindow(Display.getDefault().getBounds(),
					this.shell.getBounds()));
			this.shell.open();

			this.executeThread = new ExecuteThread();
			this.executeThread.start();
			
			while (this.shell != null && !this.shell.isDisposed()) {
				if (this.shell.getDisplay() != null
				&& !this.shell.getDisplay().readAndDispatch()) 
				{
					this.shell.getDisplay().sleep();
				}
			}
			errors = this.executeThread.errors;
			relogin = this.executeThread.relogin;
			return this.executeThread.backups;

		} 
		catch (Exception e) 
		{
			logger.fatal(e, e);
			errors = true;
			this.shell.close();
			this.shell.dispose();
			return null;
		}
		
	}

	/**
	 * @return SWT.OK or SWT.CLOSE
	 */
	public int getExitChoiceAction() {
		return this.exitChoiceAction;
	}

	class ExecuteThread extends Thread {
		public Set<BackupWithItems> backups;
		public boolean errors;
		public boolean relogin;
		public ExecuteThread() {
		}

		public void run() {
			errors = false;
			relogin = false;
			try
			{
				ReceiveBackupsCommand receiveBackupsCommand = new ReceiveBackupsCommand(Global.connection, GetBackupsWindow.this.shell); 
				this.backups = receiveBackupsCommand.Execute();
				receiveBackupsCommand = null;
			}
			catch(SocketException sex)
			{
				errors = true;
				relogin = true;
			}
			catch(Exception ex)
			{
				GetBackupsWindow.this.shell.getDisplay().syncExec(new Runnable() {
					public void run() {
						MessageHandler.HandleMessage(null,true,"Could not load the backups from server.",false,null, GetBackupsWindow.this.shell);
					}
				});
				errors = true;
			}


			GetBackupsWindow.this.shell.getDisplay().syncExec(new Runnable() {
				public void run() {
					GetBackupsWindow.this.exitChoiceAction = SWT.OK;
					GetBackupsWindow.this.shell.close();
					GetBackupsWindow.this.shell.dispose();
				}
			});
		}
	}
}

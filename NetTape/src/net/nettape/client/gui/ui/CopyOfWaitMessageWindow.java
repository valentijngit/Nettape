package net.nettape.client.gui.ui;

/**
 * @autor Adi Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Mar 27, 2010
 * @description
 */

import java.lang.reflect.Method;

import net.nettape.client.gui.admin.AppImages;
import net.nettape.client.gui.admin.Language;
import net.nettape.client.gui.ui.util.WidgetUtils;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;

public class CopyOfWaitMessageWindow implements Listener {

	private static final Logger logger = Logger.getLogger(CopyOfWaitMessageWindow.class);

	private Label labelAction;

	private Button buttonCancel;

	boolean isCancelled = false;

	private final boolean showCancelButton;

	private ProgressBar progressBar;

	private final Shell shell;

	private int exitChoiceAction = SWT.CANCEL;

	private ExecuteThread executeThread;
	
	private Object window;

	public CopyOfWaitMessageWindow(final Shell parent, final boolean showCancelButton) {
		this.shell = new Shell(parent, SWT.BORDER | SWT.CLOSE
				| SWT.APPLICATION_MODAL);
		this.shell.setText(Language.getText(Language.KEY_GENERAL,
				Language.GENERAL_TEXT_MESSAGE_TITLE_WAITING));
		this.shell.setImage(AppImages.getImage16(AppImages.IMG_REFRESH));
		this.showCancelButton = showCancelButton;

		this.shell.setLayout(new GridLayout(2, false));
		this.shell.addShellListener(new ShellAdapter() {
			public void shellClosed(ShellEvent e) {
				CopyOfWaitMessageWindow.this.isCancelled = true;
				CopyOfWaitMessageWindow.this.buttonCancel.setEnabled(false);
				CopyOfWaitMessageWindow.this.exitChoiceAction = SWT.CANCEL;
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

	public void setSelectionProgressBar(int value) {
		if (this.progressBar != null && !this.progressBar.isDisposed())
			this.progressBar.setSelection(value);
	}

	public void setTextLabelAction(String text) {
		this.labelAction.setText(text);
	}

	public int open(Object window, final Method[] methods, final String[] texts) {
		try {
			this.window = window;
			this.shell.setLocation(WidgetUtils.centerInWindow(this.shell.getParent().getBounds(),
					this.shell.getBounds()));
			this.shell.open();
			Object waiter = new Object();
			synchronized(waiter)
			{
				setMaximumProgressBar(methods.length);
				
				if (methods == null || texts == null
						|| methods.length != texts.length)
					return this.getExitChoiceAction();
				for (int i = 0; i < methods.length
						&& !this.isCancelled; i++) {
					final int index = i;
					if (methods[index] == null)
						continue;
					
					setTextLabelAction(texts[index]);
					setSelectionProgressBar(index + 1);
			
					try {
						if (this.isCancelled) {
							this.exitChoiceAction = SWT.CANCEL;
							return this.exitChoiceAction;
						}
						//execute one step and wait for it
						this.executeThread = new ExecuteThread(methods[index], waiter);
						this.executeThread.start();
						waiter.wait();

					} catch (Exception e) {
						logger.fatal(e, e);
					}
					if (isCancelled) {
						exitChoiceAction = SWT.CANCEL;
						
						this.shell.close();
						this.shell.dispose();
						
						return this.exitChoiceAction;
					}
				}
			}
			this.exitChoiceAction = SWT.OK;
			this.shell.close();
			this.shell.dispose();

			// while (this.shell != null && !this.shell.isDisposed()) {
			// if (this.shell.getDisplay() != null
			// && !this.shell.getDisplay().readAndDispatch()) {
			// this.shell.getDisplay().sleep();
			// }
			// }
		} catch (Exception e) {
			logger.fatal(e, e);
			this.exitChoiceAction = SWT.CANCEL;
		}
		return this.exitChoiceAction;
	}

	/**
	 * @return SWT.OK or SWT.CLOSE
	 */
	public int getExitChoiceAction() {
		return this.exitChoiceAction;
	}

	class ExecuteThread extends Thread {
		private final Method method;
		private Object waiter;
		public ExecuteThread(Method method, Object waiter) {
			this.method = method;
			this.waiter = waiter;
		}
		public void run() {
			try {
				ExecuteThread.this.method.invoke(window);
			} catch (Exception e) {
				logger.fatal(e, e);
			}

			synchronized (waiter)
			{
				waiter.notifyAll();
			}
		}
	}
}
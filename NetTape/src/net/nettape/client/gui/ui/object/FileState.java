package net.nettape.client.gui.ui.object;

import java.io.File;

/**
 * @autor Adi Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Feb 1, 2010
 * @description
 */

public class FileState {
	
	private File file;
	private Boolean checked;
	private Boolean grayed;
	
	public FileState(){
		this.file = null;
		this.checked = Boolean.FALSE;
		this.grayed = Boolean.FALSE;
	}
	
	public FileState(File f, Boolean c, Boolean g){
		this.file = f;
		this.checked = c;
		this.grayed = g;
	}

	public File getFile() {
		return this.file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Boolean getChecked() {
		return this.checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Boolean getGrayed() {
		return this.grayed;
	}

	public void setGrayed(Boolean grayed) {
		this.grayed = grayed;
	}
}

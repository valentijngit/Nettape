package net.nettape.client.gui.admin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;

/**
 * @autor Adi Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Oct 12, 2009
 * @description
 */

public final class INIFile {

	private static final Logger logger = Logger.getLogger(INIFile.class);

	private String fileName;

	private TreeMap<String, TreeMap<String, String>> sections = new TreeMap<String, TreeMap<String, String>>();

	public INIFile(String file) {
		this.fileName = file;
	}

	public void loadFile() {
		String strLine = null;
		BufferedReader bufferRead = null;
		FileReader fileRead = null;
		String currentSection = "";
		try {
			fileRead = new FileReader(this.fileName);
			bufferRead = new BufferedReader(fileRead);
			while (bufferRead.ready()) {
				strLine = null;
				strLine = bufferRead.readLine().trim();
				if (strLine == null || strLine.length() == 0
						|| strLine.startsWith(";") || strLine.startsWith("#")) {
					continue;
				} else if (strLine.startsWith("[") && strLine.endsWith("]")) {
					TreeMap<String, String> prop = this.sections.get(strLine);
					if (prop == null)
						prop = new TreeMap<String, String>();
					currentSection = strLine.substring(1, strLine.length() - 1);
					this.sections.put(currentSection, prop);
				} else if (strLine.indexOf("=") > 0
						&& currentSection.length() > 0) {
					TreeMap<String, String> pro = this.sections.get(currentSection);
					if (pro == null)
						pro = new TreeMap<String, String>();
					String[] str = strLine.split("=");
					if (str != null && str.length == 2) {
						pro.put(str[0], str[1]);
					}
				}
			}
		} catch (Exception exc) {
			this.sections.clear();
			logger.fatal(exc, exc);
		} finally {
			if (bufferRead != null) {
				try {
					bufferRead.close();
				} catch (Exception e) {
					logger.fatal(e, e);
				}
				bufferRead = null;
			}
			if (fileRead != null) {
				try {
					fileRead.close();
				} catch (Exception e) {
					logger.fatal(e, e);
				}
				fileRead = null;
			}
		}
	}

	public void saveFile() {
		FileWriter fileWrite = null;
		try {
			fileWrite = new FileWriter(this.fileName);
			Set<String> section = this.sections.keySet();
			Iterator<String> iter = section.iterator();
			while (iter.hasNext()) {
				String str = iter.next();
				fileWrite.write("[" + str + "]\r\n");
				TreeMap<String, String> prop = this.sections.get(str);
				if (prop == null)
					continue;

				Set<String> propKey = prop.keySet();
				Iterator<String> iter1 = propKey.iterator();
				while (iter1.hasNext()) {
					String object = iter1.next();
					fileWrite.write(object + "=" + prop.get(object) + "\r\n");
				}
			}
			fileWrite.close();
		} catch (Exception e) {
			logger.fatal(e, e);
		}

	}

	public TreeMap<String, TreeMap<String, String>> getSections() {
		return this.sections;
	}

	public void setSections(TreeMap<String, TreeMap<String, String>> sections) {
		this.sections = sections;
	}
}

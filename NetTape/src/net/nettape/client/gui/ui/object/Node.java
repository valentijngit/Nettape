package net.nettape.client.gui.ui.object;

import java.io.File;
import java.util.*;

import net.nettape.client.gui.util.StringUtil;

import org.apache.log4j.Logger;

/**
 * @autor Adrian Moldovan
 * @mail mi_adrian00@yahoo.com
 * @version 1.0
 * @updatedate Jun 14, 2010
 * @description
 */
public class Node implements Cloneable {

	private static final Logger logger = Logger.getLogger(Node.class);

	private String value;

	private Node parent;

	private String path;

	private TreeMap<String, Node> childrens;

	private boolean isFolder;

	private boolean checked;

	private boolean grayed;
	
	private net.nettape.dal.object.Backupitem backupitem;
	

	public Node() {
		this.value = null;
		this.parent = null;
		this.childrens = new TreeMap<String, Node>();
		this.isFolder = Boolean.FALSE;
		this.checked = Boolean.FALSE;
		this.grayed = Boolean.FALSE;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	public Node getParent() {
		return this.parent;
	}

	public void setParent(final Node parent) {
		this.parent = parent;
	}

	public TreeMap<String, Node> getChildrens() {
		return this.childrens;
	}

	public void setChildrens(final TreeMap<String, Node> childrens) {
		this.childrens = childrens;
	}

	public void setChild(final String key, final Node node) {
		this.childrens.put(key, node);
	}

	public void addChild(final Node child) {
		child.setParent(this);
		if (this.childrens.get(child.getValue()) == null) {
			this.childrens.put(child.getValue(), child);
		} else {
			Iterator<Node> iter = child.getChildrens().values().iterator();
			while (iter.hasNext()) {
				Node node = iter.next();
				this.childrens.get(child.getValue()).addChild(node);
			}
		}
	}

	public boolean isFolder() {
		return this.isFolder;
	}

	public void setFolder(final boolean isFolder) {
		this.isFolder = isFolder;
	}

	public boolean getChecked() {
		return this.checked;
	}

	public void setChecked(final boolean checked) {
		this.checked = checked;
		this.grayed = false;
		Iterator<Node> iter = this.childrens.values().iterator();
		while (iter.hasNext()) {
			Node node = iter.next();
			node.setChecked(checked);
			// node.setGrayed();
		}
	}

	public boolean getGrayed() {
		return this.grayed;
	}

	public int countChildrens() {
		int nrChildrens = this.childrens.size();
		Iterator<Node> iter = this.childrens.values().iterator();
		while (iter.hasNext()) {
			Node child = iter.next();
			nrChildrens += child.countChildrens();
		}
		return nrChildrens;
	}

	public int countCheckedChildrens() {
		int nrChildrens = 0;
		Iterator<Node> iter = this.childrens.values().iterator();
		while (iter.hasNext()) {
			Node child = iter.next();
			if (child.getChecked()) {
				nrChildrens++;
			}
			nrChildrens += child.countCheckedChildrens();
		}
		return nrChildrens;
	}

	public void setGrayed() {
		int nrChildrens = countChildrens();
		int nrChekedChildrens = countCheckedChildrens();
		this.grayed = (nrChekedChildrens < nrChildrens) && (nrChekedChildrens > 0)
				&& (nrChildrens > 0);
		if (nrChildrens > 0) {
			this.checked = (nrChekedChildrens > 0);
		}
	}

	public String getPath() {
		if (StringUtil.isNotEmpty(this.path)) {
			return this.path;
		}
		String val = this.value + (this.isFolder ? File.separator : "");
		Node par = this.parent;
		while (par != null) {
			val = par.getValue() + File.separator + val;
			par = par.getParent();
		}
		return val;
	}

	public boolean hasChildrends() {
		return this.childrens.size() > 0;
	}

	public boolean equals(Node node) {
		return getPath().equals(node.getPath());
	}

	public void ckeckNode(Node node) {
		if (getPath().equals(node.getPath())) {
			setChecked(node.getChecked());
			Node n = getParent();
			while (n != null) {
				n.setGrayed();
				n = n.getParent();
			}
			this.getParent().setGrayed();
		} else {
			Iterator<Node> childrenNode = this.childrens.values().iterator();
			while (childrenNode.hasNext()) {
				Node node2 = childrenNode.next();
				node2.ckeckNode(node);
			}
		}
	}

	public ArrayList<Node> getCheckedListNode() {
		ArrayList<Node> result = new ArrayList<Node>();
		Iterator<Node> iterator = this.getChildrens().values().iterator();
		while (iterator.hasNext()) {
			Node node2 = iterator.next();
			
			if (node2.hasChildrends()) {
				result.addAll(node2.getCheckedListNode());
			} else {
				if (node2.getChecked()) {
					result.add(node2);
				}
			}
		}
		return result;
	}

	public void setPath(String path) {
		this.path = path;
	}



	public void setBackupitem(net.nettape.dal.object.Backupitem backupitem) {
		this.backupitem = backupitem;
	}
	
	
	public net.nettape.dal.object.Backupitem getBackupitem()
	{
		return this.backupitem;
	}

	
	public Node cloneNode() {
		try {
			return (Node) this.clone();
		} catch (CloneNotSupportedException e) {
			Node.logger.fatal(e, e);
			return null;
		}
	}
}
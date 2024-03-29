package model;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JTextField;

import view.NodeEditorView;

public class Node {
	public String name;
	public String id;
	public boolean manualID;	//don't update ID with name updates if the user has specified their own ID
	public boolean important;
	public String url;
	public String icon; //URL
	public String image; //URL
	public String desc;
	public Vector<Node> children;
	
	private final Node parent;
	
	private NodeEditorView view;
	
	public Node (final Node parent) {
		name = "";
		id = "";
		manualID = false;
		important = false;
		url = "http://";
		icon = "";
		image = "";
		desc = "";
		children = new Vector<Node>();
		this.parent = parent;
	}
	
	public NodeEditorView getView () {
		return view;
	}
	
	public void setView (NodeEditorView v) {
		//if you're setting to null, don't monkey around and just do it!
		//should be only called this way by view.close()
		if (v == null) {
			view = null;
			return;
		}//if
		
		//close previously defined view
		if (view != null) {
			view.close();
		}
		
		//actually fill in the view field!
		view = v;

		//when the window is closed, I need to delete the reference in this object and set it to null
		//view.close() will call this function with v = null (see if at the top)
		view.addWindowListener(new WindowAdapter () {
			public void windowClosing(WindowEvent event) {
				try {
					view.close();
				} catch (NullPointerException e) {
					return; //for some reason I get a NPE, but it doesn't make a difference
				}
			}
		});
	}
	
	public void setImportant (boolean important) {
		this.important = important;
		Model.updateAllViews();
	}
	
	public void addChild (Node child) {
		children.add(child);
	}
	
	public void removeChild(Node child) {
		children.remove(child);
	}

	public void makeIDFromName(JTextField toUpdate) {
		if (manualID) return;
		String newID = "";
		char [] chars = name.toCharArray();
		for (char c : chars) {
			if (Character.isWhitespace(c)) continue; //remove spaces
			newID += Character.toLowerCase(c);
		}
		this.id = newID;
		toUpdate.setText(this.id);
	}

	public void bringViewToFront () {
		if (view == null) {
			new NodeEditorView (parent, this).setVisible(true);
		} else {
			view.requestFocus();
			view.toFront();
		}//if
	}//bringViewToFront
	
	public void closeView() {
		view.close();
	}
	
	public String toString () {
		return toString("");
	}//toString
	
	public String toString (String linePrefix) {
		String value = linePrefix + "{\n";
		value += linePrefix + "\"name\": \"" + String.valueOf(name) + "\",\n";
		value += linePrefix + "\"id\": \"" + String.valueOf(id) + "\",\n";
		value += linePrefix + "\"data\": {\n";
		value += linePrefix + "	\"important\": " + String.valueOf(important) + ",\n";
		value += linePrefix + "	\"url\": \"" + String.valueOf (url) + "\",\n";
		value += linePrefix + "	\"desc\": [ \"" + String.valueOf(desc) + "\" ]\n"; //need to add a comma when you add more values
		value += linePrefix + "},\n";
		value += linePrefix + "\"children\": [\n";
		for (Node child : children) {
			value += child.toString(linePrefix + "	");
			value += ",";
		}
		if (children.size () != 0) {
			value = value.substring(0, value.length() - 1); //remove comma
		}//if
		value += "\n";
		value += linePrefix + "]\n";
		value += linePrefix + "}"; //no newline, one less tab on prefix
		
		return value;
	}
}

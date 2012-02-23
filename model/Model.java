package model;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;

import prefuse.data.Table;
import prefuse.data.Tree;


import view.IView;

public class Model {
	protected static List<IView> views;
	public MapCreatorTableModel tableModel;
	private static Model instance = new Model();
	private Node root = new Node ();
	
	private final Object [][] noDataYet = new Object [0][0];
	private final String [] headers = {"Name", "ID", "Important", "URL", "Icon", "Image", "Desc", "Children"};
	protected Model () {
		views = new ArrayList<IView> ();
		tableModel = new MapCreatorTableModel(noDataYet, headers);
		tableModel.addRow(new Vector<Object>(headers.length));
	}//default constructor
	
	public void addView (IView v) {
		views.add(v);
	}//addView
	
	public void removeView (IView v) {
		views.remove(v);
	}//removeView
	
	public Node root() {
		return root;
	}
	
	public void save () {
		String JSON = root.toString("");
			// Create file 
			FileWriter fstream;
			try {
				fstream = new FileWriter("map.json");
				BufferedWriter out = new BufferedWriter(fstream);
				out.write(JSON);
				//Close the output stream
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				JFrame error = new JFrame ("Error");
				error.getContentPane().add(new JLabel("Couldn't save file!"));
				error.setVisible(true);
			}
	}
	
	public Tree updateTree () {
		Tree tree = new Tree ();
		
		Table nodes = tree.getNodeTable();
		nodes.addColumn("name", String.class);
		
		prefuse.data.Node treeRoot = tree.addRoot();
		treeRoot.set("name", root.name);
		for (Node child : root.children){
			addChild (tree, treeRoot, child);
		}
		return tree;
	}//getTree
	
	private void addChild (Tree tree, prefuse.data.Node treeParent, Node node) {
		prefuse.data.Node treeNode = tree.addChild(treeParent);
		treeNode.set("name", node.name);
		for (Node child : node.children) {
			addChild (tree, treeNode, child);
		}
	}//addChild
	
	public static void updateAllViews () {
		for (IView v : views) {
			v.updateView ();
		}//for
	}//updateAllViews
	
	public static Model getInstance() {
		return instance;
	}
	
	private Node getNodeFromName(Node node, String name) {
		if (node.name.equals(name)) {
			return node;
		}
		for (Node child : node.children) {
			Node foundIt = getNodeFromName (child, name);
			if (foundIt != null) {
				return foundIt;
			}
		}
		return null; //didn't find it
	}//getNodeFromName

	public void openWindowFromName(String name) {
		Node node = getNodeFromName(root, name);
		if (node == null) {
			System.out.println ("Node was null");
			return; //didn't find it
		}
		System.out.println ("opening window for node" + node.name);
		node.bringViewToFront();
	}//openWindowFromName
}//Model base class

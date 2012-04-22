package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Model;
import model.Node;


@SuppressWarnings("serial")
public class NodeEditorView extends JFrame implements IView {
	private Model model = Model.getInstance();
	private final Node node;
	private final Node parent;
	private final static int HEIGHT = 260;
	
	private JPanel layout;
		private JPanel dataEntryFields;
			private JPanel labels;
				private JLabel nameLabel;
				private JLabel idLabel;
				private JLabel importantLabel;
				private JLabel urlLabel;
				private JLabel iconLabel;
				private JLabel imageLabel;
				private JLabel descLabel;
			private JPanel properties;
				private JTextField name;
				private JTextField id;
				private JCheckBox important;
				private JTextField url;
				private JTextField icon;
				private JTextField image;
				private JTextField desc;
		private ScrollPane childScroller;
			private JPanel children;
	
	public NodeEditorView (Node parent, Node node) {
		super (node.name);
		this.node = node;
		if (node.getView() != null) node.getView().close();
		node.setView(this);
		
		this.parent = parent;
		
		initializeWidgets();
		addListeners ();
		addChildrenToParents ();

		model.addView(this);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		updateView();
		this.setSize(300, HEIGHT);
	}//constructor
	
	private void initializeWidgets() {
		layout = new JPanel (new BorderLayout ());
			dataEntryFields = new JPanel (new BorderLayout());
				labels = new JPanel (new GridLayout(7,1));
					nameLabel = new JLabel ("Name:");
					idLabel = new JLabel("ID:");
					importantLabel = new JLabel ("Important:");
					urlLabel = new JLabel("URL:");
					iconLabel = new JLabel("Icon's path:");
					imageLabel = new JLabel("Image's path:");
					descLabel = new JLabel("Description:");
				properties = new JPanel (new GridLayout(7,1));
					name = new JTextField(node.name);
					id = new JTextField(node.id);
					important = new JCheckBox ("", node.important);
					url = new JTextField(node.url);
					icon = new JTextField(node.icon);
					image = new JTextField(node.image);
					desc = new JTextField(node.desc);
			childScroller = new ScrollPane();
				children = new JPanel(new FlowLayout());
				
		name.setRequestFocusEnabled(true);
	}//initializeWidgets
	
	private void addListeners() {
		name.addKeyListener(new KeyAdapter () {
			public void keyReleased(KeyEvent e) {
				//the new character
				node.name = name.getText();
				
				//this updates the id text field if the user hasn't already set
				//a manual value for node.id
				node.makeIDFromName(id);
				
				Model.updateAllViews();
			}
		});
		
		id.addKeyListener(new KeyAdapter () {
			public void keyReleased(KeyEvent e) {
				node.manualID = false;
				node.id = id.getText();
			}
		});
		
		important.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				node.setImportant(important.isSelected());
			}
		});
		
		url.addKeyListener(new KeyAdapter () {
			public void keyReleased(KeyEvent e) {
				node.url = url.getText();
			};
		});
		
		icon.addKeyListener(new KeyAdapter () {
			public void keyReleased(KeyEvent e) {
				node.icon = icon.getText();
			}
		});
		
		image.addKeyListener(new KeyAdapter () {
			public void keyReleased(KeyEvent e) {
				node.image = image.getText();
			}
		});
		
		desc.addKeyListener(new KeyAdapter () {
			public void keyReleased(KeyEvent e) {
				node.desc = desc.getText();
			}
		});
		
		this.addComponentListener(new ComponentAdapter () {
			public void componentResized(ComponentEvent e) {
				NodeEditorView.this.setSize (NodeEditorView.this.getSize().width, HEIGHT);
			}
		});
	}
	
	private void addChildrenToParents () {
		childScroller.add(children);
		labels.add(nameLabel);
		labels.add(idLabel);
		labels.add(importantLabel);
		labels.add(urlLabel);
		labels.add(iconLabel);
		labels.add(imageLabel);
		labels.add(descLabel);
		properties.add(name);
		properties.add(id);
		properties.add(important);
		properties.add(url);
		properties.add(icon);
		properties.add(image);
		properties.add(desc);
		dataEntryFields.add(labels, BorderLayout.WEST);
		dataEntryFields.add(properties, BorderLayout.CENTER);
		layout.add(dataEntryFields, BorderLayout.NORTH);
		layout.add(childScroller);
		this.getContentPane().add(layout);
	}
	
	@Override
	public void updateView() {
		this.setTitle(node.name);
		
		//update the buttons
		children.removeAll();
		for (Node n: node.children) {
			JButton button = new JButton(n.name);
			button.addActionListener(new NodeButtonListener(n));
			children.add(button);
		}
		children.add(new NodeAdderButton (this.node));
		if (node.children.isEmpty()) {
			children.add(new NodeDeleterButton(parent, node));
		}
		
		this.pack();
	}
	
	//closes the window, deregisters from models
	public void close () {
		WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
		model.removeView(this);
		node.setView (null);
	}
}

class NodeButtonListener implements ActionListener {
	private Node node;
	public NodeButtonListener (Node node) {
		this.node = node;
	}
	
	public void actionPerformed (ActionEvent e) {
		node.bringViewToFront();
	}
}//NodeButtonListener

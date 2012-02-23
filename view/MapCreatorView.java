package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Model;
import prefuse.controls.ControlAdapter;
import prefuse.data.Tree;
import prefuse.visual.VisualItem;

@SuppressWarnings("serial")
public class MapCreatorView extends JFrame implements IView {
	private Model model;
    public static final String TREE_CHI = "/Users/Devin/Documents/.workspace/prefuse-beta/data/chi-ontology.xml.gz";
	
    private JPanel layout;
    	private JButton save;
    	private TreeView treeView;
	
	public MapCreatorView() {
		//creates the main window, a JFrame
		super ("Map Creator");
		this.model = Model.getInstance();
		model.addView(this);
		
		initializeWidgets();
		addListeners ();
		addChildrenToParents ();

		this.pack();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}//default constructor
	
	private void initializeWidgets() {
		layout = new JPanel();
			save = new JButton ("Save");
			treeView = generateTree();
		layout.setLayout(new BorderLayout());
	}//initializeWidgets
	
	private void addListeners() {
		save.addActionListener (new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.save();
			}
		});
		
        treeView.addControlListener(new ControlAdapter() {
            public void itemClicked(VisualItem item, MouseEvent e) {
            	System.out.println("clicked!");
                if ( item.canGetString("name") )
                	model.openWindowFromName(item.getString("name"));
            }
        });
	}//addListeners
	
	private void addChildrenToParents() {
		layout.add(save, BorderLayout.NORTH);
		layout.add(treeView);
		this.getContentPane().add(layout);
	}//addChildrenToParents
	
	public TreeView generateTree() {
		Color BACKGROUND = Color.WHITE;
		Color FOREGROUND = Color.BLACK;
		final String label = "name";

		Tree t = null;
		try {
			t = model.updateTree ();
		} catch ( Exception e ) {
			e.printStackTrace();
			System.exit(1);
		}

		// create a new treemap
		TreeView tview = new TreeView(t, label);
		tview.setBackground(BACKGROUND);
		tview.setForeground(FOREGROUND);

		return tview;
	}
	
	public void updateView () {
		layout.remove(treeView);
		treeView = generateTree();//model.updateTree(treeView);
		layout.add(treeView);
        treeView.addControlListener(new ControlAdapter() {
            public void itemClicked(VisualItem item, MouseEvent e) {
            	System.out.println("clicked!");
                if ( item.canGetString("name") )
                	model.openWindowFromName(item.getString("name"));
            }
        });
        this.pack();
	}//updateView
}//MapCreatorView

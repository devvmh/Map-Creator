package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import model.Model;
import model.Node;

@SuppressWarnings("serial")
public class NodeAdderButton extends JButton {
	Node parent;
	public NodeAdderButton (final Node parent) {
		super ("+");
		
		this.parent = parent;
		
		this.addActionListener (new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				Node child = new Node();
				new NodeEditorView(child).setVisible(true);
				parent.addChild(child);
				Model.updateAllViews();
			}
		});
	}
}

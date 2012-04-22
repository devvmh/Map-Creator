package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import model.Model;
import model.Node;

@SuppressWarnings("serial")
public class NodeDeleterButton extends JButton {
	Node parent;
	Node node;
	public NodeDeleterButton (final Node parent, final Node node) {
		super ("-");
		
		this.parent = parent;
		this.node = node;
		
		this.addActionListener (new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				node.closeView();
				if (parent != null) {
					parent.removeChild(node);
				}
				Model.updateAllViews();
			}
		});
	}
}

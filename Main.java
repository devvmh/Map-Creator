import model.Model;
import view.MapCreatorView;
import view.NodeEditorView;

public class Main {
	public static void main (String args []) {
		Model model = Model.getInstance();
		new MapCreatorView ().setVisible(true);
		new NodeEditorView (model.root()).setVisible(true);
	}
}

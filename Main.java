import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import model.Model;
import view.MapCreatorView;
import view.NodeEditorView;

public class Main {
	public static void main (String args []) {
		Model model = Model.getInstance();
		try {
			String filename = args[1];
			String json = readFile (filename);
			model.initFromJSON (json);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println ("No map.json file could be read, starting from scratch.");
		}
		new MapCreatorView ().setVisible(true);
		
		//null is because there's no parent
		new NodeEditorView (null, model.root()).setVisible(true);
	}
	
	public static String readFile (String filename) throws IOException {
		// Open the file that is the first 
		// command line parameter
		FileInputStream fstream = new FileInputStream(filename);
		// Get the object of DataInputStream
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String json = "";
		String strLine;
		//Read File Line By Line
		while ((strLine = br.readLine()) != null)   {
			// Print the content on the console
			json += strLine + "\n";
		}//while
		return json;
	}
}

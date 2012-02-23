package listeners;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public abstract class WindowClosingListener implements WindowListener {
	public void windowActivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	
	//public void windowClosing left unimplemented
}

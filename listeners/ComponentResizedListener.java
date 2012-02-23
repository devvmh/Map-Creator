package listeners;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public abstract class ComponentResizedListener implements ComponentListener {
	public void componentHidden(ComponentEvent e) {}
	public void componentMoved(ComponentEvent e) {}
	public void componentShown(ComponentEvent e) {}
	//componentResized not implemented
}

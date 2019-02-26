package comp1206.sushi;
import comp1206.sushi.mock.MockServer;
import comp1206.sushi.server.ServerWindow;

import javax.swing.*;

public class ServerApplication {

	public static void main(String[] argv) {
		try {
			UIManager.setLookAndFeel(
					UIManager.getSystemLookAndFeelClassName());
		} catch (ReflectiveOperationException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(() -> new ServerWindow(new MockServer()));
	}
}


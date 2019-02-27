package comp1206.sushi;

import comp1206.sushi.mock.MockServer;
import comp1206.sushi.server.ServerWindow;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class ServerApplication extends Application {

	public static void main(String[] argv)

	{
//		try {
//			UIManager.setLookAndFeel(
//					UIManager.getSystemLookAndFeelClassName());
//		} catch (ReflectiveOperationException | UnsupportedLookAndFeelException e) {
//			e.printStackTrace();
//		}

		//SwingUtilities.invokeLater(() -> new ServerWindow(new MockServer()));

		launch(argv);

	}
	@Override
	public void start(Stage stage)  {
		Platform.runLater(()->new ServerWindow(new MockServer()));
	}

    @Override
    public void stop() throws Exception {
        super.stop();
        System.exit(0);
    }
}


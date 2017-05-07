package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.sun.javafx.application.PlatformImpl;

import constants.Constants;
import controller.MetaschemaEditorController;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class MetaschemaEditorView extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7621166619955849413L;
	private Stage stage;
	private WebView browser;
	private JFXPanel jfxPanel;
	private JButton okButton;
	private WebEngine webEngine;
	private MetaschemaEditorController controller;

	public MetaschemaEditorView(MetaschemaEditorController controller, String metaschemaString) {
		this.setController(controller);
		initComponents(metaschemaString);
	}

	private void initComponents(String metaschemaString) {
		jfxPanel = new JFXPanel();
		createScene(metaschemaString);
		setSize(Constants.EDITOR_WINDOW_SIZE);

		setLayout(new BorderLayout());
		add(jfxPanel, BorderLayout.CENTER);

		okButton = new JButton();
		okButton.setText("OK");

		add(okButton, BorderLayout.SOUTH);
	}

	public void addOkListener(ActionListener l) {
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PlatformImpl.runLater(new Runnable() {
					@Override
					public void run() {
						l.actionPerformed(e);
					}
				});
			}

		});
	}

	public void showFailureMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "Error loading new metaschema", JOptionPane.ERROR_MESSAGE);
	}

	public String getText() {
		return (String) webEngine.executeScript("window.myMonaco.getValue()");
	}

	private void createScene(String metaschemaString) {
		PlatformImpl.startup(new Runnable() {
			@Override
			public void run() {
				stage = new Stage();
				stage.setResizable(true);

				Group root = new Group();
				Scene scene = new Scene(root, 800, 600);
				stage.setScene(scene);

				// Set up the embedded browser:
				browser = new WebView();
				webEngine = browser.getEngine();
				webEngine.load(MetaschemaEditorView.class.getResource("/res/monaco/index.html").toExternalForm());

				com.sun.javafx.webkit.WebConsoleListener
						.setDefaultListener(new com.sun.javafx.webkit.WebConsoleListener() {

							@Override
							public void messageAdded(WebView webView, String message, int lineNumber, String sourceId) {
								System.out.println("Console: [" + sourceId + ":" + lineNumber + "] " + message);

							}

						});

				webEngine.executeScript("window.metaschema = " + metaschemaString + ";");
				try {
					webEngine.executeScript("window.metametaschema = "
							+ new String(Files.readAllBytes(Paths.get("src/res/metametaschema.json")),
									StandardCharsets.UTF_8)
							+ ";");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				ObservableList<Node> children = root.getChildren();
				children.add(browser);

				jfxPanel.setScene(scene);
			}
		});
	}

	public MetaschemaEditorController getController() {
		return controller;
	}

	public void setController(MetaschemaEditorController controller) {
		this.controller = controller;
	}
}
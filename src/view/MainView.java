
package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import constants.Constants;
import controller.MainController;
import controller.MetaschemaEditorController;
import controller.ToolBarController;
import model.Entity;
import net.miginfocom.swing.MigLayout;
import view.tree.TreeView;

public class MainView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8284254239116576700L;
	private static MainView instance;
	private TreeView treeView;

	private ToolBarView toolBarView;
	private MenuBarView menuBarView;
	private DesktopView desktopView;
	private MainController controller;

	public static MainView getInstance() {
		if (instance == null) {
			instance = new MainView();
			instance.initialize();
		}
		return instance;
	}

	private void initialize() {
		setLookAndFeel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("InfView");
		this.setSize(Constants.WINDOW_SIZE);
		this.setLocationRelativeTo(null);
		this.setLayout(new MigLayout("fill", "0[180, grow 30]5[700, grow 70]0", "0[grow 10]0[grow 90]0"));
		// Adds the menu bar.
		menuBarView = new MenuBarView();
		this.setJMenuBar(menuBarView);
		// this.setJMenuBar(menuBarView);

		// Adds the tool bar.
		ToolBarController toolBarController = new ToolBarController();
		MainView self = this;
		toolBarController.addEditMetaschemaListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MetaschemaEditorController c = new MetaschemaEditorController();
				c.show();
				c.addNewMetaschemaListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						self.treeView.refresh();
						c.hide();
					}
				});
			}
		});
		this.toolBarView = toolBarController.getView();
		toolBarView.setFloatable(false);

		this.add(this.toolBarView, "wrap, span 2 1, grow");

		// Adds the tree view.
		this.treeView = new TreeView();

		this.add(this.treeView, "grow");

		// Adds the desktop view.
		this.desktopView = new DesktopView();
		this.add(this.desktopView, "grow");

		// Attaches the listeners.
		this.setController(new MainController());
	}

	private void setLookAndFeel() {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					SwingUtilities.updateComponentTreeUI(this);
					pack();
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to
			// another look and feel.
		}
	}

	public DesktopView getDesktopView() {
		return desktopView;
	}

	public void doTableOpen(Entity entity) {
		desktopView.getMainTable().addTab(entity);
	}

	public MainController getController() {
		return controller;
	}

	public void setController(MainController controller) {
		this.controller = controller;
	}
}
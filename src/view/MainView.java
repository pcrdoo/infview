
package view;

import javax.swing.*;

import constants.Constants;
import controller.MainController;
import model.Entity;
import view.tree.TreeView;

import java.awt.BorderLayout;
import java.util.*;

public class MainView extends JFrame{
	
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
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("InfView");
		this.setSize(Constants.WINDOW_SIZE);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());

		// Adds the menu bar.
		this.menuBarView = new MenuBarView();
		this.setJMenuBar(menuBarView);

		// Adds the tool bar.
		this.toolBarView = new ToolBarView();
		this.add(this.toolBarView, BorderLayout.PAGE_START);

		// Adds the tree view.
		this.treeView = new TreeView();
		this.add(this.treeView, BorderLayout.LINE_START);

		// Adds the desktop view.
		this.desktopView = new DesktopView();
		this.add(this.desktopView, BorderLayout.CENTER);

		// Attaches the listeners.
		this.controller = new MainController(this);
	}
	
	public void doTableOpen(Entity entity) {
		//TODO : Midza
		System.out.println("otvaram " + entity.getName() + "...");
	}
}
/***********************************************************************
 * Module:  DesktopView.java
 * Author:  Random
 * Purpose: Defines the Class DesktopView
 ***********************************************************************/

package view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

import controller.DesktopController;
import net.miginfocom.swing.MigLayout;

public class DesktopView extends JPanel {
	private DesktopController desktopController;
	private TopPanel topPanel;
	private BottomPanel bottomPanel;
	public DesktopView() {
		this.setLayout(new MigLayout("fill", "5[]5", "5[grow 10]5[grow 10]5"));
		topPanel = new TopPanel();
		bottomPanel = new BottomPanel();
		this.add(topPanel, "grow, wrap");
		this.add(bottomPanel, "grow");
	}
	public TopPanel getTopPanel() {
		// TODO Auto-generated method stub
		return topPanel;
	}

}
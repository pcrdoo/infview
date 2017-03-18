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

public class DesktopView extends JPanel {
	private DesktopController desktopController;
	private TopPanel topPanel;
	private BottomPanel bottomPanel;
	public DesktopView() {
		this.setLayout(new BorderLayout());
		this.setBackground(Color.RED);
		topPanel = new TopPanel();
		bottomPanel = new BottomPanel();
		this.add(topPanel, BorderLayout.NORTH);
		this.add(bottomPanel, BorderLayout.SOUTH);
	}

}
/***********************************************************************
 * Module:  DesktopView.java
 * Author:  Random
 * Purpose: Defines the Class DesktopView
 ***********************************************************************/

package view;

import controller.DesktopController;
import java.util.*;

import javax.swing.JDesktopPane;
import javax.swing.JPanel;

public class DesktopView extends JPanel {
	private DesktopController desktopController;
	private TopPanel topPanel;
	private BottomPanel bottomPanel;
	public DesktopView() {
		topPanel = new TopPanel();
		bottomPanel = new BottomPanel();
		this.add(topPanel);
		this.add(bottomPanel);
	}

}
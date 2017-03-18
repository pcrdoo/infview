/***********************************************************************
 * Module:  TopPanel.java
 * Author:  Random
 * Purpose: Defines the Class TopPanel
 ***********************************************************************/

package view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

/** @pdOid d741f619-ce90-417f-8eae-517dd3ebf0c8 */
public class TopPanel extends JPanel {

	// privateTopPanelController topPanelController;
	private TablePanel tablePanel;

	public TopPanel() {
		this.setBackground(Color.YELLOW);
		tablePanel = new TablePanel();
		this.add(tablePanel);
	}
}
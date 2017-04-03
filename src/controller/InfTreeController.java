package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

import model.Entity;
import model.InfResource;
import view.MainView;
import view.tree.InfNode;
import view.tree.InfTree;
import view.tree.TreeView;

public class InfTreeController {

	private InfTree view;

	public InfTreeController(InfTree view) {
		this.view = view;
		this.view.addMouseListener(new DoubleClickListener());
	}

	class DoubleClickListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
				InfNode node = (InfNode) view.getLastSelectedPathComponent();
				if (node != null) {
					InfResource resource = node.getResource();
	
					if (resource instanceof Entity) {
						MainView.getInstance().doTableOpen((Entity) resource);
					}
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}
	}
}

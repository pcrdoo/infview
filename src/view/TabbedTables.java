package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

import model.Entity;
import model.files.File;
import model.files.InvalidRecordException;
import net.miginfocom.swing.MigLayout;

public class TabbedTables extends JPanel {

	private JToolBar toolbar;
	private JTabbedPane tabs; // dodajemo tablePanele
	private boolean autoRefresh;

	private JButton nextBlock;
	private JSpinner blockFactor;

	public TabbedTables(boolean autoRefresh) {
		this.setLayout(new MigLayout("fill", "", "0[]0[grow]0"));
		toolbar = new JToolBar();
		toolbar.setRollover(true);
		toolbar.setFloatable(false);
		populateToolbar();
		this.add(toolbar, "grow, wrap, height 50px");
		tabs = new JTabbedPane();
		tabs.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Entity entity = getSelectedEntity();
				if (entity != null && entity instanceof File) {
					blockFactor.setValue(((File) entity).getBlockFactor());
					blockFactor.setEnabled(true);
					nextBlock.setEnabled(true);
				} else {
					blockFactor.setEnabled(false);
					nextBlock.setEnabled(false);
				}
			}
		});
		this.add(tabs, "grow, height 250px");
		this.autoRefresh = autoRefresh;
	}

	private void populateToolbar() {
		// FETCH NEXT BLOCK
		nextBlock = new JButton("Fetch Next Block");
		nextBlock.setEnabled(false);
		nextBlock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Entity entity = getSelectedEntity();
				if (entity != null && entity instanceof File) {
					try {
						((File) entity).fetchNextBlock();
						((File) entity).fireUpdateBlockPerformed(); // ozvezavanje tabele
					} catch (IOException | InvalidRecordException ex) {
						System.out.println("Invalid blockfetch");
						ex.printStackTrace();
					}
				}

			}
		});
		toolbar.add(nextBlock);
		toolbar.addSeparator();
		toolbar.add(new JLabel("Block Factor: "));
		
		// Block factor spinner
		SpinnerModel numberModel = new SpinnerNumberModel(20, 1, 100, 1);
		blockFactor = new JSpinner(numberModel);
		blockFactor.setEnabled(false);
		JFormattedTextField field = (JFormattedTextField) blockFactor.getEditor().getComponent(0);
		DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
		formatter.setCommitsOnValidEdit(true);
		blockFactor.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				Entity entity = getSelectedEntity();
				if (entity != null && entity instanceof File) {
					((File) entity).setBlockFactor((Integer) blockFactor.getValue());
				}
			}

		});
		toolbar.add(blockFactor);
	}

	private Entity getSelectedEntity() {
		if (tabs.getSelectedComponent() == null) {
			return null;
		}
		return ((TablePanel) tabs.getSelectedComponent()).getEntity();
	}

	public boolean addTab(Entity entity) {
		for (Component c : tabs.getComponents()) {
			if (c instanceof TablePanel) {
				TablePanel panel = (TablePanel) c;
				if (panel.getEntity() == entity) {
					tabs.setSelectedComponent(c);
					return false;
				}
			}
		}
		TablePanel panel = new TablePanel(entity, autoRefresh); //
		TabComponent tabComponent = new TabComponent(tabs, entity);
		tabs.addTab(entity.getName(), panel);
		tabs.setTabComponentAt(tabs.indexOfComponent(panel), tabComponent);
		tabs.setSelectedComponent(panel);
		return true;
	}

}

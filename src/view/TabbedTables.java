package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultFormatter;

import controller.TabbedTablesController;
import model.Entity;
import model.files.File;
import model.files.SequentialFile;
import net.miginfocom.swing.MigLayout;

public class TabbedTables extends JPanel {

	private JToolBar toolbar;
	private JTabbedPane tabs; // dodajemo tablePanele
	private boolean mainTable;

	private JButton nextBlock;
	private JSpinner blockFactor;
	private JButton doSearch;
	private JTextField blocksFetched;


	private JButton doInsert;
	private JButton doModify;
	private JButton doDelete;
	private JButton doMerge;

	public TabbedTables(boolean mainTable) {
		this.setLayout(new MigLayout("fill", "", "0[]0[grow]0"));
		toolbar = new JToolBar();
		toolbar.setRollover(true);
		toolbar.setFloatable(false);
		populateToolbar();
		this.add(toolbar, "grow, wrap, height 50px");
		if (!mainTable) {
			toolbar.setVisible(false);
			this.setBackground(Color.RED);
		}
		tabs = new JTabbedPane();
		this.add(tabs, "grow, height 250px");
		this.mainTable = mainTable;
		new TabbedTablesController(this);
	}

	public void enableToolbar(Entity entity) {
		if (entity instanceof File) {
			File file = (File) entity;
			blockFactor.setValue(file.getBlockFactor());
			blocksFetched.setText(String.valueOf(file.getBlocksFetched()));
			blockFactor.setEnabled(true);
			nextBlock.setEnabled(true);
			if (entity instanceof SequentialFile) {
				doSearch.setEnabled(true);
			}
			blocksFetched.setEnabled(true);
			if(entity instanceof SequentialFile) {
				doInsert.setEnabled(true);
				doModify.setEnabled(true);
				doDelete.setEnabled(true);
				doMerge.setEnabled(true);
			}
		}
	}

	public void disableToolbar() {
		blockFactor.setEnabled(false);
		blockFactor.setValue(20);
		nextBlock.setEnabled(false);
		doSearch.setEnabled(false);
		blocksFetched.setEnabled(false);
		blocksFetched.setText("0");
		doInsert.setEnabled(false);
		doModify.setEnabled(false);
		doDelete.setEnabled(false);
		doMerge.setEnabled(false);
	}

	private void populateToolbar() {
		// FETCH NEXT BLOCK
		nextBlock = new JButton("Fetch Next Block");
		nextBlock.setEnabled(false);
		nextBlock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		toolbar.add(blockFactor);
		toolbar.addSeparator();

		// Search
		doSearch = new JButton("Search");
		doSearch.setEnabled(false);
		toolbar.add(doSearch);
		toolbar.addSeparator();

		// Counter:
		toolbar.add(new JLabel("Number of blocks fetched:"));
		blocksFetched = new JTextField("0");
		blocksFetched.setEditable(false);
		blocksFetched.setEnabled(false);
		blocksFetched.setPreferredSize(new Dimension(40, 30));
		blocksFetched.setHorizontalAlignment(SwingConstants.CENTER);
		toolbar.add(blocksFetched);
		toolbar.addSeparator();
		
		// Insert Modify Delete
		doInsert = new JButton("Insert");
		toolbar.add(doInsert);
		doInsert.setEnabled(false);
		doModify = new JButton("Modify");
		toolbar.add(doModify);
		doModify.setEnabled(false);
		doDelete = new JButton("Delete");
		toolbar.add(doDelete);
		doDelete.setEnabled(false);
		doMerge = new JButton("Merge");
		toolbar.add(doMerge);
		doMerge.setEnabled(false);
		toolbar.addSeparator();

	}

	public Entity getSelectedEntity() {
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
		TablePanel panel = new TablePanel(entity, mainTable); //
		TabComponent tabComponent = new TabComponent(tabs, entity);
		tabs.addTab(entity.getName(), panel);
		tabs.setTabComponentAt(tabs.indexOfComponent(panel), tabComponent);
		tabs.setSelectedComponent(panel);
		return true;
	}

	public JTabbedPane getTabs() {
		return tabs;
	}

	public JButton getNextBlock() {
		return nextBlock;
	}

	public JSpinner getBlockFactor() {
		return blockFactor;
	}

	public JButton getDoSearch() {
		return doSearch;
	}

	public JTextField getBlocksFetched() {
		return blocksFetched;
	}
	

	public JButton getDoInsert() {
		return doInsert;
	}

	public JButton getDoModify() {
		return doModify;
	}

	public JButton getDoDelete() {
		return doDelete;
	}

	public JButton getDoMerge() {
		return doMerge;
	}

}

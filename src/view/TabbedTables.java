package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
import model.Record;
import model.Relation;
import model.datatypes.CharType;
import model.datatypes.DateType;
import model.datatypes.VarCharType;
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
		if (mainTable) {
			this.add(toolbar, "grow, wrap, height 50px");
			toolbar.setVisible(true);
		} else {
			JLabel relations = new JLabel("Relations:");
			relations.setFont(new Font("Garamond", Font.BOLD, 20));
			this.add(relations, "grow, wrap, height 50px");
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
			} else {
				doSearch.setEnabled(false);
			}
			blocksFetched.setEnabled(true);
			if (entity instanceof SequentialFile) {
				doInsert.setEnabled(true);
				doModify.setEnabled(true);
				doDelete.setEnabled(true);
				doMerge.setEnabled(true);
			} else {
				doInsert.setEnabled(false);
				doModify.setEnabled(false);
				doDelete.setEnabled(false);
				doMerge.setEnabled(false);
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
		blocksFetched.setPreferredSize(new Dimension(100, 30));
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

	public Record getSelectedRow() {
		Entity entity = getSelectedEntity();
		if (entity == null) {
			return null;
		}
		TablePanel panel = ((TablePanel) tabs.getSelectedComponent());
		int selectedRow = panel.getTable().getSelectedRow();
		if (selectedRow == -1) {
			return null;
		}
		return panel.getTableModel().getRecordAt(selectedRow);
	}

	public String objectToString(Object o) throws Exception {
		if (o instanceof Boolean) {
			return ((Boolean) o).toString();
		} else if (o instanceof CharType) {
			return ((CharType) o).toString();
		} else if (o instanceof VarCharType) {
			return ((VarCharType) o).toString();
		} else if (o instanceof Integer) {
			return ((Integer) o).toString();
		} else if (o instanceof DateType) {
			return ((DateType) o).toString();
		} else {
			throw new Exception("Alo druskane pa taj tip nije podrzan.");
		}
	}

	public TablePanel addTab(Entity entity) {
		if (mainTable) {
			for (Component c : tabs.getComponents()) {
				if (c instanceof TablePanel) {
					TablePanel panel = (TablePanel) c;
					if (panel.getEntity() == entity) {
						tabs.setSelectedComponent(c);
						return null;
					}
				}
			}
		}
		TablePanel panel = new TablePanel(entity, mainTable); //
		TabComponent tabComponent = new TabComponent(tabs, entity);
		tabs.addTab(entity.getName(), panel);
		tabs.setTabComponentAt(tabs.indexOfComponent(panel), tabComponent);
		tabs.setSelectedComponent(panel);
		return panel;
	}

	public void populateWithChildren(Record record) {
		tabs.removeAll();
		if (record == null) {
			return;
		}
		// VELIKI REFACTORING
		SequentialFile referencedEntity = ((SequentialFile) record.getEntity());
		for (Relation r : referencedEntity.getInverseRelations()) {
			SequentialFile referringEntity = ((SequentialFile) r.getReferringAttribute().getParent());

			// referenced value to str
			Object referencedValue = record.getAttributes().get(r.getReferencedAttribute());
			String referencedValueStr = "";
			try {
				referencedValueStr = objectToString(referencedValue);
			} catch (Exception ex) {
				// todo
				System.out.println("Failed to convert obj to str.");
			}

			// build terms
			int numAttrs = referringEntity.getAttributes().size();
			String[] terms = new String[numAttrs];
			for (int i = 0; i < numAttrs; i++) {
				if (referringEntity.getAttributes().get(i) == r.getReferringAttribute()) {
					terms[i] = referencedValueStr;
				} else {
					terms[i] = "";
				}
			}

			/*
			 * System.out.println(referringEntity + " " +
			 * r.getReferringAttribute()); System.out.println(referencedEntity +
			 * " " + r.getReferencedAttribute()); for(String term : terms) {
			 * System.out.println("\"" + term + "\""); }
			 * System.out.println(referencedValueStr);
			 */

			// VRATI POINTER
			int filePointer = referringEntity.getFilePointer();
			ArrayList<Record> results = referringEntity.findRecord(terms, true, false, true);
			referringEntity.setFilePointer(filePointer);
			System.out.println(results.size());

			// dodaj tabelu
			TablePanel panel = addTab(referringEntity);
			if (panel == null) {
				System.out.println("Panel je null!");
			} else {
				panel.getTableModel().setCurrentBlock(results);
			}

		}
		// sve childrene
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

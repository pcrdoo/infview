package view;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

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
import javafx.scene.control.ToolBar;
import model.Attribute;
import model.Entity;
import model.Record;
import model.Relation;
import model.Table;
import model.datatypes.CharType;
import model.datatypes.DateType;
import model.datatypes.VarCharType;
import model.files.File;
import model.files.SequentialFile;
import net.miginfocom.swing.MigLayout;

public class TabbedTables extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1167886373482792472L;
	private JPanel toolbarArea;
	private JToolBar fileToolbar;
	private JToolBar dbToolbar;
	private JToolBar emptyToolbar;
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

	private JButton doDbFetch, doDbAdd, doDbUpdate, doDbFilter, doDbSort;

	public TabbedTables(boolean mainTable) {
		this.setLayout(new MigLayout("fill", "", "0[]0[grow]0"));

		// Toolbars
		emptyToolbar = new JToolBar();
		emptyToolbar.setFloatable(false);
		populateFileToolbar();
		populateDbToolbar();

		toolbarArea = new JPanel(new CardLayout());
		toolbarArea.add(emptyToolbar, "empty");
		toolbarArea.add(fileToolbar, "file");
		toolbarArea.add(dbToolbar, "db");
		this.add(toolbarArea, "grow, wrap, height 50px");
		toolbarArea.setVisible(true);

		setToolbar("empty");

		// Go
		if (!mainTable) {
			JLabel relations = new JLabel("Relations:");
			relations.setFont(new Font("Garamond", Font.PLAIN, 20));
			this.add(relations, "grow, wrap, height 50px");
		}
		tabs = new JTabbedPane();
		this.add(tabs, "grow, height 250px");
		this.mainTable = mainTable;
		new TabbedTablesController(this);
	}

	public void enableToolbar(Entity entity) {
		if (entity instanceof File) {
			setToolbar("file"); 
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
		} else {
			// za table sta vec treba nezavisno od toolbara
			setToolbar("db");
		}
	}

	public void disableToolbar() {
		setToolbar("empty");
	}

	private void setToolbar(String cardID) {
		CardLayout cardLayout = (CardLayout) toolbarArea.getLayout();
		cardLayout.show(toolbarArea, cardID);
		toolbarArea.revalidate();
		toolbarArea.repaint();
	}

	private void populateFileToolbar() {
		fileToolbar = new JToolBar();
		fileToolbar.setFloatable(false);
		// FETCH NEXT BLOCK
		nextBlock = new JButton("Fetch Next Block");
		nextBlock.setEnabled(false);
		nextBlock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		fileToolbar.add(nextBlock);
		fileToolbar.addSeparator();
		fileToolbar.add(new JLabel("Block Factor: "));

		// Block factor spinner
		SpinnerModel numberModel = new SpinnerNumberModel(20, 1, 100, 1);
		blockFactor = new JSpinner(numberModel);
		blockFactor.setEnabled(false);
		JFormattedTextField field = (JFormattedTextField) blockFactor.getEditor().getComponent(0);
		DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
		formatter.setCommitsOnValidEdit(true);
		fileToolbar.add(blockFactor);
		fileToolbar.addSeparator();

		// Search
		doSearch = new JButton("Search");
		doSearch.setEnabled(false);
		fileToolbar.add(doSearch);
		fileToolbar.addSeparator();

		// Counter:
		fileToolbar.add(new JLabel("Number of blocks fetched:"));
		blocksFetched = new JTextField("0");
		blocksFetched.setEditable(false);
		blocksFetched.setEnabled(false);
		blocksFetched.setPreferredSize(new Dimension(100, 30));
		blocksFetched.setHorizontalAlignment(SwingConstants.CENTER);
		fileToolbar.add(blocksFetched);
		fileToolbar.addSeparator();

		// Insert Modify Delete
		doInsert = new JButton("Insert");
		fileToolbar.add(doInsert);
		doInsert.setEnabled(false);
		doModify = new JButton("Modify");
		fileToolbar.add(doModify);
		doModify.setEnabled(false);
		doDelete = new JButton("Delete");
		fileToolbar.add(doDelete);
		doDelete.setEnabled(false);
		doMerge = new JButton("Merge");
		fileToolbar.add(doMerge);
		doMerge.setEnabled(false);
		fileToolbar.addSeparator();

	}

	private void populateDbToolbar() {
		dbToolbar = new JToolBar();
		dbToolbar.setFloatable(false);

		// OPS
		doDbFetch = new JButton("Fetch");
		dbToolbar.add(doDbFetch);
		doDbFetch.setEnabled(true);

		doDbAdd = new JButton("Add");
		dbToolbar.add(doDbAdd);
		doDbAdd.setEnabled(true);

		doDbUpdate = new JButton("Update");
		dbToolbar.add(doDbUpdate);
		doDbUpdate.setEnabled(true);

		doDbFilter = new JButton("Filter");
		dbToolbar.add(doDbFilter);
		doDbFilter.setEnabled(true);

		doDbSort = new JButton("Sort");
		dbToolbar.add(doDbSort);
		doDbSort.setEnabled(true);

		dbToolbar.addSeparator();

	}

	public Entity getSelectedEntity() {
		if (tabs.getSelectedComponent() == null) {
			return null;
		}
		return ((TablePanel) tabs.getSelectedComponent()).getEntity();
	}

	public void setSelectedRecord(Record record) {
		if (tabs.getSelectedComponent() == null) {
			return;
		}
		TablePanel panel = ((TablePanel) tabs.getSelectedComponent());
		int idx = panel.getTableModel().getRecordIndex(record);
		if (idx != -1) {
			panel.getTable().setRowSelectionInterval(idx, idx);
		}
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
		System.out.println(mainTable + "!!");
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
		Entity referencedEntity = record.getEntity();
		for (Relation r : referencedEntity.getInverseRelations()) {
			Entity referringEntity = (Entity) (r.getReferringAttributes().get(0).getParent());

			// referenced value to str
			Object referencedValue;
			String referencedValueStr;

			// map referrring attribute to referenced attr. value
			HashMap<Attribute, Object> fkMap = new HashMap<>();
			for (int i = 0; i < r.getReferringAttributes().size(); i++) {
				referencedValue = record.getAttributes().get(r.getReferencedAttributes().get(i));
				try {
					fkMap.put(r.getReferringAttributes().get(i), referencedValue);
				} catch (Exception e) {
					System.out.println(e);
				}
			}

			ArrayList<Record> results = new ArrayList<Record>();
			if (referringEntity instanceof SequentialFile) {
				SequentialFile referringFile = (SequentialFile) referringEntity;
				int numAttrs = referringFile.getAttributes().size();
				String[] terms = new String[numAttrs];
				for (int i = 0; i < numAttrs; i++) {
					Attribute currAttr = referringFile.getAttributes().get(i);
					if (r.getReferringAttributes().contains(currAttr)) {
						try {
							terms[i] = objectToString(fkMap.get(currAttr));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						terms[i] = "";
					}
				}
				// VRATI POINTER
				int filePointer = referringFile.getFilePointer();
				results = referringFile.findRecord(terms, true, false, true);
				referringFile.setFilePointer(filePointer);
			} else if (referringEntity instanceof Table) {
				Table referringTable = (Table) referringEntity;
				try {
					results = referringTable.findRecordsByFk(fkMap);
				} catch (SQLException e) {
					System.out.println("Sql exception in findRecordsByFk: " + e.getMessage());
					e.printStackTrace();
				}
			}
			// dodaj tabelu
			TablePanel panel = addTab(referringEntity);
			if (panel != null) {
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

	public JButton getDoDbFetch() {
		return doDbFetch;
	}

	public JButton getDoDbAdd() {
		return doDbAdd;
	}

	public JButton getDoDbUpdate() {
		return doDbUpdate;
	}

	public JButton getDoDbFilter() {
		return doDbFilter;
	}

	public JButton getDoDbSort() {
		return doDbSort;
	}

}

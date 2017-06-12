package view;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

import controller.TableController;
import controller.UpdateBlockListener;
import model.Entity;
import model.InfTableModel;
import model.Record;
import model.Table;
import model.files.File;
import model.files.IndexedSequentialFile;
import model.files.SequentialFile;
import net.miginfocom.swing.MigLayout;

public class TablePanel extends JPanel implements UpdateBlockListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8033998611208949143L;
	// private BottomPanelController bottomPanelController;
	private JTable table;
	private InfTableModel tableModel;

	private Entity entity;

	public TablePanel(Entity entity, boolean autoRefresh) {
		this.entity = entity;
		this.setLayout(new MigLayout("fill", "0[]0", "0[]0"));
		tableModel = new InfTableModel(entity);
		table = new JTable(tableModel);
		// table.setPreferredScrollableViewportSize(new Dimension(900, 300));
		table.setFillsViewportHeight(true);
		((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		table.getTableHeader().setReorderingAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		if (autoRefresh) {
			entity.addUpdateBlockListener(this);
		}
		add(new JScrollPane(table), "grow");
		if (autoRefresh) {
			if (entity instanceof Table
					|| (entity instanceof SequentialFile && !(entity instanceof IndexedSequentialFile))) {
				new TableController(table);
			}
		}
	}

	public void blockUpdated(ArrayList<Record> currentBlock) {
		tableModel.setCurrentBlock(currentBlock);
		tableModel.fireTableDataChanged();
	}

	public JTable getTable() {
		return table;
	}

	public Entity getEntity() {
		return entity;
	}

	public InfTableModel getTableModel() {
		return tableModel;
	}
}

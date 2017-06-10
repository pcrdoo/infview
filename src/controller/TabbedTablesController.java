package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Attribute;
import model.Entity;
import model.Record;
import model.Table;
import model.Warehouse;
import model.files.File;
import model.files.IndexedSequentialFile;
import model.files.InvalidRecordException;
import model.files.SequentialFile;
import view.GenericDialog;
import view.MainView;
import view.TabbedTables;
import view.search.DBSearchDialog;

public class TabbedTablesController {

	TabbedTables tt;

	public TabbedTablesController(TabbedTables tt) {
		this.tt = tt;
		tt.getTabs().addChangeListener(new TabChangeListener());
		tt.getNextBlock().addActionListener(new NextBlockClickListener());
		tt.getDoSearch().addActionListener(new SearchClickListener());
		tt.getBlockFactor().addChangeListener(new BlockFactorChangeListener());

		tt.getDoInsert().addActionListener(new InsertClickListener());
		tt.getDoModify().addActionListener(new ModifyClickListener());
		tt.getDoDelete().addActionListener(new DeleteClickListener());
		tt.getDoMerge().addActionListener(new MergeClickListener());

		tt.getDoDbFetch().addActionListener(new DbFetchClickListener());
		tt.getDoDbAdd().addActionListener(new DbAddClickListener());
		tt.getDoDbUpdate().addActionListener(new DbUpdateClickListener());
		tt.getDoDbFilter().addActionListener(new DbFilterClickListener());
		tt.getDoDbSort().addActionListener(new DbSortClickListener());
	}

	// Table

	private class TabChangeListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			Entity entity = tt.getSelectedEntity();
			if (entity != null) {
				tt.enableToolbar(entity);
				if (entity instanceof IndexedSequentialFile) {
					MainView.getInstance().getDesktopView().detachDetailsTable();
					MainView.getInstance().getDesktopView()
							.attachIndexTree(((IndexedSequentialFile) entity).getTree().getRoot());
				} else if (entity instanceof SequentialFile && !(entity instanceof IndexedSequentialFile)) {
					MainView.getInstance().getDesktopView().attachDetailsTable();
					MainView.getInstance().getDesktopView().detachIndexTree();
				} else {
					MainView.getInstance().getDesktopView().detachDetailsTable();
					MainView.getInstance().getDesktopView().detachIndexTree();
				}
			} else {
				tt.disableToolbar();
				MainView.getInstance().getDesktopView().detachDetailsTable();
				MainView.getInstance().getDesktopView().detachIndexTree();
			}

		}

	}

	// Db

	private class DbFetchClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Entity entity = tt.getSelectedEntity();
			/*
			 * if (!(entity instanceof Table)) { return; } Table table = (Table)
			 * entity;
			 */
			ArrayList<Record> records = new ArrayList<>();
			StringBuilder stmtBuilder = new StringBuilder();
			stmtBuilder.append("SELECT * FROM ");
			stmtBuilder.append("proizvodna_hala");
			try {
				PreparedStatement stmt = Warehouse.getInstance().getDbConnection()
						.prepareStatement(stmtBuilder.toString());
				ResultSet results = stmt.executeQuery();
				Record record = new Record(entity);
				if (results.getMetaData().getColumnCount() != entity.getAttributes().size()) {
					throw new Exception("Metaschema and database out of sync.");
				}
				while (results.next()) {
					for (Attribute attr : entity.getAttributes()) {
						Object value = results.getObject(attr.getName());
						record.addAttribute(attr, value);
					}
					records.add(record);
				}
				// obavezno je zatvaranje Statement i ResultSet objekta
				entity.fireUpdateBlockPerformed(records); // ozvezavanje
				results.close();
				stmt.close();
			} catch (SQLException ex) {
				System.out.println("SQL error: " + ex);
			} catch (Exception ex) {
				System.out.println(ex);
			}

		}

	}

	private class DbAddClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Entity entity = tt.getSelectedEntity();
			// Todo: DB Add
		}
	}

	private class DbUpdateClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Entity entity = tt.getSelectedEntity();
			// Todo: DB Update
		}
	}

	private class DbFilterClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Entity entity = tt.getSelectedEntity();
			// Todo: DB Filter
			new DBSearchDialog(entity).setVisible(true);
			
			System.out.println("KLIK FILTER");
		}
	}

	private class DbSortClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Entity entity = tt.getSelectedEntity();
			// Todo: DB Sort
		}
	}

	// Files

	private class BlockFactorChangeListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			Entity entity = tt.getSelectedEntity();
			if (entity != null && entity instanceof File) {
				((File) entity).setBlockFactor((Integer) tt.getBlockFactor().getValue());
			}
		}
	}

	private class SearchClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			GenericDialog d = new GenericDialog(tt.getSelectedEntity(), null, true, true, true);
			d.setModal(true);
			d.setVisible(true); // block!

			if (!d.isDoShit())
				return;

			if (tt.getSelectedEntity() instanceof SequentialFile) {
				SequentialFile file = (SequentialFile) tt.getSelectedEntity();
				file.fireUpdateBlockPerformed(
						file.findRecord(d.getTerms(), d.getFindAll(), d.getToFile(), d.getFromStart())); // ozvezavanje
																											// tabele
			}

			if (tt.getSelectedEntity() instanceof File) {
				File file = (File) tt.getSelectedEntity();
				tt.getBlocksFetched().setText(String.valueOf(file.getBlocksFetched()));
			}
		}
	}

	private class NextBlockClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Entity entity = tt.getSelectedEntity();
			if (entity != null && entity instanceof File) {
				try {
					ArrayList<Record> currentBlock = ((File) entity).fetchNextBlock();
					entity.fireUpdateBlockPerformed(currentBlock == null ? new ArrayList<Record>() : currentBlock); // ozvezavanje
					// tabele
					tt.getBlocksFetched().setText(String.valueOf(((File) entity).getBlocksFetched()));
				} catch (IOException | InvalidRecordException ex) {
					System.out.println("Invalid blockfetch");
					ex.printStackTrace();
				}
			}
		}

	}

	private class InsertClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			GenericDialog genericDialog = new GenericDialog(tt.getSelectedEntity(), null, false, true, false);
			genericDialog.setModal(true);
			genericDialog.setVisible(true);

			if (!genericDialog.isDoShit())
				return;

			Entity entity = tt.getSelectedEntity();

			if (entity instanceof SequentialFile) {
				try {
					((SequentialFile) entity).addRecord(genericDialog.getRecord());
				} catch (IOException e1) {
					System.out.println(e1);
					e1.printStackTrace();
				}
			}
		}
	}

	private class ModifyClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (tt.getSelectedRow() == null)
				return;

			GenericDialog genericDialog = new GenericDialog(tt.getSelectedEntity(), tt.getSelectedRow(), false, false,
					false);
			genericDialog.setModal(true);
			genericDialog.setVisible(true);

			if (!genericDialog.isDoShit())
				return;

			Entity entity = tt.getSelectedEntity();

			if (entity instanceof SequentialFile) {
				try {
					((SequentialFile) entity).updateRecord(genericDialog.getRecord());
				} catch (IOException e1) {
					System.out.println(e1);
					e1.printStackTrace();
				}
			}
		}
	}

	private class DeleteClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (tt.getSelectedRow() == null)
				return;

			Entity entity = tt.getSelectedEntity();

			if (entity instanceof SequentialFile) {
				try {
					((SequentialFile) entity).deleteRecord(tt.getSelectedRow());
				} catch (Exception e1) {
					System.out.println(e1);
					e1.printStackTrace();
				}
			}
		}
	}

	private class MergeClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Entity entity = tt.getSelectedEntity();

			if (!(entity instanceof SequentialFile)) {
				JOptionPane.showMessageDialog(tt, "Cannot merge non-sequential file formats");
				return;
			}

			try {
				((SequentialFile) entity).merge();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}

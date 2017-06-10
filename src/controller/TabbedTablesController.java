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
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Attribute;
import model.Entity;
import model.InvalidLengthException;
import model.Record;
import model.Table;
import model.Warehouse;
import model.files.File;
import model.files.IndexedSequentialFile;
import model.files.InvalidRecordException;
import model.files.SequentialFile;
import view.GenericDialog;
import view.MainView;
import view.SQLErrorDialog;
import view.TabbedTables;
import view.search.DBGenericDialog;

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

	// Db

	private class DbFetchClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Entity entity = tt.getSelectedEntity();
			if (!(entity instanceof Table)) {
				return;
			}
			Table table = (Table) entity;
			try {
				table.fetchRecords();
			} catch (SQLException ex) {

				new SQLErrorDialog(ex).launch();
			} catch (Exception ex) {
				System.err.println("gusim jastukom: " + ex.toString());
			}
		}

	}

	/*
	 * Realizovati metodu addRecord() koja služi za dodavanje sloga u tabelu.
	 * Metoda treba da bude realizovana korišćenjem objekta PreparedStatement.
	 * Sve SQLException-e u slučaju neuspešnog dodavanja sloga prikazivati kroz
	 * JOptionPane. Nakon uspešnog dodavanja sloga, sadržaj tabele treba da bude
	 * osvežen (ponovo pročitan iz baze podataka) i u tabeli treba da bude
	 * selektovan dodati slog.
	 */
	private class DbAddClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Entity entity = tt.getSelectedEntity();

			if (!(entity instanceof Table)) {
				return;
			}
			Table table = (Table) entity;
			// Neki gui dialog koji to sredjuje i vraca record za dodati
			GenericDialog addDialog = new GenericDialog(entity, null, false, true, false);
			addDialog.setVisible(true);

			if (addDialog.isClosed()) {
				return;
			}
			
			Record record = addDialog.getRecord();
			
			try {
				table.addRecord(record);
				table.fetchRecords();
				tt.setSelectedRecord(record);
			} catch (SQLException ex) {
				new SQLErrorDialog(ex).launch();
			} catch (Exception ex) {
				System.err.println("gusim jastukom: " + ex.toString());
			}

		}

	}

	/*
	 * Realizovati metodu updateRecord() koja služi za izmenu selektovanog
	 * sloga. Izmena sloga se radi na osnovu primarnog ključa. Dozvoljena je i
	 * izmena vrednosti primarnog ključa. Metoda treba da bude realizovana
	 * korišćenjem objekta PreparedStatement. Sve SQLException-e u slučaju
	 * neuspešnog dodavanja sloga prikazivati kroz JOptionPane. Nakon uspešne
	 * izmene sloga, sadržaj tabele treba da bude osvežen (ponovo pročitan iz
	 * baze podataka) i u tabeli treba da bude selektovan izmenjeni slog.
	 */
	private class DbUpdateClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Entity entity = tt.getSelectedEntity();

			if (!(entity instanceof Table)) {
				return;
			}
			Table table = (Table) entity;
			// Neki gui dialog koji to sredjuje i vraca record za updateovati
			Record record = tt.getSelectedRow();
			if (record == null) {
				return;
			}
			
			GenericDialog updateDialog = new GenericDialog(entity, record, false, true, false);
			updateDialog.setVisible(true);

			if (updateDialog.isClosed()) {
				return;
			}
			
			Record newRecord = updateDialog.getRecord();
			try {
				table.updateRecord(record, newRecord);
				table.fetchRecords();
				tt.setSelectedRecord(record);
			} catch (SQLException ex) {
				new SQLErrorDialog(ex).launch();
			} catch (Exception ex) {
				System.err.println("gusim jastukom: " + ex.toString());
			}
		}
	}

	/*
	 * Realizovati metodu findFilterRecord() koja služi za pretragu tabele po
	 * zadatim parametrima pretrage. Pretraga po zadatom kriterijumu uz
	 * mogućnost korišćenja specijalnog karaktera % za VARCHAR i CHAR polja, kao
	 * i operator =,>,< za NUMERIC i INTEGER polja. Metoda treba da bude
	 * realizovana korišćenjem objekta PreparedStatement. Kao rezultat metode u
	 * tabeli se prikazuju samo oni slogovi koji odgovaraju parametrima
	 * pretrage.
	 */
	private class DbFilterClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Entity entity = tt.getSelectedEntity();

			if (!(entity instanceof Table)) {
				return;
			}
			Table table = (Table) entity;
			DBGenericDialog dialog = new DBGenericDialog(entity, true);
			dialog.setVisible(true);

			if (dialog.isClosed()) {
				return;
			}
			
			try {
				if (!dialog.getFilterParams().getObjects().isEmpty()) {
					table.filterRecords(dialog.getFilterParams(), "");
				}
			} catch (SQLException ex) {
				new SQLErrorDialog(ex).launch();
			} catch (InvalidRecordException ex) {
				System.out.println("Invalid record ex: " + ex);
			} catch (InvalidLengthException ex) {
				System.out.println("Invalid len ex: " + ex);
			}
		}
	}

	/*
	 * Realizovati metodu sortMDI() klase UIDBFile koja služi za sortiranje
	 * slogova po zadatim kriterijumima. Metoda treba da bude realizovana
	 * korišćenjem objekta Statement.
	 */
	private class DbSortClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Entity entity = tt.getSelectedEntity();

			if (!(entity instanceof Table)) {
				return;
			}
			Table table = (Table) entity;

			DBGenericDialog dialog = new DBGenericDialog(entity, false);

			dialog.setVisible(true);

			if (dialog.isClosed()) {
				return;
			}
			
			try {
				table.sortRecords(dialog.getFilterParams().getQuery());
			} catch (SQLException ex) {
				new SQLErrorDialog(ex).launch();
			} catch (InvalidRecordException e1) {
				e1.printStackTrace();
			}
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

			if (d.isClosed())
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

			if (genericDialog.isClosed())
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

			if (genericDialog.isClosed())
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
				} else if ((entity instanceof SequentialFile && !(entity instanceof IndexedSequentialFile)) || entity instanceof Table) {
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
}

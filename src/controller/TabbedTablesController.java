package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Entity;
import model.Record;
import model.files.File;
import model.files.IndexedSequentialFile;
import model.files.InvalidRecordException;
import model.files.SequentialFile;
import view.MainView;
import view.SearchDialog;
import view.TabbedTables;

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
	}

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
			SearchDialog d = new SearchDialog(tt.getSelectedEntity(), null);
			d.setModal(true);
			d.setVisible(true); // block!
			
			if (tt.getSelectedEntity() instanceof SequentialFile) {
				SequentialFile file = (SequentialFile) tt.getSelectedEntity();
				file.fireUpdateBlockPerformed(file.findRecord(d.getTerms(), d.getFindAll(), d.getToFile(), d.getFromStart())); // ozvezavanje tabele
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
					((File) entity).fireUpdateBlockPerformed(currentBlock == null ? new ArrayList<Record>() : currentBlock); // ozvezavanje
					// tabele
					tt.getBlocksFetched().setText(String.valueOf(((File) entity).getBlocksFetched()));
				} catch (IOException | InvalidRecordException ex) {
					System.out.println("Invalid blockfetch");
					ex.printStackTrace();
				}
			}
		}

	}

	private class TabChangeListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			Entity entity = tt.getSelectedEntity();
			if (entity != null) {
				tt.enableToolbar(entity);
				if (entity instanceof SequentialFile && !(entity instanceof IndexedSequentialFile)) {
					MainView.getInstance().getDesktopView().attachDetailsTable();
					MainView.getInstance().getDesktopView().detachIndexTree();
					
				} else if (entity instanceof IndexedSequentialFile) {
					MainView.getInstance().getDesktopView().detachDetailsTable();
					MainView.getInstance().getDesktopView().attachIndexTree(((IndexedSequentialFile)entity).getTree().getRoot());
				}
			} else {
				tt.disableToolbar();
				MainView.getInstance().getDesktopView().detachDetailsTable();
				MainView.getInstance().getDesktopView().detachIndexTree();
			}

		}

	}

	private class InsertClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			SearchDialog searchDialog = new SearchDialog(tt.getSelectedEntity(), null);
			searchDialog.setModal(true);
			searchDialog.setVisible(true);
			
			Entity entity = tt.getSelectedEntity();
			
			if (entity instanceof SequentialFile) {
				try {
					((SequentialFile)entity).addRecord(searchDialog.getRecord());
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
			Record r = tt.getSelectedRow();
			SearchDialog searchDialog = new SearchDialog(tt.getSelectedEntity(), null);
			searchDialog.setModal(true);
			searchDialog.setVisible(true); // block!
		}
	}

	private class DeleteClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
//			SearchDialog searchDialog = new SearchDialog(tt.getSelectedEntity());
//			searchDialog.setModal(true);
//			searchDialog.setVisible(true); // block!
		}
	}

	private class MergeClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
//			SearchDialog searchDialog = new SearchDialog(tt.getSelectedEntity());
//			searchDialog.setModal(true);
//			searchDialog.setVisible(true); // block!
		}
	}
}

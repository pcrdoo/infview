package model.files;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.event.EventListenerList;

import model.Attribute;
import model.Entity;
import model.InfResource;
import model.Record;

public abstract class File extends Entity {
	private ArrayList<UpdateBlockListener> updateBlockListeners;
	
	public File(String name, String path, InfResource parent) {
		super(name, parent);
		// TODO Auto-generated constructor stub
		currentBlock = new ArrayList<Record>();
		this.path = path;
	}

	public abstract boolean fetchNextBlock() throws IOException;

	public abstract boolean addRecord(ArrayList<String> record) throws IOException;

	public abstract boolean updateRecord(ArrayList<String> record) throws IOException;

	public abstract int findRecord(int TODO); // TODO

	public abstract boolean deleteRecord(ArrayList<String> record);

	protected int blockFactor = 20; // velicina bloka u slogovima, menja
									// korisnik!
	protected int recordSize = 0; // velicina sloga u bajtovima, setuje se kad
									// se dodaje atribut
	// BUFFER SIZE = BLOCK_FACTOR * RECORD_SIZE
	// protected int FILE_SIZE = 0; // velicina datoteke u bajtovima
	protected int numRecords = 0; // broj slogova u datoteci
	// protected int NUM_BLOCKS = 0; // broj blokova u datoteci
	protected int filePointer = 0; // pokazivac dokle smo stigli

	protected String path; // full path do entiteta, constructor TODO
	// protected boolean directory;

	protected byte[] buffer;

	protected ArrayList<Record> currentBlock; // trenutno ucitan blok

	public ArrayList<Record> getCurrentBlock() {
		return currentBlock;
	}

	public int getBlockFactor() {
		return blockFactor;
	}

	public void setBlockFactor(int blockFactor) {
		this.blockFactor = blockFactor;
	}

	@Override
	public void addAttribute(Attribute newAttribute) {
		if (newAttribute == null)
			return;
		if (this.attributes == null)
			this.attributes = new ArrayList<Attribute>();
		if (!this.attributes.contains(newAttribute)) {
			recordSize += newAttribute.getLength(); // povecaj record size!
			this.attributes.add(newAttribute);
		}
	}

	@Override
	public void removeAttribute(Attribute oldAttribute) {
		if (oldAttribute == null)
			return;
		if (this.attributes != null)
			if (this.attributes.contains(oldAttribute)) {
				recordSize -= oldAttribute.getLength(); // smanjio record size!
				this.attributes.remove(oldAttribute);
			}
	}

	@Override
	public void removeAllAttributes() {
		if (attributes != null) {
			recordSize = 0;

			attributes.clear();
		}
	}

	/*
	 * // lista slušača koja se koristi da se osveži prikaz tabele u klasi
	 * FileView // prilikom učitavanja novog bloka iz datoteke
	 * 
	 * EventListenerList listenerBlockList = new EventListenerList();
	 * UpdateBlockEvent updateBlockEvent = null;
	 * 
	 */
	public void addUpdateBlockListener(UpdateBlockListener l) {
		updateBlockListeners.add(l);
	}
	
	public void removeUpdateBlockListener(UpdateBlockListener l) {
		updateBlockListeners.remove(l);
	}

	protected void fireUpdateBlockPerformed() {
		for (UpdateBlockListener listener : updateBlockListeners) {
			listener.blockUpdated();
		}
	}

	// ;

	// TODO: listeners IZNAD
	// TODO: ucitavanje iz serial ispod
}

package model.files;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import model.InfResource;

public class SerialFile extends File {


	public SerialFile(String name, String path, InfResource parent) {
		super(name, path, parent);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean fetchNextBlock() throws IOException {
		RandomAccessFile file = new RandomAccessFile(path, "r");
		numRecords = (int) Math.ceil(((double)file.length()) / recordSize);
/*
		// ukupan broj blokova u datoteci
		// (+1) se dodaje u slucaju da poslednji blok nije potpuno popunjen sa
		// slogovima
		// ovo se desava u 99%, za 1% BLOCK_NUM nije tacan
		BLOCK_NUM = (int) (RECORD_NUM / BLOCK_FACTOR) + 1;

		// BUFFER_SIZE je uvek jednak broju slogova u jednom bloku * velicina
		// jednog sloga
		// izuzev ako poslednji blok nije pun blok
		if (FILE_POINTER / RECORD_SIZE + BLOCK_FACTOR > RECORD_NUM)
			// na redu je citanje poslednjeg bloka
			BUFFER_SIZE = (int) (RECORD_NUM - FILE_POINTER / RECORD_SIZE) * RECORD_SIZE;
		else
			BUFFER_SIZE = (int) (RECORD_SIZE * BLOCK_FACTOR);

		buffer = new byte[BUFFER_SIZE];
		data = new String[(int) BUFFER_SIZE / RECORD_SIZE][];
		// po otvaranju pozicioniram se na prethodni kraj zahvata
		afile.seek(FILE_POINTER);
		afile.read(buffer);

		// pretvaranje procitanog niza bajtova u String
		String contentS = new String(buffer);

		// poravnanje zbog eventualne greske u encodingu, trenutno nepotrebno
		if (contentS.length() < buffer.length) {
			for (int x = contentS.length(); x < buffer.length; x++)
				contentS = contentS + " ";
		}

		// prolazi se slog po slog iz datoteke
		for (int i = 0; i < BUFFER_SIZE / RECORD_SIZE; i++) {
			// svaki slog predstavlja jednu liniju teksta
			String line = contentS.substring(i * RECORD_SIZE, i * RECORD_SIZE + RECORD_SIZE);
			// svaki red u matrici podataka ima velicinu jednaku broju polja u
			// slogu
			data[i] = new String[fields.size()];
			int k = 0;
			// prolazi se polje po polje iz sloga
			for (int j = 0; j < fields.size(); j++) {
				String field = null;
				field = line.substring(k, k + fields.get(j).getFieldLength());
				data[i][j] = field;
				k = k + fields.get(j).getFieldLength();
			}
		}

		// pozicioniramo file pointer tamo gde smo stali sa citanjem
		FILE_POINTER = afile.getFilePointer();
		afile.close();

		// ucitavanje novog bloka treba da izazove osvezavanje podataka u tabeli
		fireUpdateBlockPerformed();
*/
		return true;

	}

	@Override
	public boolean addRecord(ArrayList<String> record) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateRecord(ArrayList<String> record) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int findRecord(int TODO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean deleteRecord(ArrayList<String> record) {
		// TODO Auto-generated method stub
		return false;
	}

}

package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import model.Attribute;
import model.Entity;
import model.Package;
import model.Record;
import model.Warehouse;
import view.MainView;

public class Main {

	public static void main(String[] args) {
		try {
			InputStream in = Main.class.getResourceAsStream("/res/metaschema.json");
			BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String fileString = "";
			String line;
			while ((line = br.readLine()) != null) {
				fileString += line;
			}
			Warehouse.getInstance().loadWarehouse(fileString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MainView mainView = MainView.getInstance();
		mainView.setVisible(true);
		// mock();
	}

	public static void mock() {
		Package geo = Warehouse.getInstance().getPackages().get(0);
		Package sport = Warehouse.getInstance().getPackages().get(1);
		doLoad("src/res/mocks/MojaBaza/Drzava.txt,Drzava", geo);
		doLoad("src/res/mocks/MojaBaza/FudbalskiKlub.txt,FudbalskiKlub", sport);
		doLoad("src/res/mocks/MojaBaza/Grad.txt,Grad", geo);
	}

	public static void doLoad(String csvFile, Package pkg) {
		String[] bla = csvFile.split(",");
		Entity entity = null;
		for (Entity e : pkg.getEntities()) {
			if (e.getName().equals(bla[1])) {
				entity = e;
				break;
			}
		}
		
		String line = "";
		String cvsSplitBy = ",";
		try (BufferedReader br = new BufferedReader(new FileReader(bla[0]))) {
			line = br.readLine();
			String[] names = line.split(cvsSplitBy);
			while ((line = br.readLine()) != null) {
				String[] fields = line.split(cvsSplitBy);
				Record en = new Record(entity);
				for (int i = 0; i < names.length; i++) {
					for (Attribute a : entity.getAttributes()) {
						if (a.getName().equals(names[i])) {
							Object val = null;
							Class c = a.getValueClass();
							if (c == Integer.class) val = Integer.parseInt(fields[i]);
							if (c == String.class) val = fields[i];
							if (c == Boolean.class) val = fields[i].equals("true");

							en.addAttribute(a, val);
						}
					}
				}
				
				// TODO
				//entity.addEntry(en);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

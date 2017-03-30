package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import metaschema.MetaschemaDeserializer;
import model.Attribute;
import model.Entity;
import model.Entry;
import model.Package;
import model.Warehouse;
import view.MainView;

public class Main {

	public static void main(String[] args) {
		try {
			String fileString = new String(Files.readAllBytes(Paths.get("src/res/mock_metaschema.json")), StandardCharsets.UTF_8);
			MetaschemaDeserializer d = new MetaschemaDeserializer();
			d.deserialize(fileString, Warehouse.getInstance());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mock2();
		MainView mainView = MainView.getInstance();
		mainView.setVisible(true);
	}

	public static void mock() {
		Package pkg = new Package("Paket");
		Warehouse.getInstance().getPackages().add(pkg);
		Entity grad = new Entity("Grad");
		pkg.addEntity(grad);
		grad.addAttribute(new Attribute("Ime", String.class));
		grad.addAttribute(new Attribute("Povrsina", Float.class));
		grad.addAttribute(new Attribute("Ruzan", Boolean.class));
		grad.addEntry(spawnEntry(grad, "Beograd", 250.12f, true));
	}

	public static void mock2() {
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
				Entry en = new Entry(entity);
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
				
				entity.addEntry(en);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static Entry spawnEntry(Entity grad, String ime, float povrsina, boolean sranje) {
		ArrayList<Attribute> attrs = grad.getAttributes();
		Entry entri = new Entry(grad);
		entri.addAttribute(attrs.get(0), ime);
		entri.addAttribute(attrs.get(1), povrsina);
		entri.addAttribute(attrs.get(2), sranje);
		return entri;
	}
	
}

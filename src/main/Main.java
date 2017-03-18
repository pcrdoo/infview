package main;

import java.util.ArrayList;

import model.Attribute;
import model.Entity;
import model.Entry;
import model.Package;
import model.Warehouse;
import view.MainView;

public class Main {

	public static void main(String[] args) {
		mock();
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
		grad.addAttribute(new Attribute("Sranje", Boolean.class));
		grad.addEntry(spawnEntry(grad, "Beograd", 250.12f, true));
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

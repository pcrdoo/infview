package main;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import metaschema.MetaschemaTransmogrifier;
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
			System.out.println("we will now to use connetion for make mateschame of base thanks raheed");
			MetaschemaTransmogrifier wrangler = new MetaschemaTransmogrifier();
			System.out.println(wrangler.transmogrify(Warehouse.getInstance().getDbConnection()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MainView mainView = MainView.getInstance();
		mainView.setVisible(true);
	}
}

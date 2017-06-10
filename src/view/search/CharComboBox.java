package view.search;

import java.util.HashMap;

import javax.swing.JComboBox;

class CharComboBox extends JComboBox<String> implements ExpressionComponent{
	
	HashMap<String, String> mapa = new HashMap<>();
	
	public CharComboBox() {
		super();
		mapa.put("jednako je", "= ? ");
		mapa.put("nije jednako", "<> ? ");
		mapa.put("sadrzi", " LIKE CONCAT('%', ?, '%') ");
		mapa.put("pocinje sa", " LIKE CONCAT(?, '%') ");
		mapa.put("zavrsava sa", " LIKE CONCAT('%', ?) ");
		for(String s : mapa.keySet())
			this.addItem(s);
	}

	@Override
	public String getExpression() {
		return mapa.get(this.getSelectedItem().toString());
	}
}
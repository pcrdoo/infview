package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

import model.Entity;
import model.files.File;
import model.files.InvalidRecordException;
import net.miginfocom.swing.MigLayout;

public class TabbedTables extends JPanel {

	private JToolBar toolbar;
	private JTabbedPane tabs; // dodajemo tablePanele
	private boolean autoRefresh;

	public TabbedTables(boolean autoRefresh) {
		this.setLayout(new MigLayout("fill", "", "0[]0[grow]0"));
		toolbar = new JToolBar();
		toolbar.setRollover(true);
		populateToolbar();
		this.add(toolbar, "grow, wrap, height 50px");
		tabs = new JTabbedPane();
		this.add(tabs, "grow, height 250px");
		this.autoRefresh = autoRefresh;
	}

	private void populateToolbar() {
		// FETCH NEXT BLOCK
		JButton button = new JButton("Fetch Next Block");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Entity entity = getSelectedEntity();
				if (entity != null && entity instanceof File) {
					try {
						((File) entity).fetchNextBlock();
					} catch (IOException | InvalidRecordException ex) {
						System.out.println("Invalid blockfetch");
						ex.printStackTrace();
					}
				}

			}
		});
		toolbar.add(button);
		toolbar.addSeparator();
		toolbar.add(new JLabel("Block Factor: "));
		SpinnerModel numberModel = new SpinnerNumberModel();
		JSpinner spinner = new JSpinner(numberModel);
		JFormattedTextField field = (JFormattedTextField) spinner.getEditor().getComponent(0);
		DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
		formatter.setCommitsOnValidEdit(true);
		spinner.setValue(0);
		// na change taba promenis spinner value i disablujes ga kad nema nista TODO
		spinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				Entity entity = getSelectedEntity();
				if(entity != null && entity instanceof File) {
					((File)entity).setBlockFactor((Integer)spinner.getValue());
				}
			}

		});
		toolbar.add(spinner);
	}

	private Entity getSelectedEntity() {
		if (tabs.getSelectedComponent() == null) {
			return null;
		}
		return ((TablePanel) tabs.getSelectedComponent()).getEntity();
	}

	public boolean addTab(Entity entity) {
		for (Component c : tabs.getComponents()) {
			if (c instanceof TablePanel) {
				TablePanel panel = (TablePanel) c;
				if (panel.getEntity() == entity) {
					tabs.setSelectedComponent(c);
					return false;
				}
			}
		}
		TablePanel panel = new TablePanel(entity, autoRefresh); //
		TabComponent tabComponent = new TabComponent(tabs, entity);
		tabs.addTab(entity.getName(), panel);
		tabs.setTabComponentAt(tabs.indexOfComponent(panel), tabComponent);
		tabs.setSelectedComponent(panel);
		return true;
	}

}

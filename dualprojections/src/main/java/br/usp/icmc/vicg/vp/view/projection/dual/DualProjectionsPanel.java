package br.usp.icmc.vicg.vp.view.projection.dual;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import projection.model.ProjectionModel;
import projection.view.selection.InstanceSelection;
import br.usp.icmc.vicg.vp.model.projection.DualProjections;
import br.usp.icmc.vicg.vp.view.projection.RichProjectionPanel;

public class DualProjectionsPanel extends JPanel {

	private static final long serialVersionUID = -30850263439076166L;
	
	private RichProjectionPanel itemsPanel;
	private RichProjectionPanel dimsPanel;

	public DualProjectionsPanel() {
		
		super();
		
		this.setLayout(new GridLayout(1,2));
	}
	
	public void attach(DualProjections dualProjections) {
		
		itemsPanel = createProjectionPanel(dualProjections.getItemsModel(),
				"Items Projection");
		dimsPanel = createProjectionPanel(dualProjections.getDimensionsModel(),
				"Dimensions Projection");
		
		this.removeAll();
		
		this.add(itemsPanel);
		this.add(dimsPanel);
	}
	
	private RichProjectionPanel createProjectionPanel(ProjectionModel projModel, 
			String title) {
		
		RichProjectionPanel projPanel = new RichProjectionPanel(projModel, title);
		projPanel.setBackground(Color.WHITE);
		projPanel.setSelection(new InstanceSelection(projPanel.getProjectionPanel()));
		projPanel.setBorder(BorderFactory.createEtchedBorder());
		
		return projPanel;
	}
	
	@Override
	public void setBackground(Color color) {
		
		super.setBackground(color);
		
		if (itemsPanel != null && dimsPanel != null) {
			
			itemsPanel.setBackground(color);
			dimsPanel.setBackground(color);
		}
	}

	public JPanel getItemsPanel() {
		return itemsPanel;
	}

	public JPanel getDimsPanel() {
		return dimsPanel;
	}
}

package br.usp.icmc.vicg.vp.view.tree;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.usp.icmc.vicg.vp.model.tree.AbstractVertex;
import br.usp.icmc.vicg.vp.model.tree.InteractionsTree;

import com.mxgraph.model.mxCell;

public class InteractionsTreePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int fontSize = 12;

	private static final int X_PAD = 50;
	private static final int Y_PAD = 0;
	
	private static final int GAP = 10;
	
	private TreeComponent component;
	private JLabel titleLabel;

	private JLayeredPane layeredPane;

	private JPanel vertexThumbnail;
	private JPanel edgeThumbnail;

	private boolean stillOnVertex = false;
	
	private HoverThumbnailListener htl;
	
	public InteractionsTreePanel() {

		super();

		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBorder(new EmptyBorder(GAP, GAP, GAP, GAP));
		
		this.htl = new HoverThumbnailListener();
		this.addMouseMotionListener(htl); 
		this.addMouseListener(htl);

		// Title Label
		titleLabel = new JLabel("Interactions Tree");
		titleLabel.setOpaque(true);
		titleLabel.setFont(new Font(null, Font.BOLD, fontSize));
		
		// Thumb nails
		vertexThumbnail = createThumbnailSlot();
		edgeThumbnail = createThumbnailSlot();
		
		layeredPane = new JLayeredPane();
		
		this.add(layeredPane);
	}
	
	public void attach(InteractionsTree tree) {
		
		// Tree Component
		component = new TreeComponent(tree); 
		component.getGraphControl().addMouseMotionListener(htl); 
		component.getGraphControl().addMouseListener(htl);
		component.setBounds(this.getBounds());

		// Compose Layered Panel
		layeredPane.add(component, new Integer(0));
		layeredPane.add(vertexThumbnail, new Integer(1));
		layeredPane.add(edgeThumbnail, new Integer(1));

		this.setBackground(getBackground());
		
		this.revalidate();
	}

	public TreeComponent getComponent() {
		return component;
	}

	private JPanel createThumbnailSlot() {

		JPanel slot = new JPanel();
		slot.setLayout(new GridLayout(1,1));
		slot.setVisible(false);
		slot.setBounds(0, 0, Thumbnail.WIDTH, Thumbnail.HEIGHT);

		return slot;
	}

	@Override
	public void setBackground(Color color) {

		super.setBackground(color);

		if (component != null) {

			component.getViewport().setBackground(color);
		}
		if (this.titleLabel != null) {

			this.titleLabel.setBackground(color);
		}
	}

	class HoverThumbnailListener extends MouseAdapter {
		
		@Override
		public void mouseMoved(MouseEvent e) {

			Object cell = component.getCellAt(e.getX(), e.getY());

			boolean notOverVertex = true;
			boolean notOverEdge = true;
			
			if (cell != null) {

				// if is cell
				if (cell instanceof mxCell) {

					if (((mxCell) cell).isVertex()) {
						
						notOverVertex = false;

						if (!stillOnVertex) {

							stillOnVertex = true;

							int x = (int) ((mxCell) cell).getGeometry().getX();

							Thumbnail vertexThumb = ((AbstractVertex) cell).getThumbnail();

							vertexThumbnail.removeAll();
							vertexThumbnail.add(vertexThumb);
							vertexThumbnail.setLocation(x + X_PAD, Y_PAD);
							vertexThumbnail.setVisible(true);
							vertexThumbnail.revalidate();
						}
					}

					if (((mxCell) cell).isEdge()) {

						notOverEdge = false;
						// TODO: do something
					}
				} 
			} 
			if (notOverVertex) {
				
				vertexThumbnail.setVisible(false);
				stillOnVertex = false;
			}
			if (notOverEdge) {
				
				edgeThumbnail.setVisible(false);
			}
		}
	
		@Override
		public void mouseExited(MouseEvent e) {
			
			vertexThumbnail.setVisible(false);
			edgeThumbnail.setVisible(false);
			stillOnVertex = false;
		}
	}
}
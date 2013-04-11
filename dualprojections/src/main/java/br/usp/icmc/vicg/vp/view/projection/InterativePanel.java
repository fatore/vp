package br.usp.icmc.vicg.vp.view.projection;

import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

import projection.model.ProjectionInstance;
import projection.model.ProjectionModel;
import projection.model.Scalar;
import visualizationbasics.affinetransformation.TransformationMatrix2D;
import visualizationbasics.model.AbstractInstance;
import visualizationbasics.view.selection.AbstractSelection;

public class InterativePanel extends GenericPanel {

	private static final long serialVersionUID = 1L;

	// Selection
	private AbstractSelection selection;
	private ProjectionInstance selectedInstance;
	private Polygon selectionPolygon;
	protected Point selectionSource;
	protected Point selectionTarget;
	private Color selectionColor;
	private boolean moveInstances;

	public InterativePanel(ProjectionModel model) {

		super(model);

		this.moveInstances = true;

		this.selectionColor = java.awt.Color.RED;

		this.addMouseListener(new Selectionistener());
		this.addMouseMotionListener(new DragSelectionListener());
	}

	public void setSelection(AbstractSelection selection) {

		this.selection = selection;
	}

	public boolean isMoveInstances() {

		return moveInstances;
	}

	public void setMoveInstance(boolean moveinstances) {

		this.moveInstances = moveinstances;
	}

	@Override
	public void paintComponent(java.awt.Graphics g) {

		super.paintComponent(g);

		java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;

		//Draw the rectangle to select the instances
		if (selectionSource != null && selectionTarget != null) {

			drawSelectionRectangle(g2);
		} 
		//drawn the selection polygon
		if (selectionPolygon != null) {

			drawSelectionPolygon(g2);
		}
		updateImage();
	}

	private void drawSelectionRectangle(java.awt.Graphics2D g) {

		int x = selectionSource.x;
		int width = selectionTarget.x - selectionSource.x;

		int y = selectionSource.y;
		int height = selectionTarget.y - selectionSource.y;

		if (selectionSource.x > selectionTarget.x) {
			x = selectionTarget.x;
			width = selectionSource.x - selectionTarget.x;
		}

		if (selectionSource.y > selectionTarget.y) {
			y = selectionTarget.y;
			height = selectionSource.y - selectionTarget.y;
		}
		g.setColor(selectionColor);
		g.drawRect(x, y, width, height);

		g.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 0.45f));
		g.fillRect(x, y, width, height);
	}

	private void drawSelectionPolygon(java.awt.Graphics2D g) {

		g.setColor(selectionColor);
		g.drawPolygon(selectionPolygon);

		g.setComposite(java.awt.AlphaComposite.
				getInstance(java.awt.AlphaComposite.SRC_OVER, 0.45f));
		g.fillPolygon(selectionPolygon);
	}

	public void cleanSelectedInstances() {

		if (model != null) {
			model.cleanSelectedInstances();
			model.notifyObservers();
		}
	}

	public void removeSelectedInstances() {

		if (model != null) {
			model.removeSelectedInstances();
			model.notifyObservers();
		}
	}

	public ArrayList<ProjectionInstance> getSelectedInstances(Polygon polygon) {
		ArrayList<ProjectionInstance> selected = new ArrayList<ProjectionInstance>();

		if (model != null) {
			selected = ((ProjectionModel) model).getInstancesByPosition(polygon);
		}

		return selected;
	}

	public ArrayList<ProjectionInstance> getSelectedInstances(Point source, Point target) {
		ArrayList<ProjectionInstance> selinstances = new ArrayList<ProjectionInstance>();

		if (model != null) {
			int x = Math.min(source.x, target.x);
			int width = Math.abs(source.x - target.x);

			int y = Math.min(source.y, target.y);
			int height = Math.abs(source.y - target.y);

			Rectangle rect = new Rectangle(x, y, width, height);
			selinstances = ((ProjectionModel) model).getInstancesByPosition(rect);
		}

		return selinstances;
	}

	public void removeInstancesWithScalar(float val) {
		if (model != null) {
			ArrayList<AbstractInstance> insts = new ArrayList<AbstractInstance>();
			Scalar scalar = ((ProjectionModel) model).addScalar("cdata");
			for (AbstractInstance ai : model.getInstances()) {
				if (((ProjectionInstance) ai).getScalarValue(scalar) == val) {
					insts.add(ai);
				}
			}

			model.removeInstances(insts);
			model.notifyObservers();
		}
	}

	public void zoomIn() {

		if (model != null) {
			//set the new viewport
			viewport.width = (int) (viewport.width * 1.1);
			viewport.height = (int) (viewport.height * 1.1);
			((ProjectionModel) model).setViewport(viewport);
			
			model.notifyObservers();
		}
	}

	public void zoomOut() {

		if (model != null) {
			//set the new viewport
			viewport.width = (int) (viewport.width * 0.909);
			viewport.height = (int) (viewport.height * 0.909);
			((ProjectionModel) model).setViewport(viewport);
			
			model.notifyObservers();
		}
	}

	class DragSelectionListener extends MouseMotionAdapter {

		@Override
		public void mouseDragged(java.awt.event.MouseEvent evt) {
			if (selectedInstance != null) {
				if (model.hasSelectedInstances()) {
					TransformationMatrix2D inv = ((ProjectionModel) model).getViewportMatrix().inverse();
					float[] coord = inv.apply(evt.getX(), evt.getY());
					coord[0] -= selectedInstance.getX();
					coord[1] -= selectedInstance.getY();

					for (AbstractInstance ai : model.getSelectedInstances()) {
						ProjectionInstance pi = (ProjectionInstance) ai;
						pi.setX(coord[0] + pi.getX());
						pi.setY(coord[1] + pi.getY());
					}

					model.setChanged();
					updateImage();
				}
			} else if (selectionSource != null) {
				selectionTarget = evt.getPoint();
			} else if (selectionPolygon != null) {
				selectionPolygon.addPoint(evt.getX(), evt.getY());
			}

			repaint();
		}

	}

	class Selectionistener extends MouseAdapter {

		@Override
		public void mouseClicked(java.awt.event.MouseEvent evt) {
			super.mouseClicked(evt);

			if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
				cleanSelectedInstances();
			}
		}

		@Override
		public void mousePressed(java.awt.event.MouseEvent evt) {
			super.mousePressed(evt);

			if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
				if (model != null) {
					ProjectionInstance instance = ((ProjectionModel) model).getInstanceByPosition(evt.getPoint());
					if (instance != null) {
						model.setSelectedInstance(instance);
						model.notifyObservers();
						if (moveInstances) {
							if (model.getSelectedInstances().contains(instance)) {
								selectedInstance = instance;
							}
						}
					} else {
						selectionSource = evt.getPoint();
					}
				}
			} else if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
				selectionPolygon = new java.awt.Polygon();
				selectionPolygon.addPoint(evt.getX(), evt.getY());
			}
		}

		@Override
		public void mouseReleased(java.awt.event.MouseEvent evt) {
			super.mouseReleased(evt);

			if (model != null) {
				if ((selectionSource != null && selectionTarget != null) || selectionPolygon != null) {
					ArrayList<ProjectionInstance> newSelInst = null;

					if (selectionPolygon != null) {
						newSelInst = getSelectedInstances(selectionPolygon);
					} else {
						newSelInst = getSelectedInstances(selectionSource, selectionTarget);
					}

					if (newSelInst != null) {

						if (selection != null) {
							
							ArrayList<AbstractInstance> prevSelInst = model.getSelectedInstances();
							
							prevSelInst.addAll(newSelInst);
							
							selection.selected(new ArrayList<AbstractInstance>(prevSelInst));
						}
					}
				}
			}

			selectionPolygon = null;
			selectedInstance = null;
			selectionSource = null;
			selectionTarget = null;
		}
	}

	class ZoomListener extends MouseAdapter {

		@Override
		public void mouseWheelMoved(MouseWheelEvent e){


			if (e.getWheelRotation() < 0) {

				zoomIn();
				repaint();
			} 
			else {
				zoomOut();
				repaint();
			}
		}
	}
}

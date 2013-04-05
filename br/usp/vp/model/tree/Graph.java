package br.usp.vp.model.tree;

import com.mxgraph.view.mxGraph;

public class Graph extends mxGraph {
	
	private Integer numVertices = 0;

	public void addVertex(Object vertex) {
		
		this.getModel().add(this.getDefaultParent(), vertex, 0);
		numVertices++;
	}
	
	public Integer getNumVertices() {
		return numVertices;
	}

	public void addEdge(Vertex source, Vertex target) {
		
		this.insertEdge(this.getDefaultParent(), null, null, source, target);
	}
}

package apps;

import structures.*;
import java.util.ArrayList;

public class MST {
	
	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	public static PartialTreeList initialize(Graph graph) {
		PartialTreeList ptlist = new PartialTreeList();
		for(int i=0;i<graph.vertices.length;i++){
			Vertex v=graph.vertices[i];
			PartialTree t= new PartialTree(v);
			MinHeap<PartialTree.Arc> p = t.getArcs();
			while(v.neighbors !=null){
				PartialTree.Arc a=new PartialTree.Arc(v, v.neighbors.vertex, v.neighbors.weight);
				p.insert(a);
				v.neighbors=v.neighbors.next;
			}
			
			//t.getArcs().merge(p);
			ptlist.append(t);//add to list
		}
		return ptlist;
	}

	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	public static ArrayList<PartialTree.Arc> execute(PartialTreeList ptlist) {
		ArrayList<PartialTree.Arc> ret = new ArrayList<PartialTree.Arc>();
		while(ptlist.size()>1){
			PartialTree ptx = ptlist.remove();
			MinHeap<PartialTree.Arc> pqx= ptx.getArcs();
			PartialTree.Arc a=pqx.deleteMin();
			Vertex v1=a.v1;
			Vertex v2=a.v2;
			while(ptx.getArcs()!=null){
				if(v2.getRoot().equals(ptx.getRoot())){
					a=ptx.getArcs().deleteMin();
					v2=a.v2;
				}
				else{
					break;
				}
			}
			
			PartialTree pty= ptlist.removeTreeContaining(v2);
			ptx.merge(pty);
			
			ptlist.append(ptx);
			ret.add(a);
		}
		return ret;
	}
}

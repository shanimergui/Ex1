import java.io.Serializable;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class WGraph_DS implements weighted_graph, Serializable
{
    // Holds the node
    HashMap<Integer , node_info>  nodes;  // graph
   // Holds its neighbours
    HashMap<Integer ,  HashMap< Integer , edge_info>>  edges; // edges

    int mc;
    int edge_size;

    //constructor
    public WGraph_DS() {
        mc=0;
        edge_size=0;
        nodes = new HashMap<>();
        edges = new HashMap <Integer ,  HashMap< Integer , edge_info>> ();

    }
    // copy constructor
    public WGraph_DS(weighted_graph g) {
        mc=g.getMC();
        edge_size=g.edgeSize();


        HashMap<Integer,node_info> gr = new HashMap<Integer,node_info>();
        HashMap<Integer,HashMap<Integer,edge_info>> new_edge = new HashMap<Integer,HashMap<Integer,edge_info>>();

        for(node_info n :g.getV()){
            gr.put(n.getKey(), n);
        }
        for(node_info n :g.getV())
        {
            for(node_info ni :g.getV(n.getKey()))
            {
                new_edge.put(n.getKey(), new HashMap<Integer,edge_info>());
                new_edge.get(n.getKey()).put(ni.getKey(),new EdgeInfo(ni,g.getEdge(n.getKey(),ni.getKey())));
            }
        }
        this.nodes =gr;
        this.edges=new_edge;
    }

    @Override
    public node_info getNode(int key)

    {
        return nodes.get(key);
    }

    @Override
    // Holds neighbors of type edge_info which in addition to node_info , also has weight.
    public boolean hasEdge(int node1, int node2)
    {
        if(nodes.containsKey(node1) && nodes.containsKey(node2))
        {
            if(edges.get(node1)!=null) // If the intern Hashmap is not equal to null
                return edges.get(node1).containsKey(node2);
        }
        return false;
    }

    @Override
   // Returns the edge's weight that is connected to 2 nodes
    public double getEdge(int node1, int node2)
    {
        if(hasEdge(node1,node2))
        {
            // We put into Hashmap and went to node 2
            // Into the second Hashmap we went to node 2 and returned the weight of the edge that was between them
            return edges.get(node1).get(node2).getEdgeWeighted();
        }
        return -1;
    }


    @Override
    //We added a new node in condition that it is not present anymore
    public void addNode(int key)
    {
        if(!nodes.containsKey(key)) {
            nodes.put(key, new NodeInfo(key));
            mc++;
        }
    }

    @Override
    public void connect(int node1, int node2, double w)
    {
  // Checking if there is an edge and if the weight is superior or equal to 0
        if (node1 != node2 && !hasEdge(node1,node2)&&w>=0) {
            if(edges.get(node1)==null)
            {
                edges.put(node1, new HashMap<Integer,edge_info>()); // We are starting the intern Hashmap
                edges.get(node1).put(node2,new EdgeInfo(this.getNode(node2),w));
            }
            else {
                edges.get(node1).put(node2,new EdgeInfo(this.getNode(node2),w));
            }
            if(edges.get(node2)==null)
            {
                edges.put(node2, new HashMap<Integer,edge_info>());
                edges.get(node2).put(node1,new EdgeInfo(getNode(node1),w));
            }
            else {
                edges.get(node2).put(node1,new EdgeInfo(getNode(node1),w));
            }
            mc++;
            edge_size++;
        }
    }

    @Override
    public Collection<node_info> getV()
    {
        return nodes.values(); // we want node_info that this is the value.
    }

    @Override
    public Collection<node_info> getV(int node_id) //We returned a collection of all the neighbours
    {
        // We returned ans that inside of which there is node_info
        Collection<node_info> ans = new ArrayList<node_info>();
        if(edges.get(node_id)!=null) {
            for (edge_info i : edges.get(node_id).values()) {
                ans.add(i.getDist());
            }
        }
        return ans;
    }

    @Override
    public node_info removeNode(int key) {
        if (nodes.containsKey(key) == true) {
            if(edges.get(key)!=null) // If he isn't equal to null so there re no edges to erase
            {
                Iterator<node_info> i = getV(key).iterator(); // מביאי לי את השכנים
                while (i.hasNext()) {
                    edges.get(i.next().getKey()).remove(key); // תן לי את השכן שלך ומהשכן שלך תמחוק את key
                    edge_size--;
                    mc++;
                }
            }
        mc++;
        return nodes.remove(key); // At the end we are erasing everything the key and edge_info
    }
        return null;
    }

    @Override
    public void removeEdge(int node1, int node2)
    {
        if (hasEdge(node1,node2)) {

            edges.get(node1).remove(node2);

            edges.get(node2).remove(node1);

            mc++;
            edge_size--;
        }
    }

    @Override
    public int nodeSize()
    {
        return nodes.size();
    }

    @Override
    public int edgeSize()
    {
        return edge_size;
    }

    @Override
    public int getMC()
    {
        return mc;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WGraph_DS)) return false;

        WGraph_DS wGraph_ds = (WGraph_DS) o;

        if (mc != wGraph_ds.mc) return false;
        if (edge_size != wGraph_ds.edge_size) return false;
        if (this.nodeSize() != wGraph_ds.nodeSize()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodes, edges, mc, edge_size);
    }

    public HashMap<Integer, HashMap<Integer, edge_info>> getE()
    {
        return edges;
    }





    class NodeInfo implements node_info
    {

       private int key;
       private double tag;
       private String info;


        NodeInfo(int key)
        {
            this.key = key;
            tag=Double.POSITIVE_INFINITY; // Initially infused
            info="";
        }
        NodeInfo(node_info n){
            key=n.getKey();
            tag=n.getTag();
            info=n.getInfo();
        }

        @Override
        public int getKey()

        {
            return key;
        }

        @Override
        public String getInfo()

        {
            return info;
        }

        @Override
        public void setInfo(String s)

        {
            info = s;
        }

        @Override
        public double getTag()

        {
            return tag;
        }

        @Override
        public void setTag(double t)
        {
            tag = t;
        }

    }




    class EdgeInfo implements edge_info {

        private node_info dist;
        private double weighted;

        EdgeInfo(node_info d,double _w)
        {

            dist=d;
            weighted=_w;
        }
        EdgeInfo(edge_info e)
        {

            dist=e.getDist();
            weighted=e.getEdgeWeighted();
        }

        @Override
        public double getEdgeWeighted()


        {
            return weighted;
        }

        @Override
        public void setEdgeWeighted(double w)

        {
            weighted = w;
        }

        @Override
        public node_info getDist()

        {
            return dist;
        }

        @Override
        public void setDist(node_info dist)

        {
            this.dist = dist;
        }


    }

    public static void main(String[] args) {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);

        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        g.removeEdge(0,1);

        g.removeEdge(2,1);
        g.connect(0,1,1);
        double w = g.getEdge(1,0);
        weighted_graph g1=new WGraph_DS();
        weighted_graph g2=new WGraph_DS();
        System.out.println(g2.equals(g1));
        assertEquals(g2,g1);
       // assertEquals(w,1);
    }
}

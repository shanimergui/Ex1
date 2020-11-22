import java.io.*;
import java.util.*;

public class WGraph_Algo  implements weighted_graph_algorithms,Serializable{

    private weighted_graph g;

    public WGraph_Algo()
    {
        g=new WGraph_DS();
    }

    @Override
    public void init(weighted_graph g) //Initializing the graph
    {
        this.g=g;
    }

    @Override
    public weighted_graph getGraph()
    {
        return this.g;
    }

    @Override
    public weighted_graph copy() //We used a copy constructor and we returned the graph
    {
        weighted_graph g1 = new WGraph_DS(g);
        return g1;
    }

    @Override
    public boolean isConnected() {
        if (g.nodeSize() == 0 || g.nodeSize() == 1) // connected
        {
            return true;
        } else {
            bfs(g.getV().iterator().next());
            for (node_info i : g.getV()) {
                if (i.getTag() == Double.POSITIVE_INFINITY) return false;
            }
        }
        for (node_info i : g.getV()) {
            i.setTag(Double.POSITIVE_INFINITY);//Initializing another time
        }
        return true;
    }

    /**
     * dijkstra algorithm
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if(this.g.getNode(src)==null||this.g.getNode(dest)==null){
            return -1;
        }
        PriorityQueue<node_info> q = new PriorityQueue<node_info>(new Comparator<node_info>()
        {
            @Override
            public int compare(node_info o1, node_info o2) {
                return - Double.compare(o2.getTag(),o1.getTag());
            }
        });

        LinkedHashMap<Integer,Boolean> visit =new LinkedHashMap<Integer,Boolean>();
        for (node_info i : g.getV()){visit.put(i.getKey(),false);}

        node_info s=g.getNode(src);
        node_info d=g.getNode(dest);

        s.setTag(0);
        q.add(s);

        while(!q.isEmpty()){
            node_info v1 = q.poll();
            for(node_info i : this.g.getV(v1.getKey())){
                node_info v2=i;
                if(visit.get(v2.getKey())==false){
                    double weight= v1.getTag()+((WGraph_DS)g).getE().get(v2.getKey()).get(v1.getKey()).getEdgeWeighted();
                    if(weight<v2.getTag()){
                      v2.setTag(weight);
                        q.add(v2);
                    }
                }

            }
            visit.replace(v1.getKey(),true);
        }
        double anser=d.getTag();
        for(node_info i: g.getV()){
            i.setTag(Double.POSITIVE_INFINITY);
        }
        if(anser==Double.POSITIVE_INFINITY){
            return -1;
        }
        return anser;
    }

    @Override
    public List<node_info> shortestPath(int src, int dest) {
        PriorityQueue<node_info> q = new PriorityQueue<node_info>(new Comparator<node_info>() {
            @Override
            public int compare(node_info o1, node_info o2) {
                return - Double.compare(o2.getTag(),o1.getTag());
            }
        });

        LinkedHashMap<Integer,Boolean> visit =new LinkedHashMap<Integer,Boolean>();
        for (node_info i : g.getV()){visit.put(i.getKey(),false);}

        LinkedHashMap<node_info,node_info> p =new LinkedHashMap<node_info,node_info>();
        node_info s=g.getNode(src);
        node_info d=g.getNode(dest);

        s.setTag(0);
        q.add(s);

        while(!q.isEmpty()){
            node_info v1 = q.poll();
            for(node_info i : this.g.getV(v1.getKey())){
                node_info v2=i; // השכן
                if(visit.get(v2.getKey())==false){
                    double weight= v1.getTag()+((WGraph_DS)g).getE().get(v2.getKey()).get(v1.getKey()).getEdgeWeighted();
                    if(weight<v2.getTag()){
                        v2.setTag(weight);
                        p.put(v2,v1);
                        q.add(v2);
                    }
                }

            }
            visit.replace(v1.getKey(),true); //Saying as if we performed it, we passed on it, changed it into true
        }
        double anser=d.getTag();
        for(node_info i: g.getV()){
            i.setTag(Double.POSITIVE_INFINITY);
        }
        Stack<node_info> path = new Stack<>();
        List<node_info> pathrevers = new ArrayList<>();
        node_info node = g.getNode(dest);
        while(node != null) {
            path.add(node);
            node = p.get(node);
        }
        while(path.size()!=0)
            pathrevers.add(path.pop());
        return pathrevers;
    }

    @Override
    public boolean save(String file) {

        try
        {
            FileOutputStream fileinput = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileinput);

            out.writeObject(this.g);

            out.close();
            fileinput.close();

           return true;
        }
        catch(IOException ex) // Returns errors in the files of input / output
        {
            return false;
        }
    }

    @Override
    public boolean load(String file) {
        try {
            FileInputStream fileinput = new FileInputStream(file); //Selects the graph and putting it into the file
            ObjectInputStream in = new ObjectInputStream(fileinput); //We hade a file and turned it into an object
            g = (weighted_graph) in.readObject();
            //Becuse of the fact that it's a string we need to close
            in.close();
            fileinput.close();
            return true;
        }
        catch(IOException ex)
        {
            return false;
        }
        catch(ClassNotFoundException ex)
        {
            return false;
        }
    }

    private void bfs(node_info s)
    {
        Queue<node_info> q = new LinkedList<>(); //Creating a list
        s.setTag(1);
        q.add(s); //Exiting the stack
        while (!q.isEmpty()) //As long as as the list isn't empty
        {
            node_info s1 = q.poll();
            for (node_info i : this.g.getV(s1.getKey())) //Passing on the neighbours
            {
                System.out.println(i.getKey());
                if (i.getTag() ==Double.POSITIVE_INFINITY) //If the tag has an infinite value, we didn't pass on it
                {
                    i.setTag(1);//putting 1
                    q.add(i);//adding to the list
                }
            }
        }
    }

    public static void main(String[] args) {
        weighted_graph a = new WGraph_DS();
        a.addNode(1);
        a.addNode(2);

        a.connect(1,2,80);
        weighted_graph_algorithms algo = new WGraph_Algo();
        algo.init(a);
        System.out.println(algo.isConnected());
    }
}

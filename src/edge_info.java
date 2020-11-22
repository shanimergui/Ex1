public interface edge_info {


//getter and setter edge_info


    /**
     * Return the weight (ID) associated with this rib.
     * @return
     */
    public double getEdgeWeighted();


    /**
     *Gives the weight.
     * @param w
     */
    public void setEdgeWeighted(double w) ;



    /**
     *
     * @return node_info
     */
    public node_info getDist();




    /**
     *
     * @param dist
     */
    public void setDist(node_info dist) ;



}

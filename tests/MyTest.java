import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MyTest {

    weighted_graph a ;
    @BeforeEach
    void initGraph(){
        a =new WGraph_DS();
    }

@Test
void test1() {

    a.addNode(1);
    a.addNode(2);
    a.connect(1,2,80);

    weighted_graph_algorithms algo = new WGraph_Algo();
    algo.init(a);
    if(algo.isConnected()!=true){
        fail("graph is connected") ;
    }
}

    @Test
    void test2()
    {
        for(int i=0; i< 1000000; i++) {
            a.addNode(i);
        }
        for(int i=0; i<1000000; i+=2){
            a.connect(i,i+1,100);
        }
    if(a.nodeSize()!=1000000){
        fail("graph size 1000000") ;
    }
    if(a.edgeSize()!=500000){
        fail("graph size edge 500000") ;
    }

    }


    @Test
    void test3() {
        a.addNode(0);
        a.addNode(1);
        a.addNode(2);
        a.removeNode(0);
        if(a.nodeSize()!=2){
            fail("graph soulde to be 2");
        }
        if(a.edgeSize()!=0){
            fail("graph should to be 0 edges");
        }

    }

    @Test
    void test4() {
        a.addNode(0);
        a.addNode(1);
        a.addNode(2);
        a.connect(0,1,20);
        a.connect(1,2,30);
        a.connect(0,2,55);
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(a);
        assertTrue(ag0.isConnected());
        double d = ag0.shortestPathDist(0,2);
        assertEquals(d, 50);

    }

    @Test
    void test5() {
        a.addNode(0);
        a.addNode(1);
        a.addNode(2);
        a.connect(0,1,20);
        a.connect(1,2,30);
        a.connect(0,2,55);
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(a);
        assertTrue(ag0.isConnected());
        List<node_info> d = ag0.shortestPath(0,2);
        assertEquals(d.size(), 3);
        a.removeNode(1);
        d = ag0.shortestPath(0,2);
        assertEquals(d.size(), 2);
        double dist=ag0.shortestPathDist(0,2);
        assertEquals(dist,55);
    }

    @Test
    void test6() {
    a.addNode(0);
    a.addNode(0);
    assertEquals(a.nodeSize(),1);
    a.removeNode(0);
    a.removeNode(0);
    assertEquals(a.nodeSize(),0);
    }

    @Test
    void test7() {
        a.addNode(0);
        a.addNode(1);
        a.addNode(2);
        a.connect(0,1,20);
        a.connect(1,2,30);
        a.connect(0,2,55);
        assertEquals(a.edgeSize(),3);

        a.removeNode(1);
        assertEquals(a.edgeSize(),1);

    }


    @Test
    void test8()
    {
        a.addNode(0);
        a.addNode(1);
        a.addNode(2);
        a.connect(0,1,20);
        a.connect(1,2,30);
        a.connect(0,2,-50);
        assertEquals(a.edgeSize(),2);
        assertEquals(a.nodeSize(),3);

    }


    @Test
    void test9()
    {
        a.addNode(0);
        a.addNode(1);
        a.addNode(2);
        a.connect(0,1,20);
        a.connect(1,2,30);
        a.connect(0,2,-50);
        assertEquals(a.edgeSize(),2);
        assertEquals(a.nodeSize(),3);
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(a);
        assertTrue(ag0.isConnected());
        assertEquals(ag0.shortestPathDist(0,2),50);
    }

    @Test
    void test10() {
        a.addNode(0);
        node_info get=a.getNode(0);
        assertEquals(get.getKey(),0);
        assertEquals(get.getInfo(),"");
        assertEquals(get.getTag(),Double.POSITIVE_INFINITY);
        get.setTag(-1);
        get.setInfo("ariel");
        assertEquals(get.getInfo(),"ariel");
        assertEquals(get.getTag(),-1);
    }


    @Test
    void test11() {
        a.addNode(0);
        a.addNode(1);
        a.connect(0,1,7);
        assertEquals(a.getEdge(1,0),7);
    }

    @Test
    void test12() {
a.addNode(0);
assertEquals(a.edgeSize(),0);
assertEquals(a.nodeSize(),1);
assertEquals(a.getMC(),1);
a.removeNode(0);
        assertEquals(a.edgeSize(),0);
        assertEquals(a.nodeSize(),0);
        assertEquals(a.getMC(),2);
        a.addNode(0);
        a.addNode(1);
        a.connect(0,1,7);
        assertEquals(a.edgeSize(),1);
        assertEquals(a.nodeSize(),2);
        assertEquals(a.getMC(),5);
        a.addNode(0);
        assertEquals(a.edgeSize(),1);
        assertEquals(a.nodeSize(),2);
        assertEquals(a.getMC(),5);
        a.removeEdge(0,1);
        assertEquals(a.getEdge(0,1),-1);
        a.connect(0,1,7);
        assertEquals(a.getEdge(0,5),-1);
    }
    @Test
    void test13() {

        a.addNode(1);
        a.addNode(2);
        a.connect(1,2,80);

        weighted_graph_algorithms algo = new WGraph_Algo();
        algo.init(a);
        assertTrue(algo.isConnected());

        assertEquals(algo.shortestPathDist(1,2),80);
        assertEquals(algo.shortestPathDist(0,5),-1);
        a.addNode(0);
        assertFalse(algo.isConnected());
        assertEquals(algo.shortestPathDist(0,2),-1);
    }
}

package Algorithm;

import Graph.*;

import java.util.ArrayList;
import java.util.HashSet;

public class FordFulkerson
{
    private ArrayList<DirectedEdge> edgeList; // residual graph is the final graph after max flow is reached
    private ArrayList<FlowStep> steps; // residual graph in steps
    private Integer flow; // maximum flow
    Vertex src, snk; // start node and goal node

    HashSet<Vertex> visited; // visited node in each ford fulkerson iteration

    public FordFulkerson(ArrayList<DirectedEdge> edgeList, Vertex src, Vertex snk)
    {
        this.edgeList = new ArrayList<DirectedEdge>();
        for (DirectedEdge e : edgeList) // deep copy
            this.edgeList.add(new DirectedEdge(new Vertex(e.getU().getIndex()), new Vertex(e.getV().getIndex()), e.getCap()));
        steps = null;
        this.src = src;
        this.snk = snk;
        visited = new HashSet<Vertex>();
        flow = 0;
    }

    public ArrayList<DirectedEdge> getEdgeList()
    {
        return edgeList;
    }

    public ArrayList<FlowStep> getSteps()
    {
        return steps;
    }

    public void setEdgeList(ArrayList<DirectedEdge> edgeList)
    {
        this.edgeList = edgeList;
    }

    public Integer getFlow()
    {
        return flow;
    }

    public Vertex getSrc()
    {
        return src;
    }

    public void setSrc(Vertex src)
    {
        this.src = src;
    }

    public Vertex getSnk()
    {
        return snk;
    }

    public void setSnk(Vertex snk)
    {
        this.snk = snk;
    }


    private FlowStep getFlowStep(Vertex u, Integer runningFlow)
    {
        if (runningFlow.equals(0))
            return null;
        if (u.equals(snk)) // reached snk with a positive flow
        {
            FlowStep f = new FlowStep(runningFlow);
            return f;
        }

        visited.add(u);
        for (DirectedEdge e : edgeList)
        {
            // my edge and destination is not visited
            if (u.equals(e.getU()) && !visited.contains(e.getV()))
            {
                FlowStep f = getFlowStep(e.getV(), Math.min(runningFlow, e.getCap()));
                if (f != null) // found a path with a positive flow
                {
                    e.setHighlighted(true);
                    e.setCap(e.getCap() - f.getFlow());
                    for (DirectedEdge r : edgeList)
                    {
                        if (e.isReverse(r))
                        {
                            r.setCap(r.getCap() + f.getFlow());
                            r.setHighlighted(true);
                            break;
                        }
                    }
                    return f;
                }
            }
        }
        return null;
    }

    public void run() // runs ford fulkerson on given graph
    {
        // initially the graph is as the input and all edges are not highlighted
        steps = new ArrayList<>();
        steps.add(new FlowStep(edgeList, 0));
        flow = 0;
        while (true)
        {
            visited.clear();
            for (DirectedEdge e : edgeList)
                e.setHighlighted(false);
            FlowStep f = getFlowStep(src, (int)2e9);
            if (f == null) // Max flow reached
                break;
            f.setEdgeList(edgeList);
            steps.add(f);
            flow += f.getFlow();
        }
    }
}

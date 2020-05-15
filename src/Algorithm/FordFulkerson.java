package Algorithm;

import Graph.*;

import java.util.ArrayList;
import java.util.HashSet;

public class FordFulkerson
{
    private ArrayList<DirectedEdge> edgeList, residualGraph; // residual graph is the final graph after max flow is reached
    private ArrayList<FlowStep> steps; // residual graph in steps
    private Integer flow; // maximum flow
    Vertex src, snk; // start node and goal node

    HashSet<Vertex> visited; // visited node in each ford fulkerson iteration

    public FordFulkerson(ArrayList<DirectedEdge> edgeList, Vertex src, Vertex snk)
    {
        this.edgeList = edgeList;
        residualGraph = null;
        steps = null;
        this.src = src;
        this.snk = snk;
    }

    public ArrayList<DirectedEdge> getEdgeList()
    {
        return edgeList;
    }

    public ArrayList<DirectedEdge> getResidualGraph()
    {
        return residualGraph;
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

    // Update outgoing edges from node u after a path with positive flow is found that passes by modifiedEdge (modifiedEdge is going out from u)
    private void updateFlowStep(Vertex u, FlowStep f, DirectedEdge modifiedEdge)
    {
        boolean addedReverseEdge = false; // checker for not adding reversed edge twice
        for (DirectedEdge e : edgeList)
        {
            if (u.equals(e.getU()))
            {
                if (e.equals(modifiedEdge)) // update capacity on flow edge
                {
                    DirectedEdge UV = new DirectedEdge(e.getU(), e.getV(), e.getCap() - f.getFlow());
                    f.addModifiedEdge(UV);
                }
                else if (e.getU() == modifiedEdge.getV() && e.getV() == modifiedEdge.getU())// update capacity on reverse edge
                {
                    addedReverseEdge = true;
                    DirectedEdge VU = new DirectedEdge(e.getU(), e.getV(), e.getCap() + f.getFlow());
                    f.addUnmodifiedEdge(VU); // unmodified stands for wont show up on the GUI as highlighted
                }
                else if (e.getCap() > 0) // unmodified edge. Not sure if should add it if it has zero flow or not. See how it looks in GUI. Edge can have positive flow then go to zero then next iteration might want to remove it
                {
                    f.addUnmodifiedEdge(e);
                }
            }
        }
        if (!addedReverseEdge && modifiedEdge != null) // if the reverse edge wasn't initially in the list
        {
            DirectedEdge VU = new DirectedEdge(modifiedEdge.getV(), modifiedEdge.getU(), f.getFlow());
            f.addUnmodifiedEdge(VU);
        }
    }

    private FlowStep getFlowStep(Vertex u, Integer runningFlow)
    {
        if (runningFlow.equals(0))
            return null;
        if (u.equals(snk)) // reached snk with a positive flow
        {
            FlowStep f = new FlowStep(runningFlow);
            updateFlowStep(u, f, null);
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
                    updateFlowStep(u, f, e);
                    return f;
                }
            }
        }
        return null;
    }

    public void run() // runs ford fulkerson on given graph
    {
        // initially the graph is as the input and all edges are not highlighted
        residualGraph = edgeList;
        steps = new ArrayList<>();
        steps.add(new FlowStep(new ArrayList<>(), edgeList, 0));

        while (true)
        {
            visited.clear();

            FlowStep f = getFlowStep(src, (int)2e9);
            if (f == null) // Max flow reached
                break;

            steps.add(f);
            flow += f.getFlow();
            residualGraph = f.getResidualGraph();
        }
    }
}

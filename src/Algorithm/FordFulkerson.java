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
        this.edgeList = new ArrayList<DirectedEdge>();
        for (DirectedEdge e : edgeList) // deep copy
            this.edgeList.add(new DirectedEdge(new Vertex(e.getU().getIndex()), new Vertex(e.getV().getIndex()), e.getCap()));
        residualGraph = null;
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
            if (u.equals(e.getU()) && modifiedEdge != null)
            {
                if (e.equals(modifiedEdge)) // update capacity on flow edge
                {
                    DirectedEdge UV = new DirectedEdge(e.getU(), e.getV(), e.getCap() - f.getFlow(), true);
                    f.addModifiedEdge(UV);
                }
                else if (e.getU().equals(modifiedEdge.getV()) && e.getV().equals(modifiedEdge.getU()))// update capacity on reverse edge
                {
                    addedReverseEdge = true;
                    DirectedEdge VU = new DirectedEdge(e.getU(), e.getV(), e.getCap() + f.getFlow(), true);
                    f.addModifiedEdge(VU);
                }
            }
        }
        if (!addedReverseEdge && modifiedEdge != null) // if the reverse edge wasn't initially in the list
        {
            DirectedEdge VU = new DirectedEdge(modifiedEdge.getV(), modifiedEdge.getU(), f.getFlow(), true);
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
                    e.setCap(e.getCap() - f.getFlow());
                    return f;
                }
            }
        }
        return null;
    }

    private void addUnmodifiedEdges(FlowStep f)
    {
        for (DirectedEdge e : edgeList)
        {
            if (visited.contains(e.getU()) || visited.contains(e.getV()))
                continue;
            e.setHighlighted(false);
            f.addUnmodifiedEdge(e);
        }
    }

    public void run() // runs ford fulkerson on given graph
    {
        // initially the graph is as the input and all edges are not highlighted
        residualGraph = edgeList;
        steps = new ArrayList<>();
        steps.add(new FlowStep(new ArrayList<>(), edgeList, 0));
        flow = 0;
        while (true)
        {
            visited.clear();
            FlowStep f = getFlowStep(src, (int)2e9);
            if (f == null) // Max flow reached
                break;

            addUnmodifiedEdges(f);
            steps.add(f);
            flow += f.getFlow();
            residualGraph = f.getResidualGraph();
        }
    }
}

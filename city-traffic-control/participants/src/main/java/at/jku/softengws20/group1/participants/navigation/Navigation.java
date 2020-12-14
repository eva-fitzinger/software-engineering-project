package at.jku.softengws20.group1.participants.navigation;


import at.jku.softengws20.group1.participants.roadNetwork.Position;
import at.jku.softengws20.group1.participants.roadNetwork.Road;
import at.jku.softengws20.group1.participants.roadNetwork.RoadNetwork;

import java.util.PriorityQueue;

public class Navigation {
    private RoadNetwork roadNetwork;

    public RoadNetwork getRoadNetwork() {
        return roadNetwork;
    }

    public void setRoadNetwork(RoadNetwork roadNetwork) {
        this.roadNetwork = roadNetwork;
        recalc();
    }

    public void recalc() {

    }

    public Road[] getPath(Position current, Position destination) {
        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.add(new Node(0, current.getRoad(), null));
        while (!queue.isEmpty()) {
            Node n = queue.poll();
            if (n.destination == destination.getRoad()) return n.getPath();
            for (Road road : n.destination.getEnd().getRoads()) {
                queue.add(new Node(n.cost+road.getLength() / road.getEstimatedSpeed(), road, n));
            }
        }
        return null;
    }

    private static class Node implements Comparable<Node> {
        public final double cost;
        public final Road destination;
        public final Node previous;

        public Node(double cost, Road destination, Node previous) {
            this.cost = cost;
            this.destination = destination;
            this.previous = previous;
        }

        @Override
        public int compareTo(Node o) {
            return Double.compare(cost, o.cost);
        }

        public Road[] getPath() {
            int count = 0;
            for (Node curr = this; curr != null; curr = curr.previous) count++;
            Road[] path = new Road[count];
            for (Node curr = this; curr != null; curr = curr.previous) path[--count] = curr.destination;
            return path;
        }
    }
}

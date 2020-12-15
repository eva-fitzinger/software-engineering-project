package at.jku.softengws20.group1.participants.navigation;


import at.jku.softengws20.group1.participants.roadNetwork.Crossing;
import at.jku.softengws20.group1.participants.roadNetwork.Position;
import at.jku.softengws20.group1.participants.roadNetwork.Road;
import at.jku.softengws20.group1.participants.roadNetwork.RoadNetwork;

import java.util.HashSet;
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

    public Road getNext(Crossing current, Position destination) {
        PriorityQueue<Node> queue = new PriorityQueue<>();
        HashSet<Road> visited = new HashSet<>();
        for (Road road : current.getRoads()) {
            queue.add(new Node(0, road, null));
        }
        while (!queue.isEmpty()) {
            Node n = queue.poll();
            if (visited.contains(n.road)) continue;
            visited.add(n.road);
            if (n.road.getId() == destination.getRoad().getId()) {
                return n.getFirst();
            }
            for (Road road : n.road.getEnd().getRoads()) {
                queue.add(new Node(n.cost + road.getLength() / road.getEstimatedSpeed() + 1, road, n));
            }
        }
        return null;
    }

    private static class Node implements Comparable<Node> {
        public final double cost;
        public final Road road;
        public final Node previous;

        public Node(double cost, Road road, Node previous) {
            this.cost = cost;
            this.road = road;
            this.previous = previous;
        }

        @Override
        public int compareTo(Node o) {
            return Double.compare(cost, o.cost);
        }

        public Road getFirst() {
            if (previous == null) return road;
            else return previous.getFirst();
        }

        public Road[] getPath() {
            int count = 0;
            for (Node curr = this; curr != null; curr = curr.previous) count++;
            Road[] path = new Road[count];
            for (Node curr = this; curr != null; curr = curr.previous) path[--count] = curr.road;
            return path;
        }
    }
}

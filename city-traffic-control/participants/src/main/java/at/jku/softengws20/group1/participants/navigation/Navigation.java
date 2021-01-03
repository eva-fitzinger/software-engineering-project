package at.jku.softengws20.group1.participants.navigation;


import at.jku.softengws20.group1.participants.roadNetwork.Crossing;
import at.jku.softengws20.group1.participants.roadNetwork.Road;
import at.jku.softengws20.group1.participants.roadNetwork.RoadNetwork;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.PriorityQueue;

public class Navigation {
    private final HashMap<NavigationPair, Road> getNextCache = new HashMap<>();
    private RoadNetwork roadNetwork;
    private int version;

    public RoadNetwork getRoadNetwork() {
        return roadNetwork;
    }

    public void setRoadNetwork(RoadNetwork roadNetwork) {
        this.roadNetwork = roadNetwork;
        version = roadNetwork.getVersion();
    }

    public Road getNext(Crossing current, Road destination) {
        synchronized (getNextCache) {
            if (roadNetwork.getVersion() == version) {
                var key = new NavigationPair(current, destination);
                if (getNextCache.containsKey(key)) return getNextCache.get(key);
            } else {
                version = roadNetwork.getVersion();
                getNextCache.clear();
            }
        }

        PriorityQueue<Node> queue = new PriorityQueue<>();
        HashSet<Road> visited = new HashSet<>();
        for (Road road : current.getRoads()) {
            if (!road.isClosed()) queue.add(new Node(0, road, null));
        }
        Road next = null;
        while (!queue.isEmpty()) {
            Node n = queue.poll();
            if (visited.contains(n.road)) continue;
            visited.add(n.road);
            if (n.road.getId().equals(destination.getId())) {
                next = n.getFirst();
                break;
            }
            for (Road road : n.road.getEnd().getRoads()) {
                if (!road.isClosed())
                    queue.add(new Node(n.cost + road.getLength() / road.getEstimatedSpeed() + 1, road, n));
            }
        }
        synchronized (getNextCache) {
            if (roadNetwork.getVersion() == version)
                getNextCache.put(new NavigationPair(current, destination), next);
        }
        return next;
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

    private static class NavigationPair {
        private final Crossing crossing;
        private final Road destination;

        public NavigationPair(Crossing crossing, Road destination) {
            this.crossing = crossing;
            this.destination = destination;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            NavigationPair that = (NavigationPair) o;
            return Objects.equals(crossing, that.crossing) && Objects.equals(destination, that.destination);
        }

        @Override
        public int hashCode() {
            return Objects.hash(crossing, destination);
        }
    }
}

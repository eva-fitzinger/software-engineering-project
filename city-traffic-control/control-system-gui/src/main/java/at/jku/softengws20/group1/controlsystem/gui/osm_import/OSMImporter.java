package at.jku.softengws20.group1.controlsystem.gui.osm_import;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

class OSMImporter {

    OSMStreetNetwork parse(String filename) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(filename));

        OSMStreetNetwork streetNetwork = new OSMStreetNetwork();

        NodeList nodeList = document.getDocumentElement().getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                switch (node.getNodeName()) {
                    case "node":
                        OSMNode n = parseOSMNode(node);
                        streetNetwork.getNodes().put(n.getId(), n);
                        break;
                    case "way":
                        OSMWay way = parseOSMWay(node, streetNetwork.getNodes());
                        streetNetwork.getWays().put(way.getId(), way);
                        break;
                    default:
                        break;
                }
            }
        }
        return streetNetwork;
    }

    void combineWays(OSMStreetNetwork streetNetwork) {
        for (OSMNode node : streetNetwork.getNodes().values()) {
            if (node.getStreets().size() == 2) {
                OSMWay[] ways = node.getStreets().values().toArray(new OSMWay[0]);
                OSMWay w0 = ways[0];
                OSMWay w1 = ways[1];

                if (isSameStreetSegment(w0, w1)) {
                    int last0 = w0.getNodes().size() - 1;
                    int last1 = w1.getNodes().size() - 1;
                    int i0 = w0.getNodes().indexOf(node);
                    int i1 = w1.getNodes().indexOf(node);
                    if (i0 != 0 && i0 != last0 || i1 != 0 && i1 != last1) {
                        continue;
                    }
                    if (i0 == 0 && i1 == last1) {
                        var tmp = w0;
                        w0 = w1;
                        w1 = tmp;
                        last0 = last1;
                        var tmp2 = i0;
                        i0 = i1;
                        i1 = tmp2;
                    }
                    if(w0.isOneWay() || w1.isOneWay()) {
                        if (last0 != i0 || i1 != 0) {
                            // one-way roads cannot be reversed
                            continue;
                        }
                    }
                    mergeWays(w0, w1, i0 != last0, i1 != 0, streetNetwork);
                }
            }
        }
    }

    private boolean isSameStreetSegment(OSMWay a, OSMWay b) {
        if (a.getName() == null) return b.getName() == null;
        if (a.getName().isEmpty()) return b.getName().isEmpty();
        return a.getName().equals(b.getName());
    }

    private void mergeWays(OSMWay a, OSMWay b, boolean revertA, boolean revertB, OSMStreetNetwork streetNetwork) {
        if(revertA) {
            Collections.reverse(a.getNodes());
        }
        if(revertB) {
            Collections.reverse(b.getNodes());
        }

        b.getNodes().forEach(n -> {
            a.getNodes().add(n);
            n.getStreets().remove(b.getId());
            n.getStreets().put(a.getId(), a);
        });
        streetNetwork.getWays().remove(b.getId());
    }

    private OSMNode parseOSMNode(Node n) {
        OSMNode node = new OSMNode();
        node.setId(n.getAttributes().getNamedItem("id").getNodeValue());
        node.setLat(Double.parseDouble(n.getAttributes().getNamedItem("lat").getNodeValue()));
        node.setLon(Double.parseDouble(n.getAttributes().getNamedItem("lon").getNodeValue()));
        return node;
    }

    private OSMWay parseOSMWay(Node n, Map<String, OSMNode> nodes) {
        OSMWay way = new OSMWay();
        way.setId(n.getAttributes().getNamedItem("id").getNodeValue());
        NodeList children = n.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node c = children.item(i);
            switch (c.getNodeName()) {
                case "nd":
                    OSMNode node = nodes.get(c.getAttributes().getNamedItem("ref").getNodeValue());
                    way.getNodes().add(node);
                    node.getStreets().put(way.getId(), way);
                    break;
                case "tag":
                    String key = c.getAttributes().getNamedItem("k").getNodeValue();
                    String value = c.getAttributes().getNamedItem("v").getNodeValue();
                    way.getTags().put(key, value);
                    break;
                default:
                    break;
            }
        }
        return way;
    }
}


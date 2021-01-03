package at.jku.softengws20.group1.controlsystem.gui.osm_import;

import at.jku.softengws20.group1.shared.controlsystem.*;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileWriter;
import java.io.IOException;

public class ImportOSM {

    private static final String MAP_NAME = "urfahr";

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        OSMImporter importer = new OSMImporter();
        OSMStreetNetwork network = importer.parse("E:\\myFiles\\JKU\\WS20\\SoftwareEngineering\\osmosis\\"
                + MAP_NAME + "-highways.osm");
        importer.combineWays(network);
        ImportedRoadNetwork rn = ImportedRoadNetwork.fromOSMModel(network, new LatLonProjector());

        RoadNetwork export = new RoadNetwork() {
            @Override
            public Crossing[] getCrossings() {
                return rn.getCrossings().values().stream().map(c -> new Crossing() {
                    @Override
                    public String getId() {
                        return c.getId();
                    }

                    @Override
                    public Position getPosition() {
                        return c.getPosition();
                    }

                    /*
                    public boolean isCrossing() {
                        Set<ImportedCrossing> incomingLanesFromCrossing = new HashSet<>();
                        for (ImportedRoad r : rn.getRoads().values()) {
                            for (ImportedRoadSegment s : r.getRoadSegments()) {
                                if (s.getCrossingB() == c && !s.getRoadType().isLink()) {
                                    incomingLanesFromCrossing.add(s.getCrossingA());
                                    if (s.getRoadType() == RoadType.MOTORWAY) {
                                        return false;
                                    }
                                }
                            }
                        }
                        return incomingLanesFromCrossing.size() >= 2;
                    }*/

                    @Override
                    public String[] getRoadSegmentIds() {
                        return rn.getRoads().values().stream()
                                .flatMap(importedRoad -> importedRoad.getRoadSegments().stream())
                                .filter(s -> s.getCrossingA() == c || s.getCrossingB() == c)
                                .map(ImportedRoadSegment::getId).toArray(String[]::new);
                    }
                }).toArray(Crossing[]::new);
            }

            @Override
            public RoadSegment[] getRoadSegments() {
                return rn.getRoads().values().stream().flatMap(ir -> ir.getRoadSegments().stream()).map(s -> new RoadSegment() {
                    @Override
                    public String getId() {
                        return s.getId();
                    }

                    @Override
                    public String getRoadId() {
                        return s.getRoad().getId();
                    }

                    @Override
                    public String getRoadType() {
                        return s.getRoadType().name();
                    }

                    @Override
                    public int getDefaultSpeedLimit() {
                        return s.getSpeedLimit();
                    }

                    @Override
                    public double getLength() {
                        return s.calculateLength();
                    }

                    @Override
                    public String getCrossingAId() {
                        return s.getCrossingA() != null ? s.getCrossingA().getId() : null;
                    }

                    @Override
                    public String getCrossingBId() {
                        return s.getCrossingB() != null ? s.getCrossingB().getId() : null;
                    }

                    @Override
                    public Position[] getPath() {
                        return s.getPath().toArray(Position[]::new);
                    }

                    /*
                    public int getLaneCount() {
                        return s.getLaneCount();
                    }*/
                }).toArray(RoadSegment[]::new);
            }

            @Override
            public Road[] getRoads() {
                return rn.getRoads().values().stream().map(r -> new Road() {
                    @Override
                    public String getId() {
                        return r.getId();
                    }

                    @Override
                    public String getName() {
                        return r.getName();
                    }

                    @Override
                    public String getNumber() {
                        return r.getNumber();
                    }
                }).toArray(Road[]::new);
            }

            @Override
            public String getMaintenanceCenterRoadSegmentId() {
                return rn.getRoads().values().iterator().next().getRoadSegments().get(0).getId();
            }
        };
        ObjectMapper mapper = new ObjectMapper();
        mapper.getFactory().configure(JsonWriteFeature.ESCAPE_NON_ASCII.mappedFeature(), true);
        String result = mapper.writeValueAsString(export);
        System.out.println(result);
        FileWriter w = new FileWriter(MAP_NAME + ".json");
        w.write(result);
        w.close();
    }
}

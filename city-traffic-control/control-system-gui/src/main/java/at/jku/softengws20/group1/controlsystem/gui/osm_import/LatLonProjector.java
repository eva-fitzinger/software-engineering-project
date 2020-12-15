package at.jku.softengws20.group1.controlsystem.gui.osm_import;

import at.jku.softengws20.group1.shared.impl.model.Position;

public class LatLonProjector {

    private Frame frame = new Frame();

    public void addPos(double lat, double lon) {
        frame.addPos(lat, lon);
    }

    public Position project(double lat, double lon) {
        double dLat = lat - frame.latMin;
        double dLon = lon - frame.lonMin;

        double y = -dLat * 110.571;
        double x = dLon * 111.320 * Math.cos(frame.avgLat() * Math.PI / 180.0);
        return new Position(x, y);
    }

    private double mercY(double lon) {
        lon = lon * Math.PI / 180.0;
        return Math.log(Math.tan(lon / 2.0 + Math.PI / 4.0));
    }

    private class Frame {
        double latMin = 90;
        double latMax = -90;
        double lonMin = 180;
        double lonMax = -180;

        private void addPos(double lat, double lon) {
            latMax = Math.max(latMax, lat);
            latMin = Math.min(latMin, lat);
            lonMax = Math.max(lonMax, lon);
            lonMin = Math.min(lonMin, lon);
        }

        double avgLat() {
            return (latMax + latMin) / 2.0;
        }
    }
}

package dao.Impl;

import dao.stationsDaoInt;
import entity.stations;

public class stationsDao extends generalDao<stations, Long> implements stationsDaoInt<stations, Long> {
    public stationsDao() {
        super.obj = stations.class;
    }
}

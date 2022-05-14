package MyWebApp.dao.Impl;

import MyWebApp.dao.stationsDaoInt;
import MyWebApp.entity.stations;
import org.springframework.stereotype.Repository;

@Repository
public class stationsDao extends generalDao<stations, Long> implements stationsDaoInt<stations, Long> {
    public stationsDao() {
        super.obj = stations.class;
    }
}

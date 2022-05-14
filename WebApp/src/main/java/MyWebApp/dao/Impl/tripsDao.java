package MyWebApp.dao.Impl;

import MyWebApp.dao.tripsDaoInt;
import MyWebApp.entity.trips;
import org.springframework.stereotype.Repository;

@Repository
public class tripsDao extends generalDao<trips, Long> implements tripsDaoInt<trips, Long> {
    public tripsDao() {
        super.obj = trips.class;
    }
}
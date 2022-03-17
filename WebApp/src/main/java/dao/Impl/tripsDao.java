package dao.Impl;

import dao.tripsDaoInt;
import entity.trips;

public class tripsDao extends generalDao<trips, Long> implements tripsDaoInt<trips, Long> {
    public tripsDao() {
        super.obj = trips.class;
    }
}
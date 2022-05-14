package MyWebApp.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "stations_on_trip")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class stations_on_trip {

    @Id
    @Column(name = "entry_id")
    private long id;

    @ManyToOne(targetEntity = MyWebApp.entity.trips.class)
    @JoinColumn(name = "trip_id")
    private MyWebApp.entity.trips trip_id;

    @ManyToOne(targetEntity = stations.class)
    @JoinColumn(name = "station_id")
    private stations station_id;

    @Column(name = "station_number")
    private long st_num;

    @Column(name = "avail_seats")
    private long a_seats;

    @Column(name = "date")
    private Timestamp date;

}
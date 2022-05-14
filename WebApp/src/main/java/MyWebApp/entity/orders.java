package MyWebApp.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class orders {

    @Id
    @Column(name = "order_id")
    private long id;

    //@ManyToOne(targetEntity = stations_on_trip.class)
    //@JoinColumn(name = "trip_id")
    @Column(name = "trip_id")
    private long trip_id;

    @ManyToOne(targetEntity = clients.class)
    @JoinColumn(name = "client_id")
    private clients client_id;

    @Column(name = "start_st_number")
    private long start_num;

    @Column(name = "end_st_number")
    private long end_num;

    @Column(name = "price")
    private double price;

}

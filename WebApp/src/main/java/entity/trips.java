package entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "trips")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class trips {

    @Id
    @Column(name = "trip_id")
    private long id;

    @Column(name = "company")
    private String company;

    @Column(name = "price_coef")
    private double pr_c;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj.getClass() != this.getClass()) { return false; }
        final trips other = (trips) obj;
        return ((this.id == other.id) &&
                (this.company.equals(other.company)) &&
                (this.pr_c == other.pr_c));
    }
}

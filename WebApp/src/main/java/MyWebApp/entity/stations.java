package MyWebApp.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "stations")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class stations {

    @Id
    @Column(name = "station_id")
    private long id;

    @Column(name = "station_name")
    private String name;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj.getClass() != this.getClass()) { return false; }
        final stations other = (stations) obj;
        return (this.id == other.id) &&
                (this.name.equals(other.name));
    }
}
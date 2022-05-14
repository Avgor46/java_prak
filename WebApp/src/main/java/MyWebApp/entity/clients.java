package MyWebApp.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "clients")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class clients {

    @Id
    @Column(name = "client_id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "admin")
    private boolean admin;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String pass;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj.getClass() != this.getClass()) { return false; }
        final clients other = (clients) obj;
        return ((this.id == other.id) &&
                (this.name.equals(other.name)) &&
                (this.address.equals(other.address)) &&
                (this.phone.equals(other.phone)) &&
                (this.email.equals(other.email)) &&
                (this.login.equals(other.login)) &&
                (this.pass.equals(other.pass)) &&
                (this.admin == other.admin));
    }
}
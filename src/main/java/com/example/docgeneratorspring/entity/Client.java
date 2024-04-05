package com.example.docgeneratorspring.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "_client")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cl_id", nullable = false)
    private Long id;

    @Column(name = "cl_surname", nullable = false, length = 500)
    private String clSurname;

    @Column(name = "cl_name", nullable = false, length = 500)
    private String clName;

    @Column(name = "cl_lastname", nullable = false, length = 500)
    private String clLastname;

    @Column(name = "cl_passport", nullable = false, length = 500)
    private String clPassport;

    @Column(name = "cl_info", nullable = false, length = 500)
    private String clInfo;

    @Column(name = "cl_date_reg", nullable = false, length = 500)
    private String clDateReg;

    @Column(name = "cl_address", nullable = false, length = 500)
    private String clAddress;

    @Column(name = "cl_car_ud", nullable = false, length = 500)
    private String clCarUd;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Client client = (Client) o;
        return getId() != null && Objects.equals(getId(), client.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
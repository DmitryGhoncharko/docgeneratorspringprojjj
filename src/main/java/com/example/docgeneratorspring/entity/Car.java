package com.example.docgeneratorspring.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "_car")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id", nullable = false)
    private Long id;

    @Column(name = "car_model", nullable = false, length = 500)
    private String carModel;

    @Column(name = "car_year", nullable = false)
    private Integer carYear;

    @Column(name = "car_gov_number", nullable = false, length = 500)
    private String carGovNumber;

    @Column(name = "car_cz_number", nullable = false, length = 500)
    private String carCzNumber;

    @Column(name = "car_cz_type", nullable = false, length = 500)
    private String carCzType;

    @Column(name = "car_tex", nullable = false, length = 500)
    private String carTex;

    @Column(name = "car_color", nullable = false, length = 500)
    private String carColor;

    @Column(name = "car_type", nullable = false, length = 500)
    private String carType;

    @Column(name = "car_date_tex", nullable = false, length = 500)
    private String carDateTex;

    @Column(name = "car_st_num", nullable = false, length = 500)
    private String carStNum;

    @Column(name = "car_st_sum", nullable = false, length = 500)
    private String carStSum;

    @Column(name = "car_st_valid_date", nullable = false, length = 500)
    private String carStValidDate;

    @Column(name = "car_info", nullable = false, length = 500)
    private String carInfo;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Car car = (Car) o;
        return getId() != null && Objects.equals(getId(), car.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
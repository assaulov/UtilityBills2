package ru.assaulov.utilitybills2.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "t_metering")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"meterId", "meterDataWrite"})
@SuperBuilder(toBuilder = true)
public class Meters implements Serializable {

    @Serial
    static final long serialVersionUID = -7034897190745766939L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meter_id", nullable = false)
    private Long meterId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @Column(name = "meter_date_of_write", nullable = false)
    private LocalDate meterDataWrite;

    @Column(name = "cold_water")
    private Double coldWater;

    @Column(name = "hot_water")
    private Double hotWater;

    private Double electricity;
    private Double gas;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Override
    public String toString() {
        String userId = null;
        if (user.getUserId() != null) {
            userId = user.getUserId().toString();
        }
        return "Meters{" +
                "meterId=" + meterId +
                ", meterDataWrite=" + meterDataWrite +
                ", coldWater=" + coldWater +
                ", hotWater=" + hotWater +
                ", electricity=" + electricity +
                ", gas=" + gas +
                ", user ID=" + userId +
                '}';
    }
}

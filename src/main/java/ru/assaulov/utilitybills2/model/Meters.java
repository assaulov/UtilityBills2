package ru.assaulov.utilitybills2.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "t_metering")
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode(of = {"meterId", "meterDataWrite"})
@SuperBuilder(toBuilder = true)
public class Meters {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "meter_id", nullable = false)
	private Long meterId;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
	@Column(name = "meter_date_of_write", nullable = false)
	private LocalDate meterDataWrite;

	@Column(name = "cold_water")
	private Double coldWater;

	@Column(name = "hot_water")
	private Double hotWater;

	private Double electricity;
	private Double gas;

	@ManyToOne (fetch=FetchType.LAZY, optional=true)
	@JoinColumn(name = "user_id",  insertable = false, updatable = false)
	private User user;


}

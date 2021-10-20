package ru.assaulov.utilitybills2.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class MetersRequest {
	private String userLogin;
	private Long meterId;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
	private LocalDate meterDataWrite;
	private LocalDate dateFrom;
	private LocalDate dateTo;
	private Double coldWater;
	private Double hotWater;
	private Double electricity;
	private Double gas;
}

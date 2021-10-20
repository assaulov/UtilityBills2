package ru.assaulov.utilitybills2.payload.request;

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
	private LocalDate meterDataWrite;
	private LocalDate dateFrom;
	private LocalDate dateTo;
	private Double coldWater;
	private Double hotWater;
	private Double electricity;
	private Double gas;
}

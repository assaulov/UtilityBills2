package ru.assaulov.utilitybills2.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDate;

@Data
public class MetersRequest {
	private String userLogin;
	private long meterId;
	private LocalDate meterDataWrite;
	private LocalDate dateFrom;
	private LocalDate dateTo;
	private Double coldWater;
	private Double hotWater;
	private Double electricity;
	private Double gas;
}

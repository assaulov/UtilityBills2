package ru.assaulov.utilitybills2.payload.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MetersRequest {
	private String userLogin;
	private long meterId;
	private LocalDate meterDataWrite;
	private LocalDate dateFrom;
	private LocalDate dateTo;
}

package ru.assaulov.utilitybills2.payload.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MetersRequest {
	private long userId;
	private long meterId;
	private LocalDate dateOfWriteUtilityMeter;
	private LocalDate dateFrom;
	private LocalDate dateTo;
}

package ru.assaulov.utilitybills2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.assaulov.utilitybills2.exeptions.BaseException;
import ru.assaulov.utilitybills2.exeptions.ErrorType;
import ru.assaulov.utilitybills2.model.Meters;
import ru.assaulov.utilitybills2.payload.request.MetersRequest;
import ru.assaulov.utilitybills2.servises.implimentations.MetersServiceImp;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("meters")
public class MetersController {

	private final MetersServiceImp metersService;

	@Autowired
	public MetersController(MetersServiceImp metersService) {
		this.metersService = metersService;
	}

	@PostMapping
	public ResponseEntity<Meters> saveMeter (@RequestBody MetersRequest request){
		return Optional.of(metersService.saveMeter(request)).map(u -> new ResponseEntity<>(u, HttpStatus.OK)).orElseThrow(() -> new BaseException(
				String.format(ErrorType.ENTITY_NOT_SAVED.getDescription(), request)));
	}

	@GetMapping
	public ResponseEntity<List<Meters>> getAllMeters(@RequestBody MetersRequest request){
		return Optional.of(metersService.findAllByUser_UserId(request)).map(u -> new ResponseEntity<>(u, HttpStatus.OK)).orElseThrow(() -> new BaseException(
				String.format(ErrorType.ENTITY_NOT_SAVED.getDescription(), request)));
	}


}

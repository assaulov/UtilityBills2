package ru.assaulov.utilitybills2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.assaulov.utilitybills2.exeptions.BaseException;
import ru.assaulov.utilitybills2.exeptions.ErrorType;
import ru.assaulov.utilitybills2.model.Meters;
import ru.assaulov.utilitybills2.payload.request.MetersRequest;
import ru.assaulov.utilitybills2.payload.respose.MessageResponse;
import ru.assaulov.utilitybills2.servises.implimentations.MetersServiceImp;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("meters/{userLogin}")
public class MetersController {

	private final MetersServiceImp metersService;

	@Autowired
	public MetersController(MetersServiceImp metersService) {
		this.metersService = metersService;
	}

	@PostMapping()
	public ResponseEntity<?> saveMeter (@PathVariable("userLogin") String userLogin, @RequestBody MetersRequest request){
		request.setUserLogin(userLogin);
		System.out.println(request);
		return Optional.of(metersService.saveMeter(request)).map(u -> new ResponseEntity<>(u, HttpStatus.OK)).orElseThrow(() -> new BaseException(
				String.format(ErrorType.ENTITY_NOT_SAVED.getDescription(), request)));
	}

	@GetMapping()
	public ResponseEntity<?> getAllMeters(@PathVariable("userLogin") String userLogin){

		return Optional.of(metersService.findAllByUser(userLogin)).map(u -> new ResponseEntity<>(u, HttpStatus.OK)).orElseThrow(() -> new BaseException(
				String.format(ErrorType.ENTITY_NOT_SAVED.getDescription(), userLogin)));
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteMeter(@PathVariable("userLogin") String userLogin, @RequestBody MetersRequest request){
		System.out.println(userLogin);
		System.out.println(request);
		if(metersService.deleteMeterById(request)){
			return ResponseEntity.ok(new MessageResponse("Meter with ID: " + request.getMeterId() + " deleted successful"));
		}
		return ResponseEntity.badRequest().body(new MessageResponse("Meter with ID: " + request.getMeterId() + " not found"));
	}

	@PutMapping
	public ResponseEntity<?> updateMeter(@PathVariable("userLogin") String userLogin, @RequestBody MetersRequest request) {
		if(metersService.updateMeterById(request)){
			return ResponseEntity.ok(new MessageResponse("Meter with ID: " + request.getMeterId() + " updated successful"));
		}
		return ResponseEntity.badRequest().body(new MessageResponse("Meter with ID: " + request.getMeterId() + " not found"));
	}

	@GetMapping("/date")
	public ResponseEntity<List<Meters>> getMetersByDate(@PathVariable("userLogin") String userLogin, @RequestBody MetersRequest request){
		request.setUserLogin(userLogin);
		return Optional.of(metersService.findMetersByDate(request)).map(u -> new ResponseEntity<>(u, HttpStatus.OK)).orElseThrow(() -> new BaseException(
				String.format(ErrorType.ENTITY_NOT_FOUND.getDescription(), userLogin)));
	}

	@GetMapping("/period")
	public ResponseEntity<List<Meters>> getMetersByPeriod(@PathVariable("userLogin") String userLogin, @RequestBody MetersRequest request){
		request.setUserLogin(userLogin);
		return Optional.of(metersService.findMetersByPeriod(request)).map(u -> new ResponseEntity<>(u, HttpStatus.OK)).orElseThrow(() -> new BaseException(
				String.format(ErrorType.ENTITY_NOT_FOUND.getDescription(),userLogin)));
	}


}

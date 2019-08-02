package com.poli.policlass.controller;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.poli.policlass.model.entity.Sala;
import com.poli.policlass.repository.SalaRepository;
import com.poli.policlass.service.BlocoService;

@RestController
@RequestMapping("/salas")
public class SalaController {

	@Autowired
	private SalaRepository salaRepository;
	@Autowired
	private BlocoService blocoService;

	@GetMapping
	public List<Sala> listar() {
		return salaRepository.findAll();
	}

	@PostMapping
	public ResponseEntity<Sala> cadastrar(@RequestBody Sala sala, UriComponentsBuilder uriBuilder,
			HttpServletRequest request) {
//		request.isUserInRole("ROLE_ADMIN");
		if (sala.getBloco() != null && blocoService.existeBloco(sala.getBloco().getId())) {
			salaRepository.save(sala);
			URI uri = uriBuilder.path("/salas/{id}").buildAndExpand(sala.getId().toString()).toUri();
			return ResponseEntity.created(uri).body(sala);
		}
		return ResponseEntity.badRequest().build();
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		salaRepository.deleteById(id);
	}

}

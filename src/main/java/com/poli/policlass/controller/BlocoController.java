package com.poli.policlass.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.poli.policlass.model.entity.Bloco;
import com.poli.policlass.service.BlocoService;

@RestController
@RequestMapping("/blocos")
public class BlocoController {

	@Autowired
	private BlocoService blocoService;

	@GetMapping
	public ResponseEntity<List<Bloco>> listar() {
		return ResponseEntity.ok().body(blocoService.listarTodos());
	}

	@PostMapping
	public ResponseEntity<Bloco> cadastrar(@RequestBody Bloco bloco, UriComponentsBuilder uriBuilder) {
		blocoService.cadastrar(bloco);
		URI uri = uriBuilder.path("/blocos/{id}").buildAndExpand(bloco.getId().toString()).toUri();
		return ResponseEntity.created(uri).body(bloco);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Bloco> atualizar(@PathVariable Long id, @RequestBody Bloco bloco) {
		Bloco salvo = blocoService.buscarPorID(id);
		if (salvo != null) {
			BeanUtils.copyProperties(bloco, salvo, "id");
			return ResponseEntity.ok().body(bloco);
		}
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		Bloco bloco = blocoService.buscarPorID(id);
		if (bloco != null) {
			blocoService.removerBloco(bloco);
		}

	}
}

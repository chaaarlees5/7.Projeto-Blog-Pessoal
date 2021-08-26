package com.blogpessoal.Turma29.controladores;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blogpessoal.Turma29.modelos.Tema;
import com.blogpessoal.Turma29.repositorios.TemaRepositorio;

@RestController
@RequestMapping("/api/v1/tema")
public class TemaControlador {

	private @Autowired TemaRepositorio repositorio;

	// Um endPoint com a capacidade de trazer todos os temas.
	@GetMapping("/todos")
	public ResponseEntity<List<Tema>> pegarTodos() {
		List<Tema> objetoLista = repositorio.findAll();

		if (objetoLista.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(objetoLista);
		}
	}

	// Um endPoint com a função de gravar um novo tema no banco de dados.
	@PostMapping("/salvar")
	public ResponseEntity<Tema> salvar(@Valid @RequestBody Tema novoTema) {
		return ResponseEntity.status(201).body(repositorio.save(novoTema));
	}

	// Um endPoint com a função de trazer um unico tema por id.
	@GetMapping("/{id_tema}")
	public ResponseEntity<Tema> buscarPorId(@PathVariable(value = "id_tema") Long idTema) {
		Optional<Tema> objetoTema = repositorio.findById(idTema);

		if (objetoTema.isPresent()) {
			return ResponseEntity.status(200).body(objetoTema.get());
		} else {
			return ResponseEntity.status(204).build();
		}
	}

	// Um endPoint com a função de trazer um unico tema por Descricao (@PathVariable).
	@GetMapping("/{tema}")
	public ResponseEntity<List<Tema>> buscarPorTemaI(@PathVariable(value = "tema") String tema) {
		List<Tema> objetoLista = repositorio.findAllByTemaContainingIgnoreCase(tema);

		if (objetoLista.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(objetoLista);
		}
	}
	
	// Um endPoint com a função de trazer um unico tema por Descricao (@RequestParam).
	@GetMapping("/pesquisa")
	public ResponseEntity<List<Tema>> buscarPorTemaII(@RequestParam(defaultValue = "") String tema) {
		List<Tema> objetoLista = repositorio.findAllByTemaContainingIgnoreCase(tema);

		if (objetoLista.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(objetoLista);
		}
	}

	// Um endPoint com a função de atualizar dados de um tema.
	@PutMapping("/atualizar")
	public ResponseEntity<Tema> atualizar(@Valid @RequestBody Tema temaParaAtualizar) {
		return ResponseEntity.status(201).body(repositorio.save(temaParaAtualizar));
	}

	// Um endPoint com a função de apagar um tema do banco de dados).
	@DeleteMapping("/deletar/{id_tema}")
	public void deletarTemaPorId(@PathVariable(value = "id_tema") Long idTema) {
		repositorio.deleteById(idTema);
	}
}
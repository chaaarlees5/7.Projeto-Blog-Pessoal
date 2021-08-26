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

import com.blogpessoal.Turma29.modelos.Postagem;
import com.blogpessoal.Turma29.repositorios.PostagemRepositorio;

@RestController
@RequestMapping("/api/v1/postagem")
public class PostagemControlador {

	private @Autowired PostagemRepositorio repositorio;

	// Um endPoint com a capacidade de trazer todas as postagens.
	@GetMapping("/todas")
	public ResponseEntity<List<Postagem>> pegarTodos() {
		List<Postagem> objetoLista = repositorio.findAll();

		if (objetoLista.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(objetoLista);
		}
	}

	// Um endPoint com a função de gravar uma nova Postagem no banco de dados.
	@PostMapping("/salvar")
	public ResponseEntity<Postagem> salvar(@Valid @RequestBody Postagem novaPostagem) {
		return ResponseEntity.status(201).body(repositorio.save(novaPostagem));
	}

	// Um endPoint com a capacidade de trazer uma única postagem por ID.
	@GetMapping("/{id_postagem}")
	public ResponseEntity<Postagem> buscarPorId(@PathVariable(value = "id_postagem") Long idPostagem) {
		Optional<Postagem> objetoPostagem = repositorio.findById(idPostagem);

		if (objetoPostagem.isPresent()) {
			return ResponseEntity.status(200).body(objetoPostagem.get());
		} else {
			return ResponseEntity.status(204).build();
		}
	}

	// Um endPoint com a capacidade de trazer uma única postagem por título
	// (@PathVariable).
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> buscarPorTituloI(@PathVariable(value = "titulo") String titulo) {
		List<Postagem> objetoLista = repositorio.findAllByTituloContainingIgnoreCase(titulo);

		if (objetoLista.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(objetoLista);
		}
	}

	// Um endPoint com a capacidade de trazer uma única postagem por título
	// (@RequestParam).
	@GetMapping("/pesquisa")
	public ResponseEntity<List<Postagem>> buscarPorTituloII(@RequestParam(defaultValue = "") String titulo) {
		List<Postagem> objetoLista = repositorio.findAllByTituloContainingIgnoreCase(titulo);

		if (objetoLista.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(objetoLista);
		}
	}

	// Um endPoint com a função de atualizar dados de uma Postagem.
	@PutMapping("/atualizar")
	public ResponseEntity<Postagem> atualizar(@Valid @RequestBody Postagem postagemParaAtualizar) {
		return ResponseEntity.status(201).body(repositorio.save(postagemParaAtualizar));
	}

	// Um endPoint com a função de apagar uma Postagem do banco de dados.
	@DeleteMapping("/deletar/{id_postagem}")
	public void deletarPostagemPorId(@PathVariable(value = "id_postagem") Long idPostagem) {
		repositorio.deleteById(idPostagem);
	}

}
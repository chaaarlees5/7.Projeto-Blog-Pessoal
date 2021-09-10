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

import com.blogpessoal.Turma29.modelos.Usuario;
import com.blogpessoal.Turma29.modelos.utilidades.UsuarioDTO;
import com.blogpessoal.Turma29.repositorios.UsuarioRepositorio;
import com.blogpessoal.Turma29.servicos.UsuarioServicos;

@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioControlador {

//Instancia 'repositorio' como objeto da classe UsuarioRepositorio
	private @Autowired UsuarioRepositorio repositorio; // @Autowired é um injetor de dependência
	private @Autowired UsuarioServicos servicos;
	
//Retorna erro 204 se não encontrar nada na lista e imprime a lista se ela tiver algo.	
//Vai retornar 'ResponseEntity<List<Usuario>>' | 'pegarTodes' é o nome do método
	@GetMapping("/todes")
	public ResponseEntity<List<Usuario>> pegarTodes() {
		List<Usuario> objetoLista = repositorio.findAll();

		if (objetoLista.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(objetoLista);
		}
	}


//Body(atributos) Classe Usuario: idUsuario | nome | email | senha.
	@PostMapping("/salvar")
//'@RequestBody' vai receber dados do body e passá-los para a variável novoUsuario.
//'@Valid vai validar as obrigatoriedades dos atributos de Usuario (@NotBlank, @Email, @Size, etc).
	public ResponseEntity<Object> salvar(@Valid @RequestBody Usuario novoUsuario) {
//'objetoOptional' vai receber o que for feito no método 'cadastrarUsuario' da classe UsuarioServicos.
		Optional<Object> objetoOptional = servicos.cadastrarUsuario(novoUsuario);

		if (objetoOptional.isEmpty()) {
			return ResponseEntity.status(400).build();
		} else {
			return ResponseEntity.status(201).body(objetoOptional.get());
		}
	}
//Body(atributos) Classe Usuario: email | senha
	@PutMapping("/credenciais")
	public ResponseEntity<Object> credenciais(@RequestBody @Valid UsuarioDTO usuarioParaAutenticar) {
//'objetoOptional' vai receber o que for feito no método 'pegarCredenciais' da classe UsuarioServicos.
		Optional<?> objetoOptional = servicos.pegarCredenciais(usuarioParaAutenticar);

		if (objetoOptional.isEmpty()) {
			return ResponseEntity.status(400).build();
		} else {
			return ResponseEntity.status(201).body(objetoOptional.get());
		}
	}
	
	@GetMapping("/{id_usuario}")
// @PathVariable pega o Id passado pela rota (URL) e joga dentro de idUsuario
	public ResponseEntity<Usuario> buscarPorId(@PathVariable(value = "id_usuario") Long idUsuario) {
		Optional<Usuario> objetoUsuario = repositorio.findById(idUsuario);

		if (objetoUsuario.isPresent()) {
			// Método get() é referente ao Optional
			return ResponseEntity.status(200).body(objetoUsuario.get());
		} else {
			return ResponseEntity.status(204).build();
		}
	}
	
	@GetMapping("/nome/{nome_usuario}")
	public ResponseEntity<List<Usuario>> buscarPorNomeI(@PathVariable(value = "nome_usuario") String nome) {
		List<Usuario> objetoLista = repositorio.findAllByNomeContainingIgnoreCase(nome);
		
		if (objetoLista.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(objetoLista);
		}
	}
	
	@GetMapping("/pesquisa")
	public ResponseEntity<List<Usuario>> buscarPorNomeII(@RequestParam(defaultValue = "") String nome) {
		List<Usuario> objetoLista = repositorio.findAllByNomeContainingIgnoreCase(nome);
		
		if (objetoLista.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(objetoLista);
		}
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<Usuario> atualizar(@Valid @RequestBody Usuario usuarioParaAtualizar) {
		return ResponseEntity.status(201).body(repositorio.save(usuarioParaAtualizar));
	}
	
	@DeleteMapping("/deletar/{id_usuario}")
	public void deletarUsuarioPorId(@PathVariable(value = "id_usuario") Long idUsuario) {
		repositorio.deleteById(idUsuario);
	}
}
package com.blogpessoal.Turma29.servicos;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogpessoal.Turma29.modelos.Tema;
import com.blogpessoal.Turma29.repositorios.TemaRepositorio;

@Service
public class TemaServicos {
	
	private @Autowired TemaRepositorio repositorio;

	public Optional<Tema> atualizarTema(Tema temaParaAtualizar) {
		return repositorio.findById(temaParaAtualizar.getIdTema()).map(temaExistente -> {
			temaExistente.setIdTema(temaParaAtualizar.getIdTema());
			temaExistente.setTema(temaParaAtualizar.getTema());

			return Optional.ofNullable(repositorio.save(temaExistente));
		}).orElseGet(() -> {
			return Optional.empty();
		});
	}
}

package com.blogpessoal.Turma29.servicos;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogpessoal.Turma29.modelos.Postagem;
import com.blogpessoal.Turma29.repositorios.PostagemRepositorio;

@Service
public class PostagemServicos {

	private @Autowired PostagemRepositorio repositorio;
	
	public Optional<?> atualizarPostagem(Postagem postParaAtualizar) {
		return repositorio.findById(postParaAtualizar.getIdPostagem()).map(postExistente -> {
			postExistente.setTitulo(postParaAtualizar.getTitulo());
			postExistente.setDescricao(postParaAtualizar.getDescricao());
			postExistente.setTemaRelacionado(postParaAtualizar.getTemaRelacionado());
			return Optional.ofNullable(repositorio.save(postExistente));
		}).orElseGet(() -> {
			return Optional.empty();
		});
	}
	
}

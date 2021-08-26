package com.blogpessoal.Turma29.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blogpessoal.Turma29.modelos.Postagem;

@Repository
public interface PostagemRepositorio extends JpaRepository<Postagem, Long> {
	
	/**
	 * Método utilizado para pesquisar coluna titulo ContainigIgnoreCase
	 * 
	 * @param titulo do tipo String ContainingIgnoreCase
	 * @return List de Postagens
	 * @author charlô
	 */
	List<Postagem> findAllByTituloContainingIgnoreCase(String titulo);

}
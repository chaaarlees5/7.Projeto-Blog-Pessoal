package com.blogpessoal.Turma29.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blogpessoal.Turma29.modelos.Tema;
@Repository
public interface TemaRepositorio extends JpaRepository<Tema, Long> {

	/**
	 * Método utilizado para pesquisar coluna tema ContainigIgnoreCase
	 * 
	 * @param nome do tipo String
	 * @return List de Temas
	 * @author charlô
	 */
	List<Tema> findAllByTemaContainingIgnoreCase(String tema);
}
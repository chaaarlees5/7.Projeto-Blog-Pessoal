package com.blogpessoal.Turma29.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blogpessoal.Turma29.modelos.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
	/**
	 * Método utilizado para pesquisar na coluna nome ContaignIgnoreCase
	 * @param nome do tipo String
	 * @return List de Usuarios
	 * @author charlô
	 */
	List<Usuario> findAllByNomeContainingIgnoreCase(String nome);
}
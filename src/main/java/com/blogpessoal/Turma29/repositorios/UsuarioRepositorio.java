package com.blogpessoal.Turma29.repositorios;

import java.util.List;
import java.util.Optional;

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
	
	/**
	 * Método para pesquisar coluna email
	 * 
	 * @param email do tipo String
	 * @return Optional com Usuario
	 * @author charlô
	 * @since 1.0
	 */
	
	Optional<Usuario> findByEmail(String email);
}
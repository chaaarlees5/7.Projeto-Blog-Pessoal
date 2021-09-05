package com.blogpessoal.Turma29.servicos;

import java.util.Optional;
import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.blogpessoal.Turma29.modelos.Usuario;
import com.blogpessoal.Turma29.modelos.utilidades.UsuarioDTO;
import com.blogpessoal.Turma29.repositorios.UsuarioRepositorio;

@Service
public class UsuarioServicos {
	
	private @Autowired UsuarioRepositorio repositorio;
	
	/**
	 * Método utilizado para criação de um novo usuário e criptografia da senha
	 * 
	 * @param novoUsuario do tipo Usuario
	 * @return Optional com Usuario criado
	 * @author charlô
	 * @since 1.5
	 */
	public Optional<Object> cadastrarUsuario(Usuario novoUsuario) {
		return repositorio.findByEmail(novoUsuario.getEmail()).map(usuarioExistente -> {
			return Optional.empty();
		}).orElseGet(() -> {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String senhaCriptografada = encoder.encode(novoUsuario.getSenha());
			novoUsuario.setSenha(senhaCriptografada);
			
			return Optional.ofNullable(repositorio.save(novoUsuario));
		});
	}
	
	public Optional<?> pegarCredenciais(UsuarioDTO usuarioParaAutenticar) {
		return repositorio.findByEmail(usuarioParaAutenticar.getEmail()).map(usuarioExistente -> {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			
			if(encoder.matches(usuarioParaAutenticar.getSenha(), usuarioExistente.getSenha())) {
				
				String estruturaBasic = usuarioParaAutenticar.getEmail() + ":" + usuarioParaAutenticar.getSenha(); // gustavoboaz@gmail.com:134652
				byte[] autorizacaoBase64 = Base64.encodeBase64(estruturaBasic.getBytes(Charset.forName("US-ASCII"))); // hHJyigo-o+i7%0ÍUG465sas=-
				String autorizacaoHeader = "Basic " + new String(autorizacaoBase64); // Basic hHJyigo-o+i7%0ÍUG465sas=-

				usuarioParaAutenticar.setToken(autorizacaoHeader);
				usuarioParaAutenticar.setId(usuarioExistente.getIdUsuario());
				usuarioParaAutenticar.setNome(usuarioExistente.getNome());
				usuarioParaAutenticar.setSenha(usuarioExistente.getSenha());
				
				return Optional.ofNullable(usuarioParaAutenticar); //Usuario Credenciado
				
			} else {
				return Optional.empty();
			}
			
		}).orElseGet(() -> {
			return Optional.empty();
		});
	}
}

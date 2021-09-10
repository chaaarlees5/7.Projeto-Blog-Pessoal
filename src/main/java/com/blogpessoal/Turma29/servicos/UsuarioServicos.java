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

//'novoUsuario' recebe idUsuario | nome | email | senha
	public Optional<Object> cadastrarUsuario(Usuario novoUsuario) {
		return repositorio.findByEmail(novoUsuario.getEmail()).map(emailExistente -> {
			return Optional.empty();
		}).orElseGet(() -> {
			BCryptPasswordEncoder codificar = new BCryptPasswordEncoder();
//Encriptografa a senha recebida em novoUsuario
			String senhaCriptografada = codificar.encode(novoUsuario.getSenha());
//Substitui a senha recebida pela senha encriptografada
			novoUsuario.setSenha(senhaCriptografada);
//Retorna para o 'objetoOptional' de 'UsuarioControlador' o 'novoUsuario' com a senha encriptografada 
			return Optional.ofNullable(repositorio.save(novoUsuario));
		});
	}

//'usuarioParaAutenticar' recebe email | senha
	public Optional<?> pegarCredenciais(UsuarioDTO usuarioParaAutenticar) {
//Analisa se o email do 'usuarioParaAutenticar' existe dentro da tabela (UsuarioRepositorio)
		return repositorio.findByEmail(usuarioParaAutenticar.getEmail()).map(usuarioExistente -> {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//Compara senha digitada com a senha do 'usuarioExistente' que foi encontrado pelo 'email' 
			if(encoder.matches(usuarioParaAutenticar.getSenha(), usuarioExistente.getSenha())) {
//Cria um token através do email e senha do 'usuarioParaAutenticar'		
				String estruturaBasic = usuarioParaAutenticar.getEmail() + ":" + usuarioParaAutenticar.getSenha(); // gustavoboaz@gmail.com:134652
				byte[] autorizacaoBase64 = Base64.encodeBase64(estruturaBasic.getBytes(Charset.forName("US-ASCII"))); // hHJyigo-o+i7%0ÍUG465sas=-
				String autorizacaoHeader = "Basic " + new String(autorizacaoBase64); // Basic hHJyigo-o+i7%0ÍUG465sas=-

				usuarioParaAutenticar.setToken(autorizacaoHeader);
				usuarioParaAutenticar.setId(usuarioExistente.getIdUsuario());
				usuarioParaAutenticar.setNome(usuarioExistente.getNome());
				usuarioParaAutenticar.setSenha(usuarioExistente.getSenha());
				
				return Optional.ofNullable(usuarioParaAutenticar); //Usuario Credenciado //email e senha ok
				
			} else {
				return Optional.empty(); // Email ok e senha incorreta
			}
		}).orElseGet(() -> {
			return Optional.empty(); // Email incorreto
		});
	}
}

package com.blogpessoal.Turma29.modelos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

/**
 * Classe utilizada como Entidade no Banco de Dados para Tema. A mesma possui
 * atributos que serão colunas no banco com título: tema.
 * 
 * @author Charlô
 * @since 1.0
 */
@Entity
public class Tema {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long idTema;
	private @NotBlank String tema;

	@OneToMany(mappedBy = "temaRelacionado", cascade = CascadeType.REMOVE)
	private List<Postagem> postagens = new ArrayList();
	
	/*
	 * VIDEO MARCELO
	 	//'CascadeType.ALL' pra que quando se deletar um tema, todas as postagens daquele tema sejam excluidas.
	 * @OneToMany(mappedBy = "temaRelacionado", cascade = CascadeType.ALL)
	   private List<Postagem> postagens;
	 */
	
	public Long getIdTema() {
		return idTema;
	}

	public void setIdTema(Long idTema) {
		this.idTema = idTema;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public List<Postagem> getPostagens() {
		return postagens;
	}

	public void setPostagens(List<Postagem> postagens) {
		this.postagens = postagens;
	}

	
}
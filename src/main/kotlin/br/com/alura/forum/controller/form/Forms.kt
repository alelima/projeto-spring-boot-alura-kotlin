package br.com.alura.forum.controller.form

import br.com.alura.forum.model.Topico
import br.com.alura.forum.repository.CursoRepository
import br.com.alura.forum.repository.TopicoRepository
import org.hibernate.validator.constraints.Length
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class TopicoForm (
        @field:NotNull @field:NotEmpty @field:Length(min = 5)   val titulo: String,
        @field:NotNull @field:NotEmpty @field:Length(min = 10) val mensagem: String,
        @field:NotNull @field:NotEmpty val nomeCurso: String) {

    fun conveter(cursoRepository: CursoRepository): Topico {
        val curso = cursoRepository.findByNome(nomeCurso)
        //TODO: Fazer a checagem de nulo aqui depois
        return Topico(titulo, mensagem, curso!!)
    }
}

class AtualizacaoTopicoForm (
        @field:NotNull @field:NotEmpty @field:Length(min = 5)   val titulo: String,
        @field:NotNull @field:NotEmpty @field:Length(min = 10) val mensagem: String) {

    fun atualizar(id: Long, topicoRepository: TopicoRepository): Topico {
        val topico = topicoRepository.getOne(id)
        topico.titulo = this.titulo
        topico.mensagem = this.mensagem
        return topico
    }
}

class LoginForm(val email: String, val senha: String) {
    fun converter(): UsernamePasswordAuthenticationToken {
        return UsernamePasswordAuthenticationToken(email, senha)
    }
}

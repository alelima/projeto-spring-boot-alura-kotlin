package br.com.alura.forum.controller.dto

import br.com.alura.forum.model.Resposta
import br.com.alura.forum.model.StatusTopico
import br.com.alura.forum.model.Topico
import org.springframework.data.domain.Page
import java.time.LocalDateTime

class TopicoDto(topico: Topico) {

    val id: Long?
    val titulo: String
    val mensagem: String
    val dataCriacao: LocalDateTime

    init {
        id = topico.id
        titulo = topico.titulo
        mensagem = topico.mensagem
        dataCriacao = topico.dataCriacao
    }

    companion object {
        fun converter(topicos: Page<Topico>) : Page<TopicoDto> {
            return topicos.map { TopicoDto(it) }
        }
    }
}

class DetalhesDoTopicoDto(topico: Topico) {
    val id: Long?
    val titulo: String
    val mensagem: String
    val dataCriacao: LocalDateTime
    val nomeAutor: String?
    val status: StatusTopico
    val respostas: List<RespostaDto>

    init {
        id = topico.id
        titulo = topico.titulo
        mensagem = topico.mensagem
        dataCriacao = topico.dataCriacao
        nomeAutor = topico.autor?.nome
        status = topico.status
        respostas = topico.respostas.map {RespostaDto(it)}
    }
}

class RespostaDto(resposta: Resposta) {
    val id: Long?
    val mensagem: String?
    val dataCriacao: LocalDateTime
    val nomeAutor: String?

    init {
        id = resposta.id
        mensagem = resposta.mensagem
        dataCriacao = resposta.dataCriacao
        nomeAutor = resposta.autor?.nome
    }
}

class TokenDto(val token: String, val tipo: String)
package br.com.alura.forum.repository

import br.com.alura.forum.model.Curso
import br.com.alura.forum.model.Topico
import br.com.alura.forum.model.Usuario
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface TopicoRepository : JpaRepository<Topico, Long> {
    fun findByCursoNome(nomeCurso: String, paginacao: Pageable): Page<Topico>
}

interface CursoRepository : JpaRepository<Curso, Long> {
    fun findByNome(nomeCurso: String): Curso?
}

interface UsuarioRepository : JpaRepository<Usuario, Long> {
    fun findByEmail(email: String) : Usuario?
}
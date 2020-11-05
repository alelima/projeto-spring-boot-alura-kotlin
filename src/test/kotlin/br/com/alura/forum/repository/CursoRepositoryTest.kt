package br.com.alura.forum.repository

import br.com.alura.forum.model.Curso
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
internal class CursoRepositoryTest() {

    @Autowired
    private lateinit var cursoRepository: CursoRepository

    @Autowired
    private lateinit var em: TestEntityManager

    @Test
    fun deveriaCarregarUmCursoAoBuscarPeloSeuNome() {
        val nomeCurso = "HTML 5"

        val html5 = Curso(nomeCurso, "Programação")
        em.persist(html5)

        val curso = cursoRepository.findByNome(nomeCurso)

        assertAll("Curso",
                { assertNotNull(curso) },
                { assertEquals(nomeCurso, curso?.nome) })

    }

    @Test
    fun naoDeveriaCarregarUmCursoAoBuscarPeloSeuNome() {
        val nomeCurso = "JPA"
        val curso = cursoRepository.findByNome(nomeCurso)
        assertNull(curso)
    }

}
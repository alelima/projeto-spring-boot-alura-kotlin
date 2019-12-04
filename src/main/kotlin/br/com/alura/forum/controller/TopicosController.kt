package br.com.alura.forum.controller

import br.com.alura.forum.controller.dto.DetalhesDoTopicoDto
import br.com.alura.forum.controller.dto.TopicoDto
import br.com.alura.forum.controller.form.AtualizacaoTopicoForm
import br.com.alura.forum.controller.form.TopicoForm
import br.com.alura.forum.model.Topico
import br.com.alura.forum.repository.CursoRepository
import br.com.alura.forum.repository.TopicoRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import javax.transaction.Transactional
import javax.validation.Valid

@RestController
@RequestMapping("/topicos")
class TopicosController(val topicoRepository: TopicoRepository,
                        val cursoRepository: CursoRepository) {

    @GetMapping
    @Cacheable(value = ["listaDeTopicos"])
    fun lista(@RequestParam(required = false) nomeCurso: String?,
              @PageableDefault(page = 0, size = 10) paginacao: Pageable): Page<TopicoDto> {
        return if (nomeCurso == null) {
            TopicoDto.converter(topicoRepository.findAll(paginacao))
        } else {
            TopicoDto.converter(topicoRepository.findByCursoNome(nomeCurso, paginacao))
        }
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = ["listaDeTopicos"], allEntries = true)
    fun cadastrar(@RequestBody @Valid form: TopicoForm, uriBuilder: UriComponentsBuilder) : ResponseEntity<TopicoDto> {
        val topico: Topico = form.conveter(cursoRepository)
        topicoRepository.save(topico)

        val uriComponent = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.id)
        return ResponseEntity.created(uriComponent.toUri()).body(TopicoDto(topico))
    }

    @GetMapping("/{id}")
    fun detalhar(@PathVariable id: Long) : ResponseEntity<DetalhesDoTopicoDto> {
        val topico = topicoRepository.findByIdOrNull(id)
        topico?.let {
            return ResponseEntity.ok(DetalhesDoTopicoDto(it))
        }
        return ResponseEntity.notFound().build<DetalhesDoTopicoDto>()
    }

    @PutMapping("/{id}")
    @Transactional
    @CacheEvict(value = ["listaDeTopicos"], allEntries = true)
    fun atualizar(@PathVariable id: Long, @RequestBody @Valid form: AtualizacaoTopicoForm) : ResponseEntity<TopicoDto> {
        val topico = topicoRepository.findByIdOrNull(id)
        topico?.let {
            val topicoAtualizado = form.atualizar(id, topicoRepository)
            return ResponseEntity.ok(TopicoDto(it))
        }
        return ResponseEntity.notFound().build<TopicoDto>()
    }

    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(value = ["listaDeTopicos"], allEntries = true)
    fun remover(@PathVariable id: Long) : ResponseEntity<Any> {
        val topico = topicoRepository.findByIdOrNull(id)
        topico?.let {
            topicoRepository.deleteById(id)
            return ResponseEntity.ok().build()
        }
        return ResponseEntity.notFound().build()
    }
}
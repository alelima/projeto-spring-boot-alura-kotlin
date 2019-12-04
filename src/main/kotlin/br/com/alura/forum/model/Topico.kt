package br.com.alura.forum.model

import java.time.LocalDateTime
import java.util.ArrayList
import javax.persistence.*

@Entity
data class Topico(var titulo: String, var mensagem: String, @ManyToOne var curso: Curso) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var dataCriacao = LocalDateTime.now()
    @Enumerated(EnumType.STRING)
    var status = StatusTopico.NAO_RESPONDIDO
    @ManyToOne
    var autor: Usuario? = null
    @OneToMany (mappedBy = "topico")
    var respostas: List<Resposta> = ArrayList()

}

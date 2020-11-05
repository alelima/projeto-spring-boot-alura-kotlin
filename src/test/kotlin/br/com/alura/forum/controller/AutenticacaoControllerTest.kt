package br.com.alura.forum.controller

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.awt.PageAttributes
import java.net.URI

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("teste")
internal class AutenticacaoControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc


    @Test
    fun deveriaDevolver400CasoDadosDeAutenticacaoEstejamIncorretos() {
        val uri = URI("/auth")
        val json = "{\"email\":\"unaltorized@email.com\", \"senha\":\"123456\"}"

        mockMvc.perform(MockMvcRequestBuilders.post(uri).content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().`is`(403))

    }

}
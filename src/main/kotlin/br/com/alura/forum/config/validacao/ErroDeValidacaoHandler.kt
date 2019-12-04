package br.com.alura.forum.config.validacao

import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ErroDeValidacaoHandler(val messageSource: MessageSource) {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handle(exception: MethodArgumentNotValidException) : List<ErroDeFormularioDto> {
        val fieldErrors = exception.bindingResult.fieldErrors;
        return fieldErrors.map {
            val message = messageSource.getMessage(it, LocaleContextHolder.getLocale())
            ErroDeFormularioDto(it.field, message)
        }
    }
}
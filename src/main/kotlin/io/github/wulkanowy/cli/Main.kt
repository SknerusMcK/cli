package io.github.wulkanowy.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import io.github.wulkanowy.api.Api
import okhttp3.logging.HttpLoggingInterceptor

class Hello : CliktCommand() {

    private val debug by option().flag()

    private val api = Api()

    private val nick by option().prompt()

    private val pass by option().prompt(hideInput = true)

    override fun run() {
        api.apply {
            logLevel = if (debug) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            host = "vulcan.net.pl"
            email = nick
            password = pass
        }

        echo("Ładowanie...")

        val students = api.getStudents().blockingGet()
        echo("Znaleziono uczniów:")
        students.forEach { echo("${it.studentName} - ${it.description}") }
    }
}

fun main(args: Array<String>) = Hello().main(args)

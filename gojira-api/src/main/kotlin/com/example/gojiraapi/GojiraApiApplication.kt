package com.example.gojiraapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GojiraApiApplication

fun main(args: Array<String>) {
	runApplication<GojiraApiApplication>(*args)
}
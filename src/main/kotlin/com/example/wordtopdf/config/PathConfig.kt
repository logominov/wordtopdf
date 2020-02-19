package com.example.wordtopdf.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Configuration
@Order(1)
open class PathConfig {

    @Value("\${user.dir}")
    private val workingDir: String? = null


    @Bean(FILES_FOLDER)
    open fun tempFolderPath(): Path {
        val tempFolderPath = Paths.get("$workingDir/files")
        return +tempFolderPath
    }

    companion object {
        const val FILES_FOLDER= "temp"
    }
}

/**
 * Used in this class to remove repeated usage
 */
private operator fun Path.unaryPlus(): Path {
    return Files.createDirectories(this)
}
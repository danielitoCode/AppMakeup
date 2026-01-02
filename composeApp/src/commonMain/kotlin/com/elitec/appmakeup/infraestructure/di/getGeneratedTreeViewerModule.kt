package com.elitec.appmakeup.infraestructure.di

import com.elitec.appmakeup.data.filesystem.OkioGeneratedTreeWriter
import com.elitec.appmakeup.domain.codegen.GeneratedTreeWriter
import org.koin.dsl.module

fun getFileSystemModule() = module {
    single<GeneratedTreeWriter> {
        OkioGeneratedTreeWriter(
            fileSystem = get()
        )
    }
}
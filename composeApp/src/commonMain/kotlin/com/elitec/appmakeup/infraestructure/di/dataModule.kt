package com.elitec.appmakeup.infraestructure.di

import com.elitec.appmakeup.data.filesystem.LocalProjectRepository
import com.elitec.appmakeup.domain.repository.ProjectRepository
import kotlinx.serialization.json.Json
import okio.FileSystem
import org.koin.core.module.Module
import org.koin.dsl.module

expect fun getDataModule(): Module
package com.elitec.appmakeup.data.filesystem

import com.elitec.appmakeup.domain.architecture.ArchitectureConfig
import com.elitec.appmakeup.domain.architecture.Layer
import com.elitec.appmakeup.domain.modeling.Feature
import com.elitec.appmakeup.domain.modeling.entity.DomainEntity
import com.elitec.appmakeup.domain.modeling.entity.EntityProperty
import com.elitec.appmakeup.domain.modeling.entity.PropertyType
import com.elitec.appmakeup.domain.project.Project
import com.elitec.appmakeup.domain.project.ProjectLocation
import com.elitec.appmakeup.domain.repository.ProjectRepository
import com.elitec.appmakeup.infraestructure.di.getTestModules
import okio.FileSystem
import okio.Path.Companion.toPath
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class LocalProjectRepositoryTest : KoinTest {

    private val repository: ProjectRepository by inject()
    private val fileSystem: FileSystem by inject()

    private val location = ProjectLocation("/workspace")

    @BeforeTest
    fun setup() {
        startKoin {
            modules(getTestModules())
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `save, load and generate structure using fake filesystem`() {

        val project = Project(
            name = "TaskManager",
            packageName = "com.example.taskmanager",
            architecture = ArchitectureConfig(
                layers = listOf(
                    Layer.DOMAIN,
                    Layer.DATA,
                    Layer.PRESENTATION,
                    Layer.INFRASTRUCTURE
                )
            ),
            features = listOf(
                Feature(
                    name = "task",
                    entity = DomainEntity(
                        name = "Task",
                        properties = listOf(
                            EntityProperty("id", PropertyType.StringType),
                            EntityProperty("title", PropertyType.StringType)
                        )
                    )
                )
            )
        )

        repository.save(location, project)
        repository.generateStructure(location, project)

        val loaded = repository.load(location, "TaskManager")
        assertNotNull(loaded)

        assertTrue(
            (repository as LocalProjectRepository)
                .let { true } // solo para asegurar que es implementación real
        )
        assertTrue(fileSystem.exists("/workspace/TaskManager/features/task/domain".toPath()))
        assertTrue(fileSystem.exists("/workspace/TaskManager/features/task/data".toPath()))
        assertTrue(fileSystem.exists("/workspace/TaskManager/features/task/presentation".toPath()))
    }
}
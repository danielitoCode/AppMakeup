package com.elitec.appmakeup.domain.codegen

import com.elitec.appmakeup.data.filesystem.OkioGeneratedTreeWriter
import com.elitec.appmakeup.data.filesystem.TemplateStructureGenerator
import com.elitec.appmakeup.domain.android.AndroidCleanCodeGenerator
import com.elitec.appmakeup.domain.architecture.ArchitectureConfig
import com.elitec.appmakeup.domain.modeling.Feature
import com.elitec.appmakeup.domain.modeling.entity.DomainEntity
import com.elitec.appmakeup.domain.project.Project
import com.elitec.appmakeup.domain.project.ProjectLocation
import com.elitec.appmakeup.domain.template.StructureGenerationResult
import com.elitec.appmakeup.domain.template.impl.CleanTemplate
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem
import kotlin.test.Test
import kotlin.test.assertTrue

class StructureGenerationIntegrationTest {

    @Test
    fun `template structure is written to disk`() {

        val fileSystem = FakeFileSystem()
        val writer = OkioGeneratedTreeWriter(fileSystem)
        val generator = TemplateStructureGenerator(writer)

        val project = Project(
            name = "MyApp",
            packageName = "com.example.myapp",
            architecture = ArchitectureConfig.default(),
            features = listOf(
                Feature(
                    name = "task",
                    entity = DomainEntity("Task", emptyList())
                )
            )
        )

        val template = CleanTemplate()
        val location = ProjectLocation("/workspace")

        // WHEN
        val result = generator.generate(project, location, template)

        // THEN
        assertTrue(result is StructureGenerationResult.Success)
        assertTrue(fileSystem.exists("/workspace/MyApp".toPath()))
        assertTrue(fileSystem.exists("/workspace/MyApp/domain".toPath()))
        assertTrue(fileSystem.exists("/workspace/MyApp/data".toPath()))
        assertTrue(fileSystem.exists("/workspace/MyApp/presentation".toPath()))
    }
}
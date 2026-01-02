package com.elitec.appmakeup.domain.codegen

import com.elitec.appmakeup.domain.android.AndroidCleanCodeGenerator
import com.elitec.appmakeup.domain.architecture.ArchitectureConfig
import com.elitec.appmakeup.domain.modeling.Feature
import com.elitec.appmakeup.domain.modeling.entity.DomainEntity
import com.elitec.appmakeup.domain.project.Project
import com.elitec.appmakeup.domain.template.impl.CleanTemplate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AndroidCleanCodeGeneratorTest {

    private val generator = AndroidCleanCodeGenerator()

    @Test
    fun `generates root project directory`() {

        val project = fakeProject()

        val result = generator.generate(
            project = project,
            template = CleanTemplate()
        )

        assertEquals(project.name, result.name)
        assertTrue(result.children.isNotEmpty())
    }

    @Test
    fun `generates feature with domain data and presentation layers`() {

        val result = generator.generate(
            project = fakeProject(),
            template = CleanTemplate()
        )

        val src = result.children.first { it.name == "src" } as GeneratedDirectory
        val feature = src.children.first { it.name == "task" } as GeneratedDirectory

        val layerNames = feature.children.map { it.name }

        assertTrue("domain" in layerNames)
        assertTrue("data" in layerNames)
        assertTrue("presentation" in layerNames)
    }

    private fun fakeProject(): Project =
        Project(
            name = "MyApp",
            packageName = "com.example.myapp",
            architecture = ArchitectureConfig.default(),
            features = listOf(
                Feature(
                    name = "task",
                    entity = DomainEntity(
                        name = "Task",
                        properties = emptyList()
                    )
                )
            )
        )
}
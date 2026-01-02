package com.elitec.appmakeup.data.filesystem

import com.elitec.appmakeup.domain.codegen.GeneratedDirectory
import com.elitec.appmakeup.domain.codegen.GeneratedFile
import com.elitec.appmakeup.domain.project.ProjectLocation
import com.elitec.appmakeup.domain.template.DirectoryNode
import com.elitec.appmakeup.domain.template.FileNode
import com.elitec.appmakeup.domain.template.ProjectStructure
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem
import kotlin.test.Test
import kotlin.test.assertTrue

class OkioGeneratedTreeWriterTest {

    private val fileSystem = FakeFileSystem()
    private val writer = OkioGeneratedTreeWriter(fileSystem)

    @Test
    fun `writes project structure to filesystem`() {

        val structure = ProjectStructure(
            directories = listOf(
                DirectoryNode(
                    name = "MyApp",
                    children = listOf(
                        DirectoryNode(
                            name = "src",
                            files = listOf(
                                FileNode(name = "Main.kt")
                            )
                        )
                    )
                )
            )
        )

        val location = ProjectLocation("/workspace")

        // WHEN
        writer.write(location, structure)

        // THEN
        assertTrue(fileSystem.exists("/workspace/MyApp".toPath()))
        assertTrue(fileSystem.exists("/workspace/MyApp/src".toPath()))
        assertTrue(fileSystem.exists("/workspace/MyApp/src/Main.kt".toPath()))
    }
}
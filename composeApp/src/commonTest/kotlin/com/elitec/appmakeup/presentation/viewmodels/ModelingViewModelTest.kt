package com.elitec.appmakeup.presentation.viewmodels

import com.elitec.appmakeup.domain.architecture.ArchitectureConfig
import com.elitec.appmakeup.domain.architecture.Layer
import com.elitec.appmakeup.domain.modeling.Feature
import com.elitec.appmakeup.domain.modeling.entity.DomainEntity
import com.elitec.appmakeup.domain.modeling.entity.EntityProperty
import com.elitec.appmakeup.domain.modeling.entity.PropertyType
import com.elitec.appmakeup.domain.project.Project
import com.elitec.appmakeup.domain.project.ProjectLocation
import com.elitec.appmakeup.domain.repository.ProjectRepository
import com.elitec.appmakeup.domain.usecase.AddEntityPropertyUseCase
import com.elitec.appmakeup.domain.usecase.AddFeatureUseCase
import com.elitec.appmakeup.domain.usecase.CreateProjectUseCase
import com.elitec.appmakeup.domain.usecase.GenerateProjectStructureUseCase
import com.elitec.appmakeup.domain.usecase.InitializeProjectUseCase
import com.elitec.appmakeup.domain.usecase.ValidateProjectUseCase
import com.elitec.appmakeup.domain.validation.RuleViolation
import com.elitec.appmakeup.infraestructure.di.getTestCaseUseModule
import com.elitec.appmakeup.infraestructure.di.getTestModules
import com.elitec.appmakeup.infraestructure.di.getTestValidationModule
import kotlinx.coroutines.test.runTest
import okio.FileSystem
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.getValue
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ModelingViewModelTest : KoinTest {

    private val location = ProjectLocation("/workspace")

    private val generate: GenerateProjectStructureUseCase by inject()
    private val validate: ValidateProjectUseCase by inject()
    private val addFeature: AddFeatureUseCase by inject()
    private val addEntity: AddEntityPropertyUseCase by inject()
    private val create: CreateProjectUseCase by inject()
    private val initialize: InitializeProjectUseCase by inject()

    @BeforeTest
    fun setup() {
        startKoin {
            modules(
                getTestModules(),
                getTestValidationModule(),
                getTestCaseUseModule()
            )
        }
    }

    private fun baseProject() = Project(
        name = "MyApp",
        packageName = "com.example.myapp",
        architecture = ArchitectureConfig.default(),
        features = emptyList()
    )

    @Test
    fun `create project initializes state`() = runTest {

        val viewModel = createViewModel()
        val project = baseProject()

        viewModel.createProject(project, location)

        val state = viewModel.state.value
        assertEquals(project, state.project)
        assertEquals(location, state.location)
        assertFalse(state.isDirty)
    }

    @Test
    fun `add feature marks project as dirty`() = runTest {

        val viewModel = createViewModel()
        viewModel.createProject(baseProject(), location)

        val feature = Feature(
            name = "task",
            entity = DomainEntity(
                name = "Task",
                properties = emptyList()
            )
        )

        viewModel.addFeature(feature)

        val state = viewModel.state.value
        assertEquals(1, state.project!!.features.size)
        assertTrue(state.isDirty)
    }

    @Test
    fun `add property updates entity`() = runTest {

        val viewModel = createViewModel()

        val feature = Feature(
            name = "task",
            entity = DomainEntity("Task", emptyList())
        )

        viewModel.createProject(
            baseProject().copy(features = listOf(feature)),
            location
        )

        viewModel.selectFeature("task")

        viewModel.addProperty(
            EntityProperty(
                name = "id",
                type = PropertyType.StringType
            )
        )

        val properties =
            viewModel.state.value.project!!
                .features.first().entity.properties

        assertEquals(1, properties.size)
    }

    @Test
    fun `generate stops on validation errors`() = runTest {

        val viewModel = createViewModel()
        viewModel.createProject(baseProject(), location)

        viewModel.generate()

        val state = viewModel.state.value
        assertTrue(state.validationErrors.isNotEmpty())
    }

    private fun createViewModel(): ModelingViewModel =
        ModelingViewModel(
            createProject = create,
            addFeature = addFeature,
            addEntityProperty = addEntity,
            validateProject = validate,
            generateStructure = generate,
            initializeProject = initialize
        )

    @AfterTest
    fun tearDown() {
        stopKoin()
    }
}
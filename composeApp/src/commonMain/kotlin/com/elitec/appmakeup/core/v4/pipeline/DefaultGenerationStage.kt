package com.elitec.appmakeup.core.v4.pipeline

import com.elitec.appmakeup.core.v4.contracts.GenerationContext

class DefaultGenerationStage(
    private val domainGenerator: LayerGenerator? = null,
    private val dataGenerator: LayerGenerator? = null,
    private val presentationGenerator: LayerGenerator? = null,
    private val repositoryGenerator: LayerGenerator? = null,
    private val mapperGenerator: LayerGenerator? = null
) : GenerationStage {

    override fun generate(
        context: GenerationContext,
        plan: GenerationPlan
    ): List<GeneratedArtifact> {

        val artifacts = mutableListOf<GeneratedArtifact>()

        if (plan.generateDomain) {
            domainGenerator?.let {
                artifacts += it.generate(context)
            }
        }

        if (plan.generateData) {
            dataGenerator?.let {
                artifacts += it.generate(context)
            }
        }

        if (plan.generateRepositories) {
            repositoryGenerator?.let {
                artifacts += it.generate(context)
            }
        }

        if (plan.generateMappers) {
            mapperGenerator?.let {
                artifacts += it.generate(context)
            }
        }

        if (plan.generatePresentation) {
            presentationGenerator?.let {
                artifacts += it.generate(context)
            }
        }

        return artifacts
    }
}
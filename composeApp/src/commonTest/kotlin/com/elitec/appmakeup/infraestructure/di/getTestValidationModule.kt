package com.elitec.appmakeup.infraestructure.di

import com.elitec.appmakeup.domain.project.Project
import com.elitec.appmakeup.domain.validation.ProjectRule
import com.elitec.appmakeup.domain.validation.RuleViolation
import org.koin.core.module.Module
import org.koin.dsl.module

fun getTestValidationModule(): Module = module {
    single<ProjectRule> {
        object : ProjectRule {
            override fun validate(project: Project): List<RuleViolation> {
                return listOf(RuleViolation("forced error"))
            }
        }
    }
}
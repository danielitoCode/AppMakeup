package com.elitec.appmakeup.projects.templates

class ProjectTemplateRenderer {
    fun render(
        content: String,
        variables: Map<String, String>
    ): String {
        var result = content
        variables.forEach { (key, value) ->
            result = result.replace("{{${key}}}", value)
        }
        return result
    }
}
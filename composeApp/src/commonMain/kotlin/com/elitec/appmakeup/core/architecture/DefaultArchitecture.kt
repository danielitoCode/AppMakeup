package com.elitec.appmakeup.core.architecture

import com.elitec.appmakeup.core.v4.definition.CoreArchitecture
import com.elitec.appmakeup.core.v4.definition.CoreLayer

object DefaultArchitecture {

    val value: CoreArchitecture =
        CoreArchitecture(
            supportedLayers = setOf(
                CoreLayer.DOMAIN,
                CoreLayer.DATA,
                CoreLayer.INFRASTRUCTURE
            ),
            dependencyRules = mapOf(
                CoreLayer.DOMAIN to emptySet(),
                CoreLayer.DATA to setOf(CoreLayer.DOMAIN),
                CoreLayer.INFRASTRUCTURE to setOf(CoreLayer.DOMAIN, CoreLayer.DATA)
            )
        )
}
package com.elitec.appmakeup.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material.icons.filled.FilePresent
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.skia.Surface
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height

@Composable
fun TestScreen(
    windowSize: WindowSize,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.weight(1f).fillMaxSize().background(Color.Red)
        ) {

        }
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.End,
        ) {
            Surface(
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.fillMaxHeight().padding(
                    start = 10.dp, end = 5.dp
                )
            ) {
                Column(
                    modifier = Modifier.padding(10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Estructura de archivos"
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Surface(
                            shape = RoundedCornerShape(5.dp),
                            color = MaterialTheme.colorScheme.errorContainer
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "",
                                modifier = Modifier.size(15.dp),
                                tint = MaterialTheme.colorScheme.onErrorContainer
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        style = MaterialTheme.typography.bodySmall,
                        text = """
                                📒 com.company.project
                                |____features
                                |    |____maestro
                                |         |____data
                                |         |    |____repositories
                                |         |    |     |____💽 MaestroRepositoryImpl.kt
                                |         |    |____mappers
                                |         |    |    |____💽 MaestroDto.toDomain.kt
                                |         |    |____dto
                                |         |____domain
                                |         |    |____entities
                                |         |    |____repositories
                                |         |    |____usecases
                                |         |_____presentation
                                |              |____viewmodels
                                |              |____uiStates
                                |              |____screens
                                |              |____components
                                |              |____navigation
                                |              |____util
                                |____infrastructure
                                     |____di
                                     |____exceptions
                            """.trimIndent()
                    )
                }
            }
            Column(
                verticalArrangement = Arrangement.Top
            ) {
                Surface(
                    shadowElevation = 5.dp,
                    tonalElevation = 5.dp,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.padding(start = 0.dp, end = 10.dp)
                ) {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Default.FileOpen,
                            contentDescription = "Icon TreeView",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

enum class WindowSize {
    Minim, Floating, Maximized, Expanded
}
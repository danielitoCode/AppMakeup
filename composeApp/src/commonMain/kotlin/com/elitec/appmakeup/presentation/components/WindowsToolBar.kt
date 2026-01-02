package com.elitec.appmakeup.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Expand
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Minimize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import appmakeup.composeapp.generated.resources.Res
import appmakeup.composeapp.generated.resources.sinfoto
import com.elitec.appmakeup.presentation.uiStates.AppUiState
import org.jetbrains.compose.resources.painterResource

@Composable
fun WindowsToolBar(
    onThemeChange: () -> Unit,
    minimize: () -> Unit,
    maximize: () -> Unit,
    appTheme: AppUiState,
    exit: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember{ mutableStateOf(false) }
    val iconThemeButton by animateColorAsState(MaterialTheme.colorScheme.onBackground)

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(Res.drawable.sinfoto),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(25.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                color = MaterialTheme.colorScheme.onBackground,
                text = "APPMakeUP"
            )
            Spacer(
                modifier = Modifier.width(15.dp)
            )
            IconButton(
                onClick = onThemeChange,
                modifier = Modifier.safeContentPadding()
            ) {
                AnimatedContent(
                    targetState = if (appTheme.isDarkTheme)
                        Icons.Default.LightMode
                    else
                        Icons.Default.DarkMode
                ) { icon ->
                    Icon(
                        imageVector = icon,
                        contentDescription = "Toggle theme",
                        tint = iconThemeButton
                    )
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(5.dp),
                color = MaterialTheme.colorScheme.onSurface,
                onClick = {
                    minimize()
                }
            ) {
                Icon(
                    tint = MaterialTheme.colorScheme.surface,
                    imageVector = Icons.Default.Minimize,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
            Surface(
                shape = RoundedCornerShape(5.dp),
                color =MaterialTheme.colorScheme.surface ,
                onClick = {
                    isExpanded = !isExpanded
                    maximize()
                }
            ) {
                AnimatedContent(
                    targetState = if(isExpanded) {
                        Icons.Default.ExpandMore
                    } else {
                        Icons.Default.ExpandLess
                    }
                ) { icon ->
                    Icon(
                        tint = MaterialTheme.colorScheme.onSurface,
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }

            }
            Surface(
                shape = RoundedCornerShape(5.dp),
                color = MaterialTheme.colorScheme.error,
                onClick = {
                    exit()
                }
            ) {
                Icon(
                    tint = MaterialTheme.colorScheme.onError,
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
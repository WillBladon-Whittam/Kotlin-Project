package pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen


abstract class BaseContent: Screen {
    /**
     * Composable BasePage all pages inherit from.
     *
     * This contents functions used across multiple pages,
     * and sets up the initial Column them which is consistent throughout all pages
     */

    @Composable
    open fun Content(content: @Composable () -> Unit) {
        /**
         * Configure the default column theme
         */
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF3F51B5), Color(0xFFE3F2FD))
                    )
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content()
        }
    }

    @Composable
    fun ActionButton(text: String, backgroundColor: Color = Color(0xFF6200EE),
                     textColor: Color = Color.White, onClick: () -> Unit ) {
        /**
         * Universal Action Button that is used for over all pages.
         */
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(48.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = text,
                color = textColor,
                style = MaterialTheme.typography.button.copy(fontSize = 16.sp)
            )
        }
    }
}
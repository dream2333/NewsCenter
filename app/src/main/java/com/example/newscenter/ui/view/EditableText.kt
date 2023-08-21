package com.example.newscenter.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EditableText(
    default: String,
    description:String,
    placeholder: String,
    pattern: String,
    onConfirm: (text: String) -> Unit
) {
    var text by remember { mutableStateOf(default) }
    var isEditing by remember { mutableStateOf(false) }
    var editingText by remember { mutableStateOf(text) }
    if (isEditing) {
        AlertDialog(
            onDismissRequest = { isEditing = false },
            title = { Text("Update Info") },
            text = {
                OutlinedTextField(
                    label = { Text(description) },
                    value = editingText,
                    onValueChange = { editingText = it },
                    modifier = Modifier.fillMaxWidth(),
                    isError = !editingText.matches(Regex(pattern)),
                    singleLine = true,
                )
            },
            confirmButton = {
                Button(
                    enabled = editingText.matches(Regex(pattern)),
                    onClick = {
                        isEditing = false
                        text = editingText
                        onConfirm(editingText)
                    }) {
                    Text("OK")
                }
            },
            dismissButton = {
                Button(onClick = { isEditing = false }) {
                    Text("Cancel")
                }
            }
        )
    }
    Text(
        text = text.ifEmpty { placeholder },
        modifier = Modifier
            .padding(16.dp)
            .clickable { isEditing = true },
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.secondary
    )

}
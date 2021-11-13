package com.imdatcandan.wundercars.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.imdatcandan.wundercars.R
import com.imdatcandan.wundercars.presentation.viewmodel.CarViewModel

@Composable
fun ErrorRetryDialog(message: String, viewModel: CarViewModel = hiltViewModel()) {
    MaterialTheme {
        Column {
            val openDialog = remember { mutableStateOf(true) }

            if (openDialog.value) {

                AlertDialog(
                    onDismissRequest = {
                        openDialog.value = true
                    },
                    title = {
                        Text(text = stringResource(id = R.string.dialog_error_title))
                    },
                    text = {
                        Text(text = stringResource(id = R.string.dialog_error_message, message))
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                openDialog.value = false
                                viewModel.getCarList()
                            }) {
                            Text(text = stringResource(id = R.string.dialog_error_button))
                        }
                    }
                )
            }
        }
    }
}
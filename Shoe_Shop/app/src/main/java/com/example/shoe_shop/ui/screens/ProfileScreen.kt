package com.example.shoe_shop.ui.screens

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoe_shop.ui.theme.AppTypography
import com.example.shoe_shop.R
import com.example.shoe_shop.ui.components.DisableButton

@Composable
fun ProfileScreen() {
    var isEditing by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("Еmmanuel") }
    var lastName by remember { mutableStateOf("Oyiboke") }
    var address by remember { mutableStateOf("Nigeria") }
    var phone by remember { mutableStateOf("") }
    var profileBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            profileBitmap = bitmap
        }
    }

    val hasChanges by remember(name, lastName, address, phone) {
        derivedStateOf {
            name != "Еmmanuel" || lastName != "Oyiboke" || address != "Nigeria" || phone != ""
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Spacer(modifier = Modifier.size(40.dp))

                Text(
                    text = stringResource(id = R.string.profile),
                    style = AppTypography.headingSemiBold16,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = {
                        if (isEditing) {

                            name = "Еmmanuel"
                            lastName = "Oyiboke"
                            address = "Nigeria"
                            phone = ""
                        }
                        isEditing = !isEditing
                    },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        painter = painterResource(id =
                            if (isEditing) R.drawable.edit else R.drawable.edit
                        ),
                        contentDescription = if (isEditing)
                            stringResource(R.string.cancel)
                        else
                            stringResource(R.string.edit),
                        tint = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE0E0E0)),
                    contentAlignment = Alignment.Center
                ) {
                    if (profileBitmap != null) {
                        Image(
                            bitmap = profileBitmap!!.asImageBitmap(),
                            contentDescription = stringResource(R.string.profile_photo),
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = { cameraLauncher.launch(null) }
                ) {
                    Text(
                        text = stringResource(R.string.change_profile_photo),
                        style = AppTypography.bodyRegular14,
                        color = Color(0xFF2196F3)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Еmmanuel Oyiboke",
                    style = AppTypography.bodyRegular20
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isEditing) {
                    EditableField(
                        label = stringResource(id = R.string.your_name),
                        value = name,
                        onValueChange = { name = it }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    EditableField(
                        label = stringResource(id = R.string.last_name),
                        value = lastName,
                        onValueChange = { lastName = it }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    EditableField(
                        label = stringResource(id = R.string.address),
                        value = address,
                        onValueChange = { address = it }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    EditableField(
                        label = stringResource(id = R.string.phone_number),
                        value = phone,
                        onValueChange = { phone = it }
                    )
                } else {
                    InputField(label = stringResource(id = R.string.your_name), value = name)
                    Spacer(modifier = Modifier.height(16.dp))
                    InputField(label = stringResource(id = R.string.last_name), value = lastName)
                    Spacer(modifier = Modifier.height(16.dp))
                    InputField(label = stringResource(id = R.string.address), value = address)
                    Spacer(modifier = Modifier.height(16.dp))
                    InputField(label = stringResource(id = R.string.phone_number), value = phone)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            if (isEditing) {
                DisableButton(
                    text = "Сохранить",
                    onClick = {

                        isEditing = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    enabled = hasChanges
                )
            }
        }
    }
}

@Composable
private fun InputField(
    label: String,
    value: String
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = label,
            style = AppTypography.bodyMedium16.copy(
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFFF5F5F5),
            border = CardDefaults.outlinedCardBorder()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = if (value.isNotEmpty()) value else "Не указано",
                    style = AppTypography.bodyRegular16.copy(
                        color = if (value.isNotEmpty()) Color.Black else Color.Gray
                    )
                )
            }
        }
    }
}

@Composable
private fun EditableField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = label,
            style = AppTypography.bodyMedium16.copy(
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle = AppTypography.bodyRegular16,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF6200EE),
                unfocusedBorderColor = Color(0xFFE0E0E0)
            )
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}
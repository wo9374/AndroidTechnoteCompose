@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.androidtechnotecompose.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.androidtechnotecompose.R
import com.example.androidtechnotecompose.viewmodel.RoomViewModel
import kotlinx.coroutines.launch

@Composable
fun RoomScreen(
    roomViewModel: RoomViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val list by roomViewModel.numbers.collectAsState()
    val scrollState = rememberLazyListState()

    Column(modifier = Modifier.fillMaxSize()) {

        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = 4.dp)
        ) {
            items(items = list) {

                var menuExpanded by remember { mutableStateOf(false) }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    CardContent(
                        title = it.strValue,
                        menu = {

                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "menu",
                                modifier = Modifier
                                    .alpha(0.5f)
                                    .clickable { menuExpanded = true }
                            )
                            DropdownMenu(
                                expanded = menuExpanded,
                                onDismissRequest = { menuExpanded = false }
                            ) {
                                DropdownMenuItem(
                                    onClick = {
                                        roomViewModel.deleteItem(it)
                                        menuExpanded = false
                                    }
                                ) {
                                    Text(text = "삭제")
                                }
                            }
                        }
                    )
                }
            }
        }


        val textState = remember { mutableStateOf("") }
        val focusManager = LocalFocusManager.current

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            value = textState.value,
            onValueChange = { textValue ->
                textState.value = textValue
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.ThumbUp,
                    contentDescription = "Email Icon",
                    tint = MaterialTheme.colors.primary
                )
            },
            placeholder = {
                Text(text = stringResource(R.string.add_text))
            },
            colors = TextFieldDefaults.textFieldColors(textColor = Color.LightGray),
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Sentences,
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                if (textState.value.isNotEmpty()) {
                    coroutineScope.launch {
                        roomViewModel.insertItem(textState.value)
                        textState.value = ""
                        focusManager.clearFocus()
                        scrollState.animateScrollToItem(list.lastIndex)
                    }
                } else {
                    Toast.makeText(context, "추가할 Text를 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
            }),
        )
    }
}

@Composable
fun CardContent(
    title: String,
    menu: @Composable (BoxScope.() -> Unit)? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(16.dp)
            .wrapContentHeight()
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.h6,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }

        if (menu != null) {
            Box(
                modifier = Modifier.align(Alignment.CenterVertically),
                content = menu
            )
        }
    }
}
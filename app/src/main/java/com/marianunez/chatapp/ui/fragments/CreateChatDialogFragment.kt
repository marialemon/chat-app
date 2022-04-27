package com.marianunez.chatapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.DialogFragment
import com.marianunez.chatapp.data.network.response.ChatResponse
import com.marianunez.chatapp.ui.theme.ChatAppTheme

class CreateChatDialogFragment : DialogFragment() {

    interface ChatListener {
        fun onChat(chat: ChatResponse)
    }

    private var chatListener: ChatListener? = null

    companion object {
        //la TAG es útil para identificar el fragment
        const val TAG = "Create Chat Dialog"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ChatAppTheme {
                    Surface(
                        modifier = Modifier
                            .wrapContentSize()
                    ) {
                        Modal()
                    }
                }
            }
        }
    }

    @Composable
    fun Modal() {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Title()
                CloseButton()
            }
            InputTextFields()
            ModalButton(text = "Create")
        }
    }

    @Composable
    fun InputTextFields() {
        // this var will be observed by this composable function
        var text by remember {
            mutableStateOf("")
        }
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text(text = "Enter name") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text(text = "Enter description") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )
    }

    @Composable
    fun Title() {
        Text(
            text = "Create chat",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }

    @Composable
    fun ModalButton(text: String) {
        Button(
            onClick = { onCreateClicked() },
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            Text(text = text)
        }
    }

    @Composable
    fun CloseButton() {
        IconButton(onClick = { dismiss() }) {
            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = "Close button"
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        ChatAppTheme {
            Modal()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ChatListener) {
            chatListener = context
        }
    }

    private fun onCreateClicked() {
     //   val chat = ChatResponse(null)
    }
}
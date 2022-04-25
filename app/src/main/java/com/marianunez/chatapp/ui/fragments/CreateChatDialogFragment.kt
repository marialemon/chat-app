package com.marianunez.chatapp.ui.fragments

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
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.DialogFragment
import com.marianunez.chatapp.databinding.FragmentCreateChatDialogBinding
import com.marianunez.chatapp.ui.theme.ChatAppTheme

class CreateChatDialogFragment : DialogFragment(), View.OnClickListener {

    private var _binding: FragmentCreateChatDialogBinding? = null
    private val binding get() = _binding!!

    companion object {
        //la TAG es útil para identificar el fragment
        const val TAG = "Create Chat Dialog"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateChatDialogBinding.inflate(inflater, container, false)
        binding.modalCompose.apply {
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
        return binding.root
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
            InputTextField("Enter name")
            InputTextField("Enter description")
            ModalButton(text = "Create")
        }
    }

    @Composable
    fun InputTextField(label: String) {
        // this var will be observed by this composable function
        var text by remember {
            mutableStateOf("")
        }
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text(text = label) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
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

    override fun onClick(v: View) {
        // TODO
    }

    private fun onCreateClicked() {
        //TODO
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
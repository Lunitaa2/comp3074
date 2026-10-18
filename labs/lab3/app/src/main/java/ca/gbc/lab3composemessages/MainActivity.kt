package ca.gbc.lab3composemessages


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Message(val author: String, val body: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    Conversation(sampleMessages)
                }
            }
        }
    }
}

@Composable
fun MessageCard(msg: Message) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(54.dp)
                .clip(CircleShape)
                .border(2.dp, Color(0xFFE53935), CircleShape) // Red circular outline
        )

        Spacer(modifier = Modifier.width(14.dp))

        Column {
            Text(
                text = msg.author,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF333333)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFF1F3F5),
                border = androidx.compose.foundation.BorderStroke(0.5.dp, Color(0xFFE0E0E0))
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    fontSize = 15.sp,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 40.dp, bottom = 24.dp)
    ) {
        items(messages) { message ->
            MessageCard(msg = message)
        }
    }
}

val sampleMessages = listOf(
    Message("Joe", "Hi!"),
    Message("Jim", "How are you?"),
    Message("Joe", "Test..1..2...3"),
    Message("Joe", "I hate coding!!!"),
    Message("Joe", "Hi!"),
    Message("Jim", "How are you?"),
    Message("Joe", "Test..1..2...3"),
    Message("Joe", "I hate coding!!!"),
    Message("Joe", "Hi!"),
    Message("Jim", "How are you?"),
    Message("Joe", "Test..1..2...3"),
    Message("Joe", "I hate coding!!!")
)
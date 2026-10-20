package ca.gbc.lab4roomdatabase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import ca.gbc.lab4roomdatabase.data.AppDatabase
import ca.gbc.lab4roomdatabase.ui.theme.CustomerApp
import ca.gbc.lab4roomdatabase.ui.theme.CustomerViewModel
import ca.gbc.lab4roomdatabase.ui.theme.CustomerViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(applicationContext)

        setContent {
            MaterialTheme {
                val viewModel: CustomerViewModel = viewModel(
                    factory = CustomerViewModelFactory(database.customerDao())
                )
                CustomerApp(viewModel)
            }
        }
    }
}
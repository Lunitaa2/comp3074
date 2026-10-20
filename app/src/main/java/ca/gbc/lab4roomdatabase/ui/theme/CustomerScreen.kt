package ca.gbc.lab4roomdatabase.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ca.gbc.lab4roomdatabase.data.Customer

@Composable
fun CustomerApp(viewModel: CustomerViewModel) {
    val customers by viewModel.customers.collectAsStateWithLifecycle(initialValue = emptyList())
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    CustomerScreen(
        customers = customers,
        searchQuery = searchQuery,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onClearSearch = viewModel::clearSearch,
        onAddCustomer = viewModel::addCustomer,
        onDeleteCustomer = viewModel::deleteCustomer
    )
}

@Composable
fun CustomerScreen(
    customers: List<Customer>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onClearSearch: () -> Unit,
    onAddCustomer: (String, Int, Boolean) -> Unit,
    onDeleteCustomer: (Customer) -> Unit
) {
    var name by rememberSaveable { mutableStateOf("") }
    var ageText by rememberSaveable { mutableStateOf("") }
    var isActive by rememberSaveable { mutableStateOf(false) }
    var message by rememberSaveable { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        Text(
            text = "Room Customer Demo",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(Modifier.height(12.dp))

        // Search Bar (Student Challenge)
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            label = { Text("Search by customer name") },
            singleLine = true,
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    TextButton(onClick = onClearSearch) {
                        Text("Clear")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        // Form Inputs
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Customer name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = ageText,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() }) {
                    ageText = newValue
                }
            },
            label = { Text("Age") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Active customer")
            Spacer(Modifier.width(8.dp))
            Switch(
                checked = isActive,
                onCheckedChange = { isActive = it }
            )
        }

        Button(
            onClick = {
                val age = ageText.toIntOrNull()
                if (name.isBlank() || age == null) {
                    message = "Enter a valid name and age."
                } else {
                    onAddCustomer(name.trim(), age, isActive)
                    name = ""
                    ageText = ""
                    isActive = false
                    message = "Customer submitted."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Customer")
        }

        message?.let {
            Spacer(Modifier.height(6.dp))
            Text(it, color = MaterialTheme.colorScheme.primary)
        }

        Spacer(Modifier.height(12.dp))

        // Customer List
        CustomerList(
            customers = customers,
            onDeleteCustomer = onDeleteCustomer,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun CustomerList(
    customers: List<Customer>,
    onDeleteCustomer: (Customer) -> Unit,
    modifier: Modifier = Modifier
) {
    if (customers.isEmpty()) {
        Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text("No customers in the database.")
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = customers,
                key = { customer -> customer.id }
            ) { customer ->
                CustomerRow(
                    customer = customer,
                    onDelete = { onDeleteCustomer(customer) }
                )
            }
        }
    }
}

@Composable
fun CustomerRow(
    customer: Customer,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = customer.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text("ID: ${customer.id}")
                Text("Age: ${customer.age}")
                Text(
                    if (customer.isActive) "Active customer" else "Inactive customer"
                )
            }
            TextButton(onClick = onDelete) {
                Text("Delete", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
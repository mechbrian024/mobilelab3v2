package com.example.pokai.pokaichao_comp304sec001_lab3.Screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.pokai.pokaichao_comp304sec001_lab3.Database.DataSource
import com.example.pokai.pokaichao_comp304sec001_lab3.ViewModel.ProductViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProductScreen(
    navController: NavController,
    viewModel: ProductViewModel,
    productId: Int,
    context: Context
) {

    val products by viewModel.allProducts.collectAsStateWithLifecycle(initialValue = emptyList())
    val product = products.find { it.id == productId } ?: return

    var name by remember { mutableStateOf(product.name) }

    // price
    var price by remember { mutableDoubleStateOf(product.price) }
    var priceString by remember { mutableStateOf(price.toString()) }
    var priceError by remember { mutableStateOf(false) }

    // dateOfDelivery
    var dateOfDelivery by remember {
        mutableStateOf(
            LocalDate.parse(
                product.dateOfDelivery
            )
        )
    }
    var dateString by remember { mutableStateOf(dateOfDelivery.toString()) }
    var dateError by remember { mutableStateOf(false) }

    // Category
    val categoryStrings = DataSource.categories.map { stringResource(id = it) }
    val defaultCategory = product.category
    var category by remember { mutableStateOf(defaultCategory) }
    val itemPosition =
        remember { mutableIntStateOf(categoryStrings.indexOf(category).coerceAtLeast(0)) }

    // Favorite
    var favorite by remember { mutableStateOf(product.isFavorite) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("View Product (ID: $productId)") }) },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        )
        {
            // Name
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                isError = name.trim().isEmpty()
            )

            // Price
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = priceString,
                onValueChange = {
                    priceString = it
                    runCatching {
                        val parsed = it.toDouble()

                        val decimalPattern = Regex("""^\d+(\.\d{0,2})?$""") // 小數最多兩位
                        if (parsed < 0 || !decimalPattern.matches(it)) {
                            throw IllegalArgumentException("Invalid price")
                        }

                        price = parsed
                        priceError = false
                    }.onFailure {
                        priceError = true
                    }
                },
                isError = priceError,
                label = { Text("Price") }
            )

            // Date
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = dateString,

                onValueChange = {
                    dateString = it
                    runCatching {
                        val parsedDate = LocalDate.parse(it)
                        dateOfDelivery = parsedDate
                        dateError = false
                    }.onFailure {
                        dateError = true
                    }
                },
                isError = dateError,
                label = { Text("Date of Delivery") },
                suffix = { Text("YYYY-MM-DD") }

            )

            // Category dropdown
            Spacer(modifier = Modifier.height(8.dp))
            var expanded by remember {
                mutableStateOf(false)
            }

            Box(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        expanded = true
                    }
                ) {
                    Text(text = stringResource(id = DataSource.categories[itemPosition.intValue]))
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "DropDown Icon"
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }) {
                    categoryStrings.forEachIndexed { index, categoryText ->
                        DropdownMenuItem(text = {
                            Text(text = categoryText)
                        },
                            onClick = {
                                expanded = false
                                itemPosition.intValue = index
                                category = categoryText
                            })
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "Favorite: $favorite") // 👈 動態顯示 true / false
                Spacer(modifier = Modifier.width(8.dp))
                Switch(
                    checked = favorite,
                    onCheckedChange = { favorite = it }
                )
            }

            // update button
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    runCatching {
                        if (name.trim().isEmpty()) {
                            val toast = Toast.makeText(
                                context,
                                "Error: Please enter a name for your product.",
                                Toast.LENGTH_SHORT
                            )
                            toast.show()
                            throw IllegalArgumentException("Empty Name Field")
                        } else if (price <= 0) {
                            val toast = Toast.makeText(
                                context,
                                "Error: Please enter a positive value for price",
                                Toast.LENGTH_SHORT
                            )
                            toast.show()
                            throw IllegalArgumentException("Price must be above 0")
                        } else if (dateError) {
                            val toast = Toast.makeText(
                                context,
                                "Error: Please enter a correctly formatted date.",
                                Toast.LENGTH_SHORT
                            )
                            toast.show()
                            throw IllegalArgumentException("Invalid Date Format")
                        } else {
                            viewModel.update(
                                product.copy(
                                    name = name,
                                    price = price,
                                    dateOfDelivery = dateOfDelivery.toString(),
                                    category = category,
                                    isFavorite = favorite
                                )
                            )
                        }
                    }.onFailure {
                        val toast = Toast.makeText(
                            context,
                            "Error: Product not changed.",
                            Toast.LENGTH_SHORT
                        )
                        toast.show()
                    }.onSuccess {
                        navController.popBackStack()
                    }
                },
                enabled = !priceError && !dateError,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Change")
            }

            // delete button
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    viewModel.delete(product)
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Delete")
            }
        }
    }
}
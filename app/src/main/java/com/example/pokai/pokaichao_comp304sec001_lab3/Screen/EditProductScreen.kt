package com.example.pokai.pokaichao_comp304sec001_lab3.Screen

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
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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

@Composable
fun EditProductScreen(navController: NavController, viewModel: ProductViewModel, productId: Int) {

    val products by viewModel.allProducts.collectAsStateWithLifecycle(initialValue = emptyList())
    val product = products.find { it.id == productId } ?: return

    var name by remember { mutableStateOf(product?.name ?: "") }
    var price by remember { mutableStateOf(product?.price ?: 0.0) }

    // dateOfDelivery
    var dateOfDelivery by remember {
        mutableStateOf(
            LocalDate.parse(
                product?.dateOfDelivery ?: LocalDate.now().toString()
            )
        )
    }
    var dateString by remember { mutableStateOf(dateOfDelivery.toString()) }
    var dateError by remember { mutableStateOf(false) }

    // Category
    val categoryStrings = DataSource.categories.map { stringResource(id = it) }
    val defaultCategory = product?.category ?: categoryStrings[0]
    var category by remember { mutableStateOf(defaultCategory) }
    val itemPosition =
        remember { mutableStateOf(categoryStrings.indexOf(category).coerceAtLeast(0)) }

    // Favorite
    var favorite by remember { mutableStateOf(product?.isFavorite ?: false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    )
    {
        // Name
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )

        // Price
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = price.toString(),
            onValueChange = { price = it.toDoubleOrNull() ?: 0.0 },
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
                Text(text = stringResource(id = DataSource.categories[itemPosition.value]))
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
                            itemPosition.value = index
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

        Button(
            onClick = {
                if (name.isNotEmpty() && price.toDouble() > 0) {
                    viewModel.update(
                        product.copy(
                            name = name,
                            price = price.toDouble(),
                            dateOfDelivery = dateOfDelivery.toString(),
                            category = category,
                            isFavorite = favorite
                        )
                    )
                    navController.popBackStack()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Change")
        }
    }
}
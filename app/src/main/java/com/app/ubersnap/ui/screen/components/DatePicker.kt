package com.app.ubersnap.ui.screen.components

import android.app.DatePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.ubersnap.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePicker(
    dueDate: MutableState<String>,
    pattern: String = "yyyy-MM-dd",
) {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    val date = if (dueDate.value.isNotBlank()) LocalDate.parse(dueDate.value, formatter) else LocalDate.now()
    val dialog = DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->
            dueDate.value = LocalDate.of(year, month + 1, dayOfMonth).toString()
        },
        date.year,
        date.monthValue - 1,
        date.dayOfMonth,
    )

    Card(
        modifier = Modifier
            .padding(top = 8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    dialog.show()
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                if(dueDate.value=="") stringResource(R.string.select_a_due_date) else dueDate.value,
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.figtree_semi_bold)),
                modifier = Modifier
                    .padding(8.dp)
            )
        }
    }
}
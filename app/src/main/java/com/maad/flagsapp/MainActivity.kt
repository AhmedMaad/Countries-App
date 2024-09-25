package com.maad.flagsapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.maad.flagsapp.data.DataSource
import com.maad.flagsapp.model.Country
import com.maad.flagsapp.ui.theme.FlagsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlagsAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CountryApp(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CountriesList(countries: List<Country>) {
    LazyColumn {
        items(countries) { country ->
            CountryListItem(
                country = country,
            )
        }
    }
}

@Composable
fun OtherCountriesText(modifier: Modifier = Modifier) {

    val context = LocalContext.current

    val annotatedString = buildAnnotatedString {
        append("Check the countries' population from ")

        withStyle(
            style = SpanStyle(
                color = Color.Blue,
                textDecoration = TextDecoration.Underline
            )
        ) {
            pushStringAnnotation(
                tag = "here_tag",
                annotation = "https://www.worldometers.info/geography/how-many-countries-are-there-in-the-world/"
            )
            append("here")
            pop()
        }

    }

    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "here_tag", start = offset, end = offset)
                .firstOrNull()?.let {
                    val i = Intent(Intent.ACTION_VIEW, it.item.toUri())
                    context.startActivity(i)
                }
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp)
    )

}


@Composable
fun CountryListItem(country: Country, modifier: Modifier = Modifier) {

    val context = LocalContext.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = modifier.padding(8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = country.pic),
                contentDescription = stringResource(id = country.name),
                modifier = modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = modifier.width(8.dp))

            Text(
                text = stringResource(id = country.name),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Cursive,
                    fontWeight = FontWeight.Bold
                ),
            )
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_location),
                    contentDescription = "Location",
                    modifier = modifier.clickable {
                        openCountryLocation(context, country.lat, country.long)
                    }
                )
            }
        }
    }
}

fun openCountryLocation(context: Context, lat: Double, long: Double) {
    val i = Intent(Intent.ACTION_VIEW, "geo:$lat,$long".toUri())
    i.`package` = "com.google.android.apps.maps"
    context.startActivity(i)
}

@Composable
private fun CountryApp(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        CountriesList(countries = DataSource().getCountriesData())
        OtherCountriesText()
    }
}

@Preview
@Composable
private fun CountryListItemPreview() {
    CountryListItem(country = DataSource().getCountriesData()[0])
}

@Preview(showBackground = true)
@Composable
private fun CountryAppPreview() {
    CountryApp()
}
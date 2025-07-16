package com.example.kotlindsaandcoroutines

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.kotlindsaandcoroutines.ui.theme.KotlinDSAAndCoroutinesTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        // first coroutine
        GlobalScope.launch {
            repeat(100)
            {
                println("Hello from coroutines")
            }
            // second coroutine
            GlobalScope.launch {
                repeat(100)
                {
                    println("Hello from coroutines 2")
                }

                // concurrency achieve printing both statement mixed up
            }

            // suspension point in coroutines:
            GlobalScope.launch {
                repeat(100)
                {
                    delay(50L)
                    println("Hello from coroutines 3")
                }
            }

            // child coroutines
            GlobalScope.launch {
                //child coroutine
                launch {
                    launch {
                        delay(100)
                        println("Innermost coroutines finished")
                    }
                    delay(500)
                    println("Inner coroutine finished")
                }
                println("Outermost coroutine finished")
            }

            // Blocking code

            /* every app has "main thread" which primary meant to update UI
            *  Dispatcher.Main -- blocking on UI screen
            *
            *
            * */
            GlobalScope.launch(Dispatchers.Main) {

                //suspending call
            delay(300)

                // blocking call
                Thread.sleep(3000L)
                // both are same same concept ~ delay meant to delay and
                // thread meant to sleep the thread.
            }

            setContent {
                KotlinDSAAndCoroutinesTheme {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        KotlinDSAAndCoroutinesTheme {
            Greeting("Android")
        }
    }
}
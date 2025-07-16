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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.kotlindsaandcoroutines.ui.theme.KotlinDSAAndCoroutinesTheme
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis
import kotlin.time.measureTime

class MainActivity : ComponentActivity() {

    private  val customLifecycleScope = CoroutineScope(Dispatchers.Main)

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


            //lifecycle score is coupled to scope of activity
            // change in activity cancel lifecycle scopes
            lifecycleScope.launch {
                delay(5000L)
                println("Lifecycle scope coroutine finished")
            }


            // multiple coroutines:

            lifecycleScope.launch {
                launch {
                    delay(2000L)
                }
                launch {
                    delay(5000L)
                }
            }

            // Coroutine jobs :

            lifecycleScope.launch {
              val coroutinejob1 =  launch {
                    delay(2000L)
                  println("job 1 finished")
                }
                val coroutinejob2 =       launch {
                    delay(5000L)
                    println("job 2 finished")
                }
                delay(1000L)
                coroutinejob1.cancel()
                println("job state: ${coroutinejob1.isActive} and ${coroutinejob2.isActive}")

                // suspend the coroutines they are running in for as long the job takes to complete
                // or cancelled
                val timeMillis = measureTime {
                    coroutinejob1.join()
                    coroutinejob2.join()
                }
                println("jobs took time in $timeMillis milliseconds")
            }

            // own coroutine scope builder
            customLifecycleScope.launch {
                delay(5000L)
                    println("Lifecycle scope coroutine finished")
            }

            // deffer -- to store job data

            val profileDeferred = async {
            println("fetching profile data....")
                delay(2000L)
                "profile"
            }.await()
            val postsDeferred = async {
                println("fetching posts data....")
                delay(3000L)
                "posts"
            }.await()

            val timeMillis2 = measureTime {
//                val posts = postsDeferred.await()
//                val profile = profileDeferred.await()

//                println("profile and post loaded $posts and $profile")
            }

            // coroutine in compose::::::::::::

    /*
    * LaunchedEffect(key1 = counter) {
    * delay(500L)
    * println("launched effect finished")
    * }
    *
    * // produce state -------------------------------------------
    * val counter by produceState(initialValue =0)
    * while(true)
    * {
    * delay(1000L)
    * value++
    * }
    * }
    *
    * // remember coroutine scope -- lifetime is bound to coroutine
    * val scope = rememberCoroutineScope()
    *
    *
    * */

            // Coroutine Context ------------

//         val job =  coroutineContext[Job]
//            val name = coroutineContext[CoroutineName]
            // val handler = coroutineContext[CoroutineExceptionHandler]
//            val dispatcher = coroutineContext

            // coroutineContext.cancle   // etc.

            // val scope = coroutineScope[CoroutinesScope]



            // Coroutine context:
            suspend fun withContextDemo()
            {
                println("Thread: ${Thread.currentThread()}")

                // Main thread - UI update
                withContext(Dispatchers.Main)
                {
                    println("Thread: ${Thread.currentThread()} ")
                }
                // IO - network call, reading from file
                withContext(Dispatchers.IO)
                {
                    println("Thread: ${Thread.currentThread()}")
                }
            }


            // Dispatchers:

            fun ioDefaultDispatcher()
            {
                //UI update
                Dispatchers.Main

                // db query -- IO
                Dispatchers.IO

                // CPU intensive task
                Dispatchers.Default

                // global scope launches in default coroutine
                // dispatcher 1 & dispatcher 2
                GlobalScope.launch {
             println("Thread : ${Thread.currentThread()}  ")
                }
                launch {
                    println("Thread : ${Thread.currentThread()}  ")
                }

                // IO has  only one dispatcher --
                GlobalScope.launch(Dispatchers.IO) {
                    println("Thread : ${Thread.currentThread()}  ")
                    launch {
                        println("Thread : ${Thread.currentThread()}  ")
                    }
                }


//                // IO takes 60 network calls at once, Default takes 8 threads at once
//                fun ioDefaultDispatcher()
//                {
//                    val threads = hashMapOf<Long, String>()
//                 val job=   GlobalScope.launch (Dispatchers.Default)
//                    repeat(100){
//                    launch {
//                        threads[Thread.currentThread().id] = Thread.currentThread().name
//
//                        // blocking network call
//                        Thread.sleep(1000L)
//                    }
//                    }
//                    // default takes 8 threads at once to complete
//                    GlobalScope.launch {
//                        val timeMillis = measureTimeMillis {
//                            job.join()
//                        }
//                        println("Launched ${threads.keys.size}")
//                    }

                // type of dispatchers:
                // Dispatcher.IO
                // Dispatcher.Main
                //Dispatcher.Main.immediate
                //Dispatcher.Default
                // Dispatcher.Main.immediate
                //Dispatcher.unconfined
                }
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
    override fun onDestroy() {
        super.onDestroy()
        // cancle custom scope:::
        customLifecycleScope.cancel()
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


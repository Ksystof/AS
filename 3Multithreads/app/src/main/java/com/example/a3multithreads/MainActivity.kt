package com.example.a3multithreads

import android.R
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity


class MainActivity :  ComponentActivity() {

    fun startNewThread() {
        // Create a new thread
        val myThread = Thread {
            // Your code to be executed in the new thread goes here
            for (i in 0..4) {
                Log.d("MyThread", "Doing work in new thread: $i")
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            // end of code
        }

        // Start the new thread
        myThread.start()
    }

    fun startAnotherThread() {
        // Create a another thread
        val myThread = Thread {
            // Your code to be executed in the new thread goes here
            for (i in 0..4) {
                Log.d("MyThread", "Doing work in new thread: " + i * 10)
                try {
                    Thread.sleep(500)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            // end of code
        }

        // Start the new thread
        myThread.start()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Call the startNewThread() method to create and start a new thread
        startNewThread()
        startAnotherThread()
    }
}



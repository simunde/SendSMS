package com.example.sendsms

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService

class MainActivity : AppCompatActivity() {

    private lateinit var message : EditText
    private lateinit var number : EditText
    private lateinit var send:Button

    private var userMessage:String =""
    private var userNumber:String = ""


    companion object {
        private const val PERMISSION_SEND_SMS = 123
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        message= findViewById(R.id.editTextMessage)
        number= findViewById(R.id.editTextNumber)
        send= findViewById(R.id.buttonSend)

        send.setOnClickListener {
            userMessage=message.text.toString()
            userNumber=number.text.toString()
            if (checkAndRequestPermissions()) {
                sendSMS(userMessage, userNumber)
            }        }
    }

    private fun checkAndRequestPermissions(): Boolean {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.SEND_SMS), PERMISSION_SEND_SMS)
            return false
        }
        return true
    }
    fun sendSMS(userMessage:String, userNumber: String){

        val smsManager: SmsManager = SmsManager.getDefault() // This is the correct way to get the SmsManager instance.
        smsManager.sendTextMessage(userNumber, null, userMessage, null, null)
        Toast.makeText(applicationContext, "Message Sent", Toast.LENGTH_LONG).show()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_SEND_SMS && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            sendSMS(userMessage, userNumber)
        } else {
            Toast.makeText(applicationContext, "SMS Permission Denied", Toast.LENGTH_SHORT).show()
        }

    }


}
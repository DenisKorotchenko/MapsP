package com.deniskorotchenko.mapsp

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    private val REQUEST_PERMISSION_FINE_LOCATION = 1
    private var isPermission = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QuestDataBase(this).initDataBase()
        setContentView(R.layout.activity_start)
        button.setOnClickListener{
            showPermission()
            if (!isPermission){
                Toast.makeText(this, "Разрешения не получены", Toast.LENGTH_LONG).show()
            }
            else {
                val intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
            }

        }
        supportActionBar!!.title = ""
        showPermission()
    }


    private fun showPermission(){
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            isPermission = false
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)) {
                showExplanation("Permission Needed", "Rationale", Manifest.permission.READ_PHONE_STATE, REQUEST_PERMISSION_FINE_LOCATION)

            }
            else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),REQUEST_PERMISSION_FINE_LOCATION)}
        }
        else {
            isPermission = true
        }
    }


    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray)
    {
        if (requestCode == REQUEST_PERMISSION_FINE_LOCATION) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted!", Toast.LENGTH_LONG).show()
                isPermission = true
            } else {
                Toast.makeText(this, "Permission Denied!", Toast.LENGTH_LONG).show()
                isPermission = false
            }
        }
    }




    private fun showExplanation(title: String,
                                message: String,
                                permission: String,
                                permissionRequestCode: Int){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok) { dialog, id -> requestPermission(permission, permissionRequestCode) }
        val created = builder.create()
        created.show()
    }

    private fun requestPermission(permissionName: String, permissionRequestCode: Int) {
        ActivityCompat.requestPermissions(this,
                arrayOf(permissionName), permissionRequestCode)
    }
}

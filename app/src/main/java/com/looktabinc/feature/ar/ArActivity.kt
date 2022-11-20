package com.looktabinc.feature.ar

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.looktabinc.Config
import com.looktabinc.LocationProvider
import com.looktabinc.R
import com.looktabinc.base.BaseActivity
import com.looktabinc.databinding.ActivityArBinding
import com.wikitude.architect.ArchitectStartupConfiguration
import java.io.IOException


class ArActivity : BaseActivity<ActivityArBinding>(R.layout.activity_ar) {

    val KEY = "vxU3jqQ8yxZstSyEmy1f3aXkQwUBK91BHHuJWuCmgZRiXnDrXASxs0R43mSgj7Vvp0SxBtVvzTNPzSWBJuZhJKwhbYUyDa/PGvcmNOnqdQAZgsp1t2DfcPTzGUlZQ8SbfBrCHDN/Dfi4Sut803fPDalnakr2u4ofGabxLEa6dgdTYWx0ZWRfXwTNnZbzVu325DDh1NetPifL8b5IuSIWFd6ht45p9ss5tgQv5/A5ucH0LhimRrMX4XlF0fNrF3RX71GF5hALz8uQ/3No/ZO0tR9sn5UMN+wu463YAlp74hvvqfKJgdPKRliedMzFhKpne4AetUzYJeg7PlRM4zFtyjGRj0nxHOW+i8v+bK+tADSAP0tZX6FP1/Lx+sWOVa4w46BIC/Zo7qoEp9Xcuceqy36K8A6T2l25rWCGnwuQEPLdFZL7PtwCCkpO9MHiZJMMyspBFqDQA4JXYY3Bk9W1naMpFwVEnl23bXG9+X+bixEdwetxSZjmzIGh4D/hROANxqGNheo7hbAVxoQpUwrQM0Pkpf4TcglgtIdrPnaQjytkvFEru67UwxH3alR4sn53B6g2rPUkeHyZn+g8heOQlFOP7FhxJYU6EI9sMAi7jNfJL/kWBE1PANC3V+7WvXEvZzlY/HHGXXli5zSk+P9kH3c2hxygtQk+Ep/Jiat9Q2Qfrxh2mBv7oySJS/zcXAZbSOveanSIDPzva24d6RdbCYcHAEcOSL1uYNK5HE41k+XMZEocNWOT0IPP1wWb9pNKSbsm0S7C/1siU9fp/BFdyJjEIw1vPjy5HyjcAO4pvsj6kSlBmDdsjJWtDgY3nNaELMqroXI5ITmBwkJkbMRs6kzQtnvq4QVJgphtkGFhalc="
    private val url by lazy {
        intent?.getStringExtra(Config.LOAD_URL) ?: Config.Base_URL
    }

    private var locationProvider: LocationProvider? = null
    override fun initViews() {
        checkPermission()
        Log.e("url",url)
        val config = ArchitectStartupConfiguration()
        config.licenseKey = KEY
        binding.architectView.onCreate(config)

        binding.architectView.let {
            locationProvider = LocationProvider(this, object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    if (location.hasAltitude() && location.hasAccuracy() && location.accuracy < 7) {
                        it.setLocation(
                            location.latitude,
                            location.longitude,
                            location.altitude,
                            location.accuracy
                        )
                    } else {
                        it.setLocation(
                            location.latitude,
                            location.longitude,
                            (if (location.hasAccuracy()) location.accuracy.toDouble() else 1000.0)
                        )
                    }
                }


                override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {}
                override fun onProviderEnabled(s: String) {}
                override fun onProviderDisabled(s: String) {}
            })

        }

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        binding.architectView.onPostCreate()
        try {
            binding.architectView.load(url)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.architectView.onResume()
        // start location updates
        locationProvider?.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.architectView.onPause()
        // stop location updates
        locationProvider?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.architectView.onDestroy()
    }


    //=========Permission=========//
    private val GPS_ENABLE_REQUEST_CODE = 2001
    private val PERMISSIONS_REQUEST_CODE = 100
    var REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION, Manifest
            .permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION
    )


    private fun checkPermission() {
        if (!checkLocationServicesStatus()) {
            showDialogForLocationServiceSetting()
        } else {
            checkRunTimePermission()
        }
    }

    private fun checkLocationServicesStatus(): Boolean {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            GPS_ENABLE_REQUEST_CODE ->                 //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {
                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음")
                        checkRunTimePermission()
                        return
                    }
                }
        }
    }

    private fun checkRunTimePermission() {
        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        val hasFineLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED) {
            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)
            // 3.  위치 값을 가져올 수 있음
        } else {
            //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.
            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
            ) {
                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG)
                    .show()
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(
                    this,
                    REQUIRED_PERMISSIONS,
                    PERMISSIONS_REQUEST_CODE
                )
            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(
                    this,
                    REQUIRED_PERMISSIONS,
                    PERMISSIONS_REQUEST_CODE
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        permsRequestCode: Int, permissions: Array<out String>, grandResults: IntArray
    ) {
        super.onRequestPermissionsResult(permsRequestCode, permissions, grandResults)
        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.size ==
            REQUIRED_PERMISSIONS.size
        ) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면
            var check_result = true

            // 모든 퍼미션을 허용했는지 체크합니다.
            for (result in grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false
                    break
                }
            }
            if (check_result) {
                Log.d("@@@", "start")
                //위치 값을 가져올 수 있음
            } else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있다
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        REQUIRED_PERMISSIONS[0]
                    )
                ) {
                    Toast.makeText(
                        this@ArActivity,
                        "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.",
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                } else {
                    Toast.makeText(
                        this@ArActivity,
                        "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun showDialogForLocationServiceSetting() {
        val builder = AlertDialog.Builder(this@ArActivity)
        builder.setTitle("위치 서비스 비활성화")
        builder.setMessage(
            "앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하시겠습니까?"
        )
        builder.setCancelable(true)
        builder.setPositiveButton("설정") { dialog, id ->
            val callGPSSettingIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE)
        }
        builder.setNegativeButton(
            "취소"
        ) { dialog, id -> dialog.cancel() }
        builder.create().show()
    }

    fun checkCameraPermission() {
        val permissionCheck =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            // 권한 없음
            Log.e("CameraPermission", "권한 없음")
            requestPermission()
        } else {
            // 권한이 이미 있음.
            Log.e("CameraPermission", "권한 이미 있음")
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this as FragmentActivity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1000
        )
    }
    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, ArActivity::class.java)
        }

        fun start(context: Context?, action: Intent.() -> Unit = {}) {
            val intent = Intent(context, ArActivity::class.java).apply(action)
            context?.startActivity(intent)
        }


        fun start(context: Context?, loadUrl: String, action: Intent.() -> Unit = {}) {
            val intent = Intent(context, ArActivity::class.java).apply(action)
            intent.putExtra(Config.LOAD_URL, loadUrl)
            context?.startActivity(intent)
        }
    }
}
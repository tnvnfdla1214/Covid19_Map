package com.example.covid19_map.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.UiThread
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.example.covid19_map.R
import com.example.covid19_map.databinding.ActivityMainBinding
import com.example.covid19_map.ui.DialogBottomSheetMap
import com.example.covid19_map.ui.base.BindingActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main),
    OnMapReadyCallback {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun initView() {
        attachFragmentmanager()
    }

    fun attachFragmentmanager(){
        val mapFragment = supportFragmentManager.run {
            // 옵션 설정
            val option = NaverMapOptions().mapType(NaverMap.MapType.Basic)
                .camera(CameraPosition(LatLng(37.530039, 126.926209), 16.0))
                .locationButtonEnabled(false)
            findFragmentById(R.id.map_covid) as MapFragment? ?: MapFragment.newInstance(option)
                .also {
                    beginTransaction().add(R.id.map_covid, it).commit()
                }
        }

        mapFragment.getMapAsync(this)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions,
                grantResults
            )
        ) {
            if (!locationSource.isActivated) {
                Log.d(TAG, "MainActivity - onRequestPermissionsResult 권한 거부됨")
                naverMap.locationTrackingMode = LocationTrackingMode.None
            } else {
                Log.d(TAG, "MainActivity - onRequestPermissionsResult 권한 승인됨")
                naverMap.locationTrackingMode = LocationTrackingMode.Follow
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @UiThread
    override fun onMapReady(map: NaverMap) {
        map.locationSource = locationSource
        this.naverMap = map.apply {
            binding.btnLocation.map = this
        }

        viewModel.getCenterList()

        viewModel.CenterList.observe(this, Observer { dbList ->
            val markers = mutableListOf<Marker>()
            dbList.forEach {
                markers += Marker().apply {
                    // 마커의 위치 지정
                    position = LatLng(it.lat.toDouble(), it.lng.toDouble())
                    // 마커의 정보를 담을 해쉬맵
                    val hashMap = HashMap<String, Any>().apply {
                        put("id", it.id)
                        put("address", it.address)
                        put("centerName", it.centerName)
                        put("facilityName", it.facilityName)
                        put("phoneNumber", it.phoneNumber)
                        put("updatedAt", it.updatedAt)
                    }
                    // 아이콘 설정
                    icon = if (it.centerType.contains("중앙")) {
                        MarkerIcons.GREEN.also {
                            hashMap["icon"] = R.drawable.navermap_default_marker_icon_green
                        }
                    } else {
                        MarkerIcons.BLUE.also {
                            hashMap["icon"] = R.drawable.navermap_default_marker_icon_blue
                        }
                    }
                    // 해당 마커의 정보 지정
                    tag = hashMap
                    isHideCollidedSymbols = true
                    isIconPerspectiveEnabled = true
                    setOnClickListener {
                        naverMap.moveCamera(
                            CameraUpdate.scrollAndZoomTo(position, 16.0)
                                .animate(CameraAnimation.Easing)
                        )
                        DialogBottomSheetMap(this@MainActivity, hashMap).apply {
                            show(supportFragmentManager, tag)
                        }
                        true
                    }
                }
            }
            CoroutineScope(Dispatchers.Main).launch {
                markers.forEach { marker ->
                    marker.map = naverMap
                }
            }
        })



        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        // 사용자 현재 위치 받아오기
        var currentLocation: Location?
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(this@MainActivity).apply {
                lastLocation.addOnSuccessListener { location: Location? ->
                    currentLocation = location
                    // 위치 오버레이의 가시성은 기본적으로 false로 지정되어 있습니다. 가시성을 true로 변경하면 지도에 위치 오버레이가 나타납니다.
                    // 파랑색 점, 현재 위치 표시
                    naverMap.locationOverlay.run {
                        isVisible = true
                        position = LatLng(currentLocation!!.latitude, currentLocation!!.longitude)
                    }

                    // 카메라 현재위치로 이동
                    val cameraUpdate = CameraUpdate.scrollTo(
                        LatLng(
                            currentLocation!!.latitude,
                            currentLocation!!.longitude
                        )
                    )
                    with(naverMap) {
                        naverMap.moveCamera(cameraUpdate)
                        locationTrackingMode = LocationTrackingMode.Follow
                    }
                }
            }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
        private const val TAG = "MainActivity"
    }
}
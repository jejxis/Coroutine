package com.example.coroutine

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.coroutine.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.run{
            buttonDownload.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch { //Dispatchers.MAIN: UI 관련 요소를 다룰 수 있다.
                    progress.visibility = View.VISIBLE//프로그래스바 동작하기
                    val url = editUrl.text.toString()//플레인 텍스트 값 저장

                    val bitmap = withContext(Dispatchers.IO){//배개그라운드 처리를 담당하는 IO 컨텍스트에서 진행.
                        loadImage(url)
                    }

                    imagePreview.setImageBitmap(bitmap)
                    progress.visibility = View.GONE

                }
            }
        }

    }
}
suspend fun loadImage(imageUrl: String): Bitmap {
    val url = URL(imageUrl)//URL 객체 생성
    val stream = url.openStream()//URL이 가지고 있는 openStream을
    return BitmapFactory.decodeStream(stream)//Bitmap이미지로 저장한 후 반환
}
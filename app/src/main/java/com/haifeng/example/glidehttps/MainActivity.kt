package com.haifeng.example.glidehttps

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = PictureListAdapter(this)

        list_view.adapter = adapter
        list_view.layoutManager = LinearLayoutManager(this)

        //图片数据来源于豆瓣开放API
        val list = arrayListOf<String>(
            "https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1451933721.86.webp",

            "https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p37599.webp",

            "https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p44375.webp",
            "https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1564150860.79.webp"

        )
        adapter.updateData(list)


    }
}

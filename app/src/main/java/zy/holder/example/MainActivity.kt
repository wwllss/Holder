package zy.holder.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import zy.holder.OneAdapter
import zy.holder.appb.AppBActivity
import zy.holder.model.BModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        helloWorld.setOnClickListener {
            AppBActivity.start(this)
        }
        val bModel = BModel()
        val holder = OneAdapter.newHolder(findViewById(R.id.parent), bModel)
        holder.bindData(bModel)

    }
}

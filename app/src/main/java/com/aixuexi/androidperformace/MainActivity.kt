package com.aixuexi.androidperformace

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import androidx.appcompat.app.AppCompatActivity
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.LayoutInflaterCompat
import com.zhangyue.we.x2c.X2C
import com.zhangyue.we.x2c.ano.Xml

@Xml(layouts = ["activity_main"])
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LayoutInflaterCompat.setFactory2(layoutInflater,object : LayoutInflater.Factory2{
            override fun onCreateView(parent: View?, name: String, context: Context, attrs: AttributeSet): View? {
                if(TextUtils.equals(name,"TextView")){
                    // 生成自定义TextView
                }
                val time = System.currentTimeMillis()
                val view = delegate.createView(parent,name,context,attrs)
                return view
            }

            override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
                return null
            }

        })
//        AsyncLayoutInflater(this).inflate(R.layout.activity_main,null)
//        { view, resid, parent -> setContentView(view) }
        X2C.setContentView(this,R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
    }

}
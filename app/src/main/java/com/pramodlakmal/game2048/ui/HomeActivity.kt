package com.pramodlakmal.game2048.ui

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.pramodlakmal.game2048.BuildConfig
import com.pramodlakmal.game2048.R
import com.pramodlakmal.game2048.shared.LocalData
import kotlinx.android.synthetic.main.finish_dialog.view.*
import kotlinx.android.synthetic.main.home_activity.*

class HomeActivity : AppCompatActivity() {
    var count = "classic"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        about_info.setOnClickListener {
            val dialog = Dialog(this)
            val view: View = LayoutInflater.from(this)
                .inflate(R.layout.about_game, findViewById(R.id.container), false)
            dialog.setContentView(view)

            view.findViewById<View>(R.id.button_exit_yes)
                .setOnClickListener { view2: View? ->
                    dialog.cancel()
                    dialog.dismiss()
                }
            dialog.show()

            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        initType()
        previous_game_type.setOnClickListener {
            when (count) {

                "big"->{
                    count="classic"
                    game_type_name.text = "CLASSIC (4x4)"
                    image_type.setImageResource(R.drawable.img_2)

                }

                "classic" -> {
                    count="small"
                    game_type_name.text = "SMALL (3x3)"
                    image_type.setImageResource(R.drawable.img_small)

                }


            }
        }
        next_type_game.setOnClickListener {
            when (count) {

                "small"->{
                    count="classic"
                    image_type.setImageResource(R.drawable.img_2)
                    game_type_name.text = "CLASSIC (4x4)"
                }
                "classic"->{
                    count="big"
                    image_type.setImageResource(R.drawable.img_1)
                    game_type_name.text = "BIG (5x5)"

                }
                "big"->{
                    image_type.setImageResource(R.drawable.img_1)
                    game_type_name.text = "BIG (5x5)"

                }

            }
        }

        start_game.setOnClickListener{
              when(count){
                  "classic"->{
                      LocalData.setGameTypeCount("classic")
                      startActivity(Intent(this@HomeActivity,GameActivity::class.java))
                  }
                  "small"->{
                      LocalData.setGameTypeCount("small")
                      startActivity(Intent(this@HomeActivity,GameSmallActivity::class.java))

                  }
                  "big"->{
                      LocalData.setGameTypeCount("big")
                      startActivity(Intent(this@HomeActivity,GameBigActivity::class.java))

                  }
              }

        }
    }

    private fun initType() {
        if (LocalData.getGameTypeCount().isEmpty()) {
            image_type.setImageResource(R.drawable.img_2)
            game_type_name.text = "CLASSIC (4x4)"
        } else {
            when (LocalData.getGameTypeCount()) {
                "classic" -> {
                    count="classic"
                    image_type.setImageResource(R.drawable.img_2)
                    game_type_name.text = "CLASSIC (4x4)"

                }
                "small"->{
                    count="small"
                    image_type.setImageResource(R.drawable.img_small)
                    game_type_name.text = "SMALL (3x3)"


                }
                else -> {
                    count="big"
                    image_type.setImageResource(R.drawable.img_1)
                    game_type_name.text = "BIG (5x5)"

                }
            }
        }
    }
}
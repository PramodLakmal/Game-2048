package com.pramodlakmal.game2048.ui

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pramodlakmal.game2048.BuildConfig
import com.pramodlakmal.game2048.Contract.GameContract
import com.pramodlakmal.game2048.Contract.GameContractBig
import com.pramodlakmal.game2048.R
import com.pramodlakmal.game2048.data.Movement
import com.pramodlakmal.game2048.presenter.GamePresenter
import com.pramodlakmal.game2048.presenter.GamePresenterBig
import com.pramodlakmal.game2048.repository.GameRepository
import com.pramodlakmal.game2048.repository.GameRepositoryBig
import com.pramodlakmal.game2048.shared.LocalData
import com.pramodlakmal.game2048.utils.MovementDetector
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.finish_dialog.view.*

class GameBigActivity:AppCompatActivity(), GameContractBig.View {

    private lateinit var btnUndo: ImageView
    private lateinit var btnNewGame: ImageView
    private lateinit var score: TextView
    private lateinit var target: TextView
    private lateinit var record: TextView

    //    private lateinit var dialog: DialogExit
    private lateinit var btnUndo2: ImageView
    private var finish: Boolean = true

    private val buttons: ArrayList<TextView> = ArrayList(25)
    private val presenter = GamePresenterBig(this, GameRepositoryBig())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_big)
        loadViews()
        presenter.startGame()


    }

    override fun onBackPressed() {
        val dialog = Dialog(this)
        val view: View = LayoutInflater.from(this)
            .inflate(R.layout.exit_dialog, findViewById(R.id.container), false)
        dialog.setContentView(view)
        view.findViewById<View>(R.id.button_exit_no).setOnClickListener { view1: View? ->
            dialog.cancel()
            dialog.dismiss()
        }
        view.findViewById<View>(R.id.button_exit_yes)
            .setOnClickListener { view2: View? -> finish() }
        dialog.show()

        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    }
    private fun shareTextOnly(title: String) {

        // The value which we will sending through data via
        // other applications is defined
        // via the Intent.ACTION_SEND
        val intentt = Intent(Intent.ACTION_SEND)

        // setting type of data shared as text
        intentt.type = "text/plain"
        intentt.putExtra(Intent.EXTRA_SUBJECT, "Subject Here")

        // Adding the text to share using putExtra
        intentt.putExtra(Intent.EXTRA_TEXT, title)
        startActivity(Intent.createChooser(intentt, "Share Via"))
    }

    private fun loadViews() {
        score = findViewById(R.id.score_Game)
        record = findViewById(R.id.record_game)
        btnUndo = findViewById(R.id.undo_game)
        btnNewGame = findViewById(R.id.restart_game)

        btnUndo2 = findViewById(R.id.undo_game2)



        btnUndo.setOnClickListener{
            if(!LocalData.getGameUndoTypeBig()) {
                presenter.undo()
                LocalData.setGameUndoTypeBig(true)
                btnUndo.visibility = View.INVISIBLE
                btnUndo2.visibility = View.VISIBLE
            }
        }

//        findViewById<AppCompatButton>(R.id.home).setOnClickListener{
//            dialog = DialogExit(this , {finish()} , "Exit the game?")
//            dialog.setCancelable(false)
//            dialog.show()
//        }

        findViewById<ImageView>(R.id.restart_game).setOnClickListener{
            presenter.restart()
            btnUndo.visibility = View.INVISIBLE
            btnUndo2.visibility = View.VISIBLE
            LocalData.setGameUndoTypeBig(true)
        }


        val mainContainer = findViewById<LinearLayout>(R.id.mainContainerBig)
        //  Score = findViewById(R.id.realScore)
        for (i in 0 until mainContainer.childCount) {
            val childContainer = mainContainer.getChildAt(i) as LinearLayout
            for (j in 0 until childContainer.childCount) {
                buttons.add(childContainer.getChildAt(j) as TextView)
            }
        }


        // Score = findViewById(R.id.realScore)
        //  Record = findViewById(R.id.realRecord)
        val movementDetector = MovementDetector(this)
        movementDetector.setOnMovementListener {
            btnUndo.visibility = View.VISIBLE
            btnUndo2.visibility = View.INVISIBLE
            LocalData.setGameUndoTypeBig(false)
            when (it) {
                Movement.LEFT -> presenter.moveLeft()
                Movement.RIGHT -> presenter.moveRight()
                Movement.DOWN -> presenter.moveDown()
                Movement.UP -> presenter.moveUp()
            }
        }

        mainContainer.setOnTouchListener(movementDetector)
    }

    override fun changeState(matrix: Array<Array<Int>>) {
        if (!LocalData.getGameUndoTypeBig()){
            btnUndo.visibility = View.VISIBLE
            btnUndo2.visibility = View.INVISIBLE
        }
        else{
            btnUndo.visibility = View.INVISIBLE
            btnUndo2.visibility = View.VISIBLE
        }
        score.text = presenter.getScore().toString()
        record.text = presenter.getRecord().toString()

        for (i in matrix.indices) {
            for (j in 0 until matrix[i].size) {
                val button = buttons[5 * i + j]
                val value = matrix[i][j]

                if (value == 1024 || value == 2048 || value == 4096 || value == 8192||value >= 128||value >= 256) {
                    button.textSize = 24f
                }else{
                    button.textSize=33f
                }

                when (value) {
                    2, 4 -> {
                        button.setTextColor(Color.parseColor("#766962"))
                    }
                    8 -> {
                        button.setTextColor(Color.parseColor("#F9F4F3"))
                    }
                    else -> {
                        button.setTextColor(Color.parseColor("#F9F4F3"))
                    }
                }
                //     Score.text = presenter.getScore().toString()
                if (value == 0) button.text = ""
                else button.text = matrix[i][j].toString()
                button.setBackgroundResource(
                    when (value) {
                        0 -> R.drawable.bg_item0
                        2 -> R.drawable.color_2
                        4 -> R.drawable.color_4
                        8 -> R.drawable.color_8
                        16 -> R.drawable.color_16
                        32 -> R.drawable.color_32
                        64 -> R.drawable.color_64
                        128 -> R.drawable.color_128
                        256 -> R.drawable.color_256
                        512 -> R.drawable.color_512
                        1024 -> R.drawable.color_1024
                        2048 -> R.drawable.color_2048
                        else -> R.drawable.else_color

                    }
                )
            }
        }
    }

    override fun onPause() {
        if (finish) {
            LocalData.setGameUndoType(true)
            presenter.restart()
        }else{
            presenter.saveData()

        }
        super.onPause()
    }

    override fun onDestroy() {
        if (finish){
            LocalData.setGameUndoTypeBig(true)
            presenter.restart()
        }
        super.onDestroy()
    }
    override fun showFinishDialog() {
//

        finish=true
        val dialog = Dialog(this)
        val view: View = LayoutInflater.from(this)
            .inflate(R.layout.finish_dialog, findViewById(R.id.container), false)
        dialog.setContentView(view)
        view.moves.text=resources.getString(R.string.tv_win_dialog,presenter.getScore().toString())
        view.findViewById<View>(R.id.button_exit_no).setOnClickListener { view1: View? ->
            finish()
        }
        view.findViewById<View>(R.id.button_exit_yes)
            .setOnClickListener { view2: View? ->
                dialog.cancel()
                dialog.dismiss()
                LocalData.setGameUndoTypeBig(true)
                presenter.startGame()
                presenter.restart()
            }
        dialog.show()

        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}
package com.pramodlakmal.game2048.repository

import com.pramodlakmal.game2048.Contract.GameContract
import com.pramodlakmal.game2048.Contract.GameContractSmall
import com.pramodlakmal.game2048.shared.LocalData


class GameRepositorySmall : GameContractSmall.Repository {
    private var maxValue = LocalData.getTargetSmall()
    private var matrix = LocalData.getMatrixSmall()
    private var score = LocalData.getScoreSmall()
    private var record = LocalData.getRecordSmall()
    private var minValue = 2
    private var oldMatrix = arrayOf(
        arrayOf(0, 0,  0),
        arrayOf(0, 0,  0),
        arrayOf(0, 0,  0),

    )
    private var undoMatrix = LocalData.getMatrixUndoSmall()
    private var oldScore = LocalData.getScoreUndoSmall()
    private var oldRecord = LocalData.getRecordUndoSmall()

    init {
        addFirst()
    }

    private fun addFirst() {
        for (i in matrix.indices) {
            for (j in 0 until matrix[i].size) {
                if (matrix[i][j] != 0) return
            }
        }
        addElement()
        addElement()
    }

    private fun addElement() {
        val elements = ArrayList<Pair<Int, Int>>()
        for (i in matrix.indices) {
            for (j in 0 until matrix[i].size) {
                if (matrix[i][j] == 0) elements.add(Pair(i, j))
            }
        }
        elements.shuffle()
        if (elements.size == 0) return

        val element = elements[0]
        matrix[element.first][element.second] = minValue
    }

    override fun getMatrix(): Array<Array<Int>> {
        return matrix
    }


    override fun moveLeft() {
        var isSwipe = false
        oldScore = score
        oldRecord = record
        for (i in matrix.indices) {
            for (j in 0 until matrix[i].size) {
                oldMatrix[i][j] = matrix[i][j]
            }
        }
        for (i in matrix.indices) {
            var state = true
            val amounts: ArrayList<Int> = arrayListOf()
            for (j in 0 until matrix[i].size) {
                if (matrix[i][j] == 0) continue

                if (amounts.isEmpty()) {
                    amounts.add(matrix[i][j])
                } else {
                    if (amounts.last() == matrix[i][j] && state) {
                        state = false
                        isSwipe = true
                        amounts[amounts.lastIndex] = matrix[i][j] * 2
                        score += amounts[amounts.lastIndex]
                        if (amounts[amounts.lastIndex] > maxValue) maxValue =
                            amounts[amounts.lastIndex]
                    } else {
                        state = true
                        amounts.add(matrix[i][j])
                    }
                }
                matrix[i][j] = 0
            }
            for (j in 0 until amounts.size) {
                matrix[i][j] = amounts[j]
            }
        }
        if (isSwipe) {
            for (i in matrix.indices) {
                for (j in 0 until matrix[i].size) {
                    undoMatrix[i][j] = oldMatrix[i][j]
                }
            }
            if (horizontalChanged()) {
                addElement()
            }
        }

    }

    override fun moveRight() {
        var isSwipe = false
        oldScore = score
        oldRecord = record
        for (i in matrix.indices) {
            for (j in 0 until matrix[i].size) {
                oldMatrix[i][j] = matrix[i][j]
            }
        }
        for (i in matrix.indices) {
            var state = true
            val amounts: ArrayList<Int> = arrayListOf()
            for (j in 0 until matrix[i].size) {
                if (matrix[2 - i][2 - j] == 0) continue

                if (amounts.isEmpty()) {
                    amounts.add(matrix[2 - i][2 - j])
                } else {
                    if (amounts.last() == matrix[2 - i][2 - j] && state) {
                        state = false
                        isSwipe = true
                        amounts[amounts.lastIndex] = matrix[2 - i][2 - j] * 2
                        score += amounts[amounts.lastIndex]
                        if (amounts[amounts.lastIndex] > maxValue) maxValue =
                            amounts[amounts.lastIndex]
                    } else {
                        state = true
                        amounts.add(matrix[2 - i][2 - j])
                    }
                }
                matrix[2 - i][2 - j] = 0
            }
            for (j in 0 until amounts.size) {
                matrix[2 - i][2 - j] = amounts[j]
            }
        }
        if (isSwipe) {
            for (i in matrix.indices) {
                for (j in 0 until matrix[i].size) {
                    undoMatrix[i][j] = oldMatrix[i][j]
                }
            }
        }
if (horizontalChanged()) {
    addElement()
}
    }

    override fun moveUp() {
        var isSwipe = false
        oldScore = score
        oldRecord = record
        for (i in matrix.indices) {
            for (j in 0 until matrix[i].size) {
                oldMatrix[i][j] = matrix[i][j]
            }
        }
        for (i in matrix.indices) {
            var state = true
            val amounts: ArrayList<Int> = arrayListOf()
            for (j in 0 until matrix[i].size) {
                if (matrix[j][i] == 0) continue

                if (amounts.isEmpty()) {
                    amounts.add(matrix[j][i])
                } else {
                    if (amounts.last() == matrix[j][i] && state) {
                        state = false
                        isSwipe = true
                        amounts[amounts.lastIndex] = matrix[j][i] * 2
                        score += amounts[amounts.lastIndex]
                        if (amounts[amounts.lastIndex] > maxValue) maxValue =
                            amounts[amounts.lastIndex]
                    } else {
                        state = true
                        amounts.add(matrix[j][i])
                    }
                }
                matrix[j][i] = 0
            }
            for (j in 0 until amounts.size) {
                matrix[j][i] = amounts[j]
            }
        }
        if (isSwipe) {
            for (i in matrix.indices) {
                for (j in 0 until matrix[i].size) {
                    undoMatrix[i][j] = oldMatrix[i][j]
                }
            }

        }
        if (horizontalChanged()){
            addElement()

        }
    }

    override fun moveDown() {
        var isSwipe = false
        oldScore = score
        oldRecord = record
        for (i in matrix.indices) {
            for (j in 0 until matrix[i].size) {
                oldMatrix[i][j] = matrix[i][j]
            }
        }
        for (i in matrix.indices) {
            var state = true
            val amounts: ArrayList<Int> = arrayListOf()
            for (j in 0 until matrix[i].size) {
                if (matrix[2 - j][i] == 0) continue

                if (amounts.isEmpty()) {
                    amounts.add(matrix[2 - j][i])
                } else {
                    if (amounts.last() == matrix[2 - j][i] && state) {
                        state = false
                        isSwipe = true
                        amounts[amounts.lastIndex] = matrix[2 - j][i] * 2
                        score += amounts[amounts.lastIndex]
                        if (amounts[amounts.lastIndex] > maxValue) maxValue =
                            amounts[amounts.lastIndex]
                    } else {
                        state = true
                        amounts.add(matrix[2 - j][i])
                    }
                }
                matrix[2 - j][i] = 0
            }
            for (j in 0 until amounts.size) {
                matrix[2 - j][i] = amounts[j]
            }
        }
        if (isSwipe) {
            for (i in matrix.indices) {
                for (j in 0 until matrix[i].size) {
                    undoMatrix[i][j] = oldMatrix[i][j]
                }
            }
        }
        if (horizontalChanged()){
            addElement()
        }
    }

    override fun getRecord(): Int {
        if (record < score)
            record = score
        return record
    }

    override fun getTarget(): Int {
        return maxValue * 2
    }

    override fun getScore(): Int {
        return score
    }
    private fun horizontalChanged(): Boolean {
        for (i in 0 until matrix.size) {
            for (j in 0 until matrix[i].size) {
                if (matrix[i][j] != oldMatrix[i][j]) {
                    return true
                }
            }
        }
        return false
    }
    override fun saveData() {
        if (record <= score)
            LocalData.setRecordSmall(record)
        LocalData.setScoreSmall(score)
        LocalData.setMatrixUndoSmall(undoMatrix)
        LocalData.setMatrixSmall(matrix)
        LocalData.setTargetSmall(maxValue)
        LocalData.setScoreUndoSmall(oldScore)
        LocalData.setRecordUndoSmall(oldRecord)
    }



    override fun undo() {
        for (i in matrix.indices) {
            for (j in 0 until matrix[i].size) {
                matrix[i][j] = undoMatrix[i][j]
            }
        }
        score = oldScore
        record = oldRecord
    }

    override fun restart() {
        matrix = arrayOf(
            arrayOf(0, 0, 0,),
            arrayOf(0, 0, 0,),
            arrayOf(0, 0, 0,),

        )
        score = 0
        maxValue = 2
        addElement()
        addElement()
    }


}


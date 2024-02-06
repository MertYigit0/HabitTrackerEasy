package com.mertyigit0.habittrackereasy

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class GraphView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val cellSize = 50
    private val gapSize = 2 // 1 dp boşluk
    private val numCols = 52
    private val numRows = 7
    private val data = Array(numRows) { BooleanArray(numCols) }


    private var startColumn = 0 // Başlangıç sütunu

    init {
        // Örnek veri oluştur
        for (i in 0 until numRows) {
            for (j in 0 until numCols) {
                data[i][j] = false
            }
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = numCols * cellSize + (numCols - 1) * gapSize.dpToPx()
        val height = numRows * cellSize + (numRows - 1) * gapSize.dpToPx()
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val cellWidth = cellSize.toFloat()
        val cellHeight = cellSize.toFloat()

        val paint = Paint().apply {
            style = Paint.Style.FILL
        }

        // Her hücreyi çiz
        for (i in 0 until numRows) {
            for (j in startColumn until minOf(startColumn + 52, numCols)) { // 26 sütunun aynı anda görünmesi için
                val left = (j * (cellWidth + gapSize.dpToPx())).toFloat()
                val top = (i * (cellHeight + gapSize.dpToPx())).toFloat()
                val right = left + cellWidth
                val bottom = top + cellHeight

                val radius = 8f // Köşe yarıçapı

                paint.color = getColorForValue(data[i][j])
                canvas.drawRoundRect(left, top, right, bottom, radius, radius, paint)
            }
        }
    }

    private fun getColorForValue(value: Boolean): Int {
        return if (value) {
            Color.parseColor("#4cdd7a") // true ise açık yeşil
        } else {
            Color.parseColor("#27674a") // false ise koyu yeşil
        }
    }
    fun getNumCols(): Int {
        return numCols
    }
    fun getNumRows(): Int {
        return numRows
    }
    fun getData(): Array<BooleanArray> {
        return data
    }


    // dp değerini px'e dönüştürmek için yardımcı bir fonksiyon
    private fun Int.dpToPx(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }
}

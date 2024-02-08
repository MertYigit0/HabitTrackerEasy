package com.mertyigit0.habittrackereasy

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar

class GraphView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val cellSize = 50
    private val gapSize = 2 // 1 dp boşluk
    private val numCols = 53
    private val numRows = 7
    private val data = Array(numRows) { BooleanArray(numCols) }

    val startDate = Calendar.getInstance()

    init {
        // Örnek veri oluştur
        for (i in 0 until numRows) {
            for (j in 0 until numCols) {
                data[i][j] = false
            }
        }

        // Başlangıç tarihini 2024 yılının ilk pazartesi günü olarak ayarla
        startDate.set(2024, Calendar.JANUARY, 1)
        while (startDate.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            startDate.add(Calendar.DAY_OF_YEAR, 1)


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
        for (i in 0 until numCols) {
            for (j in 0 until numRows) {
                val left = (i * (cellWidth + gapSize.dpToPx())).toFloat()
                val top = (j * (cellHeight + gapSize.dpToPx())).toFloat()
                val right = left + cellWidth
                val bottom = top + cellHeight

                val radius = 8f // Köşe yarıçapı

                paint.color = getColorForValue(data[j][i])

                canvas.drawRoundRect(left, top, right, bottom, radius, radius, paint)

                // Hücrenin içine günü yaz
                val date = getDateForPosition(i, j)
                val dateFormat = SimpleDateFormat("dd", Locale.getDefault())
                val dateString = dateFormat.format(date)
                val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = Color.WHITE
                    textAlign = Paint.Align.CENTER
                    textSize = 32f
                }
                val textX = left + cellWidth / 2
                val textY = top + cellHeight / 2 - (textPaint.descent() + textPaint.ascent()) / 2
                canvas.drawText(dateString, textX, textY, textPaint)

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

    private fun getDateForPosition(col: Int, row: Int): Date {
        val position = col * numRows + row
        val date = startDate.clone() as Calendar
        date.add(Calendar.DAY_OF_YEAR, position)
        return date.time
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

    fun getStartDate(): Any {
        return startDate
    }

    fun getStartDateMillis(): Long {
        return startDate.timeInMillis
    }

}

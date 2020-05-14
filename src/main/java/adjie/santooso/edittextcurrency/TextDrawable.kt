package adjie.santooso.edittextcurrency

import android.graphics.*
import android.graphics.drawable.Drawable

class TextDrawable(private val text: String) : Drawable() {
    private val paint: Paint = Paint()
        .apply {
            color = Color.WHITE
            textSize = 22f
            isAntiAlias = true
            isFakeBoldText = true
            setShadowLayer(6f, 0f, 0f, Color.BLACK)
            style = Paint.Style.FILL
            textAlign = Paint.Align.LEFT
        }

    override fun draw(canvas: Canvas) {
        canvas.drawText(text, 0f, 0f, paint)
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun setColorFilter(cf: ColorFilter?) {
        paint.colorFilter = cf
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }
}
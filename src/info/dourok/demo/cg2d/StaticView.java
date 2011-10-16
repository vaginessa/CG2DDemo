/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.dourok.demo.cg2d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.View;

/**
 * 
 * @author drcher
 */
public class StaticView extends CustomPaintView.InnerView{
    private ShapeDrawable mDrawable;
    private Paint bgPaint;
    private Paint bfPaint;
    private Bitmap buffer;
    private Canvas bfCanvas;
public StaticView(Context context,CustomPaintView view) {
        super(context, view);
        init();
    }

    public void init(){
        bfPaint = new Paint();
        
        bgPaint = new Paint();
        bgPaint.setColor(0xFFFFFFFF);
        bgPaint.setStyle(Style.FILL);
        
        int x = 10;
        int y = 10;
        int width = 300;
        int height = 50;
        mDrawable = new ShapeDrawable(new OvalShape());
        mDrawable.setBounds(x, y, width, height);        
        mDrawable.getPaint().setColor(0xff74AC23);
        mDrawable.getPaint().setStyle(Style.STROKE);
    }
//    @Override
//    /**
//     * the method draw in view iwill call onDraw 
//     */
//    public void draw(Canvas canvas) {
////        
////        System.out.println(getWidth()+"  "+getHeight());
////        ArcShape arcs = new ArcShape(30f, 60f);
////        arcs.resize(200, 200);
////        ShapeDrawable sd = new ShapeDrawable(arcs);
////        sd.setBounds(0, 0, 200, 200);
////        sd.setColorFilter(0xffff, Mode.DST);
////        sd.draw(canvas);
////        
////        p1.setColor(0xff00ff);
////        p1.setStrokeWidth(10f);
////        p1.setStyle(Paint.Style.FILL_AND_STROKE);
////        
////        canvas.drawText("Bitch", 10f, 10f, p1);
////        super.draw(canvas);
//        
//        
//        mDrawable.draw(canvas);
//        canvas.drawColor(0xff00ff);

//    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(changed){
            buffer = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            bfCanvas = new Canvas(buffer);
        }
        drawBuffer();
    }
    private void drawBuffer(){
        bfCanvas.drawColor(bgPaint.getColor());
        int i = bfCanvas.save();
        bfCanvas.clipRect(0, 0, bfCanvas.getWidth(), 20);
        bfCanvas.drawColor(0xffff0000);
        mDrawable.draw(bfCanvas);
        bfCanvas.drawPoint(bfCanvas.getWidth()/2+i*10, 10f, mPaint);
        bfCanvas.restoreToCount(i);
        bfCanvas.drawRect(0, 0, 250, 250, mPaint);        
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        
        canvas.drawBitmap(buffer, 0, 0,bfPaint);
        
    }

    @Override
    public void repaint() {
        drawBuffer();
        invalidate();
    }
}

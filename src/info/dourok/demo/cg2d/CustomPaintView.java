/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package info.dourok.demo.cg2d;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import com.example.android.apis.graphics.ColorPickerDialog;

/**
 *
 * @author drcher
 */
public class CustomPaintView extends LinearLayout implements OnClickListener,AdapterView.OnItemSelectedListener,TextWatcher,ColorPickerDialog.OnColorChangedListener{
    
    public static abstract class InnerView extends View{
        protected CustomPaintView paintView;
        protected Paint mPaint;
        public InnerView(Context context,CustomPaintView paintView) {
            super(context);
            this.paintView =paintView;
            mPaint =paintView.getPaint();
        }
        
        public abstract void repaint();
    }
    
    private ColorPickerDialog colorPickerDialog;
    
    private Spinner stlSpinner;
    private Spinner capSpinner;
    private Spinner joinSpinner;
    private EditText widthEditText;
    private Button colorButton;
    private Button drawButton;
    protected  Paint paint;
    protected  InnerView iView;
    protected ViewGroup banner;
    public CustomPaintView(Context context) {
        super(context);
        paint = new Paint();
        
        colorPickerDialog = new ColorPickerDialog(context, this, paint.getColor());
        
        setOrientation(VERTICAL);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        banner=(ViewGroup) inflater.inflate(R.layout.custom_painter, null);
        addView(banner, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        
        stlSpinner =(Spinner) banner.findViewById(R.id.paint_style);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
            context, R.array.paint_style_value, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);        
        stlSpinner.setAdapter(adapter);
        stlSpinner.setOnItemSelectedListener(this);
        stlSpinner.setSelection(paint.getStyle().ordinal());
        
        capSpinner =(Spinner) banner.findViewById(R.id.stroke_cap);
        adapter = ArrayAdapter.createFromResource(
            context, R.array.stroke_cap_value, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);        
        capSpinner.setAdapter(adapter);
        capSpinner.setOnItemSelectedListener(this);
        capSpinner.setSelection(paint.getStrokeCap().ordinal());
        
        joinSpinner = (Spinner) banner.findViewById(R.id.stroke_jion);
        adapter = ArrayAdapter.createFromResource(
            context, R.array.stroke_jion_value, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        joinSpinner.setAdapter(adapter);
        joinSpinner.setOnItemSelectedListener(this);
        joinSpinner.setSelection(paint.getStrokeJoin().ordinal());
        
        widthEditText = (EditText) banner.findViewById(R.id.stroke_width);
        widthEditText.setText(Float.toString(paint.getStrokeWidth()));
        widthEditText.addTextChangedListener(this);        
        
        colorButton = (Button) banner.findViewById(R.id.paint_color);
        colorButton.setOnClickListener(this);
        colorButtonColor(paint.getColor());

        drawButton = (Button) banner.findViewById(R.id.draw);
        drawButton.setOnClickListener(this);
    }

    private void colorButtonColor(int c){
        colorButton.setBackgroundColor(c);
        int fc = 0;
        if((c&0xff+c&0xff00>>8+c&0xff0000>>16)>0x188){
            fc = 0xff000000;
        }else{
            fc = 0xffffffff;
        }
        colorButton.setTextColor(fc);
    }
    
    public Paint getPaint() {
        return paint;
    }

    public void setiView(InnerView view) {
        if(iView!=null){
            removeView(iView);
        }
        iView = view;
        addView(iView);
    }
    
    
    
    public CustomPaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void onClick(View v) {
        if(v == drawButton){
            if(iView!=null){
                iView.repaint();
            }
        }else if (v == colorButton){
            colorPickerDialog.show();
        }
    }
    
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent==stlSpinner){
            switch(position){
                case 0:
                    paint.setStyle(Paint.Style.FILL);
                    break;
                case 1:
                    paint.setStyle(Paint.Style.STROKE);
                    break;
                case 2:
                    paint.setStyle(Paint.Style.FILL_AND_STROKE);
            }
        }else if(parent==capSpinner){
            switch(position){
                case 0:
                    paint.setStrokeCap(Paint.Cap.BUTT);
                    break;
                case 1:
                    paint.setStrokeCap(Paint.Cap.ROUND);
                    break;
                case 2:
                    paint.setStrokeCap(Paint.Cap.SQUARE);
            }
        }else if(parent==joinSpinner){
            switch(position){
                case 0:
                    paint.setStrokeJoin(Paint.Join.MITER);
                    break;
                case 1:
                    paint.setStrokeJoin(Paint.Join.ROUND);
                    break;
                case 2:
                    paint.setStrokeJoin(Paint.Join.BEVEL);
            }
        }
    }
    

    public void onNothingSelected(AdapterView<?> parent) {}

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    public void afterTextChanged(Editable s) {
        float f =0f;
        try{
        f = Float.parseFloat(s.toString());
        }catch(NumberFormatException ex){
            f = 0f;
        }
        paint.setStrokeWidth(f);
    }

    public void colorChanged(int color) {
        paint.setColor(color);
        colorButtonColor(color);        
    }
}

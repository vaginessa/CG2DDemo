/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package info.dourok.demo.cg2d;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;

/**
 *
 * @author drcher
 */
public class CustomPaintActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        CustomPaintView customPaintView =new CustomPaintView(this);
        setContentView(customPaintView);
    }
    
}

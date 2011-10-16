package info.dourok.demo.cg2d;
import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

public class MainActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        CustomPaintView customPaintView =new CustomPaintView(this);
        StaticView view =new StaticView(this, customPaintView);
        customPaintView.setiView(view);
        setContentView(customPaintView);
        
    }
}

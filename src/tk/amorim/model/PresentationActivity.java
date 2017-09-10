package tk.amorim.model;

/**
 * Created by lucas on 01/09/2017.
 */
public class PresentationActivity extends Activity {
    @Override
    public int getType() {
        return 2;
    }

    @Override
    public String getTypeDesc() {
        return "Apresentação";
    }
}

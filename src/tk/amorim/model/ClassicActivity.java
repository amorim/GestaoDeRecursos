package tk.amorim.model;

/**
 * Created by lucas on 01/09/2017.
 */
public class ClassicActivity extends Activity {

    @Override
    public int getType() {
        return 1;
    }

    @Override
    public String getTypeDesc() {
        return "Atividade Clássica";
    }
}

package tk.amorim.model;

/**
 * Created by lucas on 01/09/2017.
 */
public class LabActivity extends Activity {
    @Override
    public int getType() {
        return 3;
    }

    @Override
    public String getTypeDesc() {
        return "Atividade de Laboratório";
    }
}

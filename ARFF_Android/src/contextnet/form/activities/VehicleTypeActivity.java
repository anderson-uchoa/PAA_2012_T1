package contextnet.form.activities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import contextnet.form.App;
import contextnet.form.FixedEnumerations;
import contextnet.form.FormReader;
import contextnet.form.R;

public class VehicleTypeActivity extends Activity {
	
	private App ap;
	
	//variaveis locais para lista
	private ListView listView;
	private TextView tvTitle;
    private String[] vehicleList;
	private TextView tvBottom;
	
    //variaveis que serão repassadas
    private String _cnetfolderpath;
    private String _inspectionplacestring;
    private List<String> _nextList;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ap = (App) getApplication();
        
        _inspectionplacestring = ((FixedEnumerations.InspectionPlace) getIntent().getExtras().get("inspectionplace")).getDescription();
        _cnetfolderpath = getIntent().getExtras().get("cnetfolderpath").toString();
        final ColorDrawable divcolor = new ColorDrawable(Color.LTGRAY);
		
        tvTitle = (TextView) findViewById(R.id.titlebar);
        if(tvTitle != null) 
        	tvTitle.setText(getResources().getText(R.string.app_name) + " - Tipo do Veículo (" + _inspectionplacestring + "):");
        
        populateList();
        listView = (ListView) findViewById(R.id.listview);
		if(listView==null)
			return;
		listView.setDivider(divcolor);
		listView.setDividerHeight(2);
		listView.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, vehicleList));
		listView.setTextFilterEnabled(true);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view,
        	int position, long id) {
        	
        		File aux;
        		String vehicletype = FixedEnumerations.VehicleTypes.CAMINHAO.getTypeFromId(position).getTag();
        		
        		aux = new File(_cnetfolderpath + "/form_" + vehicletype + ".xml");
		    	if(!aux.exists())
		    	{
		    		Toast.makeText(getApplicationContext(), "Arquivo de formulario não encontrado!", Toast.LENGTH_SHORT).show();
		    		return;
		    	}
		    	
		    	InputStream fos = null;
		    	FormReader fR;
		    	try {
					fos = new FileInputStream(aux);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				};
				ap.setFileInputStream(fos);
				fR = ap.getReader();
				fR.labelParser(0);
				_nextList = new ArrayList<String>();
				_nextList = fR.labels;
				if(_nextList.size() > 0)
				{
					Intent i = new Intent(view.getContext(), FormActivity.class);
					i.putExtra("inspectionplacestring", _inspectionplacestring);
					i.putExtra("vehicletypestring", FixedEnumerations.VehicleTypes.CAMINHAO.getTypeFromId(position).getDescription());
        			i.putExtra("cnetfolderpath", _cnetfolderpath);
					i.putStringArrayListExtra("nextlist", (ArrayList<String>) _nextList);
					startActivity(i);
				}
				
        	}
        });
        
        tvBottom = (TextView) findViewById(R.id.bottombar);
		tvBottom.setText(ap.connectionString);
        tvBottom.setCompoundDrawablesWithIntrinsicBounds(ap.connectionDrawable, ap.topDrawable, 0, 0);
	}

	/**
	 * Private Methods
	 */
	
	private void populateList() {
		vehicleList = new String[FixedEnumerations.VehicleTypes.MAX_NO];
		for(int i = 0; i < vehicleList.length; i++)
			vehicleList[i] = FixedEnumerations.VehicleTypes.CAMINHAO.getTypeFromId(i).getDescription();
	}
}

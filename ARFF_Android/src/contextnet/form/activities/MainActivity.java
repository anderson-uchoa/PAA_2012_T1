package contextnet.form.activities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import contextnet.form.App;
import contextnet.form.FixedEnumerations;
import contextnet.form.R;

public class MainActivity extends Activity {
	
	private App ap;
	private Handler mHandler;
	private AlertDialog aDlgUserInput;
	private AlertDialog aDlgIpInput;
	
	//interface
	private TextView tvBottom;
	private List<String> placeList;
	private ArrayAdapter<CharSequence> spinnerAdapter;
	private Spinner spnPlace;
	private TextView tvTitle;
	private Button btnAdv;
	
	//variaveis que serão repassadas
	private FixedEnumerations.InspectionPlace _inspectionplace;
	private String _cnetfolderpath;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mHandler = new Handler();
        mHandler.post(mUpdate);
        
        ap = (App) getApplication();
        
		aDlgIpInput = ap.setDialogInputBuilder(this, "Ip para conexão (inclua os pontos): ", "ip").create();
        aDlgIpInput.show();
        
        aDlgUserInput = ap.setDialogInputBuilder(this, "Nome do funcionário corrente: ", "user").create();
        aDlgUserInput.show();
        
    	
        tvTitle = (TextView) findViewById(R.id.titlebar);
        if(tvTitle != null) tvTitle.setText(getResources().getText(R.string.app_name) + " - Local da Inspeção");

    	spnPlace = (Spinner) findViewById(R.id.spinner_place);
        if(spnPlace != null)
        {
        	spinnerAdapter = new ArrayAdapter<CharSequence> (this, android.R.layout.simple_spinner_item );
        	spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        	populateList();
            for(int i = 0; i < placeList.size(); i++) {
            	spinnerAdapter.add(placeList.get(i));
            }
            spnPlace.setAdapter(spinnerAdapter);
            
            spnPlace.setOnItemSelectedListener(new OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) 
                {
                	setCurrentPlace(arg2);
                }

    			private void setCurrentPlace(int arg2) {
    				if(arg2 == FixedEnumerations.InspectionPlace.ESTRADA.getId())
    					_inspectionplace = FixedEnumerations.InspectionPlace.ESTRADA;
    				else
    					_inspectionplace = FixedEnumerations.InspectionPlace.GARAGEM;
				}

				public void onNothingSelected(AdapterView<?> arg0) {
    				//nada
    			}
            });

            btnAdv = (Button) findViewById(R.id.btn_adv);
            if(btnAdv != null)
            {
            	btnAdv.setOnClickListener( new OnClickListener()
                { 	
            		/** 
                     * Called when a specific button in this view is pressed
                     * 
                     * @param view
                     * @return void
                     * 
                     * */
            		public void onClick(View view)
         	        {
            			// Checa a existencia da pasta cnet-exportedforms}
            	        if(SDCardState(Environment.getExternalStorageState()) && safeDirectory(Environment.getExternalStorageDirectory().getAbsolutePath(), "/cnet-exportedfiles"))
            	        {
	            			//Avançar para a proxima tela repassando o currentPlace para o uso da próxima atividade
	            			Intent i = new Intent(view.getContext(), VehicleTypeActivity.class);
	            			i.putExtra("inspectionplace", _inspectionplace);
	            			i.putExtra("cnetfolderpath", _cnetfolderpath);
	                		startActivity(i);
            	        }
            	        else
            	        	Toast.makeText(getBaseContext(), "SD não está pronto ou pasta base \"cnet-exportedfiles\" não foi encontrada!", Toast.LENGTH_LONG).show();
         	        }
                });
            }
            
            tvBottom = (TextView) findViewById(R.id.bottombar);
            tvBottom.invalidate();
            tvBottom.setText(ap.connectionString);
            tvBottom.setCompoundDrawablesWithIntrinsicBounds(ap.connectionDrawable, ap.topDrawable, 0, 0);
        }
    }
  
	/**
     * Private Methods
     */

    /**
	 * Funcao que popula a lista de lugares disponíveis
     * 
	 */
    private void populateList() {
    	placeList = new ArrayList<String>();
    	placeList.add(FixedEnumerations.InspectionPlace.ESTRADA.getDescription());
    	placeList.add(FixedEnumerations.InspectionPlace.GARAGEM.getDescription());
	}
    
    /**
	 * Funcao que checa o diretorio correto de SDcard externo no aparelho, se houver
	 
     * @return boolean - se ha um diretorio compativel (true) ou nao (false)
     * 
	 */
	private boolean safeDirectory(String root, String sufix)
	{		
		File fPath1 = new File(root + "/external_sd" + sufix);
		File fPath2 = new File(root + "/sd" + sufix);
		File fPath3 = new File(root + sufix);
		
		//tratar caso que users.xml nao existe para cada if (safeFileFlag);
		if(fPath1.exists() && fPath1.isDirectory())
		{
			_cnetfolderpath = root + "/external_sd" + sufix;
			return true;
		}
		else if(fPath2.exists() && fPath2.isDirectory())
		{
			_cnetfolderpath = root + "/sd" + sufix;
			return true;
		}
		else if(fPath3.exists() && fPath3.isDirectory())
		{
			_cnetfolderpath = root + sufix;
			return true;
		}
		else
			return false;			
	}
	
	/**
     * Funcao que checa se ha um SDCard no mobile e se ele e writeble.
     * 
     * @return boolean - retorna se o sdcard e compativel com as condicoes de leitura/escrita.
     * 
     */
    private boolean SDCardState(String state)
    {
    	boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;

        if (Environment.MEDIA_MOUNTED.equals(state)) 
        {
            // We can read and write the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // We can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            // Something else is wrong. It may be one of many other states, but all we need
            //  to know is we can neither read nor write
            mExternalStorageAvailable = false;
            mExternalStorageWriteable = false;
        }
        
        return (mExternalStorageAvailable && mExternalStorageWriteable);
    }
    
    //for updating the view every second
    private Runnable mUpdate = new Runnable() {

		//private int i = 0;

		public void run() {
			//i++;
			tvBottom.setText(ap.connectionString);
	        tvBottom.setCompoundDrawablesWithIntrinsicBounds(ap.connectionDrawable, ap.topDrawable, 0, 0);
	        mHandler.postDelayed(this, 1000);
	        
	        /*if(i > 10)
	        {
	        	ap.setConnected();
	        }*/
    	}
    };
}

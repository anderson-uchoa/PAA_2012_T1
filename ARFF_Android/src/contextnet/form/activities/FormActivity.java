package contextnet.form.activities;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
import contextnet.form.App;
import contextnet.form.FormReader;
import contextnet.form.R;

public class FormActivity extends Activity {
	
	private FormReader fR;
	private Node currNode;
	private App ap;
	
	//variaveis locais para lista
	private TextView tvTitle;
	private ListView listView;
	private TextView tvBottom;
    
	//variaveis que foram/serão repassadas
    private String _cnetfolderpath;
    private String _vehicletypestring;
    private String _inspectionplacestring;
    private List<String> _labelList;
    
    private List<String> _nextList;
    private String _currentItemId;
    private String _labelstring;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ap = (App) getApplication();
        
        _inspectionplacestring = getIntent().getExtras().get("inspectionplacestring").toString();
        _vehicletypestring = getIntent().getExtras().get("vehicletypestring").toString();
        _cnetfolderpath = getIntent().getExtras().get("cnetfolderpath").toString();
        _labelList = getIntent().getStringArrayListExtra("nextlist");
        
        /* Definição da lista */
        final ColorDrawable divcolor = new ColorDrawable(Color.LTGRAY);
		
        tvTitle = (TextView) findViewById(R.id.titlebar);
        if(tvTitle != null) 
        	tvTitle.setText(getResources().getText(R.string.app_name) + " - Formulário (" + _vehicletypestring + "):");
        
        listView = (ListView) findViewById(R.id.listview);
		if(listView==null)
			return;
		listView.setDivider(divcolor);
		listView.setDividerHeight(2);
		listView.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, _labelList));
		listView.setTextFilterEnabled(true);
        /* Fim da definição da lista */
        
        tvBottom = (TextView) findViewById(R.id.bottombar);
		tvBottom.setText(ap.connectionString);
        tvBottom.setCompoundDrawablesWithIntrinsicBounds(ap.connectionDrawable, ap.topDrawable, 0, 0);
	}
    
    @Override
    protected void onResume(){
        super.onResume();
		
        listView.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View view,
		        int position, long id) {

		    	fR = ap.getReader();
		    	boolean labelFlag = constructNextMenu(position);
		    
	    		Intent i = new Intent(view.getContext(), Dinamic.class);
	    		i.putExtra("labelstring", _labelstring);
	    		i.putExtra("vehicletypestring", _vehicletypestring);
	    		i.putStringArrayListExtra("nextlist", (ArrayList<String>) _nextList);
	    		startActivity(i);
		    }
		});
    }
    
    /**
     * Métodos privados
     */
    private boolean constructNextMenu(int pos) 
    {	
    	currNode = fR.getNode(pos);
		
    	//nova tela de opções
    	if(currNode.getNodeName().equals("label"))
    	{
    		_labelstring = fR.getItemName(pos);
    		NodeList aux = currNode.getChildNodes();
    		fR.incrementHierarchy();
    		_nextList = fR.genericLabelParser(aux);
    		return true;
    	}

    	return false;
	}
    
    @Override
    protected void onRestart() {
    	super.onRestart();
    	fR.decrementHierarchy();
    }
}

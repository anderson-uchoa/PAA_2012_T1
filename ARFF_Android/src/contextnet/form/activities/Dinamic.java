package contextnet.form.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import contextnet.form.App;
import contextnet.form.FixedEnumerations;
import contextnet.form.FormReader;
import contextnet.form.R;

public class Dinamic extends Activity {
	
	private App ap;
	private int id = 1;
	private FormReader fR;
	private String currId;
	private String currName;
	private String currOrientation;
	private int currLength;
	private LinearLayout lastLinearLyt;
	private String currCategory;
	
	//interface
	private TextView tvTitle;
	private Button save;
	private Button discard;	
	private TextView tvBottom;
	
	//variaveis passadas para esta activity
	private String _labelstring;
	private List<String> _itemlabellist;
	private String _vehicletypestring;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinamic);

        ap = ((App) getApplication());
        fR = ap.getReader();
        
        _labelstring = getIntent().getExtras().getString("labelstring");
        _itemlabellist = getIntent().getStringArrayListExtra("nextlist");
        _vehicletypestring = getIntent().getExtras().getString("vehicletypestring");
        
        tvTitle = (TextView) findViewById(R.id.titlebar);
        if(tvTitle != null) 
        	tvTitle.setText("Formulário - " + _labelstring);
        
        //interface dinâmica
 		final RelativeLayout rLayout = (RelativeLayout)findViewById(R.id.itemlayout);
        save = (Button)findViewById(R.id.save);
        discard = (Button)findViewById(R.id.discard);
		
		for(int i = 0; i < _itemlabellist.size(); i++)
        {
        	String itemType = fR.getItemType(i);
        	currId = fR.getItemId(i);
        	currName = _itemlabellist.get(i);
        	currOrientation = fR.getItemOrientation(i);
        	currLength = Integer.parseInt(fR.getItemLength(i));
        	id = layoutAddition(itemType, rLayout, id);
        }
		
		/* Listeners */
        // Save button listener
		save.setOnClickListener( new OnClickListener()
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
	        		//TODO: monta e envia mensagem com mudanças feitas no estilo de escrita de log
	        		int index = 1;
	        		String s = "<label name=\"" + _labelstring + "\">";
	        		while (index*3 < id)
	        		{
	        			//tipos possíveis atualmente: TextView, EditText, Spinner
	        			View v = rLayout.findViewById(index*3);
	        			s += "<item id=\"" + fR.getItemId(index - 1) + "\">";
	        			String classname = v.getClass().toString().substring(21);
	        			
	        			if(classname.equals("TextView"))
	        				s += ((TextView) v).getText().toString();
	        			else if(classname.equals("EditText"))
	        				s += ((EditText) v).getText().toString();
	        			else
	        				s += ((Spinner) v).getSelectedItem().toString();     
	        			
	        			s += "</item>";
		        		index++;
	        		}
	        		s += "</label>";
	        		
	        		//TODO: enviar mensagem
	        		ap.sendSDDLMessage(s);
	        		Toast.makeText(getBaseContext(), s, Toast.LENGTH_LONG).show();
	        		finish();
	        	}
	        });
		
		// Discard Button listener
        discard.setOnClickListener( new OnClickListener()
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
        		//exits without saving
        		finish();
        	}
        });
        
        tvBottom = (TextView) findViewById(R.id.bottombar);
		tvBottom.setText(ap.connectionString);
        tvBottom.setCompoundDrawablesWithIntrinsicBounds(ap.connectionDrawable, ap.topDrawable, 0, 0);
    }
    
    private int layoutAddition(String type, RelativeLayout rl, int id)
	{
    	int padding_in_dp = 5; //5dp
        int padding_in_px = (int) (padding_in_dp * (getResources().getDisplayMetrics().density) + 0.5f);
    	
        int standardPadding = 4*padding_in_px; //20dp
		int layoutType = LayoutParams.MATCH_PARENT;
		int fixedSize = 30*padding_in_px;
		boolean horizontal = false;
    	if(currOrientation.equals("horizontal"))
    	{
    		horizontal = true;
    		standardPadding /= 2;
		}
        
        //types: text(1), varchar(2), numeric(3), longvarchar(4), e roller(5)
    	//sempre 3 ids por tipo, começando por id = 1
        if(type.equals("text"))
		{    
        	LinearLayout outerLyt = new LinearLayout(this);
	        outerLyt.setId(id);
	        outerLyt.setPadding(standardPadding, 6*padding_in_px, 3*padding_in_px, 0);
	        outerLyt.setLayoutParams(new LayoutParams(
	        		layoutType, LayoutParams.WRAP_CONTENT));
	        outerLyt.setOrientation(LinearLayout.HORIZONTAL);
	        if(horizontal)
        		lastLinearLyt.addView(outerLyt);
        	else
        	{
		        if((id - 1) > 1)
		        {
		        	RelativeLayout.LayoutParams outerLytLayout = new RelativeLayout.LayoutParams(
		        			RelativeLayout.LayoutParams.MATCH_PARENT, 
		        			RelativeLayout.LayoutParams.WRAP_CONTENT);
		        	outerLytLayout.addRule(RelativeLayout.BELOW, id - 3);
			        rl.addView(outerLyt, outerLytLayout);
		        }
		        else
			        rl.addView(outerLyt);
        	}
	        id++;		
    		
	        TextView tv1 = new TextView(this);
	        tv1.setId(id);
	        tv1.setTextSize(18);
	        tv1.setTextColor(Color.BLACK);
	        tv1.setText(currName);
	        tv1.setLayoutParams(new LayoutParams(
	                LayoutParams.WRAP_CONTENT,
	                LayoutParams.WRAP_CONTENT));
	        outerLyt.addView(tv1);
	        id++;
	       
	        String textInput = "";
	        if(currId.equals("TIPO"))
	        {
	        	textInput = _vehicletypestring;
	        }
	        
        	TextView tv2 = new TextView(this);
	        tv2.setId(3);
	        tv2.setTextSize(18);
	        tv2.setTextColor(Color.BLACK);
	        tv2.setPadding(2*padding_in_px, 0, 0, 0);
	        tv2.setText(textInput);
	        tv2.setLayoutParams(new LayoutParams(
	                LayoutParams.WRAP_CONTENT,
	                LayoutParams.WRAP_CONTENT));
	        outerLyt.addView(tv2);
	        id++;
		}
        else if(type.equals("varchar"))
        {       
        	LinearLayout outerLyt = new LinearLayout(this);
	        outerLyt.setId(id);
	        outerLyt.setPadding(standardPadding, 6*padding_in_px, 3*padding_in_px, 0);
	        outerLyt.setLayoutParams(new LayoutParams(
	        		LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	        outerLyt.setOrientation(LinearLayout.HORIZONTAL);
	        if(horizontal)
        		lastLinearLyt.addView(outerLyt);
        	else
        	{
        		if((id - 1) > 1)
	        	{
		        	RelativeLayout.LayoutParams outerLytLayout = new RelativeLayout.LayoutParams(
		        			RelativeLayout.LayoutParams.WRAP_CONTENT, 
		        			RelativeLayout.LayoutParams.WRAP_CONTENT);
		        	outerLytLayout.addRule(RelativeLayout.BELOW, id - 3);
			        rl.addView(outerLyt, outerLytLayout);
		        }
		        else
			        rl.addView(outerLyt);
        	}
	        id++;
	        
        	TextView tv1 = new TextView(this);
	        tv1.setId(id);
	        tv1.setTextSize(18);
	        tv1.setTextColor(Color.BLACK);
	        tv1.setPadding(0, 0, 3*padding_in_px, 0);
	        tv1.setText(currName);
	        tv1.setLayoutParams(new LayoutParams(
	                LayoutParams.WRAP_CONTENT,
	                LayoutParams.WRAP_CONTENT));
	        outerLyt.addView(tv1);
	        id++;
	           
	        EditText et1 = new EditText(this);
	        et1.setId(id);
	        et1.setSingleLine(true);
	        int width = fixedSize;
	        if(currLength > 0)
	        {
	        	InputFilter[] FilterArray = new InputFilter[1];
		        FilterArray[0] = new InputFilter.LengthFilter(currLength);
		        et1.setFilters(FilterArray);
		        width = (int) (currLength*4.5*padding_in_px);
	        }
	        et1.setInputType(InputType.TYPE_CLASS_TEXT);
	        et1.setLayoutParams(new LayoutParams(
	        		width, LayoutParams.WRAP_CONTENT));
	        outerLyt.addView(et1);
	        id++;
	        
	        lastLinearLyt = outerLyt;
        }
        else if(type.equals("numeric"))
        {   
        	LinearLayout outerLyt = new LinearLayout(this);
	        outerLyt.setId(id);
	        outerLyt.setLayoutParams(new LayoutParams(
	        		LayoutParams.WRAP_CONTENT, 
	        		LayoutParams.WRAP_CONTENT));
	        outerLyt.setOrientation(LinearLayout.HORIZONTAL);
	        if(horizontal)
	        {
		        outerLyt.setPadding(3*padding_in_px, 0, 3*padding_in_px, 0);
        		lastLinearLyt.addView(outerLyt);
	        }
        	else
        	{
		        outerLyt.setPadding(standardPadding, 6*padding_in_px, 3*padding_in_px, 0);
		        if((id - 1) > 1)
		        {
		        	RelativeLayout.LayoutParams outerLytLayout = new RelativeLayout.LayoutParams(
		        			RelativeLayout.LayoutParams.WRAP_CONTENT, 
		        			RelativeLayout.LayoutParams.WRAP_CONTENT);
		        	outerLytLayout.addRule(RelativeLayout.BELOW, id - 3);
		        	rl.addView(outerLyt, outerLytLayout);
		        }
		        else
			        rl.addView(outerLyt);
        	}
	        id++;
	        
        	TextView tv1 = new TextView(this);
	        tv1.setId(id);
	        tv1.setTextSize(18);
	        tv1.setTextColor(Color.BLACK);
	        tv1.setPadding(0, 0, 3*padding_in_px, 0);
	        tv1.setText(currName);
	        tv1.setLayoutParams(new LayoutParams(
	                LayoutParams.WRAP_CONTENT,
	                LayoutParams.WRAP_CONTENT));
	        outerLyt.addView(tv1);
	        id++;
	        
	        EditText et1 = new EditText(this);
	        et1.setId(id);
	        et1.setSingleLine(true);
	        int width = fixedSize;
	        if(currLength > 0)
	        {
	        	InputFilter[] FilterArray = new InputFilter[1];
		        FilterArray[0] = new InputFilter.LengthFilter(currLength);
		        et1.setFilters(FilterArray);
		        width = currLength*3*padding_in_px;;
	        }
	        et1.setInputType(InputType.TYPE_CLASS_NUMBER);
	        et1.setLayoutParams(new LayoutParams(
	        		width, LayoutParams.WRAP_CONTENT));
	        outerLyt.addView(et1);
	        id++;
        }
        else if(type.equals("longvarchar"))
        {     
        	//TODO: sempre below, mudar para poder ser RIGHT_OF também (é necessário?)
    		TextView tv1 = new TextView(this);
	        tv1.setId(id);
	        tv1.setTextSize(18);
	        tv1.setTextColor(Color.BLACK);
	        tv1.setPadding(0, 4*padding_in_px, 0, 4*padding_in_px);
	        tv1.setGravity(Gravity.CENTER);
	        tv1.setText(currName);
	        tv1.setLayoutParams(new LayoutParams(
	                LayoutParams.MATCH_PARENT,
	                LayoutParams.WRAP_CONTENT));
	        if((id - 1)> 1)
	        {
	        	RelativeLayout.LayoutParams tv1Layout = new RelativeLayout.LayoutParams(
	        			RelativeLayout.LayoutParams.MATCH_PARENT, 
	        			RelativeLayout.LayoutParams.WRAP_CONTENT);
		        tv1Layout.addRule(RelativeLayout.BELOW, id - 3);
		        rl.addView(tv1, tv1Layout);
	        }
	        else
		        rl.addView(tv1);
	        id++;
	        
	        LinearLayout outerLyt = new LinearLayout(this);
	        outerLyt.setId(id);
	        outerLyt.setPadding(4*padding_in_px, 0, 4*padding_in_px, 0);
	        outerLyt.setLayoutParams(new LayoutParams(
	                LayoutParams.MATCH_PARENT,
	                60*padding_in_px));
	        RelativeLayout.LayoutParams outerLytLayout = new RelativeLayout.LayoutParams(
	        		RelativeLayout.LayoutParams.MATCH_PARENT, 
	        		 60*padding_in_px);
	        outerLytLayout.addRule(RelativeLayout.BELOW, tv1.getId());
	        id++;
	        
    		EditText et1 = new EditText(this);
    		et1.setId(id);
	        et1.setInputType(InputType.TYPE_CLASS_TEXT);
	        et1.setGravity(Gravity.TOP | Gravity.LEFT);
	        et1.setSingleLine(false);
	        et1.setVerticalScrollBarEnabled(true);
	        et1.setMaxLines(12);
	        et1.setLayoutParams(new LayoutParams(
	                LayoutParams.MATCH_PARENT,
	                LayoutParams.WRAP_CONTENT));
	        outerLyt.addView(et1);
	        rl.addView(outerLyt, outerLytLayout);
	        id++;
        }
        else if(type.equals("roller"))
        {
        	List<String> list = null;
        	Spinner rl1 = new Spinner(this);
	        ArrayAdapter<CharSequence> spinnerAdapter;
	    	        	
        	if(currId.equals("CATEGORIA"))
	        {
        		//lista é de categorias
        		list = populateCategoryList();
        		
        		//listener das categorias
        		rl1.setOnItemSelectedListener(new OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) 
                    {
                    	setCurrentCategory(arg2);
                    }

        			private void setCurrentCategory(int arg2) {
        				if(arg2 == FixedEnumerations.Categories.A.getId())
        					currCategory = FixedEnumerations.Categories.A.getDescription();
        				else if(arg2 == FixedEnumerations.Categories.B.getId())
        					currCategory = FixedEnumerations.Categories.B.getDescription();
        				else if(arg2 == FixedEnumerations.Categories.C.getId())
        					currCategory = FixedEnumerations.Categories.C.getDescription();
        				else if(arg2 == FixedEnumerations.Categories.D.getId())
        					currCategory = FixedEnumerations.Categories.D.getDescription();
        				else
        					currCategory = FixedEnumerations.Categories.E.getDescription();
    				}

    				public void onNothingSelected(AdapterView<?> arg0) {
        				//nada
        			}
                });	
	        }
        	
        	LinearLayout outerLyt = new LinearLayout(this);
	        outerLyt.setId(id);
	        outerLyt.setPadding(standardPadding, 6*padding_in_px, 3*padding_in_px, 0);
	        outerLyt.setLayoutParams(new LayoutParams(
	        		LayoutParams.WRAP_CONTENT, 
	        		LayoutParams.WRAP_CONTENT));
	        outerLyt.setOrientation(LinearLayout.HORIZONTAL);
	        if(horizontal)
        		lastLinearLyt.addView(outerLyt);
        	else
        	{
		        if((id - 1) > 1)
		        {
		        	RelativeLayout.LayoutParams outerLytLayout = new RelativeLayout.LayoutParams(
		        			RelativeLayout.LayoutParams.WRAP_CONTENT, 
		        			RelativeLayout.LayoutParams.WRAP_CONTENT);
		        	outerLytLayout.addRule(RelativeLayout.BELOW, id - 3);
		        	rl.addView(outerLyt, outerLytLayout);
		        }
		        else
			        rl.addView(outerLyt);
        	}
	        id++;
	        
        	TextView tv1 = new TextView(this);
	        tv1.setId(id);
	        tv1.setTextSize(18);
	        tv1.setTextColor(Color.BLACK);
	        tv1.setPadding(0, 0, 3*padding_in_px, 0);
	        tv1.setText(currName);
	        tv1.setLayoutParams(new LayoutParams(
	                LayoutParams.WRAP_CONTENT,
	                LayoutParams.WRAP_CONTENT));
	        outerLyt.addView(tv1);
	        id++;
	        
	        rl1.setId(id);
	        spinnerAdapter = new ArrayAdapter<CharSequence> (this, android.R.layout.simple_spinner_item );
        	spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			for(int i = 0; i < list.size(); i++) {
            	spinnerAdapter.add(list.get(i));
            }
            rl1.setAdapter(spinnerAdapter);
                    
	        outerLyt.addView(rl1);
	        id++;
        }
        
        return id;
	}
    
    /**
	 * Funcao que popula a lista de categorias disponíveis
     * 
	 */
    private List<String> populateCategoryList() {
    	List<String> categoryList = new ArrayList<String>();
    	categoryList.add(FixedEnumerations.Categories.A.getDescription());
    	categoryList.add(FixedEnumerations.Categories.B.getDescription());
    	categoryList.add(FixedEnumerations.Categories.C.getDescription());
    	categoryList.add(FixedEnumerations.Categories.D.getDescription());
    	categoryList.add(FixedEnumerations.Categories.E.getDescription());
    	
    	return categoryList;
	}
}

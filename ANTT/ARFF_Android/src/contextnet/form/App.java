package contextnet.form;

import java.io.InputStream;

import android.app.AlertDialog.Builder;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputFilter;
import android.text.InputType;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import contextnet.sddl.MessageTask;
import contextnet.sddl.NetworkTask;

public class App extends Application {

    private FormReader fR;
    private NetworkTask networkTask;
	private MessageTask messageTask;
    private App myself;
    
    //interface
	public int topDrawable;
	public String connectionString;
	public int connectionDrawable;
	private String connectedString;
	private int connectedDrawable;
	private String connectingString;
	private int connectingDrawable;
	private String disconnectedString;
	private int disconnectedDrawable;
    
    //especifico de retorno de dialogs
    private String userName = "";

	//TODO: passar variaveis mais vistas para cá também: path da pasta principal e nome do usuário
    @Override
    public void onCreate() {
        super.onCreate();
        myself = this;
        fR = new FormReader();
        initializeInterface();
        setDisconnected();
    }

	public Builder setDialogInputBuilder(Context context, String title, final String type)
    {
    	int padding_in_dp = 5; //5dp
        int padding_in_px = (int) (padding_in_dp * (getResources().getDisplayMetrics().density) + 0.5f);
    	
    	Builder bd = new Builder(context);
    	final EditText input = new EditText(context);
		InputFilter[] FilterArray = new InputFilter[1];
    	if(type.equals("user"))
		{
    		FilterArray[0] = new InputFilter.LengthFilter(50);
    		input.setFilters(FilterArray);
		}
		else if(type.equals("ip"))
		{
	        FilterArray[0] = new InputFilter.LengthFilter(15);
	        input.setFilters(FilterArray);
	        //TODO: pontos?
	        //input.setInputType(InputType.TYPE_CLASS_NUMBER);
	        input.setInputType(InputType.TYPE_CLASS_TEXT);
		}
    	input.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
    	
    	LinearLayout outerLyt = new LinearLayout(context);
        outerLyt.setPadding(4*padding_in_px, 0, 4*padding_in_px, 0);
        outerLyt.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        outerLyt.addView(input);
        
		bd.setTitle(title)
        	.setCancelable(false)
        	.setView(outerLyt)
        	.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            /** 
             * Called when a specific button in this dialog is pressed
             * 
             * @param dialog
             * @param id
             * @return void
             * 
             * */
        	public void onClick(DialogInterface dialog, int id) {
				if(type.equals("user"))
        			userName = input.getText().toString();
        		else if(type.equals("ip"))
        		{
        			//TODO: fazer essa mudança ser dinamica para a tela principal
        			setConnecting();
        			networkTask = new NetworkTask(input.getText().toString(), myself);
        			networkTask.execute();
        		}
        		dialog.dismiss();
            }
        });
		
		return bd;
    }

	public String getUserName() {
		return userName;
	}
	
    public void setFileInputStream(InputStream fos)
    {
    	fR.setFileStream(fos);
    }

    public FormReader getReader(){
        return fR;
    }
    
    //private methods
    private void initializeInterface() 
    {
    	topDrawable = R.drawable.separator;
	
    	connectedString = "    Conectado";
		connectedDrawable = R.drawable.connected;
		
		connectingString = "    Conectando";
		connectingDrawable = R.drawable.connecting;
		
		disconnectedString = "    Desconectado";
		disconnectedDrawable = R.drawable.disconnected;
	}
    
    public void setConnected()
    {
    	connectionString = connectedString;
    	connectionDrawable = connectedDrawable;
    }
    
    private void setConnecting()
    {
    	connectionString = connectingString;
    	connectionDrawable = connectingDrawable;
    }
    
    private void setDisconnected()
    {
    	connectionString = disconnectedString;
    	connectionDrawable = disconnectedDrawable;
    }

	public void sendSDDLMessage(String s) {
		messageTask = new MessageTask(networkTask.getConnection(), s, networkTask.getVehicleInfo());
		messageTask.execute();
	}
}
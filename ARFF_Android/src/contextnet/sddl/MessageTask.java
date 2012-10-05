package contextnet.sddl;

import java.util.LinkedList;
import java.util.List;

import lac.cnclib.net.NodeConnection;
import lac.cnclib.sddl.message.ApplicationMessage;
import android.os.AsyncTask;

public class MessageTask extends AsyncTask<Void, byte[], Boolean> {
	
	//connection
	/**
	* The connection between the node and the remote server, uses RUDP.
	*/
	private NodeConnection myConnection;
	private VehicleInformation vehicleLocalInfo;
	
	public MessageTask(NodeConnection con, String s, VehicleInformation info)
	{
		myConnection = con;
		vehicleLocalInfo = info;
		vehicleLocalInfo.setFormString(s);
		//TODO: set TAG para username, local da inspecao, tipo de veiculo etc?
	}
	
	@Override
	protected Boolean doInBackground(Void... arg0) {
		Boolean result = true;
		
		//enviar mensagem
		ApplicationMessage vehicleInfo = new ApplicationMessage();
    	List<String> tags = new LinkedList<String>();
        tags.add("Formulario");
    	
    	vehicleInfo.setContentObject(vehicleLocalInfo);
    	vehicleInfo.setTagList(tags);
    	vehicleInfo.setSenderID(vehicleLocalInfo.getId());
    	
    	try {
    		myConnection.sendMessage(vehicleInfo);
        }
        catch (Exception e) {
        	result = false;
        	e.printStackTrace();
        }

		return result;
	}

}

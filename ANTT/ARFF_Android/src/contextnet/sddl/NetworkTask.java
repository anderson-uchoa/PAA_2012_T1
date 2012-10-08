package contextnet.sddl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.UUID;

import lac.cnclib.net.NodeConnection;
import lac.cnclib.net.mrudp.MrUdpNodeConnection;
import android.os.AsyncTask;
import contextnet.form.App;

public class NetworkTask extends AsyncTask<Void, byte[], Boolean> {

	private String ipAddress;
	private UUID vehicleId;
	
	//connection
	/**
	* The connection between the node and the remote server, uses RUDP.
	*/
	private NodeConnection myConnection;
	private App application;
	private boolean connected;
	private VehicleInformation vehicleLocalInfo;
	
	/**
	* Which port to attempt a connection. TODO: this should be defined in the CLientLib.
	*/
	public static final int DEFAULT_SDDL_PORT = 5500;

    public NetworkTask(String ip, App ap)
    {
    	connected = false;
    	application = ap;
    	ipAddress = ip;
    }

    /**
     * Overriten methods
     */
    
	@Override
	protected Boolean doInBackground(Void... arg0) 
	{
		boolean result = true; 
					
		try {
			myConnection = new MrUdpNodeConnection();
		} catch (IOException e) {
			result = false;
			e.printStackTrace();
		}
		
		if(result)
		{
			myConnection.addNodeConnectionListener(new NetworkListener());
			
			SocketAddress sc = new InetSocketAddress(ipAddress, DEFAULT_SDDL_PORT);
			
			try {
				myConnection.connect(sc);
			}
			catch (IOException e) {
				//TODO: cai aqui quando não conecta, tratar isso
				result = false;
				e.printStackTrace();
			}
			
			if(result)
			{
				connected = true;
				application.setConnected();
				//TODO: identificar veiculo propriamente pelo nome do usuário
		    	vehicleLocalInfo = new VehicleInformation(new UUID(1, 1));
			}
		}
		
        return result;
	}

	public NodeConnection getConnection() {
		return myConnection;
	}

	public VehicleInformation getVehicleInfo() {
		return vehicleLocalInfo;
	}
	
	/*apenas para log
    @Override
    protected void onPreExecute() {
        Log.i("AsyncTask", "onPreExecute");
    }
    
	@Override
    protected void onProgressUpdate(byte[]... values) {
        if (values.length > 0) {
            Log.i("AsyncTask", "onProgressUpdate: " + values[0].length + " bytes received.");
        }
    }
	
    @Override
    protected void onCancelled() {
        Log.i("AsyncTask", "Cancelled.");
    }
    
    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            Log.i("AsyncTask", "onPostExecute: Completed with an Error.");
        } else {
            Log.i("AsyncTask", "onPostExecute: Completed.");
        }
    }*/
}

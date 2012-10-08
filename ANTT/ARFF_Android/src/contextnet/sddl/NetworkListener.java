package contextnet.sddl;

import java.net.SocketAddress;
import java.util.List;

import lac.cnclib.net.NodeConnection;
import lac.cnclib.net.NodeConnectionListener;
import lac.cnclib.sddl.message.Message;
import lac.cnclib.sddl.serialization.Serialization;

public class NetworkListener implements NodeConnectionListener {

	public void connected(NodeConnection remoteCon) {
	    System.out.println("Veículo conectado com sucesso!");
	}

	public void reconnected(NodeConnection remoteCon, SocketAddress endPoint,
			boolean wasHandover, boolean wasMandatory) {
	    System.out.println("Reconectado!");
	}

	public void disconnected(NodeConnection remoteCon) {
	    System.out.println("Desconectado!");
	}

	public void newMessageReceived(NodeConnection remoteCon, Message message) {
	    System.out.println("Msg recebida: " + Serialization.getObjectFromBytes(message.getContent()).toString());
	}
	
	public void unsentMessages(NodeConnection remoteCon, List<Message> unsentMessages) {
	    System.out.println("unsentMessages: " + unsentMessages.size());
	}

	public void internalException(NodeConnection remoteCon, Exception e) {
	    System.err.println(e.getStackTrace());
	}
}

package br.rio.puc.inf.lac.arff.middleware.service;

import java.io.IOException;

import lac.cnclib.sddl.message.ApplicationMessage;
import lac.cnclib.sddl.serialization.Serialization;
import lac.cnet.sddl.topics.GroupAdvertisementTopic;
import lac.cnet.sddl.topics.Message;
import lac.cnet.sddl.topics.PrivateMessageTopic;
import lac.cnet.sddl.udi.core.DDSLayer;
import lac.cnet.sddl.udi.core.UniversalDDSLayerFactory;
import br.rio.puc.inf.lac.arff.application.model.Inspector;

public class InspectorService {

  private DDSLayer ddsEntityManger;

  public void initialize() {
    this.ddsEntityManger = UniversalDDSLayerFactory.getInstance(UniversalDDSLayerFactory.SupportedDDSVendors.CoreDX);
    this.ddsEntityManger.createParticipant(UniversalDDSLayerFactory.CNET_DOMAIN);
    this.ddsEntityManger.createPublisher();
    this.ddsEntityManger.createSubscriber();

    InspectorNodeInformationListener inspectorNodeInformationListener = new InspectorNodeInformationListener(this);

    Object messageTopic = this.ddsEntityManger.createTopic(Message.class.getCanonicalName(), Message.class.getName());

    this.ddsEntityManger.createDataReader(messageTopic, inspectorNodeInformationListener);

    GroupAdvertisementListener groupAdvertisementListener = new GroupAdvertisementListener(this);

    Object groupAdvertisemenTopic = this.ddsEntityManger.createTopic(GroupAdvertisementTopic.class.getCanonicalName(), GroupAdvertisementTopic.class.getName());

    this.ddsEntityManger.createDataReader(groupAdvertisemenTopic, groupAdvertisementListener);

    Object privateMessageTopic = this.ddsEntityManger.createTopic(PrivateMessageTopic.class.getCanonicalName(), PrivateMessageTopic.class.getName());

    this.ddsEntityManger.createDataWriter(privateMessageTopic);
  }

  public void processGroupAdvertisement(GroupAdvertisementTopic groupAdvertisementTopic) {
    System.out.println("Aqui o seriço de Middleware processa os dados");
    for (int groupId : groupAdvertisementTopic.groupOperationCollection) {
      String strMessage = null;
      if (groupId > 0) {
        strMessage = "Você foi incluído no grupo" + groupId;
      }
      else {
        strMessage = "Você foi removido do grupo" + Math.abs(groupId);
      }
      this.sendMessageToMobileNode(groupAdvertisementTopic, strMessage);
    }
  }

  public void sendMessageToMobileNode(GroupAdvertisementTopic groupAdvertisementTopic, String strMessage) {
    PrivateMessageTopic privateMessageTopic = new PrivateMessageTopic();
    privateMessageTopic.leastSignificantBitsGatewayId = groupAdvertisementTopic.leastSignificantBitsGatewayId;
    privateMessageTopic.mostSignificantBitsGatewayId = groupAdvertisementTopic.mostSignificantBitsGatewayId;
    privateMessageTopic.leastSignificantBitsVehicleId = groupAdvertisementTopic.leastSignificantBitsVehicleId;
    privateMessageTopic.mostSignificantBitsVehicleId = groupAdvertisementTopic.mostSignificantBitsVehicleId;

    ApplicationMessage x = new ApplicationMessage();
    x.setContentObject(strMessage);

    try {
      privateMessageTopic.message = Serialization.getObjectByteStream(x);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    privateMessageTopic.groupId = (int) UniversalDDSLayerFactory.BROADCAST_FLAG;
    privateMessageTopic.groupType = (int) UniversalDDSLayerFactory.BROADCAST_FLAG;
    this.ddsEntityManger.writeTopic(PrivateMessageTopic.class.getSimpleName(), privateMessageTopic);
  }

  public void processMobileNodeInformation(Inspector inspector) {
    System.out.println("Aqui o seriço de Middleware processa os dados");
  }
}

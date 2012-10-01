package br.rio.puc.inf.lac.arff.middleware.service;

import lac.cnet.sddl.topics.GroupAdvertisementTopic;
import lac.cnet.sddl.udi.core.DDSLayer;
import lac.cnet.sddl.udi.core.UniversalDDSLayerFactory;

public class InspectorService {

  private DDSLayer ddsEntityManger;

  public void initialize() {

    this.ddsEntityManger = UniversalDDSLayerFactory.getInstance(UniversalDDSLayerFactory.SupportedDDSVendors.CoreDX);
    this.ddsEntityManger.createParticipant(UniversalDDSLayerFactory.CNET_DOMAIN);
    this.ddsEntityManger.createPublisher();
    this.ddsEntityManger.createSubscriber();

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

    }
  }
}

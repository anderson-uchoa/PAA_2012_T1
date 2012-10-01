package br.rio.puc.inf.lac.arff.middleware.service;

import lac.cnet.sddl.topics.GroupAdvertisementTopic;
import lac.cnet.sddl.udi.core.listener.UDIDataReaderListener;

public class GroupAdvertisementListener implements UDIDataReaderListener<Object> {

  private InspectorService inspectorService;

  public GroupAdvertisementListener(InspectorService inspectorService) {
    this.inspectorService = inspectorService;
  }

  @Override
  public void onNewData(Object objGroupAdvertisementTopic) {
    if (objGroupAdvertisementTopic instanceof GroupAdvertisementTopic) {
      GroupAdvertisementTopic groupAdvertisementTopic = (GroupAdvertisementTopic) objGroupAdvertisementTopic;
      this.inspectorService.processGroupAdvertisement(groupAdvertisementTopic);
    }
  }
}

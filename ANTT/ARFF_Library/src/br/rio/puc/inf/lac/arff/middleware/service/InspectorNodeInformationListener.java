package br.rio.puc.inf.lac.arff.middleware.service;

import lac.cnclib.net.Message;
import lac.cnclib.sddl.serialization.Serialization;
import lac.cnet.sddl.udi.core.listener.UDIDataReaderListener;
import br.rio.puc.inf.lac.arff.application.model.Inspector;

public class InspectorNodeInformationListener implements UDIDataReaderListener<Object> {

  private InspectorService inspectorService;

  public InspectorNodeInformationListener(InspectorService inspectorService) {
    this.setInspectorService(inspectorService);

  }

  @Override
  public void onNewData(Object messageTopic) {
    if (messageTopic instanceof Message) {
      Message message = (Message) messageTopic;
      Inspector inspector = (Inspector) Serialization.getObjectFromBytes(message.getContent());

      this.inspectorService.processMobileNodeInformation(inspector);
    }

  }

  public InspectorService getInspectorService() {
    return inspectorService;
  }

  public void setInspectorService(InspectorService inspectorService) {
    this.inspectorService = inspectorService;
  }

}

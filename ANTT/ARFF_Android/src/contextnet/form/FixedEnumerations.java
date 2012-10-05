package contextnet.form;

public final class FixedEnumerations {
	
	public FixedEnumerations(){}
	    
	public enum InspectionPlace {
	    ESTRADA(0, "Estrada"),
	    GARAGEM(1, "Garagem");
	    
	    private int id;
	    private String description;
	    
		private InspectionPlace(int id, String des){
	        this.id = id;
	        description = des;
	    }
	    
		public int getId() {
	    	return id;
	    }
	    
		public String getDescription() {
	    	return description;
	    }
		
		/*final void setAttributesFromId(int id) {
			if(id == ESTRADA.id)
			{
				this.description = ESTRADA.description;
				this.id = ESTRADA.id;
			}
			else
			{
				this.description = GARAGEM.description;
				this.id = GARAGEM.id;
			}
		}*/
	}
	
	public enum VehicleTypes {
	    CAMINHAO(0, "Caminhão", "caminhao"),
	    PASSEIO(1, "Passeio", "passeio"),
	    ONIBUS(2, "Ônibus", "onibus"),
	    ESPECIAL(3, "Carga-Especial", "especial");
	    
	    public static int MAX_NO = 4;
	    private int id;
	    private String description;
	    private String tag;
	    
		private VehicleTypes(int id, String des, String tag){
	        this.id = id;
	        description = des;
	        this.tag = tag;
	    }
	    
		public int getId() {
	    	return id;
	    }
	    
		public String getDescription() {
	    	return description;
	    }
	    
		public String getTag() {
	    	return tag;
	    }
		
		public VehicleTypes getTypeFromId(int id)
		{
			if(id == CAMINHAO.id)
			{
				return CAMINHAO;
			}
			else if(id == PASSEIO.id)
			{
				return PASSEIO;
			}
			else if(id == ONIBUS.id)
			{
				return ONIBUS;
			}
			else
			{
				return ESPECIAL;
			}
		}
		
		/*final void setAttributesFromId(int id) {
			if(id == CAMINHAO.id)
			{
				this.description = CAMINHAO.description;
				this.id = CAMINHAO.id;
			}
			else if(id == PASSEIO.id)
			{
				this.description = PASSEIO.description;
				this.id = PASSEIO.id;
			}
			else if(id == ONIBUS.id)
			{
				this.description = ONIBUS.description;
				this.id = ONIBUS.id;
			}
			else
			{
				this.description = ESPECIAL.description;
				this.id = ESPECIAL.id;
			}
		}*/
	}
	
	public enum Categories {
	    A(0, "A"),
	    B(1, "B"),
	    C(2, "C"),
	    D(3, "D"),
	    E(4, "E");
	    
	    private int id;
	    private String description;
	    
		private Categories(int id, String des){
	        this.id = id;
	        description = des;
	    }
	    
		public int getId() {
	    	return id;
	    }
	    
		public String getDescription() {
	    	return description;
	    }
	}
}

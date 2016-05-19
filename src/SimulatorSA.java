import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SimulatorSA {

	public static  class No {
		String solucao;
		int custo;

		List<String> cidades;

		public String getSolucao() {
			return solucao;
		}

		public void setSolucao(String solucao) {
			this.solucao = solucao;
		}

		public int getCusto() {
			return custo;
		}

		public void setCusto(int custo) {
			this.custo = custo;
		}

		public List<String> getCidades() {
			return cidades;
		}

		public void setCidades(List<String> cidades) {
			this.cidades = cidades;
		}
		
		public No(){
			super();
		}
		public No(List<String> cidades, int custo){
			super();
			this.cidades = cidades;
			this.custo = custo;
		}

		@Override
		public String toString() {
			String temp = "";
			for (int i = 0; i < cidades.size(); i++) {
				temp += cidades.get(i);

			}
			return temp;
		}

	}

	public static List<String> permutarCidadesDuasConsencutivas(
			List<String> solucao) {
		
		
		Random random = new Random();
		int pos_cidade1 = random.nextInt(5);
		int pos_cidade2 = 0;
		String label_cidade1;
		String label_cidade2;
		if (pos_cidade1 + 1 == solucao.size()) {
			label_cidade1 = solucao.get(pos_cidade1);
			label_cidade2 = solucao.get(pos_cidade2);

		} else {
			label_cidade1 = solucao.get(pos_cidade1);
			pos_cidade2 = pos_cidade1 + 1;
			label_cidade2 = solucao.get(pos_cidade2);

		}

		solucao.set(pos_cidade1, label_cidade2);
		solucao.set(pos_cidade2, label_cidade1);

		return solucao;

	}

	public static int funcaoAvaliacaoCusto(List<String> solucao) {

		int soma = 0;
		for (int i = 0; i < solucao.size()-1; i++) {
			soma += DISTANCIA.get(solucao.get(i) + solucao.get(i+1));
		}

		soma += DISTANCIA.get(solucao.get(0) + solucao.get(solucao.size()-1));

		return soma;

	}

	public static List<String> solucoes = new ArrayList<String>();
	public static final Map<String, Integer> DISTANCIA = new HashMap<String, Integer>();

	static {
		DISTANCIA.put("AD", 5);
		DISTANCIA.put("DA", 5);
		DISTANCIA.put("AB", 2);
		DISTANCIA.put("BA", 2);
		DISTANCIA.put("AC", 4);
		DISTANCIA.put("CA", 4);
		DISTANCIA.put("AE", 3);
		DISTANCIA.put("EA", 3);
		
		DISTANCIA.put("BC", 3);
		DISTANCIA.put("CB", 3);
		DISTANCIA.put("BE", 4);
		DISTANCIA.put("EB", 4);
		DISTANCIA.put("BD", 6);
		DISTANCIA.put("DB", 6);
		
		DISTANCIA.put("CD", 3);
		DISTANCIA.put("DC", 3);		
		DISTANCIA.put("CE", 5);
		DISTANCIA.put("EC", 5);
		
		DISTANCIA.put("DE", 4);
		DISTANCIA.put("ED", 4);
	}

	public static final float LIMIAR = (float) 0.05;
	public static int NUMERO_MAXIMO_INTERACOES = 4;
	public static int ESQUEMA_ESFRIAMENTO = 90;
	public static final float EULER = (float) 2.71;
	public static String aceita = "N";
	static float T  = 1;

	public static void main(String[] args) {
		List<String> solucao_atual = new ArrayList<String>();
		solucao_atual.add(0,"B");
		solucao_atual.add(1,"D");
		solucao_atual.add(2,"E");
		solucao_atual.add(3, "C");
		solucao_atual.add(4, "A");
		
		float prob = 0;
		int custo = funcaoAvaliacaoCusto(solucao_atual);
		No no = new No(solucao_atual, custo);
		No solucao_final = no;
		
		TableBuilder tb = new TableBuilder();
   	 
    
    	
    	tb.addRow("INT", "TEMP", "SOLUCAO", "CUSTO", "LIMIAR", "PROB", "ACEITA");
    	tb.addRow(new Integer(0).toString(), new Float(T).toString(), no.toString(), new Integer(no.getCusto()).toString(), new Float(LIMIAR).toString(), new Double(prob).toString(), "");


		for (int i = 0; i < NUMERO_MAXIMO_INTERACOES; i++) {
			
			T = T*ESQUEMA_ESFRIAMENTO/100;
			List<String> solucao_nova = permutarCidadesDuasConsencutivas(no.getCidades());
			 custo = funcaoAvaliacaoCusto(solucao_nova);
			
			if(custo < no.getCusto()  ){
				no = new No(solucao_nova, custo);
				solucao_final = new No(solucao_nova, custo);
				aceita = "S";
				
			}else{
				prob = (float) Math.pow(EULER, -(custo-no.getCusto())/T);
					if(prob >LIMIAR){
						no = new No(solucao_nova, custo);
						solucao_final = new No(solucao_nova, custo);
						aceita = "S";
					}
						
			}
	    	tb.addRow(new Integer(i+1).toString(), new Float(T).toString(), no.toString(), new Integer(no.getCusto()).toString(), new Float(LIMIAR).toString(), new Double(prob).toString(), aceita);

		
			prob=0;
			aceita ="N";
		}
		
		System.out.println(tb.toString());
		
		System.out.println("SOLUCAO FINAL: "+ solucao_final.toString() + " - CUSTO:" + solucao_final.getCusto());

	}

}

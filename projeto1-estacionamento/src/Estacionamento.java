import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Estacionamento {
	private String[] placas;

	public Estacionamento(int n) {
		this.placas = new String[n];
		for (int i = 0; i < n; i++) { 
			placas[i] = "vazia";
		}
		//criar arquivo de dados vazio
		try {
			FileWriter saida = new FileWriter(new File("placas.csv"), false);	
			saida.write( "vaga;placa\n");	//primeira linha
			saida.close();
		} catch (IOException e) {};
	}

	public void entrar(String placa, int vaga) throws Exception { 
		// ocupar a vaga com a placa

		if (vaga > placas.length || vaga < 1) 
			throw new Exception("entrar - vaga inexistente " + vaga);

		if (!this.consultarVaga(vaga).equals("vazia")) 
			throw new Exception("entrar - vaga esta ocupada "+vaga);

		placas[vaga - 1] = placa;
	}

	public void sair(int vaga) throws Exception { 
		// desocupar a vaga 
		if (vaga > placas.length || vaga < 1) 
			throw new Exception("sair - vaga inexistente " + vaga);

		if (this.consultarVaga(vaga).equals("vazia")) {
			throw new Exception("sair - vaga nao esta ocupada "+vaga);
		}
		placas[vaga - 1] = "vazia";
	}

	public int consultarPlaca(String placa)  {
		// retorna a vaga da placa, ou -1 caso nao exista
		for (int i = 0; i < placas.length; i++) {
			if (placas[i].equals(placa)) 
				return i+1 ;
		}
		return -1;
	}

	public String consultarVaga(int vaga) throws Exception {  
		// retorna a placa da vaga
		if (vaga > placas.length || vaga < 1) 
			throw new Exception("consultar vaga - vaga inexistente " + vaga);

		return placas[vaga - 1];
	}

	public void transferir(int vaga1, int vaga2) throws Exception {
		// move a placa da vaga1 para a vaga2

		if (vaga1 > placas.length || vaga1 < 1) 
			throw new Exception("transferir - vaga inexistente " + vaga1);
		if (vaga2 > placas.length || vaga2 < 1) 
			throw new Exception("transferir - vaga inexistente " + vaga2);
		if (vaga1 == vaga2) 
			throw new Exception("transferir - Vaga origem e destino devem ser distintas");
		if (consultarVaga(vaga1).equals("vazia")) 
			throw new Exception("transferir - A vaga origem nao esta ocupada!");
		if (!consultarVaga(vaga2).equals("vazia")) {
			throw new Exception("transferir - A vaga destino nao esta vazia!");
		}

		String temporario = placas[vaga2 - 1];
		placas[vaga2 - 1] = placas[vaga1 - 1];
		placas[vaga1 - 1] = temporario;
	}

	public String[] consultarGeral() {
		// retorna as N placas
		return placas;
	}

	public ArrayList<Integer> consultarVagasLivres() {
		// retorna os numeros das placas Vazias
		ArrayList<Integer> livres = new ArrayList<>();
		for (int i=0; i < placas.length; i++) {
			if(placas[i].equals("vazia")) 
				livres.add(i+1);

		}
		return livres;
	}

	public void lerDados()  throws Exception{
		// ler do arquivo placas.csv, a placa de cada vaga ocupada no momento
		try {
			Scanner arquivo = new Scanner(new File("placas.csv"));
			String entrada, placa;
			int vaga;
			String [] partes;
			String cabecalho = arquivo.nextLine();
			while(arquivo.hasNextLine()) {
				entrada = arquivo.nextLine();
				partes = entrada.split(";");
				vaga = Integer.parseInt(partes[0]);
				placa = partes[1];
				placas[vaga-1]=placa;
			}
		} catch (FileNotFoundException e) {
			throw new Exception("arquivo inexistente");
		}
	}

	public void gravarDados() throws Exception{
		// (re)gravar no arquivo placas.csv, a placa de cada vaga ocupada no momento
		try {
			FileWriter saida = new FileWriter(new File("placas.csv"), false); //append=false
			saida.write( "vaga;placa\n");	//primeira linha
			for (int i=0; i < placas.length; i++) {
				if(! placas[i].equals("vazia")) {
					saida.write((i+1) + ";"+placas[i]+"\n");
				}
			}
			saida.close();
		} catch (IOException e) {
			throw new Exception("problema na gravacao do arquivo de saida");
		}
	}
}

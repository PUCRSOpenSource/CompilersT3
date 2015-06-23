/*
 * @author Daniel Amarante - 13201876-3 - daniel.amarante2@gmail.com
 * @author Matthias Nunes - 08105058-5 - matthiasnunes@gmail.com
 * @author Pedro Kühn - 10200237-5 - pedrohk@gmail.com
 */

import java.util.ArrayList;
import java.util.Iterator;


public class TabSimb
{
    private ArrayList<TS_entry> lista;
    
    public TabSimb( )
    {
        lista = new ArrayList<TS_entry>();
    }
    
     public void insert( TS_entry nodo ) {
      lista.add(nodo);
    }    
    
    public void listar() {
      int cont = 0;  
      System.out.println("\n\nListagem da tabela de simbolos:\n");
      for (TS_entry nodo : lista) {
          System.out.println(nodo);
      }
    }
      
    public TS_entry pesquisa(String umId, String escopo) {
      for (TS_entry nodo : lista) {
          if (nodo.getEscopo().equals(escopo) || nodo.getEscopo().equals("")) {
            if (nodo.getId().equals(umId)) {
              return nodo;
            }
    }
          
      }
      return null;
    }

    public  ArrayList<TS_entry> getLista() {return lista;}
}




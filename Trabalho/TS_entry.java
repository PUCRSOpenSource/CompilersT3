import java.util.ArrayList;

public class TS_entry
{
   private String id;
   private ClasseID classe;  
   private String escopo;
   private Type tipo;
   private int nElem;
   private ArrayList<Type> parametros;
   private TabSimb locais;


   public TS_entry(String id, Type tipo, String escopo, ClasseID classe) {
      this.id = id;
      this.tipo = tipo;
      this.escopo = escopo;
      this.classe = classe;
      locais = new TabSimb();
   }

   //construtor para funcoes e procedures;
   public TS_entry(String id, Type tipo, String escopo, ClasseID classe, ArrayList<Type> parametros) {
      this.id = id;
      this.tipo = tipo;
      this.escopo = escopo;
      this.classe = classe;
      this.parametros = parametros;
      locais = new TabSimb();
   }


   public String getId() {
       return id; 
   }

   public String getEscopo() {
       return escopo; 
   }

   public Type getTipo() {
       return tipo; 
   }
   
   public int getNumElem() {
       return nElem; 
   }

   public ArrayList<Type> getParametros() {
      return parametros;
   }

   
   public String toString() {
       StringBuilder aux = new StringBuilder("");
        
	     aux.append("Id: ");
	     aux.append(String.format("%-10s", id));
	     aux.append("\tClasse: ");
	     aux.append(classe);
	     aux.append("\tEscopo: ");
	     aux.append(String.format("%-4s", escopo));
	     aux.append("\tTipo: "); 
	     aux.append(tipo2str(this.tipo));
       aux.append("Parametros: ");
       aux.append(String.format("%-10s", parametros));

       
         

        ArrayList<TS_entry> lista = locais.getLista();
        for (TS_entry t : lista) {
            aux.append("\n\t");
	    		  aux.append(t.toString());
        }

      return aux.toString();

   }

    public String tipo2str(Type tipo) {
      if (tipo == null)  return "null"; 
     	else if (tipo==Type.Int)    return "integer"; 
      else if (tipo==Type.Bool)   return "boolean"; 
      else if (tipo==Type.Double) return "real";
      else if (tipo==Type.Error)  return  "_erro_";
	    else                        return "erro/tp";
   }



   // public void insereLocal(String id, int tp, ClasseID cl) {
   //      locais.insert(new TS_entry(id, tp, cl));
   //}

}







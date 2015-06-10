import java.util.ArrayList;

public class TS_entry
{
   private String id;
   private ClasseID classe;  
   private String escopo;
   private Type tipo;
   private int nElem;
   private TabSimb locais;


   public TS_entry(String id, Type tipo, String escopo, ClasseID classe) {
      this.id = id;
      this.tipo = tipo;
      this.escopo = escopo;
      this.classe = classe;
      locais = new TabSimb();
   }


   public String getId() {
       return id; 
   }

   public Type getTipo() {
       return tipo; 
   }
   
   public int getNumElem() {
       return nElem; 
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







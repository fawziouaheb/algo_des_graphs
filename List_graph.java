import java.util.Scanner;
import javax.naming.CompositeName;
import javax.print.DocFlavor.INPUT_STREAM;
import javax.rmi.ssl.SslRMIClientSocketFactory;
import javax.swing.GroupLayout;
import javax.swing.plaf.synth.SynthEditorPaneUI;

import java.lang.reflect.Array;
import java.rmi.server.ExportException;
import java.security.cert.X509CRLSelector;
import java.util.ArrayList;
import java.util.DuplicateFormatFlagsException;
import java.util.HashMap;

class List_graph
{
    private HashMap<String,ArrayList<String>> graph; // declaration d'un graphe tableau hashmap
   int d1=0;
  
   ArrayList<String> explore1= new ArrayList<String>();
   ArrayList<String> gr= new ArrayList<String>();
    public  List_graph(HashMap<String,ArrayList<String>> graph)
    {
        this.graph = graph;
    }
        

    public   ArrayList<String> Get_successor(String x) //recuperer les successeur d'un sommet
    {
        return this.graph.get(x); 
    }

    public boolean Is_arc(String x,String y) //verifier si  un arc
    {
        return this.Get_successor(x).contains(y); 
    }
    public boolean Is_art(String x,String y){
        return this.Get_successor(x).contains(y) && this.Get_successor(y).contains(x);
    }

    public static HashMap<String,ArrayList<String>> Write_graph() // saisir un graphe au clavier
    {

        Scanner reader = new Scanner(System.in);

        HashMap<String,ArrayList<String>> graph = new HashMap<String,ArrayList<String>>();

        ArrayList<ArrayList<String>> succesors_list = new ArrayList<ArrayList<String>>();

        String tmp_successors;

        System.out.println("ecrire les points de grahe separer par des  virgules");
        String point_graphe= reader.nextLine().trim(); 

        for(String s : point_graphe.split(",") )
        {
            succesors_list.add(new ArrayList<String>() );

            System.out.println("succ de " + s + " separe  par des une virgules : ");
            tmp_successors =  reader.nextLine().trim();

            for(String p :  tmp_successors.split(",") )
                succesors_list.get(succesors_list.size() - 1).add(p);

            graph.put(s,succesors_list.get(succesors_list.size() - 1) );
        }

        return graph;
    }
public ArrayList<String> affichage(){   //recuperer la liste de l'ensemble des sommets
    ArrayList<String> ps = new ArrayList<String>();
    this.graph.forEach((key,value)->{
      // System.out.println(key +"->"+value);
    ps.add(key);
    });
   
return ps;}
    public ArrayList<String> get_pred(String x){ //Calculer le predecesseur 
                                                        //(parcour l'ensemble des sommet on verifie si l'element appartien a la liste des sucesseur de l'ensemble)
        ArrayList<String> pred = new ArrayList<String>();
        this.graph.forEach((key,value)->{
            if(this.Get_successor(key).contains(x)){
			
            pred.add(key);
          
            }
		});
       
    return pred;}

    public ArrayList<String> desc(String x){// calcule les descendant sommet en parametre
        ArrayList<String> explore = new ArrayList<String>();
        ArrayList<String> composante =new ArrayList<String>();
        ArrayList<String> p =new ArrayList<String>();
        explore.add(x);
        String h;
        String premier;
        while(!explore.isEmpty()){
          
            premier=explore.get(0);
            explore.remove(0);
            composante.add(premier);    
          p=this.Get_successor(premier);
          
         for(int i=0 ;i<p.size() ; i++){
              h=p.get(i);
              if(!explore.contains(h) &&  !composante.contains(h) && (!h.isEmpty())){
                  
           explore.add(h);}
          }
        
        }
     composante.remove(0);
   return composante;}
   public ArrayList<String> ancre(String x){//calculer l'ancetre
ArrayList<String> explore= new ArrayList<String>();
ArrayList<String> composante= new ArrayList<String>();
ArrayList<String> p= new ArrayList<String>();
String h,premier;
explore.add(x);
while(!explore.isEmpty()){
          
    premier=explore.get(0);
    explore.remove(0);
    composante.add(premier);    
  p=this.get_pred(premier);
  
 for(int i=0 ;i<p.size() ; i++){
      h=p.get(i);
      if(!explore.contains(h) && !composante.contains(h) && (!h.isEmpty())){
         // System.out.println("c'est la ou je vais ajouter "+h);
   explore.add(h);}
  }

}
composante.remove(0);
return composante;
   }
   public ArrayList<String> cfc(String x){//composante fortement connexe
    ArrayList<String> desc= new ArrayList<String>();
    ArrayList<String> ancre= new ArrayList<String>();
    ArrayList<String> composante= new ArrayList<String>();
    desc=this.desc(x);
    ancre=this.ancre(x);
    for (int i=0; i<ancre.size();i++){
        if(desc.contains(ancre.get(i)) &&  !composante.contains(ancre.get(i))   ){
            composante.add(ancre.get(i));
        }
    }

   return composante;}
   public static ArrayList<String> removeDuplicates(ArrayList<String> list)     //eliminer les doublons dans une liste
    {
  
        // Create a new ArrayList
        ArrayList<String> newList = new ArrayList<String>();
  
        // Traverse through the first list
        for (String element : list) {
  
            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {
  
                newList.add(element);
            }
        }
  
        // return the new list
        return newList;
    }
   public ArrayList<String> cc(String x){   // calcule et renvoi le nbr composante connexe
       ArrayList<String> composante = new ArrayList<String>();
       ArrayList<String> ancre = new ArrayList<String>();
      ArrayList<String> desc = new ArrayList<String>();
      desc=this.desc(x);
    ancre=this.ancre(x);
    composante.add(x);
    composante.addAll(ancre);
    composante.addAll(desc);
    composante=removeDuplicates(composante);
   
    return composante;
   }
   public int ncc(){ // renvoi le nombre de composant
       int d=0;
       ArrayList<String>  list_t= new ArrayList<String>();
       ArrayList<String>  temp= new ArrayList<String>();
       for (int k=0; k<this.affichage().size();k++){
        temp=cc(this.affichage().get(k));
        if(!list_t.contains(temp.get(0))){
  d=d+1;
        }
         list_t.addAll(cc(this.affichage().get(k)));
         
    }


       
   return d;}
   public ArrayList<String> ParcLarg(String x){ //Parcour en largeur d'un  sous graphe
       ArrayList<String> explore= new  ArrayList<String>();
       ArrayList<String> atteintNg= new  ArrayList<String>();
       ArrayList<String> atteintAg= new  ArrayList<String>();
       ArrayList<String> tmp =new ArrayList<String>();
  int d=0;
  d=this.d1;
  explore=this.explore1;
    atteintAg.add(x);
    String premier,h;
    while(atteintAg.size() != 0){
       // System.out.println("je suis la!!!");
        premier=atteintAg.get(0);
    
        atteintAg.remove(0);
         explore.add(premier);
        System.out.println("la distance de    "+premier+"  c'est  "+d);
        tmp=this.Get_successor(premier);    
         for (int i =0;i<tmp.size();i++){
           h=tmp.get(i);

           if(!explore.contains(h) &&  !atteintAg.contains(h)  && !h.isEmpty()){

           atteintNg.add(h);
           }
         }
         if(atteintAg.size()==0 && atteintNg.size()!=0){
             d=d+1;
             for(int j=0; j<atteintNg.size();j++){
                
                 atteintAg.add(atteintNg.get(j));
               
             }
             atteintNg.clear();
             
         }

    }
    this.d1=d+1;
    return explore;}
    public ArrayList<String> ParcLarg1(String x){  //parcour en large final 
        ArrayList<String> tmp2= new  ArrayList<String>();
      ArrayList<String> tpmp =new ArrayList<String>();
        tpmp= this.ParcLarg(x);
       for (int k=0; k<this.affichage().size();k++){
            if(!tpmp.contains(affichage().get(k))){
            tmp2=this.ParcLarg(affichage().get(k));
                 for(int y=0;y<tmp2.size();y++){
                  if(!tpmp.contains(tmp2.get(y))){
                      tpmp.add(tmp2.get(y));
                    }
                }
                this.explore1=tpmp;
             }
        }
        tpmp=removeDuplicates(tpmp);
        return tpmp;}
         
public ArrayList<String> get_ex(){ //renvoiyer un pointeur d'un graph
        return this.gr;
    }
    public int verifier(ArrayList<String> ex,String u){   //verifier si un sommets appartient a une liste se successeur
        int p=0;
        ArrayList<String> tmp =new ArrayList<String>();
      tmp=Get_successor(u);
          for(int h=0;h<tmp.size();h++){
              if(!ex.contains(tmp.get(h))){
                  p=1;
                       
            }
          }
    return p;}
    public ArrayList<String> parc_profo( String x){
        int emm=0;
        int de=0;
        String u;
        ArrayList<String> pile= new ArrayList<String>();
       ArrayList<String> exp=new ArrayList<String>();
       ArrayList<String> li=new ArrayList<String>();
       
       exp=get_ex();
        ArrayList<String> suc= new ArrayList<String>();
        if(!exp.contains(x)){
            pile.add(x);
        }
        while(!pile.isEmpty()){
            
               u=pile.get(pile.size()-1);
           
           System.out.println("l'ordre de depilement du caraceter  "+u+"  c'est"+de);
           de=de+1;
            pile.remove(pile.size()-1);
       
            if(!exp.contains(u)){
                exp.add(u);
                suc=Get_successor(u);  
                if(!suc.isEmpty()){
                for(int j=0;j<suc.size();j++){
                    if(!exp.contains(suc.get(j)) && !(suc.get(j).isEmpty())){
                        System.out.println("l'ordre empilement du caractere   "+suc.get(j)+"   c'est "+emm);
                        pile.add(suc.get(j));
                        emm=emm+1;
                    }
                }
            }

            }
        }
        return exp;


    }

        public ArrayList<String> profo1( String x ){
        ArrayList<String> expf= new ArrayList<String>();
        ArrayList<String> list2 = new ArrayList<String>();
        expf=this.parc_profo(x);
        for (int u=0; u<this.affichage().size();u++){
            if(!expf.contains(this.affichage().get(u))){
                list2=parc_profo(this.affichage().get(u));
                for(int t=0;t<list2.size();t++){
                    if(!expf.contains(list2.get(t))){
                        expf.add(list2.get(t));
                    }
                        
                }
                this.gr=expf;
            }

        }
  
    return expf;}
    public static String sommet()
    { //saisir un sommets au clavier
        String x;
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        return str;
    }

    public static void exercicetp2(){ // le graph et tout l'ensemble des methode
        String x,y;
        System.out.println("Veuillez saisir le graphe");
        List_graph graph = new List_graph(List_graph.Write_graph() );
       /* System.out.println("Veuillez saisir un sommet x");
        x=sommet();
        System.out.println("le succeseur de " + x + " est : " + graph.Get_successor(x));
        x=sommet();
        System.out.println("Veuillez saisir un sommet y");
        x=sommet();
        System.out.println("le predecesseur de " + y + " est : " + graph.get_pred(y));
        System.out.println("les descendant de " + y + " est : " + graph.desc(y));
        System.out.println("les ancetre de " + x + " est : " + graph.ancre(x));
        System.out.println("Saisir le numero de l'exercice ");*/
        
        int ex = 0;
        while(ex!=-1){
            System.out.println("Saisir une methode: ");
            Scanner num = new Scanner(System.in);
            ex = num.nextInt();
            System.out.println("Saisir le sommet: ");

            x=sommet();
        if(ex==5)
        {     
            System.out.println("la composante connexe de " + x + " est " + graph.cc(x));
            
        }
        if(ex==6)
        {
            System.out.println("la liste du parcour en largeur de " + x + " est " + graph.ParcLarg1(x));
        }
        if(ex==7)
        {
            System.out.println(graph.profo1(x));
        }  
        if(ex==1)
        {System.out.println("le succeseur de " + x + " est : " + graph.Get_successor(x));
           
        }
        if(ex==2)
        {  System.out.println("le predecesseur de " + x + " est : " + graph.get_pred(x));
        }
        if(ex==3)
        {
            System.out.println("les ancetre de " + x + " est : " + graph.ancre(x));
        }
        if(ex==4)
        {
            System.out.println("les descendant de " + x + " est : " + graph.desc(x));
        }
        if(ex==8)
        {
            System.out.println("le nombre de composante connexe du graphe est " + graph.ncc());
        }
        if(ex==9)
        {
            y=sommet();
            System.out.println(graph.Is_arc(x,y));
        }
        if(ex==10)
        {
            y=sommet();
            System.out.println(graph.Is_art(x,y));
        }
      
      }
    }
   
               public static void main(String[] args) {
       exercicetp2();
    

    }
}

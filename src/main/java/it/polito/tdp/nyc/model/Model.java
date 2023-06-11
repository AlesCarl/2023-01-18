package it.polito.tdp.nyc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jgrapht.Graphs;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.util.LengthUnit;
import com.javadocmd.simplelatlng.LatLngTool; 

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.nyc.db.NYCDao;

    public class Model {
    	
    	 NYCDao dao =new NYCDao() ;
         private SimpleWeightedGraph<String, DefaultWeightedEdge> graph;  // SEMPLICE, PESATO, NON ORIENTATO
         private List<String> allLocation ; 
 		 int gradoMax ; 
// prova altro

    	
    	 
    public Model() {
    	this.dao= new NYCDao() ; 
    	this.graph= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
    	this.allLocation= new ArrayList<>();

    		
    		
    	}
    	 
    	 public List<String> getAllProvider() {
    		 return dao.getAllProvider();
    	 }

 		private void loadAllNodes(String provider) {
 			
 		allLocation= dao.getAllLocation(provider);
 			
 			
 			
 		}
     	
    	
    	 
    	 public void creaGrafo( String provider, Double distanza) {
    		 
    		 loadAllNodes(provider); 
 	 		//System.out.println("size: " +this.allNACode.size());

    		 /** VERTICI */
 	    	Graphs.addAllVertices(this.graph, allLocation);
 	 		System.out.println("NUMERO vertici GRAFO: " +this.graph.vertexSet().size());
 	 		System.out.println("vertici GRAFO: " +this.graph.vertexSet());
 	 		
 	 		
 	 		
 	 		/** ARCHI **/ 
 	 		
 	 		for(String l1: this.allLocation) {
 	 			for(String l2: this.allLocation) {
 	 				
 	 				if(l1.compareTo(l2)!=0) { 
 	 					double peso= coordinate(l1,l2,provider); 
 	 			 
 	 			 				if(peso<=distanza && peso>0) {
 	 		 						Graphs.addEdgeWithVertices(this.graph, l1, l2, peso);
 	 		        }
 	 			}
 	 		}
    	 }
 	 		System.out.println("\nnumero ARCHI: "+ this.graph.edgeSet().size());
    	
      }

    	 
    	 //date le due località l1, l2, 
		private double coordinate(String l1, String l2, String provider) {
	   /* 
       considerando per ogni località (l1, l2...),  la media delle latitudini e longitudini degli hotspot installati 
        DATO UN  provider p */
			double distanza=0; 
			
			LatLng lng1= dao.getLatLang(l1, provider);
			LatLng lng2= dao.getLatLang(l2, provider);
		
			
			if(lng1!= null && lng2!= null)
	        distanza = LatLngTool.distance(lng1,lng2,LengthUnit.KILOMETER) ;
	       
			return distanza;
		}
		
		List<String> vertexGradoMax= new ArrayList<>(); 
		
		
		public int analisiGrafo () {
	//  trovare e stampare i vertici del grafo che hanno il maggior numero di vicini. 
			gradoMax=0; 
			
			for(String ss : this.graph.vertexSet() ) {
				
		         if( graph.degreeOf(ss)>gradoMax) {
		        	 vertexGradoMax.clear();
		        	 gradoMax= this.graph.degreeOf(ss);
		         } 

				 if( graph.degreeOf(ss) == gradoMax) { 
						vertexGradoMax.add(ss);	
				 }
			}
			return gradoMax; 
		}
	
		
		private List<String> parziale; 
		private List<String> percorsoOttimale; 
		int totLocalita; 

		
		
		public List<String> camminoAciclico (String localitaTarget, String s) {
			 
			
			  parziale= new ArrayList<String>() ; // qui inseriro' tutti i vari vertici scelti

			  parziale.add(verticeCasuale());  //vertice casuale (1d)  da cui partire.
			  totLocalita=1; 
			
			  
			  percorsoOttimale= new ArrayList<String>(); 
			  
			  
			  ricorsione( parziale, totLocalita,localitaTarget,s ); 
			  
			  return percorsoOttimale; 

			
		  }
		
		
		private void ricorsione( List<String> parziale, int totLocalita,String localitaTarget,String s) {
			
			/*data una località target (t) e  inserire una stringa s (non vuota),
			  trovare (se esiste), un cammino aciclico che: 
		      
		      	1. inizi da una delle località calcolate al punto 1d (scelta in modo casuale) 
		      	4. termini in t;
		      	2. tocchi il maggior numero di località;
		      	3. non passi per località il cui nome contenga la sottostringa s.
			 */
			
			String current = parziale.get(parziale.size()-1);

			
			 /** 1. condizione uscita */        // DA MIGLIORARE, NON CREDO BASTI 
			  
			  if(current.compareTo(localitaTarget)==0) {  	
				  //percorsoOttimale.add(localitaTarget);
				  return; //OCCHIO non si fermano tutte le ricorsioni, ma solo questa
			  }
			  
			  
			  
			  /** 2. qui trovo la SOLUZIONE MIGLIORE **/ 
			  
			  if(parziale.size() > totLocalita ) {
				  
					 totLocalita= this.parziale.size(); 
					 percorsoOttimale = new ArrayList<>(parziale);
				 }
			  
			  
			  /** 3. aggiungo String da successori in parziale */   

			  List<String> successori= Graphs.neighborListOf(graph, current);
			  
			  //?????? ???????     oppure successorListOf    ??????? ?????? ????? 
			

			  for(String ss: successori) {
				  if(!parziale.contains(ss) && !ss.contains(s)) { 
				   parziale.add(ss); 
				   ricorsione(parziale, totLocalita,localitaTarget,s ); 	
				   parziale.remove(ss);
				  }
			  }	  
			  
			
				  
			  }
			
		
		
		
		
		
		private String verticeCasuale() {
		 Random random = new Random();
		 int randomIndex = random.nextInt(vertexGradoMax.size());
		 return vertexGradoMax.get(randomIndex);

	}

		public List<String> getListGradoMax() {
			return vertexGradoMax; 
		}
		
		
		
    	
		public int getVertici() {
			return graph.vertexSet().size();
		}
	    
		public int getNumEdges() {
		     return graph.edgeSet().size(); 
		 }
		
		public List<String> getVerticiCmb() {
			return this.allLocation; 
		}
    	 
    	 
    	 
    	 
    	 
    	 
    	 
    	 
    	 
    }
	
	

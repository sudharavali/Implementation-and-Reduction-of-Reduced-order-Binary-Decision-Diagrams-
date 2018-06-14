/**
 *
 * @author Sudha Ravali Yellapantula
 */

import java.util.HashMap;
import java.util.ArrayList;
import java.io.*;
import java.util.Arrays;

public class BDD
{
    
    
	ArrayList <Node> T;
	HashMap <String,Integer> H;
	int vars;
	int x[];
	int x_val[];
	
	public static int count=0;
	int restrict_var;
	int restrict_var_val;
	BDD restrict_robdd;
    
    public static void main(String args[])
	{
		BDD obj=new BDD(5);
		obj.build(0);
             	System.out.println("Output:\n\n");
				System.out.println("Expression 1: equiv( (and (and (equiv(x[0], x[1]), equiv ( x[2], x[3])), not(x[4]))), (or(and(equiv(x[0],x[1]),equiv(x[2],x[4])),or(x[0],x[3])))");
				System.out.println( "ROBDD Table T1 : \n");
                System.out.println("u   var   Low   High");
                for (int i =0; i<obj.T.size(); i++){
                System.out.print(i+"    "+(((obj.T.get(i)).index)+"     "+(obj.T.get(i)).low)+"     "+((obj.T.get(i)).high));
                System.out.println();
                }
        
        System.out.println();
        System.out.println();
        System.out.println();
        
        BDD.count++;
        
        BDD obj2=new BDD(5);
        obj2.build(0);
        	
			System.out.println("Expression 2: and(equiv( (or (and (equiv(x[0], x[1]), equiv ( x[2], x[3])), not(x[4]))), (or(and(equiv(x[0],x[1]),equiv(x[2],x[4])),or(x[0],x[3])))),or((equiv(x[0],x[1])), x[2] ))");
        	System.out.println( "ROBDD Table T2 : \n");
        	System.out.println("u   Index   Low   High");
        	for (int i =0; i<obj2.T.size(); i++){
                System.out.print(i+"    "+(((obj2.T.get(i)).index)+"     "+(obj2.T.get(i)).low)+"     "+((obj2.T.get(i)).high));
                System.out.println();
                }
          
        	
        	//APPLY Code
            //Instance of Apply Class 	
            Apply res_robdd=new Apply(obj,obj2);
            
            //Index of root nodes of two ROBDDs passed as parameters to the app() function.
            int root1_u=obj.T.size()-1;
            int root2_u=obj2.T.size()-1;
            
            res_robdd.app(root1_u, root2_u);
            
            BDD obj4=res_robdd.obj3;
            
        	System.out.println( "\n\n\nApplying AND operator on ROBDD-1 and ROBDD-2: ");
        	System.out.println("Expression 3: and(Expression-1, Expression-2)");
        	System.out.println("Resultant ROBDD Table-3:\n");
        	System.out.println("u   Index   Low   High");
        	for (int i =0; i<obj4.T.size(); i++){
                System.out.print(i+"    "+(((obj4.T.get(i)).index)+"     "+(obj4.T.get(i)).low)+"     "+((obj4.T.get(i)).high));
                System.out.println();
                }

        	
        	
        	
        	
        	
    	//Restrict Function Calls
        int res_bdd_vars=obj2.vars; 
        BDD obj3=new BDD(res_bdd_vars);
        obj3.restrict_robdd=obj2;
        obj3.restrict_var=4;
        obj3.restrict_var_val=0;
        obj3.restrict(obj2.T.size()-1);
        System.out.println("\n\n\nRestrict Function Result on Expression 2:\nRestrict variable= 4\nRestrict Variable boolean value=0");
        System.out.println( "\nRestrict ROBDD Table T4 : ");
    	System.out.println("u   Index   Low   High");
    	for (int i =0; i<obj3.T.size(); i++){
            System.out.print(i+"    "+(((obj3.T.get(i)).index)+"     "+(obj3.T.get(i)).low)+"     "+((obj3.T.get(i)).high));
            System.out.println();
            }	
        	
        	
        	
        	
        	
        //SAT COUNT	Function calls
        	
        System.out.println("\n\n\nSAT COUNT for Expression 2 ROBDD=  "+obj2.count(obj2.T.size()-1));
        
        
        
        
        //ANY SAT Function calls 
        int val=obj2.anysat(obj2.T.size()-1);
        
        if(val==0) {
            System.out.println("\n\n\nNO Satisfying Assignment Exists");

        }
        else
        {
        System.out.println("\n\n\nSatisfying Assignment for Expression 2");
        
        for(int i=0;i<obj2.vars;i++)
        {
        	System.out.println("x["+i+"]="+obj2.x_val[i]);
        }
        }
        
        	
 

	};
	

	
	
	
       
	public BDD(int n)
	{
		vars = n;
		x = new int[n];
		x_val=new int[n];
		for(int i=0;i<n;i++)
                {
               
			x[i] = -1;
			x_val[i]=0;
			
                }
		this.T = new ArrayList<Node>();
		this.H = new HashMap<String, Integer>();
		
		//Initializing T
		Node zero=new Node();
		zero.addNodeVal(n,-1,-1);
		
		Node one=new Node();
		one.addNodeVal(n,-1,-1);

		
		T.add(zero);
		T.add(one);
		
        
    
	}
     
	
//Expression..................................................................................................	
	
	boolean eval()
	{
		boolean exp;
		if(count==0) {
			exp =(equiv( (and (and (equiv(x[0], x[1]), equiv ( x[2], x[3])), not(x[4]))), (or(and(equiv(x[0],x[1]),equiv(x[2],x[4])),or(x[0],x[3])))) ==1) ?true:false;

		//exp = ((and(imp(not(x[0]),equiv(1,x[1])),not(x[2]))) == 1)?true:false;
		//exp = ((and(equiv(x[0], x[2]), not (x[3])))== 1)?true:false;
		//exp = ((and (and (equiv(x[0], x[1]), equiv ( x[2], x[3])), not(x[4])))== 1)?true:false;

	

		}
		else {
			
		//exp = ((and(equiv(x[0], x[2]), not (x[4])))== 1)?true:false;
		//exp = ((or(equiv(x[0],x[1]),x[2]))== 1)?true:false;
		//exp = ((and(equiv(x[0],x[1]),equiv(x[2],x[4]))) == 1)?true:false;
		//exp = ((or(equiv(x[0],x[1]),x[1])) == 1)?true:false;
		//exp = ((or((equiv(x[0],x[1])), x[2] ))== 1)?true:false;
		//exp = (or(and(or(and(equiv(x[0],x[1]),equiv(x[2],x[4])),or(x[0],x[3])),and(equiv(x[0], x[2]), not (x[4]))),and(equiv(x[0], x[2]), not (x[3]))) == 1)?true:false;
		
		exp =(and(equiv( (or (and (equiv(x[0], x[1]), equiv ( x[2], x[3])), not(x[4]))), (or(and(equiv(x[0],x[1]),equiv(x[2],x[4])),or(x[0],x[3])))),or((equiv(x[0],x[1])), x[2] )) ==1) ?true:false;




		}
		return exp;
	}


//Expression End..................................................................................................	


	

	
	
	
//Core Logic- Build and Make Functions............................................................................
	
    public int build(int i)
    {
        if(i+1 > vars)
        {
            if(eval())
            	return 1;
            else
            	return 0;
        }
        else
        {
            x[i] = 0;
            int l = build(i + 1);
            x[i] = 1;
            int h = build(i + 1);
          
            int val= mk(i,l,h);
            return val;
        }
    }


    
    
    public int mk(int i,int l,int h)
    {
    	
    	
    	String key=Integer.toString(i)+Integer.toString(l)+Integer.toString(h)+Integer.toString((i*l*h)+(i+l+h));
        if(l == h)
        {
        	return l;
        }
        else if(H.containsKey(key))
        {
        	return H.get(key);
        }
        else
        {              

        	Node n = new Node();
            n.addNodeVal(i,l, h);
        	T.add(n);
        	int value=T.size()-1;
        	H.put(key, value);
        	return value;
        }  
        
    }
    
// Core Logic Ends here............................................................................................  
    
 
  
    
    

    
//Restrict Code Begins here........................................................................................
    
    public int restrict(int u)
    {
    	if(restrict_robdd.T.get(u).index > restrict_var) {
    		build_again(u);
    		return u;
    	}
    	else if(restrict_robdd.T.get(u).index < restrict_var) {
    		return mk(restrict_robdd.T.get(u).index,restrict(restrict_robdd.T.get(u).low),restrict(restrict_robdd.T.get(u).high));
    	}
    	else if (restrict_var_val==0){
    		return restrict(restrict_robdd.T.get(u).low); 	 		
    	}
    	else {
    		return restrict(restrict_robdd.T.get(u).high);		
    	}
    	
    }
    
    
    
	
    public void build_again(int u)
    {
        if(u==0 || u==1)
        {
        		return;       
        }
        else
        {
            int i= this.restrict_robdd.T.get(u).index;
        	int l = this.restrict_robdd.T.get(u).low;
            int h = this.restrict_robdd.T.get(u).high;
            
            
            build_again(l);
            build_again(h);
            mk(i,l,h);
        }
    }
    
//Restrict Ends here...............................................................................................
 
 
    
    
    
    
    
//SATCount.........................................................................................................
  
    public int count(int u)
    {
    	int sat_count=0;
    	if (u==0) {
    		return 0;
    	}
    	else if(u==1) {
    		return 1;
    	}
    	else {
    		double exp1=(this.T.get(this.T.get(u).low).index)-this.T.get(u).index-1;
    		double exp2=(this.T.get(this.T.get(u).high).index)-this.T.get(u).index-1;
    		
    		sat_count=(int) (Math.pow(2, exp1)*count(this.T.get(u).low)+Math.pow(2, exp2)*count(this.T.get(u).high));
    	}
		return sat_count;

    }

//SAT Count End....................................................................................................
    
    
    
    

    
    
    
    
    
//AnySat.........................................................................................................
    /*
    public void anysat(int u){
    	
    	if(u==0) {
    		return;
    	}
    	else if(u==1) {
    		return;
    	}
    	else if(this.T.get(u).low==0) {
    		this.x_val[this.T.get(u).index]=1;
    		anysat(this.T.get(u).high);
    		return;
    	}
    	else {
    		this.x_val[this.T.get(u).index]=0;
    		anysat(this.T.get(u).low);
    		return;
    	}
    	
    }
    */
    
    public int anysat(int u){
    	
    	if(u==0) {
    		return 0;
    	}
    	else if(u==1) {
    		return 1;
    	}
    	else {
    		int val=-1;
    		this.x_val[this.T.get(u).index]=1;
    		val=anysat(this.T.get(u).high);
    		if(val==1) {
    			return 1;
    		}
    		this.x_val[this.T.get(u).index]=0;
    		val=anysat(this.T.get(u).low);
    		if(val==1) {
    			return 1;
    		}
    		return 0;
    	}
    	
    	
    }
    
    
    
//AnySat Ends here...............................................................................................    
    
    
    
    
//Operators........................................................................................................
    
    
	int and(int a, int b)
	{
		int ans = 0;
		if((a == 0) || (b == 0))
			ans = 0;
		else
			ans = 1;
		return ans;
	}
		
		
		
	
	int or(int a, int b)
	{
		int ans = 0;
		if((a == 0) && (b == 0))
			ans = 0;
		else
			ans = 1;
		return ans;
	}
	
	
	
	
	int not(int a)
	{
		int ans = 0;
		if(a == 0)
			ans = 1;
		else
			ans = 0;
		return ans;
	}
	
	

	
	int imp(int a, int b)
	{
		int ans = 0;
		if(a == 1 && b == 0)
			ans = 0;
		else
			ans = 1;
		return ans;
	}
	
	
	
	
	int equiv(int a, int b)
	{
		int ans = 0;
		if(a == b)
			ans = 1;
		else
			ans = 0;
		return ans;
	}
        
	
//End of Operators......................................................................................
	

}





 class Node
{
	int low;
	int high;
	int index;
	
	Node()
	{
		this.low=-1;
		this.high=-1;
	}

	    
    public void addNodeVal(int index,int lowChild, int highChild)
    {
        this.index = index;
        this.low = lowChild;
        this.high = highChild;
    }

  
   
 }



class Apply{
	
	BDD obj1;
	BDD obj2;
	int result;
	BDD obj3;
	
	Apply(BDD object1,BDD object2){
		obj1=object1;
		obj2=object2;
		result=0;
		
		int vars1=obj1.vars;
		int vars2=obj2.vars;
		
		//Initializing the new resultant BDD 
		if(vars2>vars1) {
		obj3=new BDD(vars2);
		}
		else {
		obj3=new BDD(vars1);	
		}

		
		
	}

	
	public int app(int u,int v) {
		
		
		int u_var=obj1.T.get(u).index;
		int v_var=obj2.T.get(v).index;
		int u_low=obj1.T.get(u).low;
		int v_low=obj2.T.get(v).low;
		int u_high=obj1.T.get(u).high;
		int v_high=obj2.T.get(v).high;
		
		
		if((u==0||u==1)&&((v==0)||v==1)) {
			result= and(u,v);
			return result;
		}
		else if(u_var==v_var){
		result=obj3.mk(u_var,app(u_low,v_low),app(u_high,v_high));	
		}
		
		else if(u_var<v_var) {
		result=obj3.mk(u_var,app(u_low,v),app(u_high,v));	
		}
		else {
		result=obj3.mk(u_var,app(u,v_low),app(u,v_high));	
		}
		
			
	return result;	
	}
	
	
	int and(int a, int b)
	{
		int ans = 0;
		if((a == 0) || (b == 0))
			ans = 0;
		else
			ans = 1;
		return ans;
	}
	
	
}
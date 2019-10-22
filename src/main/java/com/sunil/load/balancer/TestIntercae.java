package com.sunil.load.balancer;

public interface TestIntercae {

	public void test();
	
	 default void newMethod(){  
	        System.out.println("Newly added default method");  
	    } 
	 
	 default void newMethod2(){  
	        System.out.println("Newly added default method");  
	    } 
	 
	 public static void newMethod3(){  
	        System.out.println("Newly added default method");  
	    }
	 
	 public static void newMethod4(){  
	        System.out.println("Newly added default method");  
	    } 
}

import java.util.Arrays;
import java.util.Scanner;

public class Trivium {
	
	String keystream_b;
    String plaintext = "";
	String newplaintext;
    int[] A_IV = new int[93];
    int[] Bkey = new int[84];
    int[] arrayC = new int[111];
    int m;
    int c1, c2, c3, c4, c5, c6;
	int keystream;
	String input;
	int[] result = new int [3000];
    
    {
    //initialize A, B, C
    for (int i = 0; i < arrayC.length; i++) {
    	if (i == 108) {
    		arrayC[i] = 1;
    	}
    	else if (i == 109) {
    		arrayC[i] = 1;
    	}
    	else if (i == 110) {
    		arrayC[i] = 1;
    	}
    	else { arrayC[i] = 0;}
    }
    
    for (int a = 0; a < 80; a++) {
    	Bkey[a] = 0;
    }
    
    for (int b = 0; b < 80; b++) {
    	if (b ==0)
    	{
    	A_IV[b] = 1;
    	}
    	else {
    	A_IV[b] = 0;
    	}
    	}
    }
    

    //ask for plain text
    String userinput() {
    	Scanner userinput = new Scanner(System.in);  // Create a Scanner object
    	System.out.println("Enter the plain text: ");
    	this.input = userinput.nextLine(); 
    	return input;
    }
    

    //convert plain text to binary   
    void stringtoBinary(String string){
        String binary = "";
		int x;
        char[] Char = string.toCharArray();
        for (int i = 0; i < Char.length; i++) {
            String tmp;
        	tmp = Integer.toBinaryString(Char[i]);
            x = tmp.length();
            if (x != 8){
                x = 8-x;
                if (x == 8){
                    binary += tmp;
                } else if (x > 0 ){
                    for (int j = 0; j < x; j++) {
                        binary += "0"; 
                    }
                } 
            }            
            binary += tmp;
        } 
        this.result = Arrays.stream(binary.split("")).mapToInt(Integer::parseInt).toArray();
      }
    
    
    //right shift
    void helper() {
    	for (int i = A_IV.length - 1; i >= 0; i--) {
    	    if(i == 0) A_IV[0] = c3;
    	    else {
    	    	A_IV[i] = A_IV[i-1];
    	    }
    	}
    	for (int i = Bkey.length - 1; i >=0; i--) {
    	    if(i == 0) Bkey[0] = c1;
    	    else {
    	    	Bkey[i] = Bkey[i-1];
    	    }
    	}
    	for (int i = arrayC.length - 1; i >=0; i--) {
    	    if(i == 0) arrayC[0] = c2;
    	    else{
				arrayC[i] = arrayC[i-1];
			}
    	}
    }
    
    
    
    //algorithm
    int generateKey() {
    	c1 = (A_IV[65] ^ A_IV[90] & A_IV[91] ^ A_IV[92] ^ Bkey[77]);
    	c2 = (Bkey[68] ^ Bkey[81] & Bkey[82] ^ Bkey[83] ^arrayC[87]);
    	c3 = (arrayC[65]^arrayC[108]& arrayC[109]^arrayC[110]^A_IV[68]);
    	
    	c4 = (A_IV[65]^A_IV[90]& A_IV[91]^A_IV[92]);
    	c5 = (Bkey[68]^Bkey[81]& Bkey[82]^Bkey[83]);
    	c6 = (arrayC[65]^arrayC[108]& arrayC[109]^arrayC[110]);
    	keystream = (c4^c5^c6); 
    	return keystream;
    }
    
    //the warm up
    int[] keystreamoutput = new int [3000];
    void warmup() {
    	for (int i = 0; i < 1153 ; i++) {
    		generateKey();
    		helper();
    	}
    	for (int i = 1153 ; i <result.length+1153 ; i++) {
    		int tmp = generateKey();
    		helper();
    		keystreamoutput [i -1153] = tmp;
    	}
    }
    
    //encryption
    int[] cypher = new int[3000];
    void encrypt(){
    	System.out.println("Ciphers: ");
    	stringtoBinary(input);
    	for (int i = 0; i < result.length; i++) {
    	        cypher[i] = (result[i]^keystreamoutput[i]);
    	        int x = cypher[i];
    	        System.out.print(x);
    	}
    	System.out.println();	
    }
    
    //decryption
    void decrypt(){
    	int[] decryption = new int[3000];	
    	System.out.println("Plaintext Decrypted: ");
    	for (int i = 0; i < cypher.length; i++) {
	        decryption[i] = (cypher[i]^keystreamoutput[i]);
    	}
    	String decstring = Arrays.toString(decryption).replaceAll(",","").replaceAll(" ","").replace("[", "").replace("]", "").trim();
    	char[] result = new char[decstring.length() / 8];                                                                                                                    
    	for (int i = 0; i < decstring.length(); i += 8) {
    	    String sub = decstring.substring(i, i + 8);
    	    result[i / 8] = (char) Integer.parseInt(sub, 2);
    	}
    	System.out.println(new String(result));
    	   }
    
    
    public static void main(String[] args) {
        Trivium tr = new Trivium();
        tr.userinput();
        long startTime = System.nanoTime();
        tr.warmup();
        System.out.println();
        tr.encrypt(); 
        long elapsedTime = System.nanoTime() - startTime;
        System.out.println("Execution time for encryption(millis): "
                + elapsedTime/1000000);
        startTime = System.nanoTime();
        tr.decrypt();
        elapsedTime = System.nanoTime() - startTime;
        System.out.println("Execution time for decryption(millis): "
                + elapsedTime/1000000);
    }
    
}

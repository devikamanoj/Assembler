import java.io.*;
import java.util.*;
public class Assembler
{
     static Scanner in = new Scanner(System.in);
    public static void main(String[] args)
    {
        /*to be done
            file read
            1st pass, add labels to symbol table.
            2nd pass....go through the instructors and change it into binary....store to a hack file
        */

        // 1ST PASS
        Assemblers.symboltable();
       
        try  
        {  
            File file=new File("D:\\S2\\EoC\\Lab\\Lab Codes\\Assembler\\src\\AsmCode.ASM");    //creates a new file instance  
            FileReader FR=new FileReader(file);   //reads the file  
            BufferedReader BR=new BufferedReader(FR);  //creates a buffering character input stream  
            StringBuffer SB=new StringBuffer();    //constructs a string buffer with no characters  
            String line;   
            while((line=BR.readLine())!=null)  
            {  
                SB.append(line);      //appends line to string buffer  
                SB.append("\n"); 
                Assemblers.LabelHandling(line);
            } 
            FR.close();    
        }            
        catch(IOException e)  
        {  
            System.out.println();
        }  
        //2ND PASS
        try  
        {  
            File file=new File("D:\\S2\\EoC\\Lab\\Lab Codes\\Assembler\\src\\AsmCode.ASM");    //creates a new file instance  
            FileReader FR=new FileReader(file);   //reads the file  
            BufferedReader BR=new BufferedReader(FR);  //creates a buffering character input stream  
            StringBuffer SB=new StringBuffer();    //constructs a string buffer with no characters  
            String line;
            File NewFile = new File("D:\\S2\\EoC\\Lab\\Lab Codes\\Assembler\\src\\MaxL.hack");
            PrintWriter out = new PrintWriter(NewFile);
          
            while((line=BR.readLine())!=null)  
            {  
                SB.append("\n"); 
                String FinalCode=  Assemblers.Check(line);
                if(!FinalCode.isEmpty())
                {
                    out.println(FinalCode);
                }
            }  
            out.close();
            FR.close();     
        }            
        catch(IOException e)  
        {  
            System.out.println();
        }  
    }    
}


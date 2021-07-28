import java.util.*;
public class Assemblers
 {
    static Hashtable<String, String> comp = new Hashtable<String, String>();
    static Hashtable<String, String> dest = new Hashtable<String, String>();
    static Hashtable<String, String> jump = new Hashtable<String, String>();
    static Hashtable<String, String> symbols = new Hashtable<String, String>();

    static int CommandLine = 0;
    static int value =16;

    static void Table()
     {
        comp.put("0", "0101010");
        comp.put("1", " 0111111");
        comp.put("-1", "0111010");
        comp.put("D", "0001100");
        comp.put("A", "0110000");
        comp.put("M", "1110000");
        comp.put("!D", "0001101");
        comp.put("!A", "0110001");
        comp.put("!M", "1110001");
        comp.put("-D", "0001111");
        comp.put("-A", "0110011");
        comp.put("-M", "1110011");
        comp.put("D+1", "0011111");
        comp.put("A+1", "0110111");
        comp.put("M+1", "1110111");
        comp.put("D-1", "0001110");
        comp.put("A-1", "0110010");
        comp.put("M-1", "1110010");
        comp.put("D+A", "0000010");
        comp.put("D+M", "1000010");
        comp.put("D-A", "0010011");
        comp.put("D-M", "1010011");
        comp.put("A-D", "0000111");
        comp.put("M-D", "1000111");
        comp.put("D&A", "0000000");
        comp.put("D&M", "1000000");
        comp.put("D|A", "0010101");
        comp.put("D|M", "1010101");

        dest.put("null", "000");
        dest.put("M", "001");
        dest.put("D", "010");
        dest.put("MD", "011");
        dest.put("A", "100");
        dest.put("AM", "101");
        dest.put("AD", "110");
        dest.put("AMD", "111");

        jump.put("null", "000");
        jump.put("JGT", "001");
        jump.put("JEQ", "010");
        jump.put("JGE", "011");
        jump.put("JLT", "100");
        jump.put("JNE", "101");
        jump.put("JLE", "110");
        jump.put("JMP", "111");

    }
    static void symboltable()
    {
        symbols.put("SP",  "000000000000000");
        symbols.put("LCL", "000000000000001");
        symbols.put("ARG", "000000000000010");
        symbols.put("THIS","000000000000011");
        symbols.put("THAT","000000000000100");
        symbols.put("R0", "000000000000000");
        symbols.put("R1",  "000000000000001");
        symbols.put("R2",  "000000000000010");
        symbols.put("R3",  "000000000000011");
        symbols.put("R4",  "000000000000100");
        symbols.put("R5",  "000000000000101");
        symbols.put("R6",  "000000000000110");
        symbols.put("R7",  "000000000000111");
        symbols.put("R8",  "000000000001000");
        symbols.put("R9",  "000000000001001");
        symbols.put("R10", "000000000001010");
        symbols.put("R11", "000000000001011");
        symbols.put("R12", "000000000001100");
        symbols.put("R13", "000000000001101");
        symbols.put("R14", "000000000001110");
        symbols.put("R15", "000000000001111");
        symbols.put("KBD", "110000000000000");
        symbols.put("SCREEN", "100000000000000");
    }
    static String A_instruction(String line)
    {
        // A-instruction is what that starts with @
        // need to make this to decimals
        // i.e., @R0=0

        String Binary_A="";
        line=line.trim();
        if(line.startsWith("@"))
        {
            line = line.substring(1);
        }
        else if (line.startsWith("("))
        {
            line = line.substring(1,line.length()-1);//brackets @ both the sides are cut
        }
        try
        {
          // integers
            int R1 = Integer.parseInt(line); // the number with R is storred as R1
            Binary_A = Integer.toBinaryString(R1); // R1 is converted to binary
        } 
        catch (NumberFormatException e)
         {
             // strings and labels
             if(symbols.get(line)!=null)
             {
                Binary_A = symbols.get(line);
             }
             else
             {
                 Binary_A=SymTabEntry(line);
             }
        }
        while (Binary_A.length() <= 15)
        {
            Binary_A = "0" + Binary_A;
        }
        return (Binary_A);
    }
    static String C_instruction(String line)
     {
        // read from file
        // then add 1+1+1+dest+comp+jump

        int Equal = line.indexOf("="); // position of 1st occurence of "="
        int SemiColon = line.indexOf(";"); // position of 1st occurence of ";"
        Table();
        if (line.indexOf("=") == -1 && line.indexOf(";") != -1)
         {
            // comp;jump
            String comp1 = line.substring(0, SemiColon);
            String jump1 = line.substring(SemiColon + 1, line.length());
            String c = comp.get(comp1);
            String j = jump.get(jump1);

            if (c == null)
            {
                c = "0000000";
            }
            if (j == null) 
            {
                j = "000";
            }

            String Binary_c = "111" + c + "000" + j;
            return (Binary_c);
        } 
        else if (line.indexOf("=") != -1 && line.indexOf(";") == -1)
        {
            // dest=comp
            String dest1 = line.substring(0, Equal); // i.e., from the index 0 to equal is dest1
            String comp1 = line.substring(Equal + 1, line.length());
            String d = dest.get(dest1); // gets the binary form table
            String c = comp.get(comp1);

            if (c == null) 
            {
                c = "0000000";
            }
            if (d == null) 
            {
                d = "000";
            }

            String Binary_c = "111" + c + d + "000";
            return (Binary_c);
        } 
        else if (line.indexOf("=") != -1 && line.indexOf(";") != -1) 
        {
            // dest=comp;jump
            String dest1 = line.substring(0, Equal); // i.e., from the index 0 to equal is dest1
            String comp1 = line.substring(Equal + 1, SemiColon);
            String jump1 = line.substring(SemiColon + 1, line.length());
            String d = dest.get(dest1); // gets the binary form table
            String c = comp.get(comp1);
            String j = jump.get(jump1);

            if (c == null) 
            {
                c = "0000000";
            }
            if (j == null) 
            {
                j = "000";
            }
            if (d == null) 
            {
                d = "000";
            }

            String Binary_c = 111 + c + d + j;
            return (Binary_c);
        } 
        else 
        {
            return null;
        }
    }
    
    static String SymTabEntry(String NewVariable)
    {
        // if not in symboltable need to add those to the symbol table
        String Value = Integer.toBinaryString(value);
        symbols.put(NewVariable,Value);
        value++;
        return Value;
        
    }
    static String Check(String line) 
    {
        String Binary="";
        String NewString = "";
        
        for (int i = 0; i < line.length(); i++) // whitespace handling
        {
           char CurrentChar = line.charAt(i);
           if(CurrentChar=='/')
            {
                break;
            }
            if (CurrentChar != ' ')
            {
                NewString = NewString + CurrentChar;    
            }
        }
        line=NewString.trim();
        if (line.startsWith("(")) 
        {

        } 
        else if (line.startsWith("@")) 
        {
            Binary = A_instruction(line);
            CommandLine++;
        } 
        else 
        {
            Binary = C_instruction(line);
            CommandLine++;
        }
        if (line.startsWith("//"))
        {

        }
        return Binary;
    }

    static void LabelHandling(String line) 
    {
        line = line.trim();
        if(!line.isEmpty())
        {
            if (line.startsWith("//") || line.isEmpty())
            {
                
            }
            else if (line.startsWith("(")) // (code) (loop)
            {
                line = line.substring(1,line.length()-1);
                String Binary_CL = Integer.toBinaryString(CommandLine);
                symbols.put(line, Binary_CL);
            }
            else
            {
                CommandLine++;
            }
        }
    }
}

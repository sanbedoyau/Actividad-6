package Contactos;

import java.io.File;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.lang.NumberFormatException;

public class Func {
    String File;
    
    public Func(String File){
        this.File = File;
    }
    
    public String crear(String NuevoCont, long NuevoContNum){
        try{
            if (NuevoContNum == 0){
                throw new NumberFormatException("Datos incorrectos");
        }
            try{
                String Name, Name_Number;
                long Num;
                File file = new File(File);

                if (!file.exists()) {
                    file.createNewFile();
                }

                RandomAccessFile RAF = new RandomAccessFile(file,"rw");
                boolean found = false;

                while (RAF.getFilePointer() < RAF.length()){
                    Name_Number = RAF.readLine();
                    String[] split = Name_Number.split(": ");
                    Name = split[0];
                    Num = Long.parseLong(split[1]);
                    if (NuevoCont.equals(Name) || NuevoContNum == Num){
                        found = true;
                        break;
                    }
                }
                if (!found){
                    Name_Number = NuevoCont + ": " + String.valueOf(NuevoContNum);
                    RAF.writeBytes(Name_Number);
                    RAF.writeBytes(System.lineSeparator());
                    RAF.close();
                    return "Contacto añadido con éxito";
                }

                else{
                    RAF.close();
                    return "Contacto existente";
                }
            }
            catch(IOException ioe){return String.valueOf(ioe).split(": ")[1];}
        }
        catch (NumberFormatException nef){return String.valueOf(nef).split(": ")[1];}
    }
    
    public String leer(){
        try{
            String Name, Name_Number, Name_Numa = "";
            long Num;
            File file = new File(File);
            if (!file.exists()) {
                file.createNewFile();
            }
            RandomAccessFile RAF = new RandomAccessFile(file,"rw");
            while (RAF.getFilePointer() < RAF.length()){
                Name_Number = RAF.readLine();
                String[] split = Name_Number.split(": ");
                Name = split[0];
                Num = Long.parseLong(split[1]);
                Name_Numa += Name +": " + String.valueOf(Num) + "\n";
            }
            RAF.close();
            if (Name_Numa.equals("") || Name_Numa.equals("\n")){
                return "No tienes contactos guardados";
            }
            return Name_Numa;
        }
        catch(IOException ioe){return String.valueOf(ioe).split(": ")[1];}
        catch (NumberFormatException nef){return String.valueOf(nef).split(": ")[1];}
    }
    
    public String actualizar(String Contacto, long NuevoNumCont){
        try{
            if (NuevoNumCont == 0){
                throw new NumberFormatException("Datos incorrectos");
            }
            try{
                String Name, Name_Number;
                long Num;

                File file = new File(File);

                if (!file.exists()) {
                    file.createNewFile();
                }

                RandomAccessFile RAF = new RandomAccessFile(file,"rw");
                boolean found = false;

                while (RAF.getFilePointer() < RAF.length()){
                    Name_Number = RAF.readLine();
                    String[] split = Name_Number.split(": ");
                    Name = split[0];
                    Num = Long.parseLong(split[1]);

                    if (Name.equals(Contacto) || NuevoNumCont == Num){
                        found = true;
                        break;
                    }
                }
                if (found){
                    File tmpfile = new File("Tmp.txt");
                    RandomAccessFile tmpRAF = new RandomAccessFile(tmpfile,"rw");
                    RAF.seek(0);

                    while (RAF.getFilePointer() < RAF.length()){
                        Name_Number = RAF.readLine();
                        Name = Name_Number.split(": ")[0];

                        if (Name.equals(Contacto)){
                            Name_Number = Contacto + ": " + String.valueOf(NuevoNumCont);
                        }

                        tmpRAF.writeBytes(Name_Number);
                        tmpRAF.writeBytes(System.lineSeparator());
                    }
                    RAF.seek(0);
                    tmpRAF.seek(0);

                    while (tmpRAF.getFilePointer() < tmpRAF.length()) {
                        RAF.writeBytes(tmpRAF.readLine());
                        RAF.writeBytes(System.lineSeparator());
                    }

                    RAF.setLength(tmpRAF.length());
                    tmpRAF.close();
                    RAF.close();
                    tmpfile.delete();

                    return "Contacto actualizado";
                }
                else{
                    RAF.close();
                    return "Contacto inexistente";
                }
            }
            catch(IOException ioe){return String.valueOf(ioe).split(": ")[1];}
        }
        catch(NumberFormatException nef){return String.valueOf(nef).split(": ")[1];}   
    }
    
    public String borrar(String Contacto){
        try{
            String Name, Name_Number;
            long Num;
            
            File file = new File(File);
            
            if (!file.exists()) {
                file.createNewFile();
            }
            
            RandomAccessFile RAF = new RandomAccessFile(file,"rw");
            boolean found = false;
            
            while (RAF.getFilePointer() < RAF.length()){
                Name_Number = RAF.readLine();
                String[] split = Name_Number.split(": ");
                Name = split[0];
                Num = Long.parseLong(split[1]);
                
                if (Name.equals(Contacto)){
                    found = true;
                    break;
                }
            }
            if (found){
                File tmpfile = new File("Tmp.txt");
                RandomAccessFile tmpRAF = new RandomAccessFile(tmpfile,"rw");
                RAF.seek(0);
                
                while (RAF.getFilePointer() < RAF.length()){
                    Name_Number = RAF.readLine();
                    Name = Name_Number.split(": ")[0];
                    
                    if (Name.equals(Contacto)){
                        continue;
                    }
                    
                    tmpRAF.writeBytes(Name_Number);
                    tmpRAF.writeBytes(System.lineSeparator());
                }
                RAF.seek(0);
                tmpRAF.seek(0);
                
                while (tmpRAF.getFilePointer() < tmpRAF.length()) {
                    RAF.writeBytes(tmpRAF.readLine());
                    RAF.writeBytes(System.lineSeparator());
                }
                
                RAF.setLength(tmpRAF.length());
                tmpRAF.close();
                RAF.close();
                tmpfile.delete();
                
                return "Contacto eliminado";
            }
            else{
                RAF.close();
                return "Contacto inexistente";
            }
        }
        catch(IOException ioe){return String.valueOf(ioe).split(": ")[1];}
        catch (NumberFormatException nef){return String.valueOf(nef).split(": ")[1];}
    }
    
    public static boolean isNumeric(String str) { 
        try {  
            Double.parseDouble(str);  
            return true;
  }
        catch(NumberFormatException e){  
            return false;  
  }  
}
}

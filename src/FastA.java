import java.io.*;
import java.util.ArrayList;

public class FastA {
    //FastA()
    ArrayList<String> sequences = new ArrayList<String>();
    ArrayList<String> headers = new ArrayList<String>();

    public int getLength(){
        return sequences.size();
    }
    public void read(String file_path) throws Exception{
        File file = new File(file_path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        Integer line_num = 0;
        boolean line_shoud_be_header = true;
        while( (st = br.readLine()) != null){
            if(st.length() > 0) {
                if (st.charAt(0) == '>') {
                    headers.add(st);
                    if (!line_shoud_be_header) {
                        throw new Exception("line with number " + line_num.toString() +
                                "is supposed not to be a header, but does start with >");
                    }
                } else {
                    sequences.add(st);
                    if (line_shoud_be_header) {
                        throw new Exception("line with number " + line_num.toString() +
                                "is supposed to be a header, but does not start with > ");
                    }
                }

                line_shoud_be_header = !line_shoud_be_header;
            }
            line_num++;
        }
        br.close();
    }

    public void write(String file_path) throws Exception{
        BufferedWriter writer = new BufferedWriter(new FileWriter(file_path));
        for(int i = 0; i < this.getLength(); ++i){
            writer.write(headers.get(i));
            writer.newLine();
            writer.write(sequences.get(i));
            writer.newLine();
        }
        writer.close();
    }

    public String getHeader(int i) throws Exception{
        if(headers.size() > i){
            return headers.get(i);
        }else{
            throw new Exception("there are less than "+ i + "headers");
        }
    }

    public String getSequence(int i) throws Exception{
        if(sequences.size() > i){
            return sequences.get(i);
        }else{
            throw new Exception("there are less than "+ i + "sequences");
        }
    }

    public void add(String header, String sequence){
        headers.add(header);
        sequences.add(sequence);
    }

    public static void main(String[] args) throws Exception{
        FastA file = new FastA();
        file.read("/Users/klara/Downloads/dna_01.fasta");
        for(String s: file.headers){
            System.out.println(s);
        }
        for(String s: file.sequences){
            System.out.println(s);
        }
        file.write("/Users/klara/Downloads/dna_01_test.fasta");
    }
}

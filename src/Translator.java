import java.io.BufferedWriter;
import java.io.FileWriter;

public class Translator {
    String[] readingFrames;

    public Translator(String sequence) throws Exception {
        readingFrames = new String[6];
        this.translateSequence(sequence);
    }

    public void translateSequence(String sequence) throws Exception {
        for (int i = 1; i <= 3; ++i) {
            readingFrames[i -1] = translate(sequence, i);
        }

        for (int i = -1; i >= -3; --i) {
            readingFrames[-i +2] = translate(sequence, i);
        }
    }

    public String translate(String sequence, int frame) throws Exception {
        String result = new String();
        if ((frame > 0) && (frame <= 3)) {
            for (int i = frame - 1; i < sequence.length() - 2; i += 3) {
                String substr = sequence.substring(i, i + 3);
                result += translateTriplet(substr);
            }
        } else if (frame < 0 && (frame >= -3)) {
            StringBuilder sb = new StringBuilder(sequence);
            sb.reverse();
            String reverse = sb.toString();
            for (int i = -frame - 1; i < reverse.length() - 2; ++i) {
                String substr = reverse.substring(i, i + 3);
                result += translateTriplet(substr);
            }
        } else {
            throw new Exception("not a frame number");
        }

        return result;
    }

    public String[] getReadingFrames(){
        return readingFrames;
    }

    public String translateTriplet(String triplet) {
        switch (triplet) {
            case "GCA":
                return "A";
            case "GCC":
                return "A";
            case "GCG":
                return "A";
            case "GCT":
                return "A";
            case "TGC":
                return "C";
            case "TGT":
                return "C";
            case "GAC":
                return "D";
            case "GAT":
                return "D";
            case "GAA":
                return "E";
            case "GAG":
                return "E";
            case "TTC":
                return "F";
            case "TTT":
                return "F";
            case "GGA":
                return "G";
            case "GGC":
                return "G";
            case "GGG":
                return "G";
            case "GGT":
                return "G";
            case "CAC":
                return "H";
            case "CAT":
                return "H";
            case "ATA":
                return "I";
            case "ATC":
                return "I";
            case "ATT":
                return "I";
            case "AAA":
                return "K";
            case "AAG":
                return "K";
            case "CTA":
                return "L";
            case "CTC":
                return "L";
            case "CTG":
                return "L";
            case "CTT":
                return "L";
            case "TTA":
                return "L";
            case "TTG":
                return "L";
            case "ATG":
                return "M";
            case "AAC":
                return "N";
            case "AAT":
                return "N";
            case "CCA":
                return "P";
            case "CCC":
                return "P";
            case "CCG":
                return "P";
            case "CCT":
                return "P";
            case "CAA":
                return "Q";
            case "CAG":
                return "Q";
            case "AGA":
                return "R";
            case "AGG":
                return "R";
            case "CGA":
                return "R";
            case "CGC":
                return "R";
            case "CGG":
                return "R";
            case "CGT":
                return "R";
            case "AGC":
                return "S";
            case "AGT":
                return "S";
            case "TCA":
                return "S";
            case "TCC":
                return "S";
            case "TCG":
                return "S";
            case "TCT":
                return "S";
            case "ACA":
                return "T";
            case "ACC":
                return "T";
            case "ACG":
                return "T";
            case "ACT":
                return "T";
            case "GTA":
                return "V";
            case "GTC":
                return "V";
            case "GTG":
                return "V";
            case "GTT":
                return "V";
            case "TGG":
                return "W";
            case "TAC":
                return "Y";
            case "TAT":
                return "Y";
            case "TGA":
                return "";
            case "TAA":
                return "";
            case "TAG":
                return "";
            default:
                return "-1";
        }

    }

    public static void main(String[] args) throws Exception{
        if(args.length != 2){
            System.out.println("the application needs exactly two arguments: " +
                    "1. path of input file, 2. path of output file");
        }else {
            FastA file = new FastA();
            // "/Users/klara/gbi/data/dna_01.fasta"
            file.read(args[0]);
            //"/Users/klara/gbi/output/dna_readingframes.txt";
            String path = args[1];
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            for (int i = 0; i < file.getLength(); ++i) {
                Translator translator = new Translator(file.getSequence(i));
                String[] reading_frames = translator.getReadingFrames();
                for (int k = 0; k < 6; ++k) {
                    if (k <= 2) {
                        String in = String.valueOf(k + 1);
                        writer.write("+" + in + ":");
                    } else {
                        String in = String.valueOf(k - 2);
                        writer.write("-" + in + ":");
                    }
                    writer.newLine();
                    writer.write(reading_frames[k]);
                    writer.newLine();
                }
            }
            writer.close();
        }
    }
}
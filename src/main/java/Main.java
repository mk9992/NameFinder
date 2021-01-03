import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinder;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args)throws IOException {
        Document belge = null;
        try {
            belge = Jsoup.connect(args[0]).get();
        } catch (IOException harici) {

            harici.printStackTrace();

        }
        Elements ilk = belge.select("body");
        String firststring = "";

        for (Element e: ilk){

            firststring+= e.text();

        }

        String jeton[];

        try (InputStream modelin=Main.class.getClassLoader().getResourceAsStream("en-token.bin")){

            TokenizerModel modela=new TokenizerModel(modelin);
            Tokenizer tokenizerme=new TokenizerME(modela);
            jeton = tokenizerme.tokenize(firststring);

        }

        Span[] isim;

        try(InputStream modelin=Main.class.getClassLoader().getResourceAsStream("en-ner-person.bin")){

            TokenNameFinderModel isimbulan=new TokenNameFinderModel(modelin);
            NameFinderME isimbulucu=new NameFinderME(isimbulan);
            isim = isimbulucu.find(jeton);
            for(Span çek:isim) {

                for(int i = çek.getStart();i<çek.getEnd();i++){
                    System.out.print(jeton[i]+" ");

                }
                System.out.println();

            }
        }

    }
}

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Properties;

/**
 * Created by cliff on 05/10/2016.
 */
public class Parse {


    String OutputFolder;
    String NowD;
    model model;

    public Parse(String POutputFolder, String PNowD, model mod)
    {
        OutputFolder = POutputFolder;
        NowD = PNowD;
        model = mod;
    }

    public void run (String fileLoc)
    {
        try
        {
            model.addIndividual("DocStruct", "doc", "doc");

            File f = new File(fileLoc);
            Properties props = new Properties();
            props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse");

            StanfordCoreNLP nlp = new StanfordCoreNLP(props);

            String everything = "";
            BufferedReader br = new BufferedReader(new FileReader(f));
            try {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    //sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                everything = sb.toString();
                System.out.print(everything);

            } finally {
                br.close();
            }

            Annotation anno = new Annotation(everything);
            nlp.annotate(anno);
            List<CoreMap> sentences = anno.get(CoreAnnotations.SentencesAnnotation.class);
            List<CoreMap> paragraphs = anno.get(CoreAnnotations.ParagraphsAnnotation.class);

            int parac = 0;
            for(CoreMap paragraph : paragraphs)
            {
                parac++;
                String paracn = "p" + parac;
                model.addIndividual("Gate", "paragraph", paracn);
                model.addObjectProperty("DocStruct", "hasParagraph", "doc", paracn);

                String ss = paragraph.toString();
                String[] sss = ss.split(".");




            }

            int id = 0;
            int sc = 0;
            int wc = 0;
            int np = 1;
            for(CoreMap sentence : sentences)
            {
                id++;
                sc++;
                String sn = "s" + sc;
                String sp = "s" + Integer.toString(sc-1);
                String se = "s" + Integer.toString(sc+1);

                model.addDatatypeProperty("Gate", "hasID", sn, String.valueOf(id), "int");
                model.addIndividual("Gate", "Sentence", sn);

                if(sc==1)
                {
                    model.addObjectProperty("DocStruct", "hasFirstSentence", "doc", sn);
                }
                else
                {
                    model.addObjectProperty("DocStruct", "hasPreviousSentence", sn, sp);
                }


                if(sc==sentences.size())
                {
                    model.addObjectProperty("DocStruct", "hasLastSentence", "doc", sn);
                }
                else
                {
                    model.addObjectProperty("DocStruct", "hasNextSentence", sn, se);
                }

                String Sx = sentence.toString();
                String[] words = Sx.split(" ");

                model.addDatatypeProperty("Gate", "hasStartNode", sn, String.valueOf(np), "int");

                int wc1 = 0;
                for(String w : words)
                {

                    wc++;
                    wc1++;
                    w = w.replace(":", "");
                    w = w.replace(";", "");
                    w = w.replace(",", "");
                    w = w.replace(".", "");
                    w = w.replace("?", "");
                    String wn = "w" + wc;
                    model.addIndividual("Gate", "word", wn);
                    model.addObjectProperty("DocStruct", "hasWord", sn, wn);
                    model.addDatatypeProperty("Gate", "hasString", wn, String.valueOf(w), "str");

                    id++;
                    model.addDatatypeProperty("Gate", "hasID", wn, String.valueOf(id), "int");

                    model.addDatatypeProperty("Gate", "hasStartNode", wn, String.valueOf(np), "int");
                    np = np + w.length();
                    model.addDatatypeProperty("Gate", "hasEndNode", wn, String.valueOf(np), "int");


                    if(wc1==1)
                    {
                        model.addObjectProperty("DocStruct", "hasFirstWord", sn, wn);
                    }
                    if(wc1==words.length)
                    {
                        model.addDatatypeProperty("Gate", "hasEndNode", sn, String.valueOf(np-1), "int");
                        model.addObjectProperty("DocStruct", "hasLastWord", sn, wn);
                    }

                    model.addDatatypeProperty("DocStruct", "hasFirstCharacter", wn, w.substring(0, 1), "str");
                    //model.addDatatypeProperty("DocStruct", "hasLastCharacter", wn, w.substring(w.length()), "str");

                }

            }

        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.toString() + " - " + e.getMessage());
            System.out.println(e.toString());
        }

    }


}

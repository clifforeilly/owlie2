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

    public Parse(String POutputFolder, String PNowD)
    {
        OutputFolder = POutputFolder;
        NowD = PNowD;
    }

    public void run (String fileLoc)
    {
        try
        {
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

            int sc=0;
            for(CoreMap sentence : sentences)
            {
                sc++;


            }

        }
        catch (Exception ee)
        {
            ee.printStackTrace();
        }

    }


}

/**
 * Created by cliff on 26/09/2016.
 */


import static java.lang.System.clearProperty;
import static java.lang.System.in;
import static org.junit.Assert.*;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.search.Searcher.*;
import static org.semanticweb.owlapi.vocab.OWLFacet.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.junit.Ignore;
import org.junit.Test;
//import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.io.StreamDocumentTarget;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.*;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasoner;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.search.Filters;
import org.semanticweb.owlapi.util.*;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import com.google.common.base.Optional;

import uk.ac.manchester.cs.owlapi.modularity.ModuleType;
import uk.ac.manchester.cs.owlapi.modularity.SyntacticLocalityModuleExtractor;

public class kontrol {

    static String folderbase;
    static String OutputFolder;
    static String NowD;
    static String inputFileLoc;
    static model model;

    public kontrol() {

        try
        {
            //folderbase = "C:" + File.separator + "Users" + File.separator + "co17" + File.separator + "LocalStuff" + File.separator + "MyStuff" + File.separator + "Projects" + File.separator + "owlie2" + File.separator + "LaRheto" + File.separator;
            folderbase = "D:" + File.separator + "LaRheto" + File.separator;

            OutputFolder = folderbase + "Outputs";
            System.out.println("... OutputFolder=" + OutputFolder);

            NowD = GetNow();
            System.out.println("... Now=" + NowD);

            inputFileLoc = folderbase + "Inputs" + File.separator + "english-kjv-2.txt";
            //english-kjv-2.txt
            //aru1

            System.out.println("... FileLoc=" + inputFileLoc);

            model = new model(folderbase, NowD);
            Parse parse = new Parse(OutputFolder, NowD, model);
            System.out.println("... running Parse");
            parse.run(inputFileLoc);

            model.outputToFile();

        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getStackTrace());
            System.out.println(e.toString());

        }

    }

    public static String GetNow()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String n = sdf.format(new Date());
        return n;
    }


    public static void main (String[] args)
    {
        System.out.println("starting Control");

        kontrol k = new kontrol();

        System.out.println("ending Control");
    }

}

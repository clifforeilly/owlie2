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


    static String OutputFolder;
    static String NowD;
    static String inputFileLoc;

    public kontrol() {

        try {

            String URLDocStruct = "http://repositori.com/sw/onto/DocStruct.owl";

            OWLOntologyManager owlmgr = OWLManager.createOWLOntologyManager();
            IRI ontDocStruct = IRI.create(URLDocStruct);
            OWLOntology ont = owlmgr.loadOntology(ontDocStruct);

            Set<OWLAxiom> ax = ont.getAxioms();

            for (OWLAxiom a : ax) {
                System.out.println(a.toString());
            }


            OWLDataFactory fac = owlmgr.getOWLDataFactory();
            String base = "http://repositori.com/sw/onto/DocStruct.owl#";
            //prefix = ont.getOntologyID().getOntologyIRI() + "#";
            DefaultPrefixManager pm = new DefaultPrefixManager(null, null, ontDocStruct.toString());

            OWLClass doc = fac.getOWLClass("#Doc", pm);

            String u="D:\\LaRheto\\Outputs\\testo.owl";
            File f = new File(u);

            OWLNamedIndividual i = fac.getOWLNamedIndividual("#d", pm);
            OWLAxiom ax1 = fac.getOWLClassAssertionAxiom(doc, i);
            AddAxiom addax1 = new AddAxiom(ont, ax1);
            owlmgr.applyChange(addax1);
            owlmgr.saveOntology(ont, IRI.create(f.toURI()));

        } catch (Exception e) {
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


    public static void main (String[] args) {

        kontrol k = new kontrol();


        System.out.println("starting Control");

        OutputFolder = "D:\\LaRheto\\Outputs";
        System.out.println("... OutputFolder=" + OutputFolder);

        NowD = GetNow();
        System.out.println("... Hash=" + NowD);

        inputFileLoc = "D:\\LaRheto\\Inputs\\english-kjv-2.txt";
        System.out.println("... FileLoc=" + inputFileLoc);



        Parse parse = new Parse(OutputFolder, NowD);
        System.out.println("... running Parse");
        parse.run(inputFileLoc);



    }

}

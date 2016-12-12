//import org.mindswap.pellet.jena.PelletReasoner;
//import org.mindswap.pellet.jena.PelletReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.PrefixDocumentFormat;
import org.semanticweb.owlapi.io.OWLOntologyLoaderMetaData;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.OWLOntologyXMLNamespaceManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.InferredOntologyGenerator;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.clarkparsia.pellet.owlapiv3.PelletReasoner;
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.TargetSWRLRuleEngine;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.core.IRIResolver;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.exceptions.SWRLRuleEngineBridgeException;
import org.swrlapi.factory.*;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.owl2rl.OWL2RLNames;
import org.swrlapi.owl2rl.OWL2RLPersistenceLayer;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import uk.ac.manchester.cs.owl.owlapi.SWRLIndividualArgumentImpl;
import uk.ac.manchester.cs.owl.owlapi.SWRLRuleImpl;

import javax.annotation.Nonnull;

/**
 * Created by co17 on 20/10/2016.
 */
public class model {

    static String NowD;
    static String folderbase;
    String baseIRI;
    OWLOntologyManager om;
    OWLOntology ont;
    OWLDataFactory df;
    DefaultPrefixManager pm;
    OWLOntologyManager om_DocStruct;
    OWLOntology ont_DocStruct;
    OWLDataFactory fac_DocStruct;
    DefaultPrefixManager pm_DocStruct;
    OWLOntologyManager om_LassRhet;
    OWLOntology ont_LassRhet;
    OWLDataFactory fac_LassRhet;
    DefaultPrefixManager pm_LassRhet;
    OWLOntologyManager om_Gate;
    OWLOntology ont_Gate;
    OWLDataFactory fac_Gate;
    DefaultPrefixManager pm_Gate;
    OWLOntologyManager om_RhetDev;
    OWLOntology ont_RhetDev;
    OWLDataFactory fac_RhetDev;
    DefaultPrefixManager pm_RhetDev;
    OWLClass DocStruct_doc;
    OWLClass Gate_word;
    OWLClass Gate_sentence;
    OWLClass Gate_paragraph;
    OWLClass RhetDev_RhetoricalDevice;
    OWLObjectProperty hasParagraph;
    OWLObjectProperty hasSentence;
    OWLObjectProperty hasNextWord;
    OWLObjectProperty hasFirstWord;
    OWLObjectProperty hasNextSentence;
    OWLObjectProperty hasRhetoricalDevice;
    OWLDataProperty hasString;
    OWLDataProperty hasStartNode;
    OWLDataProperty hasEndNode;
    OWLIndividual RhetDev_Anaphora;

    public OWLClass getClassType(String type)
    {
        OWLClass r = null;

        if(type.equals("doc"))
        {
            r = DocStruct_doc;
        }
        else if(type.equals("word"))
        {
            r = Gate_word;
        }
        else if(type.equals("Sentence"))
        {
            r = Gate_sentence;
        }
        else if(type.equals("paragraph"))
        {
            r = Gate_paragraph;
        }

        return r;
    }


    public model(String fb, String PNow){

        try {

            NowD=PNow;
            folderbase = fb;

            baseIRI = "http://repositori.com/sw/onto/" + NowD + ".owl";
            IRI newIRI = IRI.create(baseIRI);
            om = OWLManager.createOWLOntologyManager();
            ont = om.createOntology(newIRI);
            df = om.getOWLDataFactory();
            pm = new DefaultPrefixManager(); //null, null, ont.toString()
            pm.setPrefix("DocStruct:", "http://repositori.com/sw/onto/DocStruct.owl#");
            pm.setPrefix("gate:", "http://repositori.com/sw/onto/gate.owl#");
            //OWLDocumentFormat odf = new OWLXMLDocumentFormat();
            //OWLOntologyXMLNamespaceManager onm = new OWLOntologyXMLNamespaceManager(ont, odf);
            //onm.setPrefix("DocStruct:", "http://repositori.com/sw/onto/DocStruct.owl#");

            String URLDocStruct = "http://repositori.com/sw/onto/DocStruct.owl";
            om_DocStruct = OWLManager.createOWLOntologyManager();
            IRI ontDocStruct = IRI.create(URLDocStruct);
            ont_DocStruct = om_DocStruct.loadOntology(ontDocStruct);
            fac_DocStruct = om_DocStruct.getOWLDataFactory();
            pm_DocStruct = new DefaultPrefixManager(null, null, ontDocStruct.toString());

            String URLGate = "http://repositori.com/sw/onto/gate.owl";
            om_Gate = OWLManager.createOWLOntologyManager();
            IRI ontGate = IRI.create(URLGate);
            ont_Gate = om_Gate.loadOntology(ontGate);
            fac_Gate = om_Gate.getOWLDataFactory();
            pm_Gate = new DefaultPrefixManager(null, null, ontGate.toString());

            String URLLassRhet = "http://repositori.com/sw/onto/LassoingRhetoric.owl";
            om_LassRhet = OWLManager.createOWLOntologyManager();
            IRI ontLassRhet = IRI.create(URLLassRhet);
            ont_LassRhet = om_LassRhet.loadOntology(ontLassRhet);
            fac_LassRhet = om_LassRhet.getOWLDataFactory();
            pm_LassRhet = new DefaultPrefixManager(null, null, ontLassRhet.toString());

            String URLRhetDev = "http://www.repositori.com/sw/onto/RhetoricalDevices.owl";
            om_RhetDev = OWLManager.createOWLOntologyManager();
            IRI ontRhetDev = IRI.create(URLRhetDev);
            ont_RhetDev = om_RhetDev.loadOntology(ontRhetDev);
            fac_RhetDev = om_RhetDev.getOWLDataFactory();
            pm_RhetDev= new DefaultPrefixManager(null, null, ontRhetDev.toString());

            setupClasses();

            String DocStruct_base = "http://repositori.com/sw/onto/DocStruct.owl#";
            String Gate_base = "http://repositori.com/sw/onto/gate.owl#";
            String LassRhet_base = "http://repositori.com/sw/onto/LassoingRhetoric.owl#";

        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.toString() + " - " + e.getMessage());
            System.out.println(e.toString());
        }
    }

    public void addIndividual(String onto, String owclass, String value)
    {
        OWLAxiom ax1 = null;
        if(onto.equals("DocStruct"))
        {
            OWLNamedIndividual i1 = df.getOWLNamedIndividual(IRI.create("#" + value));
            ax1 = df.getOWLClassAssertionAxiom(getClassType(owclass), i1);
        }
        else if(onto.equals("Gate"))
        {
            OWLNamedIndividual i1 = df.getOWLNamedIndividual(IRI.create("#" + value));
            ax1 = df.getOWLClassAssertionAxiom(getClassType(owclass), i1);
        }

        AddAxiom addax1 = new AddAxiom(ont, ax1);
        om.applyChange(addax1);
    }

    public void addObjectProperty(String onto, String owProp, String domain, String range)
    {
        OWLObjectPropertyAssertionAxiom ax1 = null;

        if(onto.equals("DocStruct"))
        {
            OWLNamedIndividual id = df.getOWLNamedIndividual(IRI.create("#" + domain));
            OWLNamedIndividual ir = df.getOWLNamedIndividual(IRI.create("#" + range));
            OWLObjectProperty p = fac_DocStruct.getOWLObjectProperty("#" + owProp, pm_DocStruct);
            ax1 = df.getOWLObjectPropertyAssertionAxiom(p, id, ir);
        }
        else if(onto.equals("Gate"))
        {
            OWLNamedIndividual id = df.getOWLNamedIndividual(IRI.create("#" + domain));
            OWLNamedIndividual ir = df.getOWLNamedIndividual(IRI.create("#" + range));
            OWLObjectProperty p = fac_Gate.getOWLObjectProperty("#" + owProp, pm_Gate);
            ax1 = df.getOWLObjectPropertyAssertionAxiom(p, id, ir);
        }

        AddAxiom addax1 = new AddAxiom(ont, ax1);
        om.applyChange(addax1);
    }

    public void addDatatypeProperty(String onto, String owProp, String domain, String value, String datatypetype)
    {
        OWLDataPropertyAssertionAxiom ax1 = null;

        if(onto.equals("DocStruct"))
        {
            OWLDatatype odt = null;
            OWLLiteral ol = null;

            if(datatypetype.equals("int"))
            {
                odt = df.getOWLDatatype(OWL2Datatype.XSD_INTEGER.getIRI());
                ol = df.getOWLLiteral(value, odt);
            }
            if(datatypetype.equals("str"))
            {
                odt = df.getOWLDatatype(OWL2Datatype.XSD_STRING.getIRI());
                ol = df.getOWLLiteral(value, odt);
            }

            OWLNamedIndividual id = df.getOWLNamedIndividual(IRI.create("#" + domain));
            OWLDataProperty p = fac_DocStruct.getOWLDataProperty(IRI.create("#" + owProp));
            ax1 = df.getOWLDataPropertyAssertionAxiom(p, id, ol);
        }
        else if(onto.equals("Gate"))
        {
            OWLDatatype odt = null;
            OWLLiteral ol = null;

            if(datatypetype.equals("int"))
            {
                odt = df.getOWLDatatype(OWL2Datatype.XSD_INTEGER.getIRI());
                ol = df.getOWLLiteral(value, odt);
            }
            if(datatypetype.equals("str"))
            {
                odt = df.getOWLDatatype(OWL2Datatype.XSD_STRING.getIRI());
                ol = df.getOWLLiteral(value, odt);
            }

            OWLNamedIndividual id = df.getOWLNamedIndividual(IRI.create("#" + domain));
            OWLDataProperty p = fac_Gate.getOWLDataProperty(IRI.create("#" + owProp));
            ax1 = df.getOWLDataPropertyAssertionAxiom(p, id, ol);
        }

        AddAxiom addax1 = new AddAxiom(ont, ax1);
        om.applyChange(addax1);
    }


    public void reasonPellet()
    {
        PelletReasoner reasoner = PelletReasonerFactory.getInstance().createReasoner(ont);
        reasoner.getKB().realize();
        reasoner.getKB().printClassTree();
        InferredOntologyGenerator gen = new InferredOntologyGenerator(reasoner);
        gen.fillOntology(df, ont);
    }


    public void runSWRL()
    {
        try
        {

            //SWRLClassAtom at1 = fac_LassRhet.getSWRLClassAtom(IRI.create("#" + ))
            //SWRLRule r1 = fac_LassRhet.getSWRLRule(IRI.create("#lassoAnaphora1"));

            //SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ont_LassRhet);
            //ruleEngine.infer();
            //System.out.println(ruleEngine.toString());

            //SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ont);
            //SQWRLResult result = queryEngine.runSQWRLQuery("q1", "swrlb:add(?x, 2, 2) -> sqwrl:select(?x)");

            //if (result.next())
            //  System.out.println("x: " + result.getLiteral("x").getInt());

            //SWRLRuleEngine sre = SWRLAPIFactory.createSWRLRuleEngine(ont);
            //sre.infer();

            //SQWRLQueryEngine sqe = SWRLAPIFactory.createSQWRLQueryEngine(ont);
            //SQWRLResult r = sqe.runSQWRLQuery("q1", "http://repositori.com/sw/onto/DocStruct.owl:Doc(?h) ^ hasParagraph(?h, ?i) ^ hasSentence(?i, ?z) ^ word(?x) ^ hasNextWord(?x, ?y) ^ Sentence(?z) ^ hasFirstWord(?z,?x) ^ word(?a) ^ hasNextWord(?a, ?b) ^ Sentence(?c) ^ hasFirstWord(?c, ?a) ^ hasNextSentence(?z, ?c) ^ hasString(?x, ?d) ^ hasString(?y, ?e) ^ hasString(?a, ?f) ^ hasString(?b, ?g) ^ swrlb:equal(?d, ?f) ^ swrlb:equal(?e, ?g) ^ hasStartNode(?x, ?j) ^ hasEndNode(?b, ?k) -> hasRhetoricalDevice(?h, Anaphora) ^ hasStartNode(Anaphora, ?j) ^ hasEndNode(Anaphora, ?k)");

            //SQWRLResult r = sqe.runSQWRLQuery("q1", "gate:hasWord(?s1, ?w1) ^ gate:hasWord(?s2, ?w2) ^ hasString(?w1, ?st1) ^ hasString(?w2, ?st2) ^ swrlb:equals(?st1, ?st2) -> hasNextSentence(?s1, ?s2)");

            //if(r.next())
           // {
              //  System.out.println("Name" + r.toString());
           // }

            //OWLClass cdoc = df.getOWLClass("#Doc", pm);
            //OWLClass cSentence = df.getOWLClass("#Sentence", pm);

            IRI iv1 = IRI.create(baseIRI + "#x");
            SWRLVariable v1 = df.getSWRLVariable(iv1);

            //IRI iv2 = IRI.create(baseIRI + "#y");
            //SWRLVariable v2 = df.getSWRLVariable(iv2);

            Set<SWRLAtom> body = new TreeSet<SWRLAtom>();
            body.add(df.getSWRLClassAtom(DocStruct_doc, v1));

            Set<SWRLAtom> head = new TreeSet<SWRLAtom>();
            head.add(df.getSWRLClassAtom(Gate_word, v1));

            SWRLRule rule = df.getSWRLRule(body, head);
            ont.getOWLOntologyManager().addAxiom(ont, rule);




        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.toString() + " - " + e.getMessage());
            System.out.println(e.toString());
        }
    }


    public void runSWRLAnaphora()
    {
        try
        {

            IRI ih = IRI.create(baseIRI + "#h");
            SWRLVariable vh = df.getSWRLVariable(ih);

            IRI ii = IRI.create(baseIRI + "#i");
            SWRLVariable vi = df.getSWRLVariable(ii);

            IRI iz = IRI.create(baseIRI + "#z");
            SWRLVariable vz = df.getSWRLVariable(iz);

            IRI ix = IRI.create(baseIRI + "#x");
            SWRLVariable vx = df.getSWRLVariable(ix);

            IRI iy = IRI.create(baseIRI + "#y");
            SWRLVariable vy = df.getSWRLVariable(iy);

            IRI ia = IRI.create(baseIRI + "#a");
            SWRLVariable va = df.getSWRLVariable(ia);

            IRI ib = IRI.create(baseIRI + "#b");
            SWRLVariable vb = df.getSWRLVariable(ib);

            IRI ic = IRI.create(baseIRI + "#c");
            SWRLVariable vc = df.getSWRLVariable(ic);

            IRI id = IRI.create(baseIRI + "#d");
            SWRLVariable vd = df.getSWRLVariable(id);

            IRI ie = IRI.create(baseIRI + "#e");
            SWRLVariable ve = df.getSWRLVariable(ie);

            IRI iif = IRI.create(baseIRI + "#f");
            SWRLVariable vf = df.getSWRLVariable(iif);

            IRI ig = IRI.create(baseIRI + "#g");
            SWRLVariable vg = df.getSWRLVariable(ig);

            IRI ij = IRI.create(baseIRI + "#j");
            SWRLVariable vj = df.getSWRLVariable(ij);

            IRI ik = IRI.create(baseIRI + "#k");
            SWRLVariable vk = df.getSWRLVariable(ik);

            IRI il = IRI.create(baseIRI + "#l");
            SWRLVariable vl = df.getSWRLVariable(il);

            Set<SWRLAtom> body = new TreeSet<SWRLAtom>();
            body.add(df.getSWRLClassAtom(DocStruct_doc, vh));
           // body.add(df.getSWRLClassAtom(RhetDev_RhetoricalDevice, vl));
            //SWRLIndividualArgument sia = new SWRLIndividualArgumentImpl(RhetDev_Anaphora);
           // body.add(df.getSWRLClassAtom(RhetDev_Anaphora, vl));
            //body.add(df.getSWRLIndividualArgument(RhetDev_Anaphora, vl));
            body.add(df.getSWRLObjectPropertyAtom(hasParagraph, vh, vi));
            body.add(df.getSWRLObjectPropertyAtom(hasSentence, vi, vz));
            body.add(df.getSWRLClassAtom(Gate_word, vx));
            body.add(df.getSWRLObjectPropertyAtom(hasNextWord, vx, vy));
            body.add(df.getSWRLClassAtom(Gate_sentence, vz));
            body.add(df.getSWRLObjectPropertyAtom(hasFirstWord, vz, vx));
            body.add(df.getSWRLClassAtom(Gate_word, va));
            body.add(df.getSWRLObjectPropertyAtom(hasNextWord, va, vb));
            body.add(df.getSWRLClassAtom(Gate_sentence, vc));
            body.add(df.getSWRLObjectPropertyAtom(hasFirstWord, vc, va));
            body.add(df.getSWRLObjectPropertyAtom(hasNextSentence, vz, vc));
            body.add(df.getSWRLDataPropertyAtom(hasString, vx, vd));
            body.add(df.getSWRLDataPropertyAtom(hasString, vy, ve));
            body.add(df.getSWRLDataPropertyAtom(hasString, va, vf));
            body.add(df.getSWRLDataPropertyAtom(hasString, vb, vg));
            List<SWRLDArgument> ea1 = new ArrayList<SWRLDArgument>(2);
            ea1.add(vd);
            ea1.add(vf);
            body.add(df.getSWRLBuiltInAtom(IRI.create("http://www.w3.org/2003/11/swrlb#equal"), ea1));
            List<SWRLDArgument> ea2 = new ArrayList<SWRLDArgument>(2);
            ea2.add(ve);
            ea2.add(vg);
            body.add(df.getSWRLBuiltInAtom(IRI.create("http://www.w3.org/2003/11/swrlb#equal"), ea2));
            body.add(df.getSWRLDataPropertyAtom(hasStartNode, vx, vj));
            body.add(df.getSWRLDataPropertyAtom(hasEndNode, vb, vk));

            Set<SWRLAtom> head = new TreeSet<SWRLAtom>();
            head.add(df.getSWRLObjectPropertyAtom(hasRhetoricalDevice, vh, vl));
            head.add(df.getSWRLDataPropertyAtom(hasStartNode, vl, vj));
            head.add(df.getSWRLDataPropertyAtom(hasEndNode, vl, vk));

            SWRLRule rule = df.getSWRLRule(body, head);
            ont.getOWLOntologyManager().addAxiom(ont, rule);

        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.toString() + " - " + e.getMessage());
            System.out.println(e.toString());
        }
    }

    public void outputToFile()
    {
        try {
            String u = folderbase + "Outputs" + File.separator + "ont_" + NowD + ".owl";
            File f = new File(u);
            om.saveOntology(ont, IRI.create(f.toURI()));
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.toString() + " - " + e.getMessage());
            System.out.println(e.toString());
        }
    }

    private void setupClasses()
    {
        DocStruct_doc = fac_DocStruct.getOWLClass("#Doc", pm_DocStruct);
        Gate_word = fac_Gate.getOWLClass("#word", pm_Gate);
        Gate_sentence = fac_Gate.getOWLClass("#Sentence", pm_Gate);
        Gate_paragraph = fac_Gate.getOWLClass("#paragraph", pm_Gate);
        RhetDev_RhetoricalDevice = fac_RhetDev.getOWLClass("#RhetoricalDevice", pm_RhetDev);
        RhetDev_Anaphora = fac_RhetDev.getOWLNamedIndividual(":Anaphora", pm_RhetDev);
        hasParagraph = fac_DocStruct.getOWLObjectProperty("#hasParagraph", pm_DocStruct);
        hasSentence = fac_DocStruct.getOWLObjectProperty("#hasSentence", pm_DocStruct);
        hasNextWord = fac_DocStruct.getOWLObjectProperty("#hasNextWord", pm_DocStruct);
        hasNextSentence = fac_DocStruct.getOWLObjectProperty("#hasNextSentence", pm_DocStruct);
        hasString = fac_DocStruct.getOWLDataProperty("#hasString", pm_DocStruct);
        hasStartNode = fac_Gate.getOWLDataProperty("#hasStartNode", pm_Gate);
        hasEndNode = fac_Gate.getOWLDataProperty("#hasEndNode", pm_Gate);
        hasFirstWord = fac_DocStruct.getOWLObjectProperty("#hasFirstWord", pm_DocStruct);
        hasRhetoricalDevice = fac_RhetDev.getOWLObjectProperty("#hasRhetoricalDevice", pm_RhetDev);
    }

    public void printAxioms()
    {
        Set<OWLAxiom> ax = ont_DocStruct.getAxioms();

        for (OWLAxiom a : ax) {
            System.out.println(a.toString());
        }
    }

}

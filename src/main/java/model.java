import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

import java.io.File;
import java.util.List;
import java.util.Set;

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
    OWLOntologyManager om_Gate;
    OWLOntology ont_Gate;
    OWLDataFactory fac_Gate;
    DefaultPrefixManager pm_Gate;
    OWLClass DocStruct_doc;
    OWLClass Gate_word;
    OWLClass Gate_sentence;
    OWLClass Gate_paragraph;

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
            pm = new DefaultPrefixManager(null, null, ont.toString());

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

            setupClasses();

            String DocStruct_base = "http://repositori.com/sw/onto/DocStruct.owl#";
            String Gate_base = "http://repositori.com/sw/onto/gate.owl#";

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


    public void outputToFile()
    {
        try {
            String u = folderbase + "Outputs" + File.separator + "testo_" + NowD + ".owl";
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
    }

    public void printAxioms()
    {
        Set<OWLAxiom> ax = ont_DocStruct.getAxioms();

        for (OWLAxiom a : ax) {
            System.out.println(a.toString());
        }
    }

}

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

import java.io.File;
import java.util.List;
import java.util.Set;

/**
 * Created by co17 on 20/10/2016.
 */
public class model {

    static String folderbase;
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
        else if(type.equals("sentence"))
        {
            r = Gate_sentence;
        }

        return r;
    }


    public model(String fb){

        try {

            folderbase = fb;

            om = OWLManager.createOWLOntologyManager();
            ont = om.createOntology();
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

            //prefix = ont.getOntologyID().getOntologyIRI() + "#";

            String DocStruct_base = "http://repositori.com/sw/onto/DocStruct.owl#";
            String Gate_base = "http://repositori.com/sw/onto/gate.owl#";

        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getStackTrace());
            System.out.println(e.toString());

        }
    }

    public void addIndividual(String onto, String owclass, String value)
    {
        OWLAxiom ax1 = null;
        if(onto.equals("DocStruct"))
        {
            OWLNamedIndividual i = fac_DocStruct.getOWLNamedIndividual("#" + value, pm_DocStruct);
            ax1 = fac_DocStruct.getOWLClassAssertionAxiom(getClassType(owclass), i);
        }
        else if(onto.equals("Gate"))
        {
            OWLNamedIndividual i = fac_Gate.getOWLNamedIndividual("#" + value, pm_Gate);
            ax1 = fac_Gate.getOWLClassAssertionAxiom(getClassType(owclass), i);
        }

        AddAxiom addax1 = new AddAxiom(ont, ax1);
        om.applyChange(addax1);

    }

    public void outputToFile()
    {
        try {
            String u = folderbase + "Outputs" + File.separator + "testo.owl";
            File f = new File(u);
            om.saveOntology(ont_DocStruct, IRI.create(f.toURI()));
        }
        catch(Exception e)
        {
            System.out.println("Error: " + e.getStackTrace());
            System.out.println(e.toString());
        }
    }

    private void setupClasses()
    {
        DocStruct_doc = fac_DocStruct.getOWLClass("#Doc", pm_DocStruct);
        Gate_word = fac_Gate.getOWLClass("#word", pm_DocStruct);
    }

    public void printAxioms()
    {
        Set<OWLAxiom> ax = ont_DocStruct.getAxioms();

        for (OWLAxiom a : ax) {
            System.out.println(a.toString());
        }
    }

}

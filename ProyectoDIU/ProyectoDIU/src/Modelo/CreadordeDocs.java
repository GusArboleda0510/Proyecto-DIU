/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author ANDRES ARBOLEDA
 */
public class CreadordeDocs {

    Document documento;
    File file;
    Node nodoRaiz;
    Element elementoRaiz;

    public CreadordeDocs(File file, String primerNodo) throws SAXException, IOException, ParserConfigurationException {
        this.file = file;

        if (file.exists()) {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            documento = builder.parse(file);
            documento.getDocumentElement().normalize();
            nodoRaiz = documento.getDocumentElement();
            elementoRaiz = (Element) nodoRaiz;
        } else {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            documento = builder.newDocument();
            Element nodoPadre = documento.createElement(primerNodo);
            documento.appendChild(nodoPadre);
            elementoRaiz = nodoPadre;
        }
    }

    public void generarXML(File file, Document documento) throws TransformerConfigurationException, IOException, TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = tf.newTransformer();
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        Source fuente = new DOMSource(documento);
        StreamResult rest;
        if (file.exists()) {
            rest = new StreamResult(file);
        } else {
            FileWriter fw = new FileWriter(file);
            PrintWriter pw = new PrintWriter(fw);
            rest = new StreamResult(pw);
        }
        t.transform(fuente, rest);
    }
    
    public Document getDocumento(){
        return documento;
    }
    public Element getElementoRaiz(){
        return elementoRaiz;
    }

}
